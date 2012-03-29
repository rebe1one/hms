package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.Finance;

public class FinanceDAO extends DAO implements iDAO<Finance> {
	protected final DataSource ds = DataSource.INSTANCE;

	@Override
	public List<Finance> findAll() {
		List<Finance> allFinances = new ArrayList<Finance>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Finance");

			// fetch all events from database
			Finance finance;
			
			while (rs.next()) {
				finance = new Finance();
				finance.setUserId(rs.getInt(1));
				allFinances.add(finance);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allFinances;
	}

	@Override
	public boolean delete(Finance entity) {
		return execute("DELETE FROM Finance WHERE user_id = '" + entity.getUserId() + "'");
	}

	@Override
	public Boolean insert(Finance entity) {
		return execute("INSERT INTO Finance(user_id) " + "VALUES ('" + entity.getUserId() + "')");
	}
	
	@Override
	public boolean update(Finance entity) {
		return false;
	}

}
