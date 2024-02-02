package com.hana.chagokchagok.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
@Getter @Setter
public class ReportRequest {
    public ReportRequest(int page) {
        page = Math.max(0, page - 1);
        // 페이지 번호, 페이지당 글 개수, 정렬 방향, 정렬 기준
        pageable = PageRequest.of(page, PAGE_SIZE, Sort.Direction.DESC, "reportId");
        today = LocalDate.now();
    }

    private final int PAGE_SIZE = 5;
    Pageable pageable; // 페이징 용 변수
    LocalDate today; // 오늘 날짜
}
