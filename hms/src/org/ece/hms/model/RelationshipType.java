package org.ece.hms.model;

public class RelationshipType {
	public static String ASSISTING_DOCTOR = "ASSISTING_DOCTOR";
	public static String DEFAULT_DOCTOR = "DEFAULT_DOCTOR";
	public static String DOCTOR_TO_STAFF = "DOCTOR_TO_STAFF";
	private String relationshipType;
	public String getRelationshipType() {
		return relationshipType;
	}
	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private String description;
}