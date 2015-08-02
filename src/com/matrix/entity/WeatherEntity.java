package com.matrix.entity;

public class WeatherEntity implements Entity {
	
	private String id = "%w";
	
	@Override
	public String getString() {
		return "sunny";
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public String processString(String s) {
		return s.replace(id, this.getString());
	}
	
	
}
