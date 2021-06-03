package xyz.sandwichframework.core.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Values {
	protected static Map<String, Map<Language, String>> values = (Map<String, Map<Language, String>>) Collections.synchronizedMap(new HashMap<String, Map<Language, String>>());
	private static boolean initialized = false;
	
	
	public static String value(String id, Language lang) {
		
		return "";
	}
	
	public static void initialize() {
		if(Values.initialized)
			return;
		
		Values.initialized=true;
	}
}
