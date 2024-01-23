package com.hana.chagokchagok.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//주차장 정보
@Entity
public class ParkingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="park_id")
    private Integer parkId;
    private String parkNo;
    private Boolean isParked;

    @OneToMany(mappedBy = "parkingInfo", cascade = CascadeType.ALL)
    private List<AllocationLog> allocationLogs = new ArrayList<>();

    @OneToMany(mappedBy = "parkingInfo", cascade = CascadeType.ALL)
    private List<Report> reports = new ArrayList<>();
}
