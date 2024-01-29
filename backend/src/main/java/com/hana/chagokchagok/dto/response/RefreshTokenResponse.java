package com.hana.chagokchagok.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @ToString @Setter @NoArgsConstructor
public class RefreshTokenResponse {
    private String AccessToken;
    private String message;
}
