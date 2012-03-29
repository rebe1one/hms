package org.ece.hms.model;

public class RoleType {
	private String roleId;
	private String description;
	
	// Role stuff
	public static String DOCTOR = "DOCTOR";
	public static String PATIENT = "PATIENT";
	public static String STAFF = "STAFF";
	public static String ADMIN = "ADMIN";
	public static String FINANCE = "FINANCE";
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}