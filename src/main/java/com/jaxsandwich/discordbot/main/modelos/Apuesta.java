package com.jaxsandwich.discordbot.main.modelos;

import com.jaxsandwich.discordbot.conexion.anotaciones.PrimaryKey;
import com.jaxsandwich.discordbot.conexion.anotaciones.PrivateFieldsAllowed;

@PrivateFieldsAllowed
public class Apuesta {
	@PrimaryKey
	private long id_apuesta;
	private long objetivo;
	private long JaxCoin;
	private long id_miembro;
	private long id_torneo;
	public long getId_apuesta() {
		return id_apuesta;
	}
	public void setId_apuesta(long id_apuesta) {
		this.id_apuesta = id_apuesta;
	}
	public long getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(long objetivo) {
		this.objetivo = objetivo;
	}
	public long getJaxCoin() {
		return JaxCoin;
	}
	public void setJaxCoin(long jaxCoin) {
		JaxCoin = jaxCoin;
	}
	public long getId_miembro() {
		return id_miembro;
	}
	public void setId_miembro(long id_miembro) {
		this.id_miembro = id_miembro;
	}
	public long getId_torneo() {
		return id_torneo;
	}
	public void setId_torneo(long id_torneo) {
		this.id_torneo = id_torneo;
	}
	
}
