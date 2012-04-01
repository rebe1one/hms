package org.ece.hms.model;

public class Doctor {
	private int userId;
	
	public Doctor() {
		//empty constructor
	}
	
	public Doctor(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}