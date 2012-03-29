package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.RoleType;

public class RoleTypeDAO extends DAO implements iDAO<RoleType> {
	protected final DataSource ds = DataSource.INSTANCE;

	@Override
	public List<RoleType> findAll() {
		List<RoleType> allRoleTypes = new ArrayList<RoleType>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM RoleType");

			// fetch all events from database
			RoleType roleType;
			
			while (rs.next()) {
				roleType = new RoleType();
				roleType.setRoleId(rs.getString(1));
				roleType.setDescription(rs.getString(2));
				allRoleTypes.add(roleType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allRoleTypes;
	}

	@Override
	public boolean delete(RoleType entity) {
		return execute("DELETE FROM RoleType WHERE role_id = '" + entity.getRoleId() + "'");
	}

	@Override
	public Boolean insert(RoleType entity) {
		return execute("INSERT INTO RoleType(role_id,description) " +
                "VALUES (" + entity.getRoleId() + ",'" + entity.getDescription() + "')");
	}

	@Override
	public boolean update(RoleType entity) {
		return execute("UPDATE RoleType SET description = '" + entity.getDescription() + 
                "' where role_id = " + entity.getRoleId());
	}

}
