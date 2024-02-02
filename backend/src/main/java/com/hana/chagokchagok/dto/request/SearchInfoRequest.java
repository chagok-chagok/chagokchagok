package com.hana.chagokchagok.dto.request;

import com.hana.chagokchagok.enums.SearchType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
@NoArgsConstructor
public class SearchInfoRequest {
    private SearchType type;
    private String value;
}
