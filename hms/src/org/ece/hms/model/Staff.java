package org.ece.hms.model;

public class Staff {
	private int userId;
	private int doctorId;
	public Staff() {
		//empty constructor
	}
	public Staff(int userId, int doctorId) {
		this.userId = userId;
		this.doctorId = doctorId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
}