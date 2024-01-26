package com.hana.chagokchagok.dto;

import com.hana.chagokchagok.entity.ParkingInfo;
import com.hana.chagokchagok.entity.RealtimeParking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
// AllocationLog Entity의 정적 팩토리 메소드에 들어갈 DTO
public class AllocationDto {
    public AllocationDto(RealtimeParking realtimeParking, String carNo) {
        this.parkingInfo = realtimeParking.getParkingInfo();
        this.carNo = carNo;
        this.entryTime = LocalDateTime.now();

    }

    private ParkingInfo parkingInfo;
    private String carNo;
    private LocalDateTime entryTime;
}
