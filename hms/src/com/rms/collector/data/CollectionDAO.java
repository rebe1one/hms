package com.rms.collector.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.rms.collector.model.Collection;

public class CollectionDAO extends DAO implements iDAO<Collection> {

	@Override
	public List<Collection> findAll() {
		List<Collection> allCollections = new ArrayList<Collection>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Collection");

			// fetch all events from database
			Collection collection;
			
			while (rs.next()) {
				collection = new Collection();
				collection.setId(rs.getInt(1));
				collection.setName(rs.getString(2));
				collection.setUserId(rs.getInt(3));
				allCollections.add(collection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allCollections;
	}

	@Override
	public boolean delete(Collection entity) {
		return execute("DELETE FROM Collection WHERE id = '" + entity.getId() + "'");
	}

	@Override
	public Object insert(Collection entity) {
		return execute("INSERT INTO Collection(name,user_id) " +
                "VALUES ('" + entity.getName() + "'," + entity.getUserId() + ")");
	}

	@Override
	public boolean update(Collection entity) {
		return execute("UPDATE Collection SET name = '" + entity.getName() + 
                "', user_id = " + entity.getUserId() + 
                " where id = " + entity.getId());
	}

}
