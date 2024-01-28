package com.hana.chagokchagok.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

//주차장 정보
@Entity
@Getter
public class ParkingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="park_id")
    private Integer parkId;
    @Column(name = "area_code")
    private String areaCode;
    @Column(name="park_no")
    private Integer parkNo;
    @Column(name = "is_disabled")
    private Boolean isDisabled;

    /**
     * parkNo가 areaCode, parkNo로 분리됨에 따라
     * 기존 parkNo 형태로 리턴하는 메소드
     * @author 김용준
     * @return 구역코드 + 자리번호 (ex) A01
     */
    public String getFullName() {
        return this.areaCode + this.parkNo;
    }
}
