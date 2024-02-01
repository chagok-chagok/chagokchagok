package com.hana.chagokchagok.enums;

public enum SseStatus {
    //키오스크
    CONGESTION_CLEAR, //만차 해소
    VALID_CAR_NUM, //차번호 제대로 인식
    INVALID_CAR_NUM, //차번호 오인식

    //연결관리
    ERROR,
    OPEN,

    //관리자
    SENSOR_REPORT, //자동신고시스템
    REALTIME_COMMON //차량입출차나 신고처리 변화 -> 공통바 변화

}
