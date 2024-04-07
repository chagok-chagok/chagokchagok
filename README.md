# 🚗차곡차곡🚗

> **_주차장에서 돌고 도는 당신을 위한 자동 주차 배정 시스템, 차곡차곡!_**<br>
> 혼잡한 주차장 내부에서 발생하는 다양한 문제(자리가 없음에도 계속 돌아야함, 뒤늦게 온 차가 자리선점)의<br>
> 근본적 해결책을 제시합니다. 이제 더이상 기다리지도, 찾아다니지도 말고 자리를 바로 배정받으세요!

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fchagok-chagok%2Fchagokchagok&count_bg=%2360A0FF&title_bg=%23626262&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

<br><br>
## 🎞 프로젝트 진행기간
**[SSAFY 10기 공통 프로젝트]**<br>
진행기간: `2024.01.08` ~ `2024.02.16`


<br><br>

## 팀 구성
`Backend+Frontend팀`과 `AI+Hardware팀`으로 나누어 구성하였습니다.
|이름| 역할 | 구현내용 |
| :----: | ----- | --------------------- |
| **백성욱**<br>[@b9s2w6](https://github.com/b9s2w6) | Leader <br>  Backend <br>  Infra <br>  Frontend |  JWT 로그인 및 비밀번호 수정 FE/BE 구현 <br> CI/CD(Jenkins,Docker,NginX) <br> Domain연결 <br> 라우터가드 설정 <br> https 구현  |
| **김용준**<br>[@KimYongJun12](https://github.com/KimYongJun12) | Backend <br> Frontend |  자리배정 로직, 구역판별 로직 등 차량관리 FE/BE 구현 <br> 신고 기록 조회 및 수정 FE/BE 구현 <br> Print.js를 이용한 주차확인증 인쇄로직 <br> Gerrit 및 Gitlab Git flow 환경 구축 <br> 시스템 아키텍처, 시퀀스 다이어그램 작성  |
| **최서현**<br>[@cheshireHYUN](https://github.com/cheshireHYUN) | PM <br> Backend Leader <br> Frontend <br> Design |  SSE를 통한 관리자페이지, 키오스크 실시간 업데이트 FE/BE 구현 <br> 대시보드 및 차트 FE/BE 구현 <br> 공통바, 플로팅알림 등 FE 구현 <br> 차번호 검증, 자동신고시스템, 차단바 해제 등 장고 ↔ 스프링간 IoT 통신 API 구현 <br> DB 및 엔티티설계 <br> UI/UX 피그마 디자인 <br> 각종명세 작성  |
| **박수형**<br>[@nastorond](https://github.com/nastorond) | Hardware <br> AI |  시연품(IoT주차장) 제작 <br> 모터, 초음파센서 등 제어 코드 작성 <br> 차량번호판 인식 AI 장고서버 구축&배포  |
| **임태수**<br>[@ts980730](https://github.com/ts980730) | Hardware |  시연품(IoT주차장) 제작 <br> IoT 회로 설계 <br> 모터, 초음파센서 등 제어 코드 작성  |
| **김준호** | FrontEnd |  키오스크(선호주차구역, 만차, 재촬영 등) FE구현  |


<br><br>


## 시스템 아키텍쳐

<img src="./readmeAssets/systemArcitecture2.png" width="500px">

### [API 명세서](https://www.notion.so/API-416d1c418006414d8b8912dfdab97391?pvs=4)


<br><br>


## 💻주요기능
모든 주차자리에 차단바가 설치되어있으며, 입구 키오스크를 통해 차량이 자리를 배정받으면 해당 차량이 입차되었을때만 자리의 차단바가 열리는 시스템입니다.
좀더 자세한 로직은 다음과 같습니다.

### 주차장
(1) 입구 키오스크를 통해 장애 여부 및 선호도에 따라 적합한 자리를 배정해 줍니다.<br>
(2) 입차시 주차장 내의 기존 cctv를 활용, 자동차 번호판을 찍어 AI서버에 전송합니다. <br>
(3) AI서버가 분석한 차량번호를 분석합니다.<br>
(4) 스프링서버는 차량번호가 배정된 자리의 차단바를 해제해달라는 요청을 합니다.<br>
(5) 라즈베리파이가 해당 위치의 차단바를 열어주게 됩니다.<br>

<br>

### 관리자
(1) SSE를 통해 실시간으로 주차 배정 현황을 업데이트 하며, 차단바 제어요청도 가능합니다.<br>
(2) 인식오류로 인한 다양한 예외(출차된 자리에 차량이 감지될경우 등) 자동으로 인식, 관리자에게 신고되는 업무자동화를 지원합니다.<br>
<br><br>


## 🖼서비스 화면
<br>

### kiosk

|                               메인                               |                               차량인식                                |                                자리선택                                |                            주차자리 배정                            |
| :--------------------------------------------------------------: | :-------------------------------------------------------------------: | :--------------------------------------------------------------------: | :-----------------------------------------------------------------: |
| <img src="./readmeAssets/kiosk/kiosk-choice.png" height="500px"> | <img src="./readmeAssets/kiosk/kiosk-recog-image.png" height="500px"> | <img src="./readmeAssets/kiosk/kiosk-choice-place.png" height="500px"> | <img src="./readmeAssets/kiosk/kiosk-get-place.png" height="500px"> |

|                         번호 인식 에러                          |                             자리없음                              |
| :-------------------------------------------------------------: | :---------------------------------------------------------------: |
| <img src="./readmeAssets/kiosk/kiosk-error.png" height="500px"> | <img src="./readmeAssets/kiosk/kiosk-noplace.png" height="500px"> |
<br>

### admin

|                             로그인                             |                              대쉬보드                              |                              신고기록                              |
| :------------------------------------------------------------: | :----------------------------------------------------------------: | :----------------------------------------------------------------: |
| <img src="./readmeAssets/admin/admin-login.png" width="500px"> | <img src="./readmeAssets/admin/admin-dashboard.png" width="500px"> | <img src="./readmeAssets/admin/admin-reportlog.png" width="500px"> |

|                                차량현황                                 |                                자리변경                                 |                              자리 차단바 해제                               |
| :---------------------------------------------------------------------: | :---------------------------------------------------------------------: | :-------------------------------------------------------------------------: |
| <img src="./readmeAssets/admin/admin-curr-situation.png" width="500px"> | <img src="./readmeAssets/admin/admin-chagne-section.png" width="500px"> | <img src="./readmeAssets/admin/admin-deactivate-section.png" width="500px"> |
<br><br>

### 시연품 - IoT주차장

<img src="./readmeAssets/parkinglot.jpg" width="500px">
<br><br>


### ERD

<img src="./readmeAssets/ERDiagram.png" width="500px">
<br><br>


## 🛠기술스택

### BackEnd

<img src="https://img.shields.io/badge/JAVA-3776AB?style=for-the-badge&logo=JAVA&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Django-092E20?style=for-the-badge&logo=Django&logoColor=white">&nbsp;

- Java JDK 21
- SpringBoot 3.2.2
- JPA
- Gradle
- MySQL 8.0.35
- Django

### FrontEnd

<img src="https://img.shields.io/badge/Vue.js-4FC08D?style=for-the-badge&logo=Vue.js&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=Node.js&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=Axios&logoColor=white">&nbsp;

- Vue
- Node.js
- javascript
- Axios

### HardWare

<img src="https://img.shields.io/badge/Raspberry Pi-A22846?style=for-the-badge&logo=Raspberry Pi&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Arduino-00878F?style=for-the-badge&logo=Ariduino&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=Python&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/AutoCAD-E51050?style=for-the-badge&logo=AutoCAD&logoColor=white">&nbsp;

- Raspberry Pi4 B 6.1
- Arduino IDE 2.2.1
- Python 3.9.13
- AutoCAD

### CI/CD

<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=Ubuntu&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=NGINX&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white">&nbsp;

- AWS EC2
- Ubuntu 20.04 LTS
- nginx
- Docker
- Jenkins

### 협업 툴

<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jirasoftware&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/figma-EA4335?style=for-the-badge&logo=figma&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/gerrit-EEEEEE?style=for-the-badge&logo=gerrit&logoColor=black">&nbsp;

- Git
- Jira
- Mattermost, Notion
- Figma
- gerrit

## 프로젝트 파일 구조

<details>
<summary>backend</summary>
<div markdown="1">

```
📦 backend
│  📂 bin
│  │  📂 main
│  │     📜 application-production.properties
│  │     📜 application.properties
│  │     📂 static
│  │        📜 admin.html
│  │        📜 admin.js
│  │        📜 kiosk.html
│  │        📜 kiosk.js
│  📜 build.gradle
│  📜 dummy.txt
│  📂 gradle
│  │  📂 wrapper
│  │     📜 gradle-wrapper.jar
│  │     📜 gradle-wrapper.properties
│  📜 gradlew
│  📜 gradlew.bat
│  📜 settings.gradle
│  📂 src
│     📂 main
│     │  📂 java
│     │  │  📂 com
│     │  │     📂 hana
│     │  │        📂 chagokchagok
│     │  │           📜 ChagokchagokApplication.java
│     │  │           📂 contoller
│     │  │           │  📜 AdminController.java
│     │  │           │  📜 GuestController.java
│     │  │           │  📜 ParkController.java
│     │  │           │  📜 SseController.java
│     │  │           📂 dto
│     │  │           │  📜 AllocationDto.java
│     │  │           │  📜 ErrorDto.java
│     │  │           │  📜 ParkingInfoDto.java
│     │  │           │  📜 ProcessingDetailDto.java
│     │  │           │  📜 ReportDataDto.java
│     │  │           │  📜 ReportDto.java
│     │  │           │  📜 ReportRateDto.java
│     │  │           │  📂 request
│     │  │           │  │  📜 AllocateCarRequest.java
│     │  │           │  │  📜 CarNumRequest.java
│     │  │           │  │  📜 ChangePasswordRequest.java
│     │  │           │  │  📜 ExchangeRequest.java
│     │  │           │  │  📜 GetCarlocRequest.java
│     │  │           │  │  📜 LoginRequest.java
│     │  │           │  │  📜 LogoutRequest.java
│     │  │           │  │  📜 OpenBarRequest.java
│     │  │           │  │  📜 PullOutRequest.java
│     │  │           │  │  📜 ReportRequest.java
│     │  │           │  │  📜 SearchInfoRequest.java
│     │  │           │  │  📜 ValidateAreaRequest.java
│     │  │           │  📂 response
│     │  │           │  │  📜 AllocateCarResponse.java
│     │  │           │  │  📜 CarNumValidationDto.java
│     │  │           │  │  📜 ChangePasswordDto.java
│     │  │           │  │  📜 CommonAlertResponse.java
│     │  │           │  │  📜 DashBoardResponse.java
│     │  │           │  │  📜 GetCarlocResponse.java
│     │  │           │  │  📜 LoginResponse.java
│     │  │           │  │  📜 LogoutResponse.java
│     │  │           │  │  📜 RealtimeCarsResponse.java
│     │  │           │  │  📜 RealTimeParkingInfoResponse.java
│     │  │           │  │  📜 RefreshTokenResponse.java
│     │  │           │  │  📜 ReportResponse.java
│     │  │           │  │  📜 SearchInfoResponse.java
│     │  │           │  │  📜 ValidateAreaResponse.java
│     │  │           │  📜 UnresolvedDto.java
│     │  │           │  📜 ValidationParkingInfoDto.java
│     │  │           📂 entity
│     │  │           │  📜 Admin.java
│     │  │           │  📜 AllocationLog.java
│     │  │           │  📜 ParkingInfo.java
│     │  │           │  📜 RealtimeParking.java
│     │  │           │  📜 Report.java
│     │  │           📂 enums
│     │  │           │  📜 ErrorCode.java
│     │  │           │  📜 ReportStatus.java
│     │  │           │  📜 SearchType.java
│     │  │           │  📜 SseStatus.java
│     │  │           📂 exception
│     │  │           │  📜 CustomException.java
│     │  │           │  📜 ErrorResponse.java
│     │  │           │  📜 ErrorType.java
│     │  │           │  📜 InvalidInputException.java
│     │  │           │  📜 SpotNotEmptyException.java
│     │  │           │  📜 SseEmitterIsNullException.java
│     │  │           │  📜 UnAuthorizedException.java
│     │  │           📂 repository
│     │  │           │  📜 AdminRepository.java
│     │  │           │  📜 AllocationLogRepository.java
│     │  │           │  📜 ParkingInfoRepository.java
│     │  │           │  📜 RealtimeParkingRepository.java
│     │  │           │  📜 ReportRepository.java
│     │  │           📂 service
│     │  │           │  📜 AdminService.java
│     │  │           │  📜 DjangoClient.java
│     │  │           │  📜 FeignService.java
│     │  │           │  📜 GuestService.java
│     │  │           │  📜 ParkService.java
│     │  │           │  📜 SseService.java
│     │  │           📂 util
│     │  │              📜 DjangoClientConfig.java
│     │  │              📜 GlobalExceptionHandler.java
│     │  │              📜 JwtInterceptor.java
│     │  │              📜 JWTUtil.java
│     │  │              📜 P6SpyFomatter.java
│     │  │              📜 SeparateLocation.java
│     │  │              📜 SHA256.java
│     │  │              📜 ValidateCarNum.java
│     │  │              📜 WebConfiguration.java
│     │  📂 resources
│     │     📜 application-production.properties
│     │     📜 application.properties
│     │     📜 password.properties
│     │     📂 static
│     │        📜 admin.html
│     │        📜 admin.js
│     │        📜 kiosk.html
│     │        📜 kiosk.js
│     📂 test
│        📂 java
│           📂 com
│              📂 hana
│                 📂 chagokchagok
│                    📜 ChagokchagokApplicationTests.java

```

</div>
</details>

<details>
<summary>frontend</summary>
<div markdown="1">

```
📦 frontend
│  📂 chagokchagok
│  │  📜 .env
│  │  📜 index.html
│  │  📜 jsconfig.json
│  │  📜 package-lock.json
│  │  📜 package.json
│  │  📂 public
│  │  │  📜 favicon.ico
│  │  │  📂 icon
│  │  │     📜 BarChart.png
│  │  │     📜 Doorbell.png
│  │  │     📜 hotline.png
│  │  │     📜 sensor.png
│  │  📜 README.md
│  │  📂 src
│  │  │  📂 api
│  │  │  │  📜 admin.js
│  │  │  📜 App.vue
│  │  │  📂 assets
│  │  │  │  📜 base.css
│  │  │  │  📜 disabled_icon.png
│  │  │  │  📜 exchange_car_image.png
│  │  │  │  📜 favicon.ico
│  │  │  │  📜 font.css
│  │  │  │  📜 logo.svg
│  │  │  │  📜 main.css
│  │  │  │  📜 NotoSansKR-Regular.ttf
│  │  │  │  📜 unlock_bar.png
│  │  │  │  📜 주차안내 이미지 1.png
│  │  │  │  📜 주차안내 이미지 2.png
│  │  │  │  📜 주차안내 이미지 3.png
│  │  │  │  📜 주차안내 이미지 4.png
│  │  │  │  📜 주차확인증.png
│  │  │  📂 components
│  │  │  │  📂 admin
│  │  │  │  │  📜 AppCommonBar.vue
│  │  │  │  │  📜 AppFloatingAlert.vue
│  │  │  │  │  📜 AppPageHeader.vue
│  │  │  │  │  📂 modal
│  │  │  │  │  │  📜 HomeView.vue
│  │  │  │  │  │  📜 ModalBar.vue
│  │  │  │  │  │  📜 ModalChange.vue
│  │  │  │  │  │  📜 ModalDetailReport.vue
│  │  │  │  │  📜 navbar.vue
│  │  │  │  │  📂 report
│  │  │  │  │  │  📜 ReportCountBar.vue
│  │  │  │  │  │  📜 ReportList.vue
│  │  │  │  │  📜 ReportChart.vue
│  │  │  │  │  📜 Search.vue
│  │  │  │  │  📜 Tooltip2.vue
│  │  │  │  📂 guest
│  │  │  │  │  📜 dummy.txt
│  │  │  │  📜 HelloWorld.vue
│  │  │  │  📂 icons
│  │  │  │  │  📜 CarGuest.vue
│  │  │  │  │  📜 IconCommunity.vue
│  │  │  │  │  📜 IconDocumentation.vue
│  │  │  │  │  📜 IconEcosystem.vue
│  │  │  │  │  📜 IconExit.vue
│  │  │  │  │  📜 IconMainBlue.vue
│  │  │  │  │  📜 IconMainWhtite.vue
│  │  │  │  │  📜 IconSupport.vue
│  │  │  │  │  📜 IconTooling.vue
│  │  │  │  │  📜 MdiIcon.vue
│  │  │  │  │  📜 MdiIconButton.vue
│  │  │  │  │  📜 QuestionBubble.vue
│  │  │  │  📂 kiosk
│  │  │  │  │  📜 dummy.txt
│  │  │  │  📂 layout
│  │  │  │  │  📜 AppLocation.vue
│  │  │  │  │  📜 AppLocationGuest.vue
│  │  │  │  │  📜 AppPagination.vue
│  │  │  │  │  📜 AppSection.vue
│  │  │  │  │  📜 AppSectionGuest.vue
│  │  │  │  │  📜 AppSectionMiddle.vue
│  │  │  │  │  📜 AppSectionMiddleGuest.vue
│  │  │  │  │  📜 ReportCountCard.vue
│  │  │  │  📂 main
│  │  │  │     📜 ChangePassword.vue
│  │  │  │     📜 LoginPage.vue
│  │  │  │     📜 test.vue
│  │  │  📜 main.js
│  │  │  📂 plugins
│  │  │  │  📜 webfontloader.js
│  │  │  📂 router
│  │  │  │  📜 index.js
│  │  │  📂 stores
│  │  │  │  📜 admin.js
│  │  │  │  📜 alert.js
│  │  │  │  📜 dashboard.js
│  │  │  │  📜 guest.js
│  │  │  │  📜 parkingSectionStore.js
│  │  │  │  📜 parkingStore.js
│  │  │  │  📜 report.js
│  │  │  📂 utils
│  │  │  │  📜 http-status.js
│  │  │  │  📜 mainAxios.js
│  │  │  📂 views
│  │  │     📂 admin
│  │  │     │  📜 DashBoard.vue
│  │  │     │  📜 ParkingSection.vue
│  │  │     📜 Admin.vue
│  │  │     📂 guest
│  │  │     │  📜 dummy.txt
│  │  │     │  📜 GuestView.vue
│  │  │     📜 HomeView.vue
│  │  │     📂 kiosk
│  │  │     │  📜 AllocationScreen.vue
│  │  │     │  📜 ChoiceScreen.vue
│  │  │     │  📜 NoplaceScreen.vue
│  │  │     │  📜 Print.vue
│  │  │     │  📜 RecognitionErrorScreen.vue
│  │  │     │  📜 RecommendationScreen.vue
│  │  │     📜 Main.vue
│  │  📜 vite.config.js
│  📜 package-lock.json
```

</div>
</details>

<details>
<summary>hardware</summary>
<div markdown="1">

```
📦 hardware
│  📂 client
│  │  📜 arduino.py
│  │  📜 clinet.py
│  │  📜 entrance_client.py
│  │  📜 exit_hall_client.py
│  │  📂 image
│  │  │  📜 image1.jpg
│  │  │  📜 image2.jpg
│  │  │  📜 image3.jpg
│  │  │  📜 image4.jpg
│  │  │  📜 image5.jpg
│  │  │  📜 image_io.jpg
│  │  📜 requirements.txt
│  📂 server
│     📂 aiserver
│     │  📜 asgi.py
│     │  📜 settings.py
│     │  📜 urls.py
│     │  📜 wsgi.py
│     │  📜 __init__.py
│     📂 apiv1
│     │  📜 admin.py
│     │  📜 apps.py
│     │  📂 migrations
│     │  │  📜 __init__.py
│     │  📜 models.py
│     │  📜 tests.py
│     │  📜 urls.py
│     │  📜 utils.py
│     │  📜 views.py
│     │  📜 __init__.py
│     📜 Dockerfile
│     📜 image.png
│     📜 lp_det.pt
│     📂 lp_models
│     │  📂 models
│     │  │  📜 best_acc.pth
│     │  │  📜 craft_mlt_25k.pth
│     │  📂 user_network
│     │     📜 best_acc.py
│     │     📜 best_acc.yaml
│     │     📜 transformation.py
│     📜 manage.py
│     📜 requirements.txt
│     📜 server-requirements.txt
│     📜 SpoqaHanSansNeo-Light.ttf
│     📜 yolov5s.pt
```

</div>
</details>


### **팀의 코드 컨벤션**

[커밋 컨벤션](./conventions/commit_convention.md)

[[BE] 코드컨벤션](./conventions/backend_convention.md)

[[FE] 코드컨벤션](./conventions/frontend_convention.md)

[[H/W] 코드컨벤션](./conventions/hw_convention.md)
