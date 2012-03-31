package org.ece.hms.util;

public class Filter {
	protected String key;
	protected Object value;
	
	public static Filter AND = new Filter("AND", "AND");
	public static Filter OR = new Filter("OR", "OR");
	public static Filter LB = new Filter("LB", "LB");
	public static Filter RB = new Filter("RB", "RB");
	
	public Filter(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(key);
		if (value.getClass().equals(String.class)) {
			b.append(" LIKE '%");
			b.append(value);
			b.append("%'");
		} else {
			b.append(" = ");
			b.append(value);
		}
		return b.toString();
	}
}
