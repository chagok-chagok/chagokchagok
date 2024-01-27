package com.hana.chagokchagok.dto.response;

import com.hana.chagokchagok.dto.ReportDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
public class ReportResponse {
    private Integer todayCnt;
    private Integer unsolvedCnt;
    private Integer hotlineCnt;
    private Integer autosystemCnt;
    private List<ReportDto> board = new ArrayList<>();
}
