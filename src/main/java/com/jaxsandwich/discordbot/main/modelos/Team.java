package com.jaxsandwich.discordbot.main.modelos;

import com.jaxsandwich.discordbot.conexion.anotaciones.PrimaryKey;
import com.jaxsandwich.discordbot.conexion.anotaciones.PrivateFieldsAllowed;

@PrivateFieldsAllowed
public class Team {
	@PrimaryKey
	private long id_team;
	private String nombre;
	private String tag;
	private String icono;
	private long id_guild;
	public long getId_team() {
		return id_team;
	}
	public void setId_team(long id_team) {
		this.id_team = id_team;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getIcono() {
		return icono;
	}
	public void setIcono(String icono) {
		this.icono = icono;
	}
	public long getId_guild() {
		return id_guild;
	}
	public void setId_guild(long id_guild) {
		this.id_guild = id_guild;
	}
	
}
