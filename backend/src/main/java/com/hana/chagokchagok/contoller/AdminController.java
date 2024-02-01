package com.hana.chagokchagok.contoller;

import com.hana.chagokchagok.dto.ReportDto;
import com.hana.chagokchagok.dto.request.ExchangeRequest;
import com.hana.chagokchagok.dto.request.LoginRequest;
import com.hana.chagokchagok.dto.request.OpenBarRequest;
import com.hana.chagokchagok.dto.request.ReportRequest;
import com.hana.chagokchagok.dto.response.CommonAlertResponse;
import com.hana.chagokchagok.dto.response.DashBoardResponse;
import com.hana.chagokchagok.dto.response.LoginResponse;
import com.hana.chagokchagok.dto.response.LogoutResponse;
import com.hana.chagokchagok.dto.response.RealtimeCarsResponse;
import com.hana.chagokchagok.dto.response.RefreshTokenResponse;
import com.hana.chagokchagok.dto.response.ReportResponse;
import com.hana.chagokchagok.service.AdminService;
import com.hana.chagokchagok.service.ParkService;
import com.hana.chagokchagok.service.SseService;
import com.hana.chagokchagok.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final JWTUtil jwtUtil;
    private final ParkService parkService;
    private final SseService sseService;
    @Value("${admin.key}")
    private String ADMIN_KEY;

    @GetMapping("/test")
    public void test(){
        adminService.createRoot();
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest adminInfoDto){
        LoginResponse loginResponse  = adminService.login(adminInfoDto);
        return loginResponse;
    }

    @GetMapping("/refresh")
    public RefreshTokenResponse refreshToken(HttpServletRequest request){
        //클라이언트가 가지고 있는 refreshToken 가져오기
        String token = request.getHeader("Authorization");

        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
        //정상적인 토큰일때
        if (jwtUtil.checkToken(token)) {
            if (token.equals(adminService.getRefreshToken(jwtUtil.getUserId(token)))) {
                String accessToken = jwtUtil.createAccessToken(jwtUtil.getUserId(token));
                refreshTokenResponse.setAccessToken(accessToken);
            }
        }else {
            refreshTokenResponse.setMessage("리프레쉬토큰도 사용불가!!!!!!!");
        }
        return refreshTokenResponse;
    }

    @GetMapping("/logout")
    public LogoutResponse removeToken(HttpServletRequest request) {
        LogoutResponse logoutResponse = new LogoutResponse();
        try {
            adminService.deleRefreshToken(jwtUtil.getUserId(request.getHeader("Authorization")));
            logoutResponse.setMessage("로그아웃되었습니다.");
        } catch (Exception e) {
            logoutResponse.setMessage(e.getMessage());
        }
        return logoutResponse;
    }

    /**
     * 신고 목록 조회하는 메소드
     * @author 김용준
     * @param page 페이지 번호(0번부터 시작)
     * @return reportResponse 화면에 뿌려줄 정보
     */
    @GetMapping("/report")
    public ReportResponse getReportList(@RequestParam(name = "page", defaultValue = "0") int page) {
        ReportRequest reportRequest = new ReportRequest(page);
        return adminService.getReportList(reportRequest);
    }
    /**
     * 신고 내용 수정하는 메소드
     * @author 김용준
     * @param reportDto 기존에 만들었던 ReportDto가 이 메소드의 Request 기능을 수행할 수 있음
     */
    @PutMapping("/report")
    public void updateReport(@RequestBody ReportDto reportDto) {
        boolean isNowDoneReport = adminService.updateReport(reportDto);
        if (isNowDoneReport) sseService.sendRealtimeCommon(ADMIN_KEY);

    }
    @PutMapping("/exchange")
    public ResponseEntity<String> exchangeAllocation(@RequestBody ExchangeRequest exchangeRequest) {
        ResponseEntity<String> resp = adminService.exchangeAllocation(exchangeRequest);
        sseService.sendRealtimeCommon(ADMIN_KEY);
        return resp;
    }
    @GetMapping("/common")
    public CommonAlertResponse getCommonAlertData() {
        return adminService.getCommonAlertData();
    }

    @PutMapping("/bar")
    public void openBar(@RequestBody OpenBarRequest openBarRequest) {
        parkService.openBar(openBarRequest); // 차단바 해제 요청
        sseService.sendRealtimeCommon(ADMIN_KEY);
    }

    @GetMapping("/cars")
    public RealtimeCarsResponse realtimeCars(){
        return parkService.getRealtimeCars();
    }

    @GetMapping("/dashboard")
    public DashBoardResponse getDashboard(){
        return adminService.getDashboard();
    }

}