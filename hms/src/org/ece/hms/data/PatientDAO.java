package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.Patient;

public class PatientDAO extends DAO implements iDAO<Patient> {
	protected final DataSource ds = DataSource.INSTANCE;

	@Override
	public List<Patient> findAll() {
		List<Patient> allPatients = new ArrayList<Patient>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM patients");

			// fetch all events from database
			Patient patient;
			
			while (rs.next()) {
				patient = new Patient();
				patient.setUserId(rs.getInt(1));
				patient.setAddress(rs.getString(2));
				patient.setProvince(rs.getString(3));
				patient.setSIN(rs.getInt(4));
				patient.setHealthCardNumber(rs.getInt(5));
				patient.setPhoneNumber(rs.getInt(6));
				patient.setCurrentHealth(rs.getString(7));
				allPatients.add(patient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allPatients;
	}
	
	@Override
	public boolean delete(Patient entity) {
		return execute("DELETE FROM patients WHERE user_id = '" + entity.getUserId() + "'");
	}

	@Override
	public Boolean insert(Patient entity) {
		return execute("INSERT INTO patients(user_id,address,province,SIN,health_card_number,phone_number,current_health) " +
                "VALUES (" + entity.getUserId() + ",'" + entity.getAddress() +
                "','" + entity.getProvince() + "','" + entity.getSIN() + "','" + entity.getHealthCardNumber() + 
                "','" + entity.getPhoneNumber() + "','" + entity.getCurrentHealth() + "')");
	}

	@Override
	public boolean update(Patient entity) {
		return execute("UPDATE patients SET address = '" + entity.getAddress() + 
                "', province = '" + entity.getProvince() + "', SIN = '" + entity.getSIN() +
                "', health_card_number = '" + entity.getHealthCardNumber() + 
                "', phone_number = '" + entity.getPhoneNumber() +
                "', current_health = '" + entity.getCurrentHealth() + 
                "' where user_id = " + entity.getUserId());
	}

}
