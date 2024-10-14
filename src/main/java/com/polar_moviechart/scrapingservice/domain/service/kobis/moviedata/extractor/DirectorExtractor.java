package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor;

import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.DirectorInfoDto;
import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.ScrapingExceptionDto;
import com.polar_moviechart.scrapingservice.exception.ScrapingException;
import com.polar_moviechart.scrapingservice.utls.DataExtractUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class DirectorExtractor {

    public List<DirectorInfoDto> getDirectorsInfo(WebElement directorsElement) {
        List<WebElement> directorsInfo = directorsElement.findElements(By.cssSelector("dd"));

        Pattern pattern = DataExtractUtils.getCodePattern();

        return directorsInfo.stream()
                .map(directorElement -> {
                    WebElement directorInfo = directorElement.findElement(By.cssSelector("a"));
                    String directorName = directorInfo.getText();
                    String onClickAttr = directorInfo.getAttribute("onClick");
                    Matcher matcher = pattern.matcher(onClickAttr);

                    try {
                        matcher.find();
                        Integer directorCode = Integer.parseInt(matcher.group(1));
                        return new DirectorInfoDto(directorCode, directorName);
                    } catch (Exception e) {
                        ScrapingExceptionDto exceptionDto = new ScrapingExceptionDto();
                        exceptionDto.setName(directorName);
                        throw new ScrapingException(e, exceptionDto);
                    }
                })
                .collect(Collectors.toList());
    }
}
