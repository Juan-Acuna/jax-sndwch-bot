package com.jaxsandwich.discordbot.main;

public class Reproduccion {
	public static String cancion;
	public static estadoReproduccion estado = estadoReproduccion.stoped;
	public enum estadoReproduccion{
		playing,
		paused,
		stoped,
	}
}
