package com.jaxsandwich.discordbot.configuracion;

public class Global {
	
	public static final String BOT_VERSION = "0.7.2";
	
	public static final String DISCORD_TOKEN = System.getenv().get("DISCORD_TOKEN");
	public static final String BOT_PREFIX = System.getenv().get("JAX_PREFIX");
	
	public static final String JAX_TOKEN = System.getenv().get("JAX_TOKEN");
	
	public static final String DB_HOST = System.getenv().get("DB_HOST");
	public static final String DB_USER = System.getenv().get("DB_USER");
	public static final String DB_PWD = System.getenv().get("DB_PWD");
	public static final String DB_SCHEMA = System.getenv().get("DB_SCHEMA");
	
}
