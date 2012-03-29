package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.Staff;

public class StaffDAO extends DAO implements iDAO<Staff> {
	protected final DataSource ds = DataSource.INSTANCE;

	@Override
	public List<Staff> findAll() {
		List<Staff> allStaffs = new ArrayList<Staff>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Staff");

			// fetch all events from database
			Staff staff;
			
			while (rs.next()) {
				staff = new Staff();
				staff.setUserId(rs.getInt(1));
				staff.setDoctorId(rs.getInt(2));
				allStaffs.add(staff);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allStaffs;
	}

	@Override
	public boolean delete(Staff entity) {
		return execute("DELETE FROM Staff WHERE user_id = '" + entity.getUserId() + "'");
	}

	@Override
	public Boolean insert(Staff entity) {
		return execute("INSERT INTO Staff(user_id,doctor_id) " +
                "VALUES (" + entity.getUserId() + ",'" + entity.getDoctorId() + "')");
	}

	@Override
	public boolean update(Staff entity) {
		return execute("UPDATE Staff SET doctor_id = '" + entity.getDoctorId() + 
                "' where user_id = " + entity.getUserId());
	}

}
