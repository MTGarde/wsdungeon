package com.wsdungeon.dungeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DungeonApplication {

	public static void main(String[] args) {
		SpringApplication.run(DungeonApplication.class, args);
	}

}
