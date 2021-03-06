package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.AppointmentVisitView;
import org.ece.hms.util.Filter;
import org.ece.hms.util.Util;

public class AppointmentVisitViewDAO extends DAO {
	private String sql = "SELECT * FROM (SELECT appointments.doctor_id, appointments.patient_id, appointments.date, "
			+ "appointments.length AS appointment_length, visits.appointment_id, visits.length AS visit_length, "
			+ "visits.diagnosis, visits.prescription, visits.scheduling, visits.comments, visits.timestamp, "
			+ "visits.created_by, visits.id AS visit_id FROM appointments JOIN visits ON appointments.id = visits.appointment_id) AS avv ";
	
	public List<AppointmentVisitView> find(List<Filter> filters) {
		List<AppointmentVisitView> allVisits = new ArrayList<AppointmentVisitView>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(Util.buildSQLString(sql + " WHERE ", filters));

			// fetch all events from database
			AppointmentVisitView view;
			
			while (rs.next()) {
				view = new AppointmentVisitView();
				view.setDoctorId(rs.getInt(1));
				view.setPatientId(rs.getInt(2));
				view.setDate(rs.getTimestamp(3));
				view.setAppointmentLength(rs.getInt(4));
				view.setAppointmentId(rs.getInt(5));
				view.setVisitLength(rs.getInt(6));
				view.setDiagnosis(rs.getString(7));
				view.setPrescription(rs.getString(8));
				view.setScheduling(rs.getString(9));
				view.setComments(rs.getString(10));
				view.setTimestamp(rs.getTimestamp(11));
				view.setCreatedBy(rs.getInt(12));
				view.setVisitId(rs.getInt(13));
				allVisits.add(view);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		return allVisits;
	}
	
	public List<AppointmentVisitView> findByPatientId(int patientId) {
		List<AppointmentVisitView> allVisits = new ArrayList<AppointmentVisitView>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(sql + " WHERE patient_id = " + patientId + " ORDER BY timestamp DESC");

			// fetch all events from database
			AppointmentVisitView view;
			
			while (rs.next()) {
				view = new AppointmentVisitView();
				view.setDoctorId(rs.getInt(1));
				view.setPatientId(rs.getInt(2));
				view.setDate(rs.getTimestamp(3));
				view.setAppointmentLength(rs.getInt(4));
				view.setAppointmentId(rs.getInt(5));
				view.setVisitLength(rs.getInt(6));
				view.setDiagnosis(rs.getString(7));
				view.setPrescription(rs.getString(8));
				view.setScheduling(rs.getString(9));
				view.setComments(rs.getString(10));
				view.setTimestamp(rs.getTimestamp(11));
				view.setCreatedBy(rs.getInt(12));
				view.setVisitId(rs.getInt(13));
				allVisits.add(view);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		return allVisits;
	}
	
	public List<AppointmentVisitView> findByDoctorId(int doctorId) {
		List<AppointmentVisitView> allVisits = new ArrayList<AppointmentVisitView>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(sql + " WHERE doctor_id = " + doctorId + " ORDER BY timestamp DESC");

			// fetch all events from database
			AppointmentVisitView view;
			
			while (rs.next()) {
				view = new AppointmentVisitView();
				view.setDoctorId(rs.getInt(1));
				view.setPatientId(rs.getInt(2));
				view.setDate(rs.getTimestamp(3));
				view.setAppointmentLength(rs.getInt(4));
				view.setAppointmentId(rs.getInt(5));
				view.setVisitLength(rs.getInt(6));
				view.setDiagnosis(rs.getString(7));
				view.setPrescription(rs.getString(8));
				view.setScheduling(rs.getString(9));
				view.setComments(rs.getString(10));
				view.setTimestamp(rs.getTimestamp(11));
				view.setCreatedBy(rs.getInt(12));
				view.setVisitId(rs.getInt(13));
				allVisits.add(view);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		return allVisits;
	}
}
