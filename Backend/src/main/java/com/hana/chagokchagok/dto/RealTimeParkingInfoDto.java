package com.hana.chagokchagok.dto;

import com.hana.chagokchagok.entity.ParkingInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

// 현재 주차장 현황이 저장된 DTO
@Getter @ToString @Setter
public class RealTimeParkingInfoDto {
    public RealTimeParkingInfoDto(List<ParkingInfo> parkingInfos) {
        this.total_cnt = parkingInfos.size();
        this.parks = new ArrayList<>();
        for(ParkingInfo info : parkingInfos){
            parks.add(new ParkingInfoDto(info));
            this.curr_cnt++;
        }
    }
    private List<ParkingInfoDto> parks; //주차현황
    private int total_cnt; //전체 주차자리 갯수
    private int curr_cnt; //현재 주차된자리 갯수


}
