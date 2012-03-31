package org.ece.hms.util;

import java.sql.Timestamp;
import java.util.Date;

public class DateFilter extends Filter {

	public DateFilter(String key, Object value) {
		super(key, value);
	}
	
	@Override
	public String toString() {
		Timestamp day = new Timestamp(((Date)value).getTime());
		Timestamp nextDay = new Timestamp(((Date)value).getTime() + 86400000);
		return key + " BETWEEN '" + day + "' AND '" + nextDay + "'";
	}

}
