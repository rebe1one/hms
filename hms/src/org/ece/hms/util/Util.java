package org.ece.hms.util;

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
}
