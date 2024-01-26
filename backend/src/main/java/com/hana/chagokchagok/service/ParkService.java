package com.hana.chagokchagok.service;

import com.hana.chagokchagok.dto.AllocationDto;
import com.hana.chagokchagok.dto.request.AllocateCarRequest;
import com.hana.chagokchagok.dto.response.AllocateCarResponse;
import com.hana.chagokchagok.entity.AllocationLog;
import com.hana.chagokchagok.entity.RealtimeParking;
import com.hana.chagokchagok.repository.AllocationLogRepository;
import com.hana.chagokchagok.repository.RealTimeParkingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ParkService {
    private final RealTimeParkingRepository realTimeParkingRepository;
    private final AllocationLogRepository allocationLogRepository;

    /**
     * 자리 배정 로직 수행 메소드
     * @author 김용준
     * @param allocateCarRequest
     * @return
     */
    public AllocateCarResponse getAllocatedInfo(AllocateCarRequest allocateCarRequest) {
        RealtimeParking allocatedLocation;
        // 장애 배려 차량 여부 확인
        if (allocateCarRequest.getIsDisabled()) {
            allocatedLocation = realTimeParkingRepository.findFirstWhoIsDisabled();
        } else {
            allocatedLocation = realTimeParkingRepository.findFirstWhoIsNotDisabled();
        }

        // 자리가 없다면 만차 응답 반환
        if (allocatedLocation == null) {
            System.out.println("자리업슴");
            return null;
        }
        // 자리 있다면 배정 로직 수행
        else {
            System.out.println("allocated location : " + allocatedLocation.getParkId());

            // 입출차기록 테이블에 저장
            AllocationDto allocationDto = new AllocationDto(allocatedLocation, allocateCarRequest.getCarNo());
            AllocationLog allocationLog = AllocationLog.createAllocationLog(allocationDto);
            allocationLogRepository.save(allocationLog);

            // 주차현황 테이블 업데이트
            allocatedLocation.setLog(allocationLog);
            realTimeParkingRepository.save(allocatedLocation);

            return new AllocateCarResponse(allocatedLocation, allocationLog);
        }
    }
}
