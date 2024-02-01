package com.hana.chagokchagok.repository;

import com.hana.chagokchagok.entity.Report;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> findAll(Pageable pageable); // 현재 페이지 글 목록 조회
    List<Report> findAllByReportTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay); // 오늘 날짜 신고 목록 조회

    List<Report> findTop5ByOrderByDoneTimeDesc(); //최근 처리완료된 5개 데이터를 조회
    // findById를 @Override하면 순환 참조때문에 오류가 생김
    Report findByReportId(Long reportId); // reportId로 신고 조회

    @Query("SELECT r FROM Report r WHERE r.reportTime >= :thirtyDaysAgo ORDER BY r.reportTime DESC")
    List<Report> findBefore30Days(@Param("thirtyDaysAgo") LocalDateTime thirtyDaysAgo);
}
