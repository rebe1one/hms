package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.Doctor;

public class DoctorDAO extends DAO implements iDAO<Doctor> {
	protected final DataSource ds = DataSource.INSTANCE;

	@Override
	public List<Doctor> findAll() {
		List<Doctor> allDoctors = new ArrayList<Doctor>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM doctors");

			// fetch all events from database
			Doctor doctor;
			
			while (rs.next()) {
				doctor = new Doctor();
				doctor.setUserId(rs.getInt(1));
				allDoctors.add(doctor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allDoctors;
	}
	
	public Doctor findByUserId(int userId) {
		Doctor doctor = new Doctor();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM doctors WHERE user_id = " + userId);

			// fetch all events from database
			
			if (rs.next()) {
				doctor.setUserId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return doctor;
	}

	@Override
	public boolean delete(Doctor entity) {
		return execute("DELETE FROM doctors WHERE user_id = '" + entity.getUserId() + "'");
	}

	@Override
	public Boolean insert(Doctor entity) {
		return execute("INSERT INTO doctors(user_id) " + "VALUES ('" + entity.getUserId() + "')");
	}
	
	@Override
	public boolean update(Doctor entity) {
		return false;
	}

}
