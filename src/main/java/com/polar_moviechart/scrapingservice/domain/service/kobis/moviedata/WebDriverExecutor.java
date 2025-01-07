package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata;

import com.polar_moviechart.scrapingservice.exception.ScrapingException;
import com.polar_moviechart.scrapingservice.utils.WebDriverExecutorUtils;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebDriverExecutor {

    private static final String targetUrl = "https://www.kobis.or.kr/kobis/business/stat/boxs/findDailyBoxOfficeList.do";
    private final WebDriver driver;

    public void navigateTo(String targetDate) {
        typeToDriver(targetDate);
        WebElement searchButton = driver.findElement(By.cssSelector("div.wrap_btn > button.btn_blue"));
        searchButton.click();
    }

    public void clickMoreButton() {
        while (true) {
            try {
                // 더 보기 버튼 찾기
                WebElement moreButton = driver.findElement(By.cssSelector("div.more > a.btn_more"));
                // 더 보기 버튼 클릭
                moreButton.click();
            } catch (Exception e) {
                // 더 이상 버튼이 없으면 루프 중단
                break;
            }
        }
    }

    public List<WebElement> getMovieRows(String targetDate) {
        List<WebElement> movietable;
        try {
            WebElement movieTable = driver.findElement(By.cssSelector("tbody#tbody_0"));
            movietable = movieTable.findElements(By.tagName("tr"));
        } catch (Exception e) {
            ScrapingExceptionDto exceptionDto = new ScrapingExceptionDto();
            exceptionDto.setTargetDate(targetDate);
            throw new ScrapingException(e, exceptionDto);
        }
        return movietable;
    }

    public List<WebElement> getColumnInfo(WebElement row) {
        return row.findElements(By.tagName("td"));
    }

    public WebElement moveToMovieDetailPage(WebElement row) {
        WebElement secondTd = row.findElements(By.tagName("td")).get(1);
        WebElement movieInfoLink = secondTd.findElement(By.cssSelector("span.ellip.per90 > a"));
        movieInfoLink.click();
        return driver.findElement(By.cssSelector("div[tabindex='-1']"));
    }

    private void typeToDriver(String targetDate) {
        // 타겟 날짜 포맷 변경 (예시로 2번째 인덱스부터 사용)
        String formattedDate = targetDate.substring(2);

        // 시작 날짜 입력 필드 찾기
        WebElement searchStartDate = driver.findElement(By.cssSelector("div.tf_comm > input#sSearchFrom"));
        trimDateInput(searchStartDate, 8);
        searchStartDate.sendKeys(formattedDate);
        WebElement searchEndDate = driver.findElement(By.cssSelector("div.tf_comm > input#sSearchTo"));
        trimDateInput(searchEndDate, 8);
        searchEndDate.sendKeys(formattedDate);

        // ESC 키를 눌러서 날짜 선택 창 닫기
        WebDriverExecutorUtils.doEsc(driver);
    }

    private void trimDateInput(WebElement webElement, int trimCount) {
        for (int i = 0; i < trimCount; i++) {
            webElement.sendKeys(Keys.BACK_SPACE);
        }
    }

    public void navigateMainPage() {
        driver.get(targetUrl);
        driver.navigate();
    }
}
