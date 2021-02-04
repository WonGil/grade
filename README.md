# 개인평가 - Photograde  
![image](https://user-images.githubusercontent.com/16534043/106819942-e0b8ed80-66bd-11eb-9d3e-4fccae46677e.png)

# Table of Contents
- [서비스 시나리오](#서비스-시나리오)
- [체크포인트](#체크포인트)
- [분석/설계](#분석/설계)
- [구현](#구현)
- [운영](#운영)

# 서비스 시나리오
## 기능적 요구사항
1. 회원이 사진을 선택하여 등록한다.
1. 사진이 등록되면, 사진을 분석하여 등급(grade)를 매긴다.
1. 사진의 등급이 매겨지면, 회원에게 포인트를 부여한다.
1. 포인트 정보는 회원 정보에 업데이트 된다.
1. 회원이 사진을 삭제할 수 있다.
1. 회원이 사진을 삭제하면 등급도 삭제된다.
1. 등급이 삭제되면 포인트도 삭제된다.
1. 회원이 사진의 등급 정보를 중간중간 조회한다.

## 비기능적 요구사항
1. 트랜젝션
  1. 회원이 사진을 삭제하면, 사진의 등급도 즉시 삭제된다. → Sync 호출
1. 장애격리
  1. 등급 서비스가 정상 기능이 되지 않더라도 사진을 등록할 수 있다. → Async (Event-Driven), Eventual Consistency
  1. 포인트 서비스가 과중되면, 사용자를 잠시동안 받지 않고 주문을 잠시 후에 하도록 유도한다. → Circuit Breacker, Fallback
1. 성능
  1. 회원이 사진의 등급을 사진서비스에서 확인할 수 있어야 한다. → CQRS

# 체크포인트
https://workflowy.com/s/assessment/qJn45fBdVZn4atl3

# 분석/설계
## AS-IS 조직 (Horizontally-Aligned)  
![image](https://user-images.githubusercontent.com/16534043/106823048-95a1d900-66c3-11eb-8ae7-36b9a256b8f7.png)

## TO-BE 조직 (Vertically-Aligned)  
![image](https://user-images.githubusercontent.com/16534043/106823088-a6524f00-66c3-11eb-936c-96465b835b51.png)

## EventStorming 결과
### 완성된 1차 모형  
![image](https://user-images.githubusercontent.com/16534043/106823133-bc600f80-66c3-11eb-803e-28d474edebd6.png)

### 1차 완성본에 대한 기능적/비기능적 요구사항을 커버하는지 검증  
![image](https://user-images.githubusercontent.com/16534043/106825604-95f0a300-66c8-11eb-8c98-c0e7c89d09bb.png)
- 회원이 사진을 선택하여 등록한다. (1 → OK)
- 사진이 등록되면, 사진을 분석하여 등급(grade)를 매긴다. (1, 2 → OK)
- 사진의 등급이 매겨지면, 회원에게 포인트를 부여한다. (1, 2, 3 → OK)
- 포인트 정보는 회원 정보에 업데이트 된다. (4 → OK)
- 회원이 사진을 삭제할 수 있다. (5 → OK)
- 회원이 사진을 삭제하면 등급도 삭제된다. (5, 6 → OK)
- 등급이 삭제되면 포인트도 삭제된다.(5, 6, 7 → OK)
- 회원이 사진의 등급 정보를 중간중간 조회한다. (8 → OK)

### 헥사고날 아키텍쳐 다이어그램 도출 (Polyglot)   
![image](https://user-images.githubusercontent.com/16534043/106826803-fed91a80-66ca-11eb-963b-4787bc39c271.png)
- Inbound adaptor와 Outbound adaptor를 구분함
- 호출관계에서 PubSub 과 Req/Resp 를 구분함
- 서브 도메인과 바운디드 컨텍스트의 분리:  각 팀의 KPI 별로 아래와 같이 관심 구현 스토리를 나눠가짐

# 구현
분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 BC별로 대변되는 마이크로 서비스들을 스프링부트로 구현하였다. 구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다 (각자의 포트넘버는 8081 ~ 8084, 8088 이다)
```
cd photo
mvn spring-boot:run  

cd grade
mvn spring-boot:run

cd point
mvn spring-boot:run 

cd mypage
mvn spring-boot:run  

cd gateway
mvn spring-boot:run  
```

## DDD 의 적용
msaez.io 를 통해 구현한 Aggregate 단위로 Entity 를 선언 후, 구현을 진행하였다.
Entity Pattern 과 Repository Pattern 을 적용하기 위해 Spring Data REST 의 RestRepository 를 적용하였다.
유비쿼터스 랭귀지를 영문으로 사용하였기에, 큰 문제 없이 실행이 되었다.
```java
package photograde;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Photo_table")
public class Photo {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String photoNm;
    private String user;
    private String camera;
    private String gradeId;

    @PrePersist
    public void onPrePersist(){
        PhotoRegistered photoRegistered = new PhotoRegistered();
        BeanUtils.copyProperties(this, photoRegistered);
        photoRegistered.publishAfterCommit();


    }

    @PreRemove
    public void onPreRemove(){
        PhotoDeleted photoDeleted = new PhotoDeleted();
        BeanUtils.copyProperties(this, photoDeleted);
        photoDeleted.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        photograde.external.Grade grade = new photograde.external.Grade();
        // mappings goes here
        PhotoApplication.applicationContext.getBean(photograde.external.GradeService.class)
                .gradeCancel(grade);


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPhotoNm() {
        return photoNm;
    }

    public void setPhotoNm(String photoNm) {
        this.photoNm = photoNm;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }
    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }




}

```
- 적용 후, 서비스들을 실행하고, REST API 테스트를 통하여 정상 동작을 확인할 수 있다.

  - 사진 등록 (photo)  
    ![image](https://user-images.githubusercontent.com/16534043/106830741-04862e80-66d2-11eb-81d0-c426d8d232ab.png)
    
  - 결과 확인 (photo)  
    ![image](https://user-images.githubusercontent.com/16534043/106830782-15cf3b00-66d2-11eb-8d3a-e314f382e759.png)

## Gateway 적용
- API Gateway를 톻아여 마이크로 서비스들의 진입점을 통일하였다.  
```yaml
server:
  port: 8088

---

spring:
  profiles: default
  cloud:
    gateway:
      routes:
        - id: photo
          uri: http://localhost:8081
          predicates:
            - Path=/photos/** 
        - id: grade
          uri: http://localhost:8082
          predicates:
            - Path=/grades/** 
        - id: point
          uri: http://localhost:8083
          predicates:
            - Path=/points/**,/pointCancels/**
        - id: mypage
          uri: http://localhost:8084
          predicates:
            - Path= /mypages/**
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins:
              - "*"
            allowedMethods:
              - "*"
            allowedHeaders:
              - "*"
            allowCredentials: true


---

spring:
  profiles: docker
  cloud:
    gateway:
      routes:
        - id: photo
          uri: http://photo:8080
          predicates:
            - Path=/photos/** 
        - id: grade
          uri: http://grade:8080
          predicates:
            - Path=/grades/** 
        - id: point
          uri: http://point:8080
          predicates:
            - Path=/points/**,/pointCancels/**
        - id: mypage
          uri: http://mypage:8080
          predicates:
            - Path= /mypages/**
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins:
              - "*"
            allowedMethods:
              - "*"
            allowedHeaders:
              - "*"
            allowCredentials: true

server:
  port: 8080

```

## 폴리그랏 퍼시스턴스
- point 서비스의 경우, 단순히 포인트 정보만 유지하면 되기에, NoSQL인 H2 데이터베이스보다 H2SQL 사용이 더 유리하다.
- 따라서, 아래와 같이 point 서비스는 HSQL을 사용해 구현했고, 이를 통해 마이크로 서비스 간 서로 다른 종류의 데이터베이스를 사용해도 문제 없기에 폴리그랏 퍼시스턴스로 구현되었음을 확인할 수 있다.
- point 서비스의 pom.xml  
  ![image](https://user-images.githubusercontent.com/16534043/106832906-e6223200-66d5-11eb-95ce-7d653fb8ba94.png)

## 유비쿼터스 랭귀지
- 실무에서 사용 중인 영어를 유비쿼터스 랭귀지로 지정하여 마이크로 서비스를 구현하였다. 이를 통해 모든 이해관계자들이 의미를 이해하고 사용할 수 있다.
## 동기식 호출(Req/Res 방식)과 Fallback 처리
- 분석/설계 단계에서 "회원이 사진을 삭제하면, 사진의 등급도 즉시 삭제된다." 라는 요구조건을 만족하기 위하여 상호간의 호출은 동기식 호출(Req/Res)로 구현하였다.
- 호출 프로토콜은 이미 앞서 Rest Repository에 의해 노출되어있는 REST서비스를 FeignClient를 이용하여 호출하도록 한다.
```java

package photograde.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="grade", url="${api.grade.url}")
public interface GradeService {

    @RequestMapping(method= RequestMethod.GET, path="/grades")
    public void gradeCancel(@RequestBody Grade grade);

}
```

- 사진이 삭제된 직후(@PreRemove), 등급이 삭제되도록 처리


## 비동기식 호출 (Pub/Sub 방식)
## CQRS

# 운영
## CI/CD 설정
## 오토스케일 아웃
## 무정지 재배포 (Readiness Probe)
## Self-healing (Liveness Probe)
## ConfigMap 적용
## 동기식 호출 / 서킷 브레이킹 / 장애격리
## 모니터링, 앨럿팅


