package com.italy.agrifood;

import com.italy.agrifood.search.BusinessSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgrifoodApplication implements CommandLineRunner {

	@Autowired
	private BusinessSearch businessSearch;

	public static void main(String[] args) {
		SpringApplication.run(AgrifoodApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		businessSearch.startSearch(); // Inițiază căutarea la pornirea aplicației
	}
}
