package com.hana.chagokchagok.enums;

//신고코드
public enum ErrorCode {
    HUMAN_ERROR,  // 핫라인 : 빌런차량이 잘못주차 하는 인간에러 => 관리자가 자리교환해줌 => 자동으로 solved상태로 report
    SENSOR_ERROR, // 핫라인 : 차단바가 안올라가서 주차가 안되는데요! => 관리자가 차단바 올려줌 => 자동으로 solved상태로 report
    AUTO_REPORT // 출차에러 즉 초음파센서를 이용한 자동신고 시스템 => 직접 해결해야하므로 report시점에 unresolved
}
