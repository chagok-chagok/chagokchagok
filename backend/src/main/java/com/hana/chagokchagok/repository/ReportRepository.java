package com.hana.chagokchagok.repository;

import com.hana.chagokchagok.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> findAll(Pageable pageable); // 현재 페이지 글 목록 조회
    List<Report> findAllByReportTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay); // 오늘 날짜 신고 목록 조회
    // findById를 @Override하면 순환 참조때문에 오류가 생김
    Report findByReportId(Long reportId); // reportId로 신고 조회
}
