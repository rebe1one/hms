package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.Appointment;

public class AppointmentDAO extends DAO implements iDAO<Appointment> {
	protected final DataSource ds = DataSource.INSTANCE;

	@Override
	public List<Appointment> findAll() {
		List<Appointment> allAppointments = new ArrayList<Appointment>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Appointment");

			// fetch all events from database
			Appointment appointment;
			
			while (rs.next()) {
				appointment = new Appointment();
				appointment.setId(rs.getInt(1));
				appointment.setDoctorId(rs.getInt(2));
				appointment.setPatientId(rs.getInt(3));
				appointment.setDate(rs.getDate(4));
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
		return execute("DELETE FROM Appointment WHERE id = '" + entity.getId() + "'");
	}

	@Override
	public Boolean insert(Appointment entity) {
		return execute("INSERT INTO Appointment(id,doctor_id,patient_id,date) " +
                "VALUES (" + entity.getId() + ",'" + entity.getDoctorId() +
                "','" + entity.getPatientId() + "','" + entity.getDate() + "')");
	}

	@Override
	public boolean update(Appointment entity) {
		return execute("UPDATE Appointment SET doctor_id = '" + entity.getDoctorId() + 
                "', patient_id = '" + entity.getPatientId() + "', date = '" + entity.getDate() +
                "' where id = " + entity.getId());
	}

}
