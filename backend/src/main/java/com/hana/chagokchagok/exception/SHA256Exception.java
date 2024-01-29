package com.hana.chagokchagok.exception;

public class SHA256Exception extends RuntimeException{
    public SHA256Exception() {
        super("비밀번호 암호화 오류 발생\n");
    }
}
