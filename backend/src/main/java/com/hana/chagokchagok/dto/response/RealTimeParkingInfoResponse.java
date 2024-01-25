package com.hana.chagokchagok.dto.response;

import com.hana.chagokchagok.dto.ParkingInfoDto;
import com.hana.chagokchagok.entity.RealtimeParking;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

// 현재 주차장 현황이 저장된 DTO
@Getter @ToString @Setter
public class RealTimeParkingInfoResponse {
    public RealTimeParkingInfoResponse(List<RealtimeParking> realtimeParkingInfo) {
        this.total_cnt = realtimeParkingInfo.size();
        this.parks = new ArrayList<>();
        for(RealtimeParking info : realtimeParkingInfo){
            parks.add(new ParkingInfoDto(info));
            if(info.getLog()!=null) this.curr_cnt++;
        }
    }
    private List<ParkingInfoDto> parks; //주차현황
    private int total_cnt; //전체 주차자리 갯수
    private int curr_cnt; //현재 주차된자리 갯수


}
