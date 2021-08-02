package com.jaxsandwich.discordbot.main.modelos;

import java.time.LocalDate;

import com.jaxsandwich.discordbot.conexion.anotaciones.PrimaryKey;
import com.jaxsandwich.discordbot.conexion.anotaciones.PrivateFieldsAllowed;

@PrivateFieldsAllowed
public class Torneo {
	@PrimaryKey
	private long id_torneo;
	private String titulo;
	private LocalDate fecha;
	private char individual;
	private int tamano_team;
	private int cupos;
	private long xp;
	private long jaxcoin;
	private short id_estado;
	private String url_transmision;
	private long id_guild;
	private int id_juego;
	public long getId_torneo() {
		return id_torneo;
	}
	public void setId_torneo(long id_torneo) {
		this.id_torneo = id_torneo;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public String getFechaAsString() {
		return fecha.toString();
	}
	public void setFecha(String fecha) {
		this.fecha = LocalDate.parse(fecha);
	}
	public char getIndividual() {
		return individual;
	}
	public void setIndividual(char individual) {
		this.individual = individual;
	}
	public int getTamano_team() {
		return tamano_team;
	}
	public void setTamano_team(int tamano_team) {
		this.tamano_team = tamano_team;
	}
	public int getCupos() {
		return cupos;
	}
	public void setCupos(int cupos) {
		this.cupos = cupos;
	}
	public long getXp() {
		return xp;
	}
	public void setXp(long xp) {
		this.xp = xp;
	}
	public long getJaxcoin() {
		return jaxcoin;
	}
	public void setJaxcoin(long jaxcoin) {
		this.jaxcoin = jaxcoin;
	}
	public short getId_estado() {
		return id_estado;
	}
	public void setId_estado(short id_estado) {
		this.id_estado = id_estado;
	}
	public String getUrl_transmision() {
		return url_transmision;
	}
	public void setUrl_transmision(String url_transmision) {
		this.url_transmision = url_transmision;
	}
	public long getId_guild() {
		return id_guild;
	}
	public void setId_guild(long id_guild) {
		this.id_guild = id_guild;
	}
	public int getId_juego() {
		return id_juego;
	}
	public void setId_juego(int id_juego) {
		this.id_juego = id_juego;
	}
	
}
