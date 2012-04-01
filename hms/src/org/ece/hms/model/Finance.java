package org.ece.hms.model;

public class Finance {
	private int userId;

	public Finance() {
		//empty constructor
	}
	
	public Finance(int userId) {
		this.userId = userId;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}