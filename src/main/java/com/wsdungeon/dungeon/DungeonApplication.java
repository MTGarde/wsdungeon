package com.wsdungeon.dungeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class DungeonApplication { // NEAIZMIRST IESLEGT PGADMIN DATUBAZI

	public static void main(String[] args) {
		SpringApplication.run(DungeonApplication.class, args);
	}

}
