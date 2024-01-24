package com.hana.chagokchagok.contoller;

import com.hana.chagokchagok.dto.RealTimeParkingInfoDto;
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

    @GetMapping
    public RealTimeParkingInfoDto getParkingInfo(){
        return guestService.getParkingInfo();
    }
}
