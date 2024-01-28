package com.hana.chagokchagok.dto;

import com.hana.chagokchagok.entity.ParkingInfo;
import com.hana.chagokchagok.entity.RealtimeParking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @ToString @Setter @NoArgsConstructor
public class ParkingInfoDto {
    public ParkingInfoDto(RealtimeParking info) {
<<<<<<< PATCH SET (0814e0 :bug: rebase로 인한 merge로 버그 수정)
        this.park_spot = info.getParkingInfo().getFullName();
        this.park_status = info.getAllocationLog() != null;
=======
        this.park_spot = info.getParkingInfo().getParkNo();
        this.park_status = info.getAllocationLog() == null? false : true;
>>>>>>> BASE      (9feb6d :sparkles: 관리자용 자리교환 기능 완성)
    }

    private String park_spot; //주차자리명
    private boolean park_status; //주차여부

}
