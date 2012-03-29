package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.ece.hms.model.RelationshipType;

public class RelationshipTypeDAO extends DAO implements iDAO<RelationshipType> {
	protected final DataSource ds = DataSource.INSTANCE;

	@Override
	public List<RelationshipType> findAll() {
		List<RelationshipType> allRelationshipTypes = new ArrayList<RelationshipType>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM RelationshipType");

			// fetch all events from database
			RelationshipType relationshipType;
			
			while (rs.next()) {
				relationshipType = new RelationshipType();
				relationshipType.setRelationshipType(rs.getString(1));
				relationshipType.setDescription(rs.getString(2));
				allRelationshipTypes.add(relationshipType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allRelationshipTypes;
	}

	@Override
	public boolean delete(RelationshipType entity) {
		return execute("DELETE FROM RelationshipType WHERE relationship_type = '" + entity.getRelationshipType() + "'");
	}

	@Override
	public Boolean insert(RelationshipType entity) {
		return execute("INSERT INTO RelationshipType(relationship_type,description) " +
                "VALUES ('" + entity.getRelationshipType() + "','" + entity.getDescription() + "')");
	}

	@Override
	public boolean update(RelationshipType entity) {
		return execute("UPDATE RelationshipType SET description = '" + entity.getDescription() + 
				"' where relationship_type = " + entity.getRelationshipType());
	}

}
