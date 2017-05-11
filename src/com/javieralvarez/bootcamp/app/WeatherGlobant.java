package com.javieralvarez.bootcamp.app;

import com.javieralvarez.bootcamp.clases.Conditions;
import com.javieralvarez.bootcamp.clases.Forecast;

public class WeatherGlobant{
	
 public static void main(String[] args) {
	
	Conditions cc = new Conditions();
	Forecast fc = new Forecast();
	cc.setCurrentConditions();	
	fc.setForecastConditions();
	cc.getCurrentConditions();
	fc.getForecastConditions();


	
	
	
	

}
	
	
}




