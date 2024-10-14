package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.extractor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

class DirectorExtractorTest {

    @DisplayName("온클릭 이벤트에서 감독 코드 추출 테스트")
    @Test
    void extractDirectorCode() {
        // given
        String onClickAttr1 = "mstView('people','10085973');return false;";
        String onClickAttr2 = "mstView('people','10000783');return false;";
        // when
        Pattern pattern = Pattern.compile("mstView\\('people','(\\d+)'\\);");
        Matcher matcher1 = pattern.matcher(onClickAttr1);
        Matcher matcher2 = pattern.matcher(onClickAttr2);

        // then
        assertThat(matcher1.find()).isTrue();
        assertThat(matcher1.group(1)).isEqualTo("10085973");

        assertThat(matcher2.find()).isTrue();
        assertThat(matcher2.group(1)).isEqualTo("10000783");
    }
}