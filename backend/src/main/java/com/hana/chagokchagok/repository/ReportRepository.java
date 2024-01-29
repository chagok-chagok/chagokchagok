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

    List<Report> findTop5ByOrderByDoneTimeDesc(); //최근 처리완료된 5개 데이터를 조회
}
