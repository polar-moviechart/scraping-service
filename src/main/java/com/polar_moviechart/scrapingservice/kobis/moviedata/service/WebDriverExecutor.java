package com.polar_moviechart.scrapingservice.kobis.moviedata.service;

import com.polar_moviechart.scrapingservice.utls.WebDriverExecutorUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebDriverExecutor {

    private static final String targetUrl = "https://www.kobis.or.kr/kobis/business/stat/boxs/findDailyBoxOfficeList.do";
    private WebDriver driver;

    public void navigateToPage(String targetDate) {
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

    public List<WebElement> getMovieRows() {
        WebElement movieTable = driver.findElement(By.cssSelector("tbody#tbody_0"));
        return movieTable.findElements(By.tagName("tr"));
    }

    private void typeToDriver(String targetDate) {
        // 타겟 날짜 포맷 변경 (예시로 2번째 인덱스부터 사용)
        String formattedDate = targetDate.substring(2);

        // 시작 날짜 입력 필드 찾기
        WebElement searchStartDate = driver.findElement(By.cssSelector("div.tf_comm > input#sSearchFrom"));
        WebElement searchEndtDate = driver.findElement(By.cssSelector("div.tf_comm > input#sSearchTo"));
        trimDateInput(searchStartDate, 8);
        trimDateInput(searchEndtDate, 8);

        // 새로운 날짜 입력
        searchStartDate.sendKeys(formattedDate);
        searchEndtDate.sendKeys(formattedDate);

        // ESC 키를 눌러서 날짜 선택 창 닫기
        WebDriverExecutorUtils.doEsc(driver);
    }

    private void trimDateInput(WebElement webElement, int trimCount) {
        for (int i = 0; i < trimCount; i++) {
            webElement.sendKeys(Keys.BACK_SPACE);
        }
    }

    public void initDriver() {
        WebDriverManager.chromedriver().setup();
    }

    public List<WebElement> getColumnInfo(WebElement row) {
        return row.findElements(By.tagName("td"));
    }

    public WebElement moveToMovieDetailPage(WebElement row) {
        WebElement secondTd = row.findElements(By.tagName("td")).get(1);
        WebElement movieInfoLink = secondTd.findElement(By.cssSelector("span.ellip.per90 > a"));
        movieInfoLink.click();
        return driver.findElement(By.cssSelector("div[tabindex='-1'"));
    }

    public void getMovieDetailInfo(WebElement movieDetailPage, MovieBasicInfoDto basicInfo) {
        // 영화를 클릭하면 나오는 상세 정보 템플릿에서 영화 메타데이터 가져오기
        String titleEnglish = movieDetailPage.findElement(By.cssSelector("div.hd_layer > div"))
                .getText()
                .replace("영화상영관상영중", "")
                .trim();
        String title = basicInfo.getTitle() + " " + titleEnglish;

        WebElement movieInfo = movieDetailPage.findElement(By.cssSelector("dl.ovf"));
        String synopsys = movieDetailPage.findElement(By.cssSelector("p.desc_info")).getText();

        List<WebElement> metadata = movieInfo.findElements(By.cssSelector("dt, dd"));
        String code = metadata.get(1).getText();
        String movieDetails = metadata.get(7).getText();
        String releaseDate = metadata.get(11).getText();
        String productionYear = metadata.get(13).getText();


    }
}
