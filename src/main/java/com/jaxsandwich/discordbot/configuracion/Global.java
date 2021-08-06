package com.jaxsandwich.discordbot.configuracion;

public class Global {
	private static String BETA = "beta";
	private static String MAIN = "main";
	private static final String ENV = BETA;
	
	public static final String DISCORD_TOKEN = System.getenv().get((ENV.equals(MAIN)?"DISCORD_TOKEN":"DISCORD_TOKEN_TEST"));
	public static final String JAX_TOKEN = System.getenv().get("JAX_TOKEN");
	
	public static final String DB_HOST = System.getenv().get("DB_HOST");
	public static final String DB_USER = System.getenv().get("DB_USER");
	public static final String DB_PWD = System.getenv().get("DB_PWD");
	public static final String DB_SCHEMA = System.getenv().get("DB_SCHEMA");
	
}
