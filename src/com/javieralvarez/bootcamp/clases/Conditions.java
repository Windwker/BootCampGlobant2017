package com.javieralvarez.bootcamp.clases;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Conditions {

	private DateFormat df = new SimpleDateFormat("dd/MM/YYYY");
	private Date date ;
	private String dateToString;
	private String dayDescription;
	private float temp;
	private float chill;
	private float windSpeed;
	private String sunrise;
	private String sunset;
	private float humidity;
	private float pressure;
	private float visibility;
	private Scanner sc ;
	private int error=0;
	private int sql;
	private Calendar c = Calendar.getInstance();

	public Conditions() {

	}


	
	
	public void setCurrentConditions(){
		
		setDate();
		do{
		try{
			error=0;
		sc= new Scanner(System.in);	
		System.out.println("Ingrese descripcion del clima para HOY: ");
		setDayDescription(sc.next());
		System.out.println("Ingrese temperatura actual: ");
		setTemp(sc.nextFloat());
		System.out.println("Ingrese sensacion termica: ");
		setChill(sc.nextFloat());
		System.out.println("Ingrese velocidad del viento: ");
		setWindSpeed(sc.nextFloat());
		System.out.println("Ingrese amanecer: ");
		setSunrise(sc.next());
		System.out.println("Ingrese atardecer: ");
		setSunset(sc.next());
		System.out.println("Ingrese humedad: ");
		setHumidity(sc.nextFloat());
		System.out.println("Ingrese presion atmosferica: ");
		setPressure(sc.nextFloat());
		System.out.println("Ingrese visibilidad: ");
		setVisibility(sc.nextFloat());
			
		}catch(InputMismatchException IME){
			System.out.println("Error. Formato ingresado no valido");
			error=1;
			
		}
		}while(error==1);
		
		
		try{
		
		Connection con = Conexion.getInstance().getConexion();
		Statement st = con.createStatement();
		PreparedStatement ps=null;
		ResultSet rs = st.executeQuery("SELECT date FROM WeatherGlobant.Weather WHERE type='CC'");
		
		
		while(rs.next()){
			
			
			
			
			if(getDateToString().equals(rs.getString(1))){
				sql=1;
			System.out.println("Se ha actualizado el clima para: " + getDateToString());
				
			}
			else{
				sql=0;
				System.out.println("Inserta nueva.");
			}
			
		}
		
		if(sql==0){
		
		ps=con.prepareStatement("INSERT INTO WeatherGlobant.Weather VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		}
		else if(sql==1){
		ps=con.prepareStatement("UPDATE WeatherGlobant.Weather SET date=?,description=?,temp=?,chill=?,windspeed=?,sunrise=?,sunset=?,humidity=?,pressure=?,visibility=?,type=?,low=?,high=? WHERE date=?");
		ps.setString(14, getDateToString());

		}
		else{
			System.out.println("Nulo");
		}
		
		
		
		ps.setString(1, getDateToString());
		ps.setString(2,	getDayDescription());
		ps.setFloat(3, getTemp());
		ps.setFloat(4, getChill());
		ps.setFloat(5, getWindSpeed());
		ps.setString(6, getSunrise());
		ps.setString(7, getSunset());
		ps.setFloat(8, getHumidity());
		ps.setFloat(9, getPressure());
		ps.setFloat(10, getVisibility());
		ps.setString(11, "CC");
		ps.setFloat(12, 0);
		ps.setFloat(13, 0);
		ps.execute();	
		
		
		}	
catch(Exception e){
	System.out.println("Error SQL modificando Condiciones actuales" +e.getMessage());
}
		
	}
	public void getCurrentConditions(){

		try {
			System.out.println("Clima para "+ getDateToString(0));
			String dateToString = getDateToString(0);
			Connection con = Conexion.getInstance().getConexion();
			Statement st = con.createStatement();
			PreparedStatement ps = null;
			ResultSet rs = st.executeQuery("SELECT date, description, temp, chill, windspeed, sunrise, sunset, humidity, pressure, visibility  FROM WeatherGlobant.Weather WHERE type='CC' && DATE= '"+ dateToString  +"'");
			
			//System.out.println("\nClima para hoy:" +getDateToString());
			while(rs.next()){
				
				
				System.out.println("\n"+rs.getString(2));
				System.out.println("Temperatura: " + rs.getFloat(3));
				System.out.println("Sensacion Termica: " + rs.getFloat(4));
				System.out.println("Velocidad del viento: " + rs.getFloat(5));
				System.out.println("Amanecer: "+rs.getString(6));
				System.out.println("Atarcer: "+rs.getString(7));
				System.out.println("Humedad: " +rs.getFloat(8));
				System.out.println("Presion: "+rs.getFloat(9));
				System.out.println("Visibilidad: " +rs.getFloat(10));
				
			}

		} catch (Exception e) {
			System.out.println("No se pueden obtener los datos de las condiciones actuales. Descripcion Error:" + e.getMessage());
		}
		
		
		
		
	}

	
	
	
	public String getDateToString(int i) {
		Date d = new Date();
		c.setTime(d);
		c.add(Calendar.DATE, i);
		d = c.getTime();
		DateFormat df = new SimpleDateFormat("dd/MM/YYYY");
		dateToString = df.format(d);

		return dateToString;

	}
	
	
	
	public void setDayDescription(String dayDescription) {
		this.dayDescription = dayDescription;
	}


	public void setDate(){
		
	 
	 this.date= new Date();
	}
	
	public String getDateToString(){
		
		dateToString = df.format(date);
		return dateToString;
	}
	
	
	
	public void setTemp(float temp) {
		
		this.temp = temp;
	}

	public void setChill(float chill) {
		this.chill = chill;
	}


	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}

	

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}


	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}


	public void setPressure(float pressure) {
		this.pressure = pressure;
	}


	public void setVisibility(float visibility) {
		this.visibility = visibility;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDayDescription() {
		return dayDescription;
	}

	public float getTemp() {
		return temp;
	}

	public float getChill() {
		return chill;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public String getSunrise() {
		return sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public float getHumidity() {
		return humidity;
	}

	public float getPressure() {
		return pressure;
	}

	public float getVisibility() {
		return visibility;
	}
	
	

	
	
	
	
}
