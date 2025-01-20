package com.polar_moviechart.scrapingservice;

import com.polar_moviechart.scrapingservice.domain.service.kobis.moviedata.ScrapingScheduler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScrapingServiceApplication {

	private final ScrapingScheduler scrapingScheduler;

	public static void main(String[] args) {
		SpringApplication.run(ScrapingServiceApplication.class, args);
	}

	public ScrapingServiceApplication(ScrapingScheduler scrapingScheduler) {
		this.scrapingScheduler = scrapingScheduler;
	}

	@Bean
	public CommandLineRunner run() {
		return (args) -> {
			String startDate = "2024-12-06";
			String endData = "2024-12-01";
			scrapingScheduler.doScrape(startDate, endData);
		};
	}
}
