package com.hana.chagokchagok.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hana.chagokchagok.entity.AllocationLog;
import com.hana.chagokchagok.entity.RealtimeParking;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AllocateCarResponse {
    public AllocateCarResponse(RealtimeParking realtimeParking, AllocationLog allocationLog) {
        this.allocatedLocation = realtimeParking.getParkingInfo().getFullName();
        this.carNo = allocationLog.getCarNo();
        this.entryTime = allocationLog.getEntryTime();
    }
    @JsonProperty("car_no")
    private String carNo; // 차 번호
    @JsonProperty("allocated_location")
    private String allocatedLocation; // 배정된 자리
    @JsonProperty("entry_time")
    private LocalDateTime entryTime; // 입차 시간
}
