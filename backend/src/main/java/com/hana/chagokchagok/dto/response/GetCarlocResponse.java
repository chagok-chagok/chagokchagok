package com.hana.chagokchagok.dto.response;

import com.hana.chagokchagok.entity.AllocationLog;
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
public class GetCarlocResponse {
    public GetCarlocResponse(AllocationLog allocationLog) {
        this.carNo = allocationLog.getCarNo();
        this.entryTime = allocationLog.getEntryTime();
    }
    private String carNo;
    private LocalDateTime entryTime;
}
