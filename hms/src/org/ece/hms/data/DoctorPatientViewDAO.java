package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ece.hms.model.Doctor;
import org.ece.hms.model.DoctorPatientView;
import org.ece.hms.util.Filter;
import org.ece.hms.util.Util;
import org.ece.hms.model.User;
import org.ece.hms.data.UserDAO;;

public class DoctorPatientViewDAO extends DAO {
	private String sql = "SELECT * FROM (SELECT relationships.from_id as doctor_id, patient_user.id as patient_id, patient_user.first_name, patient_user.last_name, latest_appointment_visits.timestamp FROM relationships JOIN patient_user ON (relationships.to_id = patient_user.id) LEFT JOIN latest_appointment_visits ON (latest_appointment_visits.patient_id = patient_user.id)) AS dpv ";
	
	public List<DoctorPatientView> findByDoctorId(int id) {
		List<DoctorPatientView> allViews = new ArrayList<DoctorPatientView>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(sql + " WHERE doctor_id = " + id );
			// fetch all events from database
			
			while (rs.next()) {
				DoctorPatientView view = new DoctorPatientView();
				view.setDoctorId(rs.getInt(1));
				view.setPatientId(rs.getInt(2));
				view.setFirstName(rs.getString(3));
				view.setLastName(rs.getString(4));
				view.setTimestamp(rs.getTimestamp(5));
				allViews.add(view);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allViews;
	}
	
	public List<DoctorPatientView> findByDoctorId(int id, List<Filter> filter) {
		List<DoctorPatientView> allViews = new ArrayList<DoctorPatientView>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
		    String sqlQ = "SELECT * FROM (" + sql + ") AS dpv WHERE doctor_id = " + id;
		    String filters = Util.filterToSQL(filter);
		    if (Util.isNotEmpty(filters)) {
		    	sqlQ += " AND " + Util.filterToSQL(filter);
		    }
		    System.out.println(sqlQ);
			ResultSet rs = stmt.executeQuery(sqlQ);
			// fetch all events from database
			
			while (rs.next()) {
				DoctorPatientView view = new DoctorPatientView();
				view.setDoctorId(rs.getInt(1));
				view.setPatientId(rs.getInt(2));
				view.setFirstName(rs.getString(3));
				view.setLastName(rs.getString(4));
				view.setTimestamp(rs.getTimestamp(5));
				allViews.add(view);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allViews;
	}
	
	public String findDefaultDoctorByPatientId(int id){
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT from_id as doctor_id FROM relationships WHERE to_id=" + id + " AND rel_type='DEFAULT_DOCTOR'");
			
			UserDAO userDAO = new UserDAO();
			User user = new User();;
			while(rs.next()){
				user = userDAO.findById(rs.getInt(1));
			}
			return ("Dr. " + user.getFirstName().toString() + " " + user.getLastName().toString());
		} catch (NullPointerException e){
			return "No Doctor Assigned";
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
		    ds.close();
		}
	}
}
