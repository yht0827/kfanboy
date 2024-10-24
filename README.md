# kfanboy

## 🚀 프로젝트 개요
- `kfanboy`는 K-pop 관련하여 팬 커뮤니티 플랫폼 프로젝트입니다. 
- UI는 카카오 오븐으로 대체하여 프론트엔드 부분은 생략하고 벡엔드에 초점을 맞춰 `백엔드 개발`에 주력했습니다.
- 기능 구현하는 것에만 그치지 않고 어떻게 하면 `더 많은 트래픽`을 견딜 수 있을지 고민하며 개발했습니다. 
- 객체 지향 설계 원칙을 최대한 준수하며 코드의 `재사용성` 및 `유지 보수성`을 높일 수 있도록 노력했습니다.

## 🔹 프로젝트 고려 사항
- `대규모 트래픽` 시 발생하는 `동시성`, `트랜잭션` 등의 문제를 고려하여 안정적인 서비스 방법을 고려해보았습니다.
- 나쁜 코드에 대해 지속적으로 `코드 리팩토링` 진행하였습니다.
- 대규모 데이터 검색 시 `성능 개선` 방법 고려해보았습니다.
- 서비스의 규모가 커지고 `트래픽이 증가`할 때 어떻게 `분산하고 관리`할 수 있는지 고민하며 개발하였습니다.
- 다량의 요청 상황에서 병목현상을 찾아보고 원인을 찾아 해결해보는 경험을 하기 위해서 `부하 테스트`를 진행하고 내부의 자원 상황을 모니터링해보았습니다.
- `객체지향적으로 코드`를 작성할 수 있는 방법을 탐구하였습니다.
- Github Action을 이용해서 `CI/CD 환경`을 구축해 보았습니다.

## 📕 사용 기술
- Spring Boot
- Java 17
- Gradle
- Nginx
- Naver Cloud Platform
- Docker
- JPA
- MYSQL
- Redis
- ELK

## ☁️ Architecture
![image](https://github.com/user-attachments/assets/30f23db5-eb86-4716-929d-dc92e6868666)


## 📝 WIKI
- [UseCase](https://github.com/yht0827/kfanboy/wiki/0.-UseCase)
- [Project Rules](https://github.com/yht0827/kfanboy/wiki/2.-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EA%B7%9C%EC%B9%99)
- [Prototype](https://github.com/yht0827/kfanboy/wiki/3.-%ED%99%94%EB%A9%B4-%EC%84%A4%EA%B3%84)
- [ERD](https://github.com/yht0827/kfanboy/wiki/1.-ERD-%EA%B5%AC%EC%A1%B0)
- [API Specification](https://github.com/yht0827/kfanboy/wiki/4.-API-%EB%AA%85%EC%84%B8%EC%84%9C)
- [Architecture](https://github.com/yht0827/kfanboy/wiki/5.-Architecture)
- [Issue](https://github.com/yht0827/kfanboy/wiki/6.-%EC%9D%B4%EC%8A%88-%ED%95%B4%EA%B2%B0-%EA%B4%80%EB%A0%A8-%ED%8F%AC%EC%8A%A4%ED%8C%85)

