package com.hana.chagokchagok.dto.response;

import com.hana.chagokchagok.dto.ReportDataDto;
import com.hana.chagokchagok.dto.ReportRateDto;
import com.hana.chagokchagok.dto.UnresolvedDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashBoardResponse {
    private int[] today_visits;
    private int[] previous_visits;
    int total_report;
    private ReportRateDto report_rate;
    private UnresolvedDto unresolvedDto;
}
