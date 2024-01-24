package com.hana.chagokchagok.service;

import com.hana.chagokchagok.dto.RealTimeParkingInfoDto;
import com.hana.chagokchagok.entity.ParkingInfo;
import com.hana.chagokchagok.repository.ParkingInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestService {

    private final ParkingInfoRepository parkingInfoRepository;
    public RealTimeParkingInfoDto getParkingInfo() {
        List<ParkingInfo> parkingInfos = parkingInfoRepository.findAll();
        return new RealTimeParkingInfoDto(parkingInfos);
    }
}
