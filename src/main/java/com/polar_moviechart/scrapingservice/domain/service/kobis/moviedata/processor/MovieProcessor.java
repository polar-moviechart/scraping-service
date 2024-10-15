package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.processor;

import com.polar_moviechart.scrapingservice.domain.entity.Movie;
import com.polar_moviechart.scrapingservice.domain.service.MovieCommandService;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.*;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor.DataExtractor;
import com.polar_moviechart.scrapingservice.exception.ScrapingException;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MovieProcessor {
    private final DataExtractor dataExtractor;
    private final MovieCommandService movieCommandService;
    private final StaffProcessor staffProcessor;

    @Transactional
    public Movie processNewMovie(WebElement movieDetailPage, MovieDailyStatsDto movieDailyStatsDto, String targetDate) {
        MovieInfoDto movieInfoDto = dataExtractor.getMovieInfo(movieDetailPage, movieDailyStatsDto);

        Movie movie = movieCommandService.save(movieInfoDto);
//        downloadImage(movieInfoDto);
        try {
            if (movie.getReleaseDate() == null && movie.getProductionYear() == null) {
                return movie;
            }
            StaffInfoDto staffInfoDto = dataExtractor.getStaffInfo(movieDetailPage, movie.getCode());
            if (staffInfoDto != null) {
                staffProcessor.processStaffInfo(staffInfoDto, movie.getCode(), targetDate);
            }
        } catch (Exception e) {
            ScrapingExceptionDto exceptionDto = new ScrapingExceptionDto();
            exceptionDto.setMovieName(movie.getTitle());
            exceptionDto.setTargetDate(targetDate);
            throw new ScrapingException(e, exceptionDto);
        }
        return movie;
    }

    private void downloadImage(MovieInfoDto movieInfoDto) {
        try {
            URL url = new URL(movieInfoDto.getImgUrls().get(0));
            String currentDirectory = System.getProperty("user.dir");
            Path destinationDir = Paths.get(currentDirectory);

            String originalFileName = "image.jpg";
            String newFileName = appendRandomNumberToFileName(originalFileName);

            Files.copy(url.openStream(), destinationDir.resolve(newFileName));
        } catch (IOException e) {
            throw new ScrapingException(e);
        }
    }

    private String appendRandomNumberToFileName(String fileName) {
        // 랜덤 숫자 생성 (5자리)
        Random random = new Random();
        int randomNumber = random.nextInt(100000); // 0부터 99999까지의 숫자

        // 확장자 분리
        int dotIndex = fileName.lastIndexOf('.');
        String baseName = fileName.substring(0, dotIndex);
        String extension = fileName.substring(dotIndex);

        // 랜덤 숫자를 파일 이름 끝에 추가
        return baseName + "_" + randomNumber + extension;
    }
}
