package org.ece.hms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
	protected final DataSource ds = DataSource.INSTANCE;
	
	protected boolean execute(String sql) {
		System.out.println(sql);
		try {
            Statement stmt = ds.getStatement();
            stmt.execute(sql);
            if (stmt != null) {
                stmt.close();
            }
            
            return true;
        } catch (SQLException e) {
        	e.printStackTrace();
            return false;
        } finally {
            ds.close();
        }
	}
	
	protected int executeReturn(String sql) {
		try {
            Statement stmt = ds.getStatement();
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            int result = 0;
            if (rs.next()) {
                result=rs.getInt(1);
            }
            if (stmt != null) {
                stmt.close();
            }
            return result;
        } catch (SQLException e) {
        	e.printStackTrace();
            return 0;
        } finally {
            ds.close();
        }
	}
}
