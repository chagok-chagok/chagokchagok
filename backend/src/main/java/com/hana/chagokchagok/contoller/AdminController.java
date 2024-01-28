package com.hana.chagokchagok.contoller;

import com.hana.chagokchagok.dto.request.ReportRequest;
import com.hana.chagokchagok.dto.response.CommonAlertResponse;
import com.hana.chagokchagok.dto.response.ReportResponse;
import com.hana.chagokchagok.service.AdminService;
import com.hana.chagokchagok.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/report")
    public ReportResponse getReportList(@RequestParam(name = "page", defaultValue = "0") int page) {
        ReportRequest reportRequest = new ReportRequest(page);
        return adminService.getReportList(reportRequest);
    }
    @GetMapping("/common")
    public CommonAlertResponse getCommonAlertData() {
        return adminService.getCommonAlertData();
    }

}
