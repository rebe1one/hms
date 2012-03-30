package org.ece.hms.model;

import java.sql.Timestamp;
import java.util.Date;

public class AppointmentVisitView {
	private int doctorId;
	private int patientId;
	private Date date;
	private int appointmentLength;
	private int appointmentId;
	private int visitLength;
	private String diagnosis;
	private String prescription;
	private String scheduling;
	private String comments;
	private Timestamp timestamp;
	private int createdBy;
	
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getAppointmentLength() {
		return appointmentLength;
	}
	public void setAppointmentLength(int appointmentLength) {
		this.appointmentLength = appointmentLength;
	}
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public int getVisitLength() {
		return visitLength;
	}
	public void setVisitLength(int visitLength) {
		this.visitLength = visitLength;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public String getScheduling() {
		return scheduling;
	}
	public void setScheduling(String scheduling) {
		this.scheduling = scheduling;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

}
