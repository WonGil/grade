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

```
cd recipe
mvn spring-boot:run  

cd order
mvn spring-boot:run

cd delivery
mvn spring-boot:run 

cd mypage
mvn spring-boot:run  

cd gateway
mvn spring-boot:run  
```

## DDD 의 적용
## Gateway 적용
## 폴리그랏 퍼시스턴스
## 유비쿼터스 랭귀지
## 동기식 호출(Req/Res 방식)과 Fallback 처리
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


