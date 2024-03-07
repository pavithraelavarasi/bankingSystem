package com.sfr.bankingSystem;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;

class Database {
	private Connection con = null;
	static Statement st = null;
	private static Database database = null;

	private Database()
	{
	}
	public static Database getInstance()
	{
		if(database == null)
		{
			database = new Database();
		}
		return database;
	}
	public Connection makeConnection()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			if(con == null)
			{
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/banking","postgres","1234");
				System.out.println("Database opened");
				st = con.createStatement();
				return con;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
	public void close() throws Exception
	{
		try
		{
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
