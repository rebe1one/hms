package org.ece.hms.model;

public class FromUserRelationshipView {
	private int fromId;
	private int toId;
	private String relationshipType;
	private String username;
	private String password;
	private String role;
	private String firstName;
	private String lastName;
	private int active;
	
	// Active stuff
	public static int ACTIVE = 1;
	public static int INACTIVE = 2;
	
	public FromUserRelationshipView() {
		// empty constructor
	}
	
	public FromUserRelationshipView(String username, String password, String role, String firstName, String lastName, int active) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
	}
	
	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	public String getName() {
		return firstName + " " + lastName;
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
	public boolean isActive() {
		return active == 1;
	}
}