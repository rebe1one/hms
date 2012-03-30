package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.AppointmentVisitView;
import org.ece.hms.model.PatientUserView;

public class PatientUserViewDAO extends DAO {
	private String sql = "SELECT * from patients, users WHERE patients.user_id = users.id";
	
	public List<PatientUserView> findByName(String name) {
		List<PatientUserView> allViews = new ArrayList<PatientUserView>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(sql + " AND (users.first_name LIKE '" 
					+ name + "%' OR users.last_name LIKE '" + name + "%')");
			// fetch all events from database
			PatientUserView view;
			
			while (rs.next()) {
				view = new PatientUserView();
				view.setUserId(rs.getInt(1));
				view.setAddress(rs.getString(2));
				view.setProvince(rs.getString(3));
				view.setSIN(rs.getInt(4));
				view.setHealthCardNumber(rs.getInt(5));
				view.setPhoneNumber(rs.getInt(6));
				view.setCurrentHealth(rs.getString(7));
				view.setId(rs.getInt(8));
				view.setUsername(rs.getString(9));
				view.setPassword(rs.getString(10));
				view.setRole(rs.getString(11));
				view.setFirstName(rs.getString(12));
				view.setLastName(rs.getString(13));
				view.setActive(rs.getInt(14));
				allViews.add(view);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allViews;
	}
	
	public List<PatientUserView> findUnassigned() {
		List<PatientUserView> allViews = new ArrayList<PatientUserView>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
		    ResultSet rs = stmt.executeQuery("SELECT * FROM patients, users WHERE patients.user_id = users.id AND user_id NOT IN (SELECT to_id FROM `relationships` WHERE `rel_type` = 'DEFAULT_DOCTOR')");
			// fetch all events from database
			PatientUserView view;
			
			while (rs.next()) {
				view = new PatientUserView();
				view.setUserId(rs.getInt(1));
				view.setAddress(rs.getString(2));
				view.setProvince(rs.getString(3));
				view.setSIN(rs.getInt(4));
				view.setHealthCardNumber(rs.getInt(5));
				view.setPhoneNumber(rs.getInt(6));
				view.setCurrentHealth(rs.getString(7));
				view.setId(rs.getInt(8));
				view.setUsername(rs.getString(9));
				view.setPassword(rs.getString(10));
				view.setRole(rs.getString(11));
				view.setFirstName(rs.getString(12));
				view.setLastName(rs.getString(13));
				view.setActive(rs.getInt(14));
				allViews.add(view);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allViews;
	}
}
