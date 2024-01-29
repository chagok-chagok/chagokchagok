package com.hana.chagokchagok.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

//관리자
@Entity @Getter
public class Admin {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_no")
    private Long adminNo;

    private String id;

    private String password;

    @Column(name = "refresh_token")
    private String refreshToken;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public void deleteRefreshToken(){
        this.refreshToken = null;
    }

    public void join(String id, String password){
        this.id = id;
        this.password = password;
    }
}
