package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.domain.repository.MovieRepository;
import com.polar_moviechart.scrapingservice.domain.service.*;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor.DataExtractor;
import com.polar_moviechart.scrapingservice.utls.DataExtractUtils;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrapingService {

    private final WebDriverExecutor webDriverExecutor;
    private final DataExtractor dataExtractor;
    private final MovieCommandService movieCommandService;
    private final MovieDailyStatsCommandService movieDailyStatsCommandService;
    private final DirectorQueryService directorQueryService;
    private final MovieRepository movieRepository;
    private MovieDirectorCommandService movieDirectorCommandService;
    private DirectorCommandService directorCommandService;

    @Transactional
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
                int movieCode = movieInfoDto.getCode();
                movieCommandService.save(movieInfoDto);

                List<WebElement> staffElement = webDriverExecutor.getStaffElement(movieDetailPage, movieCode);
                List<DirectorInfoDto> directorsDto = dataExtractor.getDirectorsInfo(staffElement.get(0), movieCode);
                for (DirectorInfoDto directorDto : directorsDto) {
                    if (!directorQueryService.isExists(directorDto.getCode())) {
                        directorCommandService.save(directorDto);
                        movieDirectorCommandService.save(movieCode, directorDto.getCode());
                    }
                }


            }
            movieDailyStatsCommandService.save(movieDailyStatsDto, DataExtractUtils.convertToLocalDate(targetDate));
        }
    }
}
