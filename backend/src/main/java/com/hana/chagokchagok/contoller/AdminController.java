package com.hana.chagokchagok.contoller;


import com.hana.chagokchagok.dto.request.ExchangeRequest;
import com.hana.chagokchagok.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hana.chagokchagok.dto.request.ExchangeRequest;
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
<<<<<<< PATCH SET (0814e0 :bug: rebase로 인한 merge로 버그 수정)
    @PutMapping("/exchange")
    public ResponseEntity<String> exchangeAllocation(@RequestBody ExchangeRequest exchangeRequest) {
        return adminService.exchangeAllocation(exchangeRequest);
    }
=======
>>>>>>> BASE      (9feb6d :sparkles: 관리자용 자리교환 기능 완성)


}

