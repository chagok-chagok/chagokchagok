package com.hana.chagokchagok.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hana.chagokchagok.dto.ReportDto;
import com.hana.chagokchagok.entity.Report;
import com.hana.chagokchagok.enums.ErrorCode;
import com.hana.chagokchagok.enums.ReportStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
public class ReportResponse {
    public ReportResponse(Page<Report> page, List<Report> todayReports) {
        // board에 추가하는 작업
        for (Report r : page.getContent()) {
            board.add(new ReportDto(r));
        }
        setTotalPageCount(page.getTotalPages());
        setReportCount(todayReports);
    }

    private void setReportCount(List<Report> todayReports) {
        todayCnt = todayReports.size(); // 오늘 총 신고 수 = 쿼리로 찾아온 수
        for (Report r : todayReports) {
            increaseCountByErrorCode(r.getErrorCode());
            if (r.getReportStatus() == ReportStatus.UNRESOLVED) unsolvedCnt++;
        }
    }

    /**
     * 오류 타입별 카운트 증가시키는 메소드
     * @param code 오류 타입
     */
    private void increaseCountByErrorCode(ErrorCode code) {
        switch (code) {
            case AUTO_REPORT -> autosystemCnt++;
            case HUMAN_ERROR -> hotlineCnt++;
            case SENSOR_ERROR -> sensorCnt++;
        }
    }
    @JsonProperty("today_cnt")
    private int todayCnt; // 오늘 총 신고 수
    @JsonProperty("unsolved_cnt")
    private int unsolvedCnt; // 오늘 미해결 신고 수
    @JsonProperty("hotline_cnt")
    private int hotlineCnt; // 오늘 핫라인 신고 수
    @JsonProperty("sensor_cnt")
    private int sensorCnt; // 오늘 번호 오인식 신고 수
    @JsonProperty("autosystem_cnt")
    private int autosystemCnt; // 오늘 자동 신고 수
    @JsonProperty("total_page_cnt")
    private int totalPageCount; // 총 페이지 수
    private List<ReportDto> board = new ArrayList<>(); // 현재 페이지 신고 정보
}
