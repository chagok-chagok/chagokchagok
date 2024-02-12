package com.hana.chagokchagok.dto.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CarNumValidationDto {
    private boolean validate;

    public CarNumValidationDto(boolean validate){
        this.validate = validate;
    }
}
