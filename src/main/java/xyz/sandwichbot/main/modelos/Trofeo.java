package xyz.sandwichbot.main.modelos;

import xyz.sandwichbot.conexion.anotaciones.PrimaryKey;
import xyz.sandwichbot.conexion.anotaciones.PrivateFieldsAllowed;

@PrivateFieldsAllowed
public class Trofeo {
	@PrimaryKey
	private long id_trofeo;
	private int posicion;
	private long id_team;
	private long id_torneo;
	public long getId_trofeo() {
		return id_trofeo;
	}
	public void setId_trofeo(long id_trofeo) {
		this.id_trofeo = id_trofeo;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	public long getId_team() {
		return id_team;
	}
	public void setId_team(long id_team) {
		this.id_team = id_team;
	}
	public long getId_torneo() {
		return id_torneo;
	}
	public void setId_torneo(long id_torneo) {
		this.id_torneo = id_torneo;
	}
	
}
