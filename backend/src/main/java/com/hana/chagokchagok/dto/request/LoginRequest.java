package com.hana.chagokchagok.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @ToString @Setter @NoArgsConstructor
public class LoginRequest {
    private String id;
    private String pass;
    private String accessToken;
    private String refreshToken;

    public LoginRequest(String id, String pass) {
        this.id = id;
        this.pass = pass;
    }

}
