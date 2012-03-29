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
			ResultSet rs = stmt.executeQuery("SELECT * FROM users");

			// fetch all events from database
			User user;
			
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setRole(rs.getString(4));
				user.setFirstName(rs.getString(5));
				user.setLastName(rs.getString(6));
				user.setActive(rs.getInt(7));
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" 
					+ user.getUsername() + "'");

			// fetch all events from database
			User result = new User();
			
			while (rs.next()) {
				result.setId(rs.getInt(1));
				result.setUsername(rs.getString(2));
				result.setPassword(rs.getString(3));
				result.setRole(rs.getString(4));
				result.setFirstName(rs.getString(5));
				result.setLastName(rs.getString(6));
				result.setActive(rs.getInt(7));
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
		return execute("DELETE FROM users WHERE id = '" + entity.getId() + "'");
	}

	@Override
	public Boolean insert(User entity) {
		return execute("INSERT INTO users(username,password,role,first_name,last_name,active) " +
                "VALUES ('" + entity.getUsername() +
                "','" + entity.getPassword() + "','" + entity.getRole() + "','" + entity.getFirstName() + 
                "','" + entity.getLastName() + "','" + entity.getActive() + "')");
	}

	@Override
	public boolean update(User entity) {
		return execute("UPDATE users SET username = '" + entity.getUsername() + 
                "', password = '" + entity.getPassword() + "', role = '" + entity.getRole() +
                "', first_name = '" + entity.getFirstName() + "', last_name = '" + entity.getLastName() +
                "', active = '" + entity.getActive() + "' where id = " + entity.getId());
	}

}
