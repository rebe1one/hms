package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.Appointment;
import org.ece.hms.model.AppointmentVisitUsersView;

public class AppointmentDAO extends DAO implements iDAO<Appointment> {
	protected final DataSource ds = DataSource.INSTANCE;

	@Override
	public List<Appointment> findAll() {
		List<Appointment> allAppointments = new ArrayList<Appointment>();
		try {
			// get connection
			Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM appointments");

			// fetch all events from database
			Appointment appointment;

			while (rs.next()) {
				appointment = new Appointment();
				appointment.setId(rs.getInt(1));
				appointment.setDoctorId(rs.getInt(2));
				appointment.setPatientId(rs.getInt(3));
				appointment.setDate(rs.getTimestamp(4));
				appointment.setLength(rs.getInt(5));
				allAppointments.add(appointment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ds.close();
		}

		return allAppointments;
	}

	@Override
	public boolean delete(Appointment entity) {
		return execute("DELETE FROM appointments WHERE id = '" + entity.getId()
				+ "'");
	}

	@Override
	public Boolean insert(Appointment entity) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return execute("INSERT INTO appointments(doctor_id, patient_id, date, length) "
				+ "VALUES ('"
				+ entity.getDoctorId()
				+ "','"
				+ entity.getPatientId()
				+ "','"
				+ sdf.format(entity.getDate())
				+ "', '" + entity.getLength() + "')");
	}

	public boolean update(Appointment entity) {
		return execute("UPDATE appointments SET doctor_id = '"
				+ entity.getDoctorId() + "', patient_id = '"
				+ entity.getPatientId() + "', date = '" + entity.getDate()
				+ "', length = '" + entity.getLength()
				+ "' where id = '" + entity.getId() + "'");
	}

}
