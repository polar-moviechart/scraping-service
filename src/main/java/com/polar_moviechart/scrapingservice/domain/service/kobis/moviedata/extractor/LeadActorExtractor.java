package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor;

import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.LeadActorInfoDto;
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
public class LeadActorExtractor {

    public List<LeadActorInfoDto> getLeadActorsInfo(WebElement leadActorsElement) {
        WebElement leadActorsTemplate = leadActorsElement.findElements(By.cssSelector("dd")).get(0);
        WebElement leadActorsTemplate2 = leadActorsTemplate.findElements(By.cssSelector("table > tbody > tr > td")).get(0);
        List<WebElement> leadActorElements = leadActorsTemplate2.findElements(By.cssSelector("a"));

        Pattern pattern = DataExtractUtils.getCodePattern();

        return leadActorElements.stream()
                .map(leadActor -> {
                    String leadActorName = leadActor.getText();
                    String onClickAttr = leadActor.getAttribute("onClick");
                    Matcher matcher = pattern.matcher(onClickAttr);
                    try {
                        matcher.find();
                        String leadActorCode = matcher.group(1);
                        return new LeadActorInfoDto(
                                Integer.parseInt(leadActorCode),
                                leadActorName);
                    } catch (Exception e) {
                        ScrapingExceptionDto exceptionDto = new ScrapingExceptionDto();
                        exceptionDto.setName(leadActorName);
                        throw new ScrapingException(e, exceptionDto);
                    }
                })
                .collect(Collectors.toList());
    }
}
