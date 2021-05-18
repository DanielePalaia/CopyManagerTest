package com.example.demo;

import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.postgresql.PGConnection;
import org.postgresql.copy.CopyManager;
import java.io.*;


import java.util.List;
import java.util.Properties;

public class JdbcProxy  {
	//private static Logger logger = LogService.getLogger();
	private Connection connection = null;
	private String jdbcString = null;
	private String username = null;
	private String passwd = null;
	private String database = null;
	private String delim = null;
	private String rejectLimit = null;
	private String query = null;
	private String filename = null;
	//private static final String SQL_INSERT = "INSERT INTO TEST (ID, DATA) VALUES (?,?)";

	public JdbcProxy(String jdbcString, String username, String passwd, String database, String query, String filename, String delim)    {

		this.jdbcString = jdbcString;
		this.username = username;
		this.passwd = passwd;
		this.database = database;
		this.query = query;
		this.filename = filename;
		this.delim = delim;
	}

	public void connect()    {

		try {

			// Connect to the db just the first time
			if (connection == null) {
				//logger.info("database connection is null creating one...");
				this.connection = DriverManager.getConnection(jdbcString, username, passwd);
			} else if (connection.isClosed()) {
				//logger.info("database connection is closed creating new one...");
				this.connection = DriverManager.getConnection(jdbcString, username, passwd);
			}

		}
		catch (Exception e)     {
			e.printStackTrace();
		}

		System.out.println("Connected to the database");


	}


	public boolean processEvents() {

		try {

			CopyManager cm = ((PGConnection) connection).getCopyAPI();
			//logger.info("I'm copying with closeable version");
			///Writer writer = new StringWriter();
			//StringWriter stringwriter = new StringWriter();
			FileWriter fileWriter = new FileWriter(filename);
			String copycommand = "copy " +  query + " to stdout with delimiter '"+delim+"'" + ";";
			System.out.println(copycommand);
			cm.copyOut(copycommand, fileWriter);

			fileWriter.close();
			System.out.println("string writer: " + fileWriter);
			//connection.commit();


		} catch (Exception e) {
			//logger.error("Could not insert data to Postgresql/Greenplum.", e);
			e.printStackTrace();
			return true;
		}

		return true;
	}


}