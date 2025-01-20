package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.processor;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.polar_moviechart.scrapingservice.domain.entity.Movie;
import com.polar_moviechart.scrapingservice.domain.service.MovieCommandService;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.*;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor.DataExtractor;
import com.polar_moviechart.scrapingservice.exception.ScrapingException;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
    private final AmazonS3Client s3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public Movie processNewMovie(WebElement movieDetailPage, MovieDailyStatsDto movieDailyStatsDto, String targetDate) {
        MovieInfoDto movieInfoDto = dataExtractor.getMovieInfo(movieDetailPage, movieDailyStatsDto);

        Movie movie = movieCommandService.save(movieInfoDto);
        if (!movie.isSuccess()) {
            return movie;
        }
        String s3Path = downloadImage(movieInfoDto);
        movie.setThumbnail(s3Path);
        try {
            if (movie.getReleaseDate() == null && movie.getProductionYear() == null) {
                return movie;
            }
            StaffInfoDto staffInfoDto = dataExtractor.getStaffInfo(movieDetailPage, movie.getCode());
            staffProcessor.processStaffInfo(staffInfoDto, movie.getCode(), targetDate);
        } catch (Exception e) {
            ScrapingExceptionDto exceptionDto = new ScrapingExceptionDto();
            exceptionDto.setMovieName(movie.getTitle());
            exceptionDto.setTargetDate(targetDate);
            throw new ScrapingException(e, exceptionDto);
        }
        return movie;
    }

    private String downloadImage(MovieInfoDto movieInfoDto) {
        try {
            URL url = new URL(movieInfoDto.getImgUrls().get(0));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            return putImageToS3(movieInfoDto, url);
        } catch (IOException e) {
            throw new ScrapingException(e);
        }
    }

    private String putImageToS3(MovieInfoDto movieInfoDto, URL url) throws IOException {
        String originalFileName = "thumbnail.jpg";
//        String newFileName = appendRandomNumberToFileName(originalFileName);

        Integer movieCode = movieInfoDto.getCode();  // movieCode 가져오기
        String s3Path = String.format("movies/%s/%s", movieCode, originalFileName);

        InputStream fileInputStream = url.openStream();

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, s3Path, fileInputStream, new ObjectMetadata());
        s3Client.putObject(putObjectRequest);

        fileInputStream.close();
        return s3Path;
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
        return String.format("%s_%s%s", baseName, randomNumber, extension);
    }
}
