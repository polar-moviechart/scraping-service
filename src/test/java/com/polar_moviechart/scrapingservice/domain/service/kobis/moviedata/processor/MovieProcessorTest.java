package com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class MovieProcessorTest {

    @DisplayName("")
    @Test
    void test() {
        // given
        String currentDirectory = System.getProperty("user.dir");
        Path path = Paths.get(currentDirectory);
        System.out.println("path = " + path);
        // when

        // then
    }
}