package org.ece.hms.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Util {
	public static boolean isEmpty(String s) {
		if (s == null || s.length() == 0) {
			return true;
		}
		return false;
	}
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}
	public static boolean isEmpty(Object o) {
		return o == null ? true : false;
	}
	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}
	
	public static String filterToSQL(List<Filter> filter) {
		StringBuilder b = new StringBuilder();
		for (Filter entry : filter) {
			if (entry.equals(Filter.AND)) {
				b.append(" AND ");
				continue;
			} else if (entry.equals(Filter.OR)) {
				b.append(" OR ");
				continue;
			} else if (entry.equals(Filter.LB)) {
				b.append(" (");
				continue;
			} else if (entry.equals(Filter.RB)) {
				b.append(") ");
				continue;
			}
			b.append(entry.toString());
		}
		return b.toString();
	}
	
	public static String buildSQLString(String query, List<Filter> filters) {
		String sqlFilters = Util.filterToSQL(filters);
		if (isNotEmpty(sqlFilters)) {
			return new StringBuilder(query).append(sqlFilters).toString();
		}
		return query;
	}
	
	public static String getPasswordHash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		String passwordString = new String(password);
		md.update(passwordString.getBytes());
		BigInteger hash = new BigInteger(1, md.digest());
		return hash.toString(16);
	}
}
