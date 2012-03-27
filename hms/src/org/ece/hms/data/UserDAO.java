package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.User;

public class UserDAO extends DAO implements iDAO<User> {
	protected final DataSource ds = DataSource.INSTANCE;

	@Override
	public List<User> findAll() {
		List<User> allUserLogins = new ArrayList<User>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM User");

			// fetch all events from database
			User user;
			
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				allUserLogins.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allUserLogins;
	}
	
	public User findByLogin(User user) {
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE username = '" 
					+ user.getUsername() + "'");

			// fetch all events from database
			User result = new User();
			
			while (rs.next()) {
				result.setId(rs.getInt(1));
				result.setUsername(rs.getString(2));
				result.setPassword(rs.getString(3));
			}
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return new User();
		} finally {
		    ds.close();
		}
	}

	@Override
	public boolean delete(User entity) {
		return execute("DELETE FROM User WHERE id = '" + entity.getId() + "'");
	}

	@Override
	public Boolean insert(User entity) {
		return execute("INSERT INTO User(id,username,password) " +
                "VALUES (" + entity.getId() + ",'" + entity.getUsername() +
                "','" + entity.getPassword() + "')");
	}

	@Override
	public boolean update(User entity) {
		return execute("UPDATE User SET username = '" + entity.getUsername() + 
                "', password = '" + entity.getPassword() + "' where id = " + entity.getId());
	}

}
