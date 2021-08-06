package com.jaxsandwich.discordbot.configuracion;

public class Global {
	public static String ENV_BETA = "beta";
	public static String ENV_MAIN = "main";
	public static final String ENV = ENV_BETA;
	
	public static final String DISCORD_TOKEN = System.getenv().get((ENV.equals(ENV_MAIN)?"DISCORD_TOKEN":"DISCORD_TOKEN_TEST"));
	public static final String BOT_PREFIX = (ENV.equals(ENV_MAIN)?"s.":"ss.");
	
	public static final String JAX_TOKEN = System.getenv().get("JAX_TOKEN");
	
	public static final String DB_HOST = System.getenv().get("DB_HOST");
	public static final String DB_USER = System.getenv().get("DB_USER");
	public static final String DB_PWD = System.getenv().get("DB_PWD");
	public static final String DB_SCHEMA = System.getenv().get("DB_SCHEMA");
	
}
