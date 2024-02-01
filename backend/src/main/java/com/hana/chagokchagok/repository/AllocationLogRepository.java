package com.hana.chagokchagok.repository;

import com.hana.chagokchagok.dto.AllocationDto;
import com.hana.chagokchagok.entity.AllocationLog;
import com.hana.chagokchagok.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// 입출차기록 테이블과 매핑
@Repository
public interface AllocationLogRepository extends JpaRepository<AllocationLog, Long> {

    AllocationLog findByCarNo(String carNo);

    List<AllocationLog> findAllByEntryTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay); // 특정 날짜 입차 목록 조회

}
