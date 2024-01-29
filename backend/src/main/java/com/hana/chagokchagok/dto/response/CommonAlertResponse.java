package com.hana.chagokchagok.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hana.chagokchagok.dto.ParkingInfoDto;
import com.hana.chagokchagok.dto.ProcessingDetailDto;
import com.hana.chagokchagok.entity.RealtimeParking;
import com.hana.chagokchagok.entity.Report;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter @Setter
public class CommonAlertResponse {
    public CommonAlertResponse(List<RealtimeParking> realtimeParkingInfo, List<Report> reports) {
        this.total_cnt = realtimeParkingInfo.size();
        this.parks = new ArrayList<>();
        for(RealtimeParking info : realtimeParkingInfo){
            parks.add(new ParkingInfoDto(info));
            if(info.getAllocationLog()!=null) this.curr_cnt++;
        }
        this.processingDetails = new ArrayList<>();
        for(Report report : reports){
            processingDetails.add(new ProcessingDetailDto(report));
        }
    }
    private List<ParkingInfoDto> parks; //주차현황
    private int total_cnt; //전체 주차자리 갯수
    private int curr_cnt; //현재 주차된자리 갯수
    @JsonProperty("processing_details") //처리내역
    private List<ProcessingDetailDto> processingDetails;
    
}
