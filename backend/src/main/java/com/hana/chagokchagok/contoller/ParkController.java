package com.hana.chagokchagok.contoller;

import com.hana.chagokchagok.dto.request.AllocateCarRequest;
import com.hana.chagokchagok.dto.response.AllocateCarResponse;
import com.hana.chagokchagok.service.ParkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/park")
public class ParkController {
    private final ParkService parkService;
    @PostMapping("/allocation")
    public AllocateCarResponse allocateCar(@RequestBody AllocateCarRequest allocateCarRequest) {
        System.out.println(allocateCarRequest);
        AllocateCarResponse allocateCarResponse = parkService.getAllocatedInfo(allocateCarRequest);

        // 만차일 때
        if (allocateCarResponse == null) {
            // 뭘 리턴해야 하지?
            return null;
        }
        // 자리가 배정됐을 때
        else {
            return allocateCarResponse;
        }
    }
}
