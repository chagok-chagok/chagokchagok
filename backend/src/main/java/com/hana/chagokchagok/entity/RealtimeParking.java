package com.hana.chagokchagok.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

//현시각 주차현황테이블
@Entity @Getter
@Setter // Setter를 사용하지 않고 필드값을 update하는 법을 모르겠다...
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

    public void deleteAllocationLog(){
        this.allocationLog = null;
    }

    public void changeAllocationLog(AllocationLog targetAllocationLog) {
        this.allocationLog = targetAllocationLog;
    }

}
