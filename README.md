# Ala24 서점

## 목차
1. 프로젝트 소개
2. 도메인 모델
3. 기술 스택
4. 구현 기능
5. 배운 점 및 아쉬운 점
6. 개선 사항

## 1. 프로젝트 소개
인터넷 도서 구매 웹사이트의 동작 방식을 구현한 개인 사이드 프로젝트 입니다.

### 만들게 된 계기
- 지금까지 배운 내용들을 종합적으로 구현을 해보고 싶어서 만들었습니다.

## 2. 도메인 모델
<img width="946" alt="image" src="https://user-images.githubusercontent.com/83010167/166148607-28914a6a-ab73-4097-b1c0-e878957ede69.png">

## 3. 기술 스택
- FrontEnd
  - BootStrap
- BackEnd
  - Spring Boot, JPA, Spring Data JPA, Querydsl, Thymeleaf H2(개발용), MySql(운영용)
  
## 4. 구현 기능
1. 회원 관련
    - 등록 <br/>
    ![회원가입 mp4](https://user-images.githubusercontent.com/83010167/167323204-e4f316de-18d5-491c-87b4-88b4fe9c005e.gif)

    - 조회 <br/>
    ![회원조회 mp4](https://user-images.githubusercontent.com/83010167/167323324-6c9f1504-8215-4618-b2e0-c20d1d03f421.gif)
    - 계좌 관리 <br/>
![계좌관리 mp4](https://user-images.githubusercontent.com/83010167/167323304-4176689d-0941-4f7c-8945-764de74e9a4.gif)

    - 검색 <br/>
    ![회원검색 mp4](https://user-images.githubusercontent.com/83010167/167323339-53c53682-a83d-43c3-936c-b008e254d384.gif)
3.gif)
    - 로그인 및 로그아웃 <br/>
    ![로그인 mp4](https://user-images.githubusercontent.com/83010167/167323326-930173c0-fd07-49df-95f1-2c6af5087182.gif)
![로그아웃 mp4](https://user-images.githubusercontent.com/83010167/167323328-2c8fdccb-f6e2-4356-8ef7-d8d9a2290eee.gif)
2. 상품 관련
    - 등록 <br/>

![상품 등록 mp4](https://user-images.githubusercontent.com/83010167/167323317-e8c45413-b863-453b-af7a-44982bd91b07.gif)
    - 수정 <br/>
    ![상품수정 mp4](https://user-images.githubusercontent.com/83010167/167324396-7844d06b-de94-47b2-b8aa-413d3370a79d.gif)

    - 조회 <br/>

![상품조회 mp4](https://user-images.githubusercontent.com/83010167/167323323-02d49c2d-7f4a-459b-80ff-f9e1f257f4ee.gif)
3. 주문 관련
    - 주문 <br/>
    ![상품주문1 mp4](https://user-images.githubusercontent.com/83010167/167323313-32dc6dd0-4d38-448e-a630-c64591a07004.gif)
![상품주문 mp4](https://user-images.githubusercontent.com/83010167/167323322-a0a03cfe-14b8-440a-be79-a51a5931bf59.gif)
    - 검색 <br/>
    ![주문검색 mp4](https://user-images.githubusercontent.com/83010167/167323312-170d5588-db5f-437d-a43a-a313489b93a0.gif)
    - 주문 취소 <br/>
    ![주문취소 mp4](https://user-images.githubusercontent.com/83010167/167323319-6f796a25-3961-4de5-b7db-8d4a7b58399e.gif)
    - 조회 <br/>
4. 기타
    - 회원 및 회원의 권한 별 화면 구성
    - 페이징
    - 검색 및 정렬<br/>
  ![검색및 페이징 mp4](https://user-images.githubusercontent.com/83010167/167323332-40cd1e11-90bd-4f37-8930-af477a32b415.gif)

## 5. 배운점 및 아쉬운점
- 배운점
  - 각각의 기능들이 유기적으로 연결하는 방법
  - 오류가 발생했을 때 대처법
- 아쉬운점
  - 기능 구현이 덜 된 점 ex) 장바구니
  - 화면 구성의 아쉬움

## 6. 개선사항
- javascript를 배워서 화면을 좀 더 사용자 친화적으로 바꿀 것.
- 다수의 물건을 구매할 수 있는 장바구니 기능이 추가되었으면 좋을 것 같다.
- 실제로 배포까지 해보는 것
