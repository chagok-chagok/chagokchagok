package com.hana.chagokchagok.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//현시각 주차현황테이블
@Entity @Getter @Setter @ToString
public class RealtimeParking {

    @Id
    @Column(name="park_id")
    private Integer parkId;

    @OneToOne
    @MapsId //id로 지정한 컬럼에 @OneToOne을 매핑
    @JoinColumn(name="park_id")
    private ParkingInfo parkingInfo;

    @OneToOne
    @JoinColumn(name = "allocation_id") //null이면 빈자리
    private AllocationLog allocationLog;


    public void changeAllocationLog(AllocationLog targetAllocationLog) {
        this.allocationLog = targetAllocationLog;
    }

}
