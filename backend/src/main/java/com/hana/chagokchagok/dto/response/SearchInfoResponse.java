package com.hana.chagokchagok.dto.response;


import com.hana.chagokchagok.entity.RealtimeParking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchInfoResponse {
    public SearchInfoResponse(RealtimeParking realtimeParking) {
        this.area = realtimeParking.getParkingInfo().getFullName();
        this.car_no = realtimeParking.getAllocationLog().getCarNo();
        this.entryTime = realtimeParking.getAllocationLog().getEntryTime();
    }
    private String area;
    private String car_no;
    private LocalDateTime entryTime;
}
