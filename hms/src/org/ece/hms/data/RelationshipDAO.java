package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.Relationship;

public class RelationshipDAO extends DAO implements iDAO<Relationship> {
	protected final DataSource ds = DataSource.INSTANCE;

	@Override
	public List<Relationship> findAll() {
		List<Relationship> allRelationships = new ArrayList<Relationship>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Relationship");

			// fetch all events from database
			Relationship relationship;
			
			while (rs.next()) {
				relationship = new Relationship();
				relationship.setFromId(rs.getInt(1));
				relationship.setToId(rs.getInt(2));
				relationship.setRelationshipType(rs.getString(3));
				allRelationships.add(relationship);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allRelationships;
	}

	@Override
	public boolean delete(Relationship entity) {
		return execute("DELETE FROM Relationship WHERE from_id = '" + entity.getFromId() + "'");
	}

	@Override
	public Boolean insert(Relationship entity) {
		return execute("INSERT INTO Relationship(from_id,to_id,relationship_type) " +
                "VALUES ('" + entity.getFromId() + "','" + entity.getToId() + "','" +
				entity.getRelationshipType() + "')");
	}

	@Override
	public boolean update(Relationship entity) {
		return execute("UPDATE Relationship SET relationship_type = '" + entity.getRelationshipType() + 
				"', to_id = '" + entity.getToId() + "' where from_id = " + entity.getFromId());
	}

}
