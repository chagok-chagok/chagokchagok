package com.hana.chagokchagok.contoller;

import com.hana.chagokchagok.dto.response.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/report")
    public ReportResponse getReportList() {

        return null;
    }
}
