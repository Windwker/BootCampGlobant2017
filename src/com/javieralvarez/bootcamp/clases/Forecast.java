package com.javieralvarez.bootcamp.clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Forecast {
	private Calendar c = Calendar.getInstance();
	// private static Forecast instance;
	private String text;
	private Date date;
	private float high, low;
	private String dateToString;
	private Scanner sc;
	private String dayDescription;
	private ArrayList<Forecast> lista = new ArrayList<Forecast>();
	private int sql;
	private int error;

	public Forecast() {
	}

	public Forecast(String dateToString, String description, float low, float high) {
		this.dateToString = dateToString;
		this.dayDescription = description;
		this.low = low;
		this.high = high;
	}

	public void setForecastConditions() {

		for (int i = 1; i < 6; i++) {
			do{
			try {
				error=0;
				sc = new Scanner(System.in);
				System.out.println("\n*Configurar Forecast*");
				System.out.println("Condiciones para dia: " + getDateToString(i));
				dateToString = getDateToString(i);
				System.out.println("Ingrese descripcion del clima: ");
				dayDescription = sc.next();
				System.out.println("Ingrese minima: ");
				low = sc.nextFloat();
				System.out.println("Ingrese maxima: ");
				high = sc.nextFloat();

				lista.add(new Forecast(dateToString, dayDescription, low, high));

			} catch (Exception e) {
				System.out.println("Error. Ingrese valores correctamente");
				error=1;
			}
			}while(error==1);
		}

/*		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i).getDateToString(i + 1) + " " + lista.get(i).getDayDescription() + " "
					+ lista.get(i).getLow() + " " + lista.get(i).getHigh());
		}*/

		//////////////////////////////////////// FORECAST
		//////////////////////////////////////// ///////////////////////////////////////////////////////////////
		try {
			Connection con = Conexion.getInstance().getConexion();
			Statement st = con.createStatement();
			PreparedStatement ps = null;
			ResultSet rs;

			rs = st.executeQuery("SELECT date FROM WeatherGlobant.Weather WHERE type='FC'");

			sql = 0;

			for (int i = 0; i < lista.size(); i++) {

				while (rs.next() && sql != 1) {
					if (lista.get(i).getDateToString(i + 1).equals(rs.getString(1))) {
						sql = 1;
						System.out.println("Se hace update del Forecast");

					} else {
						sql = 0;
						System.out.println("Inserta Forecast");
					}
				}

				if (sql == 0) {
					ps = con.prepareStatement("INSERT INTO WeatherGlobant.Weather VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
				} else if (sql == 1) {
					ps = con.prepareStatement(
							"UPDATE WeatherGlobant.Weather SET date=?,description=?,temp=?,chill=?,windspeed=?,sunrise=?,sunset=?,humidity=?,pressure=?,visibility=?, type=?, low=?, high=? WHERE date=?");
					ps.setString(14, getDateToString(i + 1));
				}
				ps.setString(1, lista.get(i).getDateToString(i + 1));
				ps.setString(2, lista.get(i).getDayDescription());
				ps.setFloat(3, 0);
				ps.setFloat(4, 0);
				ps.setFloat(5, 0);
				ps.setString(6, "null");
				ps.setString(7, "null");
				ps.setFloat(8, 0);
				ps.setFloat(9, 0);
				ps.setFloat(10, 0);
				ps.setString(11, "FC");
				ps.setFloat(12, lista.get(i).getLow());
				ps.setFloat(13, lista.get(i).getHigh());
				ps.execute(); // ps.close();

			}

		} catch (Exception e) {
			System.out.println("Error al modificar Forecast: " + e.getMessage());
		}

	}

	public void getForecastConditions() {
		try {

			Connection con = Conexion.getInstance().getConexion();
			Statement st = con.createStatement();
			PreparedStatement ps = null;
			ResultSet rs = st.executeQuery("SELECT date, description, low, high FROM WeatherGlobant.Weather WHERE type='FC'");
			System.out.println("\nClima para los proximos 5 dias:");
			System.out.println("");
			while(rs.next()){
				
				System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+"Min: " +rs.getFloat(3)+" "+"Max: "+rs.getFloat(4));
				
			}

		} catch (Exception e) {
			System.out.println("No se pueden obtener los datos del forecast. Descripcion Error:" + e.getMessage());
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

	public void setDate(Date date, int i) {
		c.setTime(date);
		c.add(Calendar.DATE, i);
		this.date = c.getTime();

	}

	public String getDayDescription() {
		return dayDescription;
	}

	public void setDayDescription(String text) {
		this.text = text;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

}
