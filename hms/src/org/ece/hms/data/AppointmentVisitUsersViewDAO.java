package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.Appointment;
import org.ece.hms.model.AppointmentVisitUsersView;

public class AppointmentVisitUsersViewDAO extends DAO {
	private String sql = "SELECT * FROM (SELECT appointments.doctor_id, appointments.patient_id, appointments.date, appointments.length AS appointment_length, visits.id AS visit_id, CONCAT(users.first_name, ' ', users.last_name) AS patient_name, CONCAT(doctors.first_name, ' ', doctors.last_name) AS doctor_name, appointments.id AS appointment_id FROM appointments LEFT JOIN visits ON appointments.id = visits.appointment_id LEFT JOIN users ON appointments.patient_id = users.id LEFT JOIN users AS doctors ON appointments.doctor_id = doctors.id) AS avu WHERE visit_id IS NULL";

	public List<AppointmentVisitUsersView> findAll() {
		List<AppointmentVisitUsersView> allAppts = new ArrayList<AppointmentVisitUsersView>();
		try {
			// get connection
			Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// fetch all events from database
			AppointmentVisitUsersView view;

			while (rs.next()) {
				view = new AppointmentVisitUsersView();
				view.setDoctorId(rs.getInt(1));
				view.setPatientId(rs.getInt(2));
				view.setDate(rs.getTimestamp(3));
				view.setAppointmentLength(rs.getInt(4));
				view.setVisitId(rs.getInt(5));
				view.setPatientName(rs.getString(6));
				view.setDoctorName(rs.getString(7));
				view.setAppointmentId(rs.getInt(8));
				allAppts.add(view);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ds.close();
		}
		return allAppts;
	}

	public List<AppointmentVisitUsersView> findByDoctorId(int id) {
		List<AppointmentVisitUsersView> allAppts = new ArrayList<AppointmentVisitUsersView>();
		try {
			// get connection
			Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(sql + " AND doctor_id = " + id);

			// fetch all events from database
			AppointmentVisitUsersView view;

			while (rs.next()) {
				view = new AppointmentVisitUsersView();
				view.setDoctorId(rs.getInt(1));
				view.setPatientId(rs.getInt(2));
				view.setDate(rs.getTimestamp(3));
				view.setAppointmentLength(rs.getInt(4));
				view.setVisitId(rs.getInt(5));
				view.setPatientName(rs.getString(6));
				view.setDoctorName(rs.getString(7));
				view.setAppointmentId(rs.getInt(8));
				allAppts.add(view);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ds.close();
		}
		return allAppts;
	}

	public boolean delete(AppointmentVisitUsersView entity) {
		return execute("DELETE FROM appointments WHERE id = '"
				+ entity.getAppointmentId() + "'");
	}
}
