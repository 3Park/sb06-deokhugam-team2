package com.codeit.sb06deokhugamteam2.dashboard.service;

import com.codeit.sb06deokhugamteam2.book.entity.Book;
import com.codeit.sb06deokhugamteam2.book.repository.BookRepository;
import com.codeit.sb06deokhugamteam2.common.enums.PeriodType;
import com.codeit.sb06deokhugamteam2.common.enums.RankingType;
import com.codeit.sb06deokhugamteam2.dashboard.entity.Dashboard;
import com.codeit.sb06deokhugamteam2.dashboard.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardRepository dashboardRepository;
    private final BookRepository bookRepository;

    public List<Dashboard> createBookRankings(PeriodType periodType) {

        Instant since = null;

        switch (periodType) {
            case DAILY -> since = Instant.now().minus(1, ChronoUnit.DAYS);
            case WEEKLY -> since = Instant.now().minus(7, ChronoUnit.DAYS);
            case MONTHLY -> since = Instant.now().minus(30, ChronoUnit.DAYS);
            case ALL_TIME -> {}
        }

        // 리뷰가 있는 모든 도서를 메모리에 가져와서 순위를 매겨야할까? 탑 100만 가져올까 고민 필요
        // 혹은 쿼리로 sort, limit 걸어서 가져올까?
        List<Book> sourceBooks = (since == null) ? bookRepository.findAll() : bookRepository.findAllByCreatedAtAfter(since);

        sourceBooks.removeIf(book -> book.getReviewCount() == 0);

        /*
         1. Double 타입 계산된 점수 기준 내림차순 정렬
         2. 점수가 같을 경우 도서의 생성일 기준 내림차순 정렬
         3. 정렬 순서대로 랭크 부여 예정
         */
        sourceBooks.sort(Comparator.comparingDouble((Book book) -> {
                    double bookRating = book.getRatingSum() / book.getReviewCount();
                    return book.getReviewCount() * 0.4 + bookRating * 0.6;
                }).reversed()
                .thenComparing(Book::getCreatedAt).reversed());

        List<Dashboard> rankingBooks = new ArrayList<>();

        long rank = 1L;

        /*
         1. 정렬된 순서대로 랭크 부여
         2. 1등이 제일 먼저 만들어져야 함 (보조커서 after 처리를 위해)
         */
        for (Book book : sourceBooks) {

            Dashboard dashboardEntry = Dashboard.builder()
                    .entityId(book.getId())
                    .rankingType(RankingType.BOOK)
                    .periodType(periodType)
                    .rank(rank++)
                    .build();

            rankingBooks.add(dashboardEntry);
        }

        return dashboardRepository.saveAll(rankingBooks);
    }
}
