package org.nypl.journalsystem;

import org.nypl.journalsystem.core.IPublisher;

public class Publisher implements IPublisher {
	public String name;
	public String location;
	
	public Publisher(String name, String location) {
		this.name = name;
		this.location = location;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public String getName() {
		return this.name;
	}
}
