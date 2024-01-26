package com.hana.chagokchagok.dto.response;

import com.hana.chagokchagok.entity.AllocationLog;
import com.hana.chagokchagok.entity.RealtimeParking;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class AllocateCarResponse {
    public AllocateCarResponse(RealtimeParking realtimeParking, AllocationLog allocationLog) {
        this.allocatedLocation = realtimeParking.getParkingInfo().getParkNo();
        this.carNo = allocationLog.getCarNo();
        this.entryTime = allocationLog.getEntryTime();
    }
    private String carNo; // 차 번호
    private String allocatedLocation; // 배정된 자리
    private LocalDateTime entryTime; // 입차 시간
}
