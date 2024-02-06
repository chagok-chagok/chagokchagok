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
        // 페이지 번호, 페이지당 글 개수, 정렬 방향, 정렬 기준
        page -= 1; // 프론트에서 보내는 페이지는 1부터 시작, 페이지네이션 인덱스는 0부터 시작하므로 1 빼줌
        pageable = PageRequest.of(page, PAGE_SIZE, Sort.Direction.DESC, "reportId");
        today = LocalDate.now();
    }

    private final int PAGE_SIZE = 5;
    Pageable pageable; // 페이징 용 변수
    LocalDate today; // 오늘 날짜
}
