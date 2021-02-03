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
1. 회원이 등급, 포인트 정보를 중간중간 조회한다.

## 비기능적 요구사항

# 체크포인트
https://workflowy.com/s/assessment/qJn45fBdVZn4atl3

# 분석/설계
## AS-IS 조직 (Horizontally-Aligned)
## TO-BE 조직 (Vertically-Aligned)
## EventStorming 결과
### 완성된 1차 모형
### 1차 완성본에 대한 기능적/비기능적 요구사항을 커버하는지 검증
### 헥사고날 아키텍쳐 다이어그램 도출 (Polyglot)

# 구현
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


