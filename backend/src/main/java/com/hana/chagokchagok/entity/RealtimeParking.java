package com.hana.chagokchagok.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//현시각 주차현황테이블
<<<<<<< PATCH SET (0814e0 :bug: rebase로 인한 merge로 버그 수정)
@Entity @Getter @ToString
=======
@Entity @Getter @Setter @ToString
>>>>>>> BASE      (9feb6d :sparkles: 관리자용 자리교환 기능 완성)
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
