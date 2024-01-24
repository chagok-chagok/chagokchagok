package com.hana.chagokchagok.dto;

import com.hana.chagokchagok.entity.ParkingInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @ToString @Setter @NoArgsConstructor
public class ParkingInfoDto {
    public ParkingInfoDto(ParkingInfo info) {
        this.park_spot = info.getParkNo();
        //this.park_status = info.getIsParked();
    }

    private String park_spot; //주차자리명
    private boolean park_status; //주차여부

}
