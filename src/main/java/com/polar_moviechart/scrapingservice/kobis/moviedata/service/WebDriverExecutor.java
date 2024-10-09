package com.polar_moviechart.scrapingservice.kobis.moviedata.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
        // 기존 입력 내용 삭제 (BACK_SPACE로 입력된 날짜 삭제)
        for (int i = 0; i < 8; i++) {
            searchStartDate.sendKeys(Keys.BACK_SPACE);
        }
        // 새로운 날짜 입력
        searchStartDate.sendKeys(formattedDate);

        // 종료 날짜 입력 필드 찾기
        searchStartDate = driver.findElement(By.cssSelector("div.tf_comm > input#sSearchTo"));
        // 기존 입력 내용 삭제
        for (int i = 0; i < 8; i++) {
            searchStartDate.sendKeys(Keys.BACK_SPACE);
        }
        // 새로운 날짜 입력
        searchStartDate.sendKeys(formattedDate);

        // ESC 키를 눌러서 날짜 선택 창 닫기
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).perform();
    }

    public void initDriver() {
        WebDriverManager.chromedriver().setup();
    }

    public List<WebElement> getColumnInfo(WebElement row) {
        return row.findElements(By.tagName("td"));
    }
}
