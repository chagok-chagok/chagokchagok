package com.hana.chagokchagok.repository;

import com.hana.chagokchagok.entity.AllocationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 입출차기록 테이블과 매핑
@Repository
public interface AllocationLogRepository extends JpaRepository<AllocationLog, Long> {

    AllocationLog findByCarNo(String carNo);


}
