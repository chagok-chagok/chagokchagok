package com.hana.chagokchagok.dto.response;

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

        setReportCount(todayReports);
    }

    private void setReportCount(List<Report> todayReports) {
        todayCnt = todayReports.size(); // 오늘 총 신고 수 = 쿼리로 찾아온 수
        for (Report r : todayReports) {
            increaseCountByErrorCode(r.getErrorCode());
            /*
            만약 IN_PROGRESS 또는 UNRESOLVED라면 unsolvedCnt++
            궁금한 점 1. UNRESOLVED만 세야되나? 이건 의논 해봐야 할 듯
            궁금한 점 2. 미해결 신고는 오늘 신고만 봐도 되는건가? 전체 신고 중에서 봐야 하는 게 아닌가?
             */
            ReportStatus status = r.getReportStatus();
            if (status != ReportStatus.COMPLETED) unsolvedCnt++;
        }
    }

    /**
     * 오류 타입별 카운트 증가시키는 메소드
     *
     * @param code 오류 타입
     */
    private void increaseCountByErrorCode(ErrorCode code) {
        switch (code) {
            case AUTO_REPORT -> autosystemCnt++;
            case HUMAN_ERROR -> hotlineCnt++;
            case SENSOR_ERROR -> sensorCnt++;
        }
    }

    private int todayCnt = 0; // 오늘 총 신고 수
    private int unsolvedCnt = 0; // 오늘 미해결 신고 수??? 미해결 신고 수는 전체에서 봐야되나?
    private int hotlineCnt = 0; // 오늘 핫라인 신고 수
    private int sensorCnt = 0; // 오늘 센서 고장 신고 수
    private int autosystemCnt = 0; // 오늘 자동 신고 수
    private List<ReportDto> board = new ArrayList<>(); // 현재 페이지 신고 정보
}
