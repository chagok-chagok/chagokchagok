package com.hana.chagokchagok.contoller;

import com.hana.chagokchagok.dto.response.RealTimeParkingInfoResponse;
import com.hana.chagokchagok.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestController {
    private final GuestService guestService;

    /**
     * 손님용 실시간 주차현황 조회
     * @author 최서현
     * @return RealTimeParkingInfoDto
     */
    @GetMapping
    public RealTimeParkingInfoResponse getParkingInfo(){
        return guestService.getParkingInfo();
    }
}
