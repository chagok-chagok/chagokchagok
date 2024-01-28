package com.hana.chagokchagok.contoller;


import com.hana.chagokchagok.dto.request.ExchangeRequest;
import com.hana.chagokchagok.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    @PutMapping("/exchange")
    public ResponseEntity<String> exchangeAllocation(@RequestBody ExchangeRequest exchangeRequest) {
        return adminService.exchangeAllocation(exchangeRequest);
    }


}

