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
			String startDate = "2004-06-24";
			String endData = "2004-06-24";
			scrapingScheduler.doScrape(startDate, endData);
		};
	}
}
