package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.FromUserRelationshipView;
import org.ece.hms.util.Filter;
import org.ece.hms.util.Util;

public class FromUserRelationshipViewDAO extends DAO {
	private String sql = "SELECT * FROM relationships JOIN users ON relationships.from_id = users.id";
	
	public List<FromUserRelationshipView> findAll() {
		List<FromUserRelationshipView> allAppts = new ArrayList<FromUserRelationshipView>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// fetch all events from database
			FromUserRelationshipView view;
			
			while (rs.next()) {
				view = new FromUserRelationshipView();
				view.setFromId(rs.getInt(1));
				view.setToId(rs.getInt(2));
				view.setRelationshipType(rs.getString(3));
				view.setUsername(rs.getString(5));
				view.setPassword(rs.getString(6));
				view.setRole(rs.getString(7));
				view.setFirstName(rs.getString(8));
				view.setLastName(rs.getString(9));
				view.setActive(rs.getInt(10));
				allAppts.add(view);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		return allAppts;
	}
	
	public List<FromUserRelationshipView> find(List<Filter> filters) {
		List<FromUserRelationshipView> allAppts = new ArrayList<FromUserRelationshipView>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
		    String filterString = Util.filterToSQL(filters);
		    if (Util.isNotEmpty(filters)) {
		    	sql += " WHERE " + filterString;
		    }
			ResultSet rs = stmt.executeQuery(sql);

			// fetch all events from database
			FromUserRelationshipView view;
			
			while (rs.next()) {
				view = new FromUserRelationshipView();
				view.setFromId(rs.getInt(1));
				view.setToId(rs.getInt(2));
				view.setRelationshipType(rs.getString(3));
				view.setUsername(rs.getString(5));
				view.setPassword(rs.getString(6));
				view.setRole(rs.getString(7));
				view.setFirstName(rs.getString(8));
				view.setLastName(rs.getString(9));
				view.setActive(rs.getInt(10));
				allAppts.add(view);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		return allAppts;
	}
}
