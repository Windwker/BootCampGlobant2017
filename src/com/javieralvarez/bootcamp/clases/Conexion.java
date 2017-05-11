package com.javieralvarez.bootcamp.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

	private static Conexion instance;
	private String url = "jdbc:mysql://localhost:3306?&useSSL=false";
	private String usr = "root";
	private String psw = "1234";
	private Connection con;

	
	private  Conexion() {
		try {
			con = DriverManager.getConnection(url, usr, psw);
			Statement st = con.createStatement();
			st.execute("CREATE DATABASE IF NOT EXISTS WeatherGlobant");
			st.execute("CREATE TABLE IF NOT EXISTS WeatherGlobant.Weather(date varchar(40), description varchar(50),temp float, chill float, windspeed float, sunrise varchar(60), sunset varchar(60), humidity float, pressure float, visibility float, type varchar(2),low float, high float)");
	
			
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());;
		}
	}

	
	public Connection getConexion(){
		return con;
	}
	
	public static Conexion getInstance() {
		if(instance == null){
			instance = new Conexion();
		}
		return instance;
	}

}