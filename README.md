# README.md

### 서비스 소개

> 주차장에서 돌고 도는 당신을 위한 자동 주차 배정 시스템,
> 🚗차곡차곡🚗

## 팀 구성

|                                        백성욱                                        |                                         김용준                                         |                               <center>김준호</center>                                |                                         박수형                                         |                                         임태수                                         |                                          최서현                                          |
| :----------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------: | :--------------------------------------------------------------------------------------: |
| <img src="/uploads/1117df801a93b036eb6c238b97b1cf3b/su.PNG" width="42" height="42"/> | <img src="/uploads/6b485c5c0918ce88b75b250e57ac26a6/용준.PNG" width="42" height="42"/> | <img src="/uploads/926229d301d510473b0398a02260bc6b/jh.PNG" width="42" height="42"/> | <img src="/uploads/c816b7a71ad45a343914ac36fe4c8116/수형.PNG" width="42" height="42"/> | <img src="/uploads/1dccedefbf9da4ed6b3731e45ecaa9d2/태수.PNG" width="42" height="42"/> | <img src="/uploads/01d4f0e6ee89d1c7804d0a5507a54826/사장님.PNG" width="42" height="42"/> |
|                                     팀장 <br> BE                                     |                                           BE                                           |                                  FE팀장 <br> UX/UI                                   |                                         HW팀장                                         |                                           HW                                           |                                    BE팀장 <br> UX/UI                                     |

## kiosk

|                               메인                               |                               차량인식                                |                                자리선택                                |                            주차자리 배정                            |
| :--------------------------------------------------------------: | :-------------------------------------------------------------------: | :--------------------------------------------------------------------: | :-----------------------------------------------------------------: |
| <img src="./readmeAssets/kiosk/kiosk-choice.png" height="500px"> | <img src="./readmeAssets/kiosk/kiosk-recog-image.png" height="500px"> | <img src="./readmeAssets/kiosk/kiosk-choice-place.png" height="500px"> | <img src="./readmeAssets/kiosk/kiosk-get-place.png" height="500px"> |

|                         번호 인식 에러                          |                             자리없음                              |
| :-------------------------------------------------------------: | :---------------------------------------------------------------: |
| <img src="./readmeAssets/kiosk/kiosk-error.png" height="500px"> | <img src="./readmeAssets/kiosk/kiosk-noplace.png" height="500px"> |

## admin

|                             로그인                             |                              대쉬보드                              |                              신고기록                              |
| :------------------------------------------------------------: | :----------------------------------------------------------------: | :----------------------------------------------------------------: |
| <img src="./readmeAssets/admin/admin-login.png" width="500px"> | <img src="./readmeAssets/admin/admin-dashboard.png" width="500px"> | <img src="./readmeAssets/admin/admin-reportlog.png" width="500px"> |

|                                차량현황                                 |                                자리변경                                 |                              자리 차단바 해제                               |
| :---------------------------------------------------------------------: | :---------------------------------------------------------------------: | :-------------------------------------------------------------------------: |
| <img src="./readmeAssets/admin/admin-curr-situation.png" width="500px"> | <img src="./readmeAssets/admin/admin-chagne-section.png" width="500px"> | <img src="./readmeAssets/admin/admin-deactivate-section.png" width="500px"> |

## ERD

<img src="./readmeAssets/ERDiagram.png" width="500px">

### 엔티티 구조

**(1) 주차장 ↔ 주차현황**

- 일대일 단방향 연관관계 (주차현황쪽이 외래키를 가짐)

**(2) 주차현황 ↔ 입출차기록**

- 일대일 단방향 연관관계 (주차현황쪽이 외래키를 가짐)

**(3) 주차장 ↔ 입출차기록**

- 일대다 단방향 연관관계 (입출차기록쪽이 외래키, 즉 다)

**(4) 주차장 ↔ 신고**

- 일대다 단방향 연관관계 (신고기록쪽이 외래키, 즉 다)

## 시스템 아키텍쳐

<img src="./readmeAssets/systemArcitecture.png" width="500px">

### [API 명세서](https://www.notion.so/API-416d1c418006414d8b8912dfdab97391?pvs=4)

### **팀의 코드 컨벤션**

[커밋 컨벤션](./conventions/commit_convention.md)

[[BE] 코드컨벤션](./conventions/backend_convention.md)

[[FE] 코드컨벤션](./conventions/frontend_convention.md)

[[H/W] 코드컨벤션](./conventions/hw_convention.md)
