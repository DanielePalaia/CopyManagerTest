package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import java.io.*;
import java.util.*;

@SpringBootApplication
public class DemoApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String[] args) throws Exception {

		// Loading ini file
		FileInputStream in;
		Properties jdbcProperties = new Properties();

		try {
			in = new FileInputStream("./properties.ini");
			jdbcProperties.load(in);
		}
		catch (Exception e)  {
			e.printStackTrace();
		}

		String jdbcString = jdbcProperties.getProperty("jdbcString");
		String username = jdbcProperties.getProperty("username");
		String password = jdbcProperties.getProperty("password");
		String database = jdbcProperties.getProperty("database");
		String query = jdbcProperties.getProperty("query");
		String filename = jdbcProperties.getProperty("filename");
		String delim = jdbcProperties.getProperty("delim");

		JdbcProxy databaseproxy = new JdbcProxy(jdbcString, username, password, database, query, filename, delim);
		databaseproxy.connect();
		databaseproxy.processEvents();


	}

}
