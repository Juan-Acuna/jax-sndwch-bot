package xyz.sandwichbot.main.util;

import java.util.ArrayList;

public class Tools {
	public static String arrayToString(String[] array) {
		return arrayToString(array, ",", true);
	}
	public static String arrayToString(String[] array, boolean space) {
		return arrayToString(array, ",", space);
	}
	public static String arrayToString(String[] array, String sep) {
		return arrayToString(array, sep, true);
	}
	public static String arrayToString(String[] array, String sep, boolean space) {
		array = clearArray(array);
		String str = "";
		for(String s : array) {
			str += sep + (space?" ":"") + s;
		}
		return str.substring(sep.length());
	}
	
	public static String arrayToString(int[] array) {
		return arrayToString(array, ",", true);
	}
	public static String arrayToString(int[] array, boolean space) {
		return arrayToString(array, ",", space);
	}
	public static String arrayToString(int[] array, String sep) {
		return arrayToString(array, sep, true);
	}
	public static String arrayToString(int[] array, String sep, boolean space) {
		String str = "";
		for(int s : array) {
			str += sep + (space?" ":"") + s;
		}
		return str.substring(sep.length());
	}
	
	public static String[] clearArray(String[] array) {
		ArrayList<String> l = new ArrayList<String>();
		if(array!=null) {
			for(String s : array) {
				if(s.trim().replace(" ","").length()<=0) {
					continue;
				}
				l.add(s);
			}
		}
		return (String[]) l.toArray();
	}
}
