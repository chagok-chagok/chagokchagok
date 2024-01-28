package com.hana.chagokchagok.contoller;


import com.hana.chagokchagok.dto.ReportDto;
import com.hana.chagokchagok.dto.request.ReportRequest;
import com.hana.chagokchagok.dto.response.ReportResponse;
import com.hana.chagokchagok.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    /**
     * 신고 목록 조회하는 메소드
     *
     * @param page 페이지 번호(0번부터 시작)
     * @return reportResponse 화면에 뿌려줄 정보
     * @author 김용준
     */
    @GetMapping("/report")
    public ReportResponse getReportList(@RequestParam(name = "page", defaultValue = "0") int page) {
        ReportRequest reportRequest = new ReportRequest(page);
        return adminService.getReportList(reportRequest);
    }

    /**
     * 신고 내용 수정하는 메소드
     *
     * @param reportDto 기존에 만들었던 ReportDto가 이 메소드의 Request 기능을 수행할 수 있음
     * @author 김용준
     */
    @PutMapping("/report")
    public void updateReport(@RequestBody ReportDto reportDto) {
        adminService.updateReport(reportDto);
    }
}
