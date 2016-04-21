package com.thunder.controller;

public class Position {
	private double lat;
	private double lng;
	
	public Position(double lat, double lng){
		this.setLat(lat);
		this.setLng(lng);
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
}
