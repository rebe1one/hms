package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.Visit;

public class VisitDAO extends DAO implements iDAO<Visit> {
	protected final DataSource ds = DataSource.INSTANCE;

	@Override
	public List<Visit> findAll() {
		List<Visit> allVisits = new ArrayList<Visit>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Visit");

			// fetch all events from database
			Visit visit;
			
			while (rs.next()) {
				visit = new Visit();
				visit.setId(rs.getInt(1));
				visit.setAppointmentId(rs.getInt(2));
				visit.setLength(rs.getInt(3));
				visit.setDiagnosis(rs.getString(4));
				visit.setPrescription(rs.getString(5));
				visit.setScheduling(rs.getString(6));
				visit.setComments(rs.getString(7));
				visit.setTimestamp(rs.getTimestamp(8));
				visit.setCreatedBy(rs.getInt(9));
				allVisits.add(visit);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allVisits;
	}
	
	@Override
	public boolean delete(Visit entity) {
		return execute("DELETE FROM Visit WHERE id = '" + entity.getId() + "'");
	}

	@Override
	public Boolean insert(Visit entity) {
		return execute("INSERT INTO Visit(id,appointment_id,length,diagnosis,prescription,scheduling,comments,timestamp,created_by) " +
                "VALUES (" + entity.getId() + ",'" + entity.getAppointmentId() + 
                "','" + entity.getLength() + "','" + entity.getDiagnosis() + "','" +
                entity.getPrescription() + "','" + entity.getScheduling() + "','" +
                entity.getComments() + "','" + entity.getTimestamp() + "','" +
                entity.getCreatedBy() + "')");
	}

	@Override
	public boolean update(Visit entity) {
		return execute("UPDATE Visit SET appointment_id = '" + entity.getAppointmentId() + 
                "', length = '" + entity.getLength() + "', diagnosis = '" + entity.getDiagnosis() +
                "', prescription = '" + entity.getPrescription() + 
                "', scheduling = '" + entity.getScheduling() +
                "', comments = '" + entity.getComments() +
                "', timestamp = '" + entity.getTimestamp() + 
                "', created_by = '" + entity.getCreatedBy() + 
                "' where id = " + entity.getId());
	}

}
