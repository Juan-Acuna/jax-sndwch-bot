package com.jaxsandwich.discordbot.main.modelos;

import com.jaxsandwich.discordbot.conexion.anotaciones.PrimaryKey;
import com.jaxsandwich.discordbot.conexion.anotaciones.PrivateFieldsAllowed;

@PrivateFieldsAllowed
public class Juego {
	@PrimaryKey
	private int id_juego;
	private String nombre;
	public int getId_juego() {
		return id_juego;
	}
	public void setId_juego(int id_juego) {
		this.id_juego = id_juego;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
