package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.DoctorPatientView;

public class StaffPatientViewDAO extends DAO {
	public List<DoctorPatientView> findByStaffId(int staffId) {
		List<DoctorPatientView> allViews = new ArrayList<DoctorPatientView>();

		int doctorId = 0;
		try {
			// get connection
			Statement stmt = ds.getStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT `from_id` FROM `relationships` WHERE `rel_type` = 'DOCTOR_TO_STAFF' AND `to_id` = '"
							+ staffId + "'");
			if (rs.first()) {
				doctorId = rs.getInt(1);
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ds.close();
		}

		try {
			// get connection
			Statement stmt = ds.getStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT relationships.from_id as doctor_id, patient_user.id as patient_id, patient_user.first_name, patient_user.last_name, latest_appointment_visits.timestamp FROM relationships JOIN patient_user ON (relationships.to_id = patient_user.id) LEFT JOIN latest_appointment_visits ON (latest_appointment_visits.patient_id = patient_user.id) AND doctor_id = "
							+ doctorId);
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
}
