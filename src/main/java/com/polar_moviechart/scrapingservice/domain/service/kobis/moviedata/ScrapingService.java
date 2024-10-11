package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.domain.repository.MovieRepository;
import com.polar_moviechart.scrapingservice.domain.service.MovieCommandService;
import com.polar_moviechart.scrapingservice.domain.service.MovieDailyStatsCommandService;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor.DataExtractor;
import com.polar_moviechart.scrapingservice.utls.DataExtractUtils;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrapingService {

    private final WebDriverExecutor webDriverExecutor;
    private final DataExtractor dataExtractor;
    private final MovieCommandService movieCommandService;
    private final MovieDailyStatsCommandService movieDailyStatsCommandService;
    private final MovieRepository movieRepository;

    private void doScrape(String targetDate) {
        webDriverExecutor.navigateToPage(targetDate);
        // 더 보기 버튼 클릭
        webDriverExecutor.clickMoreButton();
        List<WebElement> movieRows = webDriverExecutor.getMovieRows();

        for (WebElement row : movieRows) {
            List<WebElement> columnInfo = webDriverExecutor.getColumnInfo(row);
            MovieDailyStatsDto movieDailyStatsDto = dataExtractor.getMovieDailyStatsInfo(columnInfo);

            Optional<Long> codeOptional = movieRepository.findByCode(movieDailyStatsDto.getCode());
            if (codeOptional.isEmpty()) {
                WebElement movieDetailPage = webDriverExecutor.moveToMovieDetailPage(row);
                MovieInfoDto movieInfoDto = dataExtractor.getMovieInfo(movieDetailPage, movieDailyStatsDto);
                movieCommandService.save(movieInfoDto);

                dataExtractor.getDirectorsInfo(movieDetailPage);
            }
            movieDailyStatsCommandService.save(movieDailyStatsDto, DataExtractUtils.convertToLocalDate(targetDate));
        }
    }

}
