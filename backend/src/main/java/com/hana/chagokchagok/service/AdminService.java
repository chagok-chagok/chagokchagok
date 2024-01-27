package com.hana.chagokchagok.service;

import com.hana.chagokchagok.dto.request.ReportRequest;
import com.hana.chagokchagok.dto.response.ReportResponse;
import com.hana.chagokchagok.entity.Report;
import com.hana.chagokchagok.repository.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final ReportRepository reportRepository;

    /**
     * 신고 기록을 조회하는 메소드
     * @param reportRequest 페이지 정보, 오늘 날짜
     * @return 신고 기록 페이지에 필요한 데이터를 담은 ReportResponse
     */
    public ReportResponse getReportList(ReportRequest reportRequest) {
        // 현재 페이지 신고 내역
        Page<Report> page = reportRepository.findAll(reportRequest.getPageable());
        // 오늘의 신고 내역
        List<Report> todayReports = getTodayReports(reportRequest.getToday());
        return new ReportResponse(page, todayReports);
    }

    /**
     * 오늘 들어온 신고를 조회하는 메소드
     * @param today 오늘 날짜
     * @return 오늘 신고 리스트
     */
    private List<Report> getTodayReports(LocalDate today) {
        LocalDateTime startOfDay = LocalDateTime.of(today, LocalTime.MIN); // 오늘 0시 0분
        LocalDateTime endOfDay = LocalDateTime.of(today, LocalTime.MAX); // 오늘 23시 59분 59초 ...
        List<Report> todayReports = reportRepository.findAllByReportTimeBetween(startOfDay, endOfDay);
        return todayReports;
    }


}
