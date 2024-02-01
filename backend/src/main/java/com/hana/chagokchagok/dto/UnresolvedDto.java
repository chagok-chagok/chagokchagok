package com.hana.chagokchagok.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnresolvedDto {
    int cnt ;
    private List<ReportDataDto> report_data;
}
