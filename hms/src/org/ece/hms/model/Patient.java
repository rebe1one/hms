package org.ece.hms.model;

public class Patient {
	private int userId;
	private String address;
	private String province;
	private int SIN;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public int getSIN() {
		return SIN;
	}
	public void setSIN(int sIN) {
		SIN = sIN;
	}
	public int getHealthCardNumber() {
		return healthCardNumber;
	}
	public void setHealthCardNumber(int healthCardNumber) {
		this.healthCardNumber = healthCardNumber;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCurrentHealth() {
		return currentHealth;
	}
	public void setCurrentHealth(String currentHealth) {
		this.currentHealth = currentHealth;
	}
	private int healthCardNumber;
	private int phoneNumber;
	private String currentHealth;
}