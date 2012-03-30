package org.ece.hms.model;

public class User {
	private int id;
	private String username;
	private String password;
	private String role;
	private String firstName;
	private String lastName;
	private int active;
	
	// Active stuff
	public static int ACTIVE = 1;
	public static int INACTIVE = 2;
	
	public User() {
		// empty constructor
	}
	
	public User(String username, String password, String role, String firstName, String lastName, int active) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
	}
	
	public String getName() {
		return firstName + " " + lastName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
}