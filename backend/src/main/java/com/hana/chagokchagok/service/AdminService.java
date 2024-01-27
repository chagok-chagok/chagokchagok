package com.hana.chagokchagok.service;

import com.hana.chagokchagok.dto.request.LoginRequest;
import com.hana.chagokchagok.dto.response.LoginResponse;
import com.hana.chagokchagok.entity.Admin;
import com.hana.chagokchagok.repository.AdminRepository;
import com.hana.chagokchagok.util.JWTUtil;
import com.hana.chagokchagok.util.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final JWTUtil jwtUtil;

    public LoginResponse login(LoginRequest adminInfoDto) {
        SHA256 sha256 = new SHA256();
        LoginResponse loginResponse = new LoginResponse();
        try{
            Admin admin = adminRepository.findById(adminInfoDto.getId());
            if(admin != null && sha256.encrypt(adminInfoDto.getPass()).equals(admin.getPassword())){

                String accessToken = jwtUtil.createAccessToken(adminInfoDto.getId());
                String refreshToken = jwtUtil.createRefreshToken(adminInfoDto.getId());

                //db에 리프레시 토큰 저장하기
                admin.updateRefreshToken(refreshToken);
                adminRepository.save(admin);

                //Json으로 token전달
                loginResponse.setAccessToken(accessToken);
                loginResponse.setRefreshToken(refreshToken);

                //status = HttpStatus.CREATED;
            }else {
                loginResponse.setMessage("아이디 또는 패스워드를 확인해주세요.");
                //status = HttpStatus.UNAUTHORIZED;
            }
        }catch (Exception e) {
            //로그인에러
            loginResponse.setMessage(e.getMessage());
            //status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return loginResponse;

    }


    public String getRefreshToken(String id) {
        Admin admin = adminRepository.findById(id);
        return admin.getRefreshToken();
    }

    public void deleRefreshToken(String id) {
        Admin admin = adminRepository.findById(id);
        admin.deleteRefreshToken();
        adminRepository.save(admin);
    }
}
