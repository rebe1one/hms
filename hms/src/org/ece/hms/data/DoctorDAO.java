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
			ResultSet rs = stmt.executeQuery("SELECT * FROM Doctor");

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

	@Override
	public boolean delete(Doctor entity) {
		return execute("DELETE FROM Doctor WHERE user_id = '" + entity.getUserId() + "'");
	}

	@Override
	public Boolean insert(Doctor entity) {
		return execute("INSERT INTO Doctor(user_id) " + "VALUES (" + entity.getUserId() + "')");
	}
	
	@Override
	public boolean update(Doctor entity) {
		return false;
	}

}
