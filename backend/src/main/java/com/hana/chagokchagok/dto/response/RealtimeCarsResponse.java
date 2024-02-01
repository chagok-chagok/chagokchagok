package com.hana.chagokchagok.dto.response;


import com.hana.chagokchagok.entity.RealtimeParking;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
public class RealtimeCarsResponse {
    List<String> cars;

    public RealtimeCarsResponse(List<RealtimeParking> RealtimeParkings) {
        this.cars = new ArrayList<>();
        for(RealtimeParking realtimeParking : RealtimeParkings){
            if(realtimeParking.getAllocationLog() != null){
                cars.add(realtimeParking.getAllocationLog().getCarNo());
            }
        }
    }
}
