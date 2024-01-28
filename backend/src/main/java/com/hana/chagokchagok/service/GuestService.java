package com.hana.chagokchagok.service;

import com.hana.chagokchagok.dto.response.RealTimeParkingInfoResponse;
import com.hana.chagokchagok.entity.RealtimeParking;
import com.hana.chagokchagok.repository.RealTimeParkingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestService {

    private final RealTimeParkingRepository realTimeParkingRepository;
    public RealTimeParkingInfoResponse getParkingInfo() {
        List<RealtimeParking> realtimeParkingInfo = realTimeParkingRepository.findAll();
        return new RealTimeParkingInfoResponse(realtimeParkingInfo);
    }
}
