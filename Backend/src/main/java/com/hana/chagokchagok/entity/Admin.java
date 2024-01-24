package com.hana.chagokchagok.entity;

import jakarta.persistence.*;
import lombok.Getter;

//관리자
@Entity @Getter
public class Admin {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_no")
    private Long adminNo;

    private String name;

    private String id;

    private String password;
}
