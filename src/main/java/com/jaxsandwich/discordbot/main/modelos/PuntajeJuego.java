package com.jaxsandwich.discordbot.main.modelos;

import com.jaxsandwich.discordbot.conexion.anotaciones.PrivateFieldsAllowed;

@PrivateFieldsAllowed
public class PuntajeJuego {
	private long id_miembro;
	private int id_juego;
	private long puntaje;
	public long getId_miembro() {
		return id_miembro;
	}
	public void setId_miembro(long id_miembro) {
		this.id_miembro = id_miembro;
	}
	public int getId_juego() {
		return id_juego;
	}
	public void setId_juego(int id_juego) {
		this.id_juego = id_juego;
	}
	public long getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(long puntaje) {
		this.puntaje = puntaje;
	}
	
}
