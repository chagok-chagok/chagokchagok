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

    @Column(name="park_no")
    private String parkNo;
    @Column(name = "is_disabled")
    private Boolean isDisabled;
}
