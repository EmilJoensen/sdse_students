package edu.sdse.csvprocessor;

public class CityRecord {
	
	int id; int year; String city; int population;
	
	public CityRecord(int id, int year, String city, int population) {
		this.id = id;
		this.year = year;
		this.city = city;
		this.population = population;
	}
	
	public String toString() {
		
		return "id: " + Integer.toString(id) + ", year: " + Integer.toString(year) + ", city: " + city + ", population: " + Integer.toString(population);
		
	}

}