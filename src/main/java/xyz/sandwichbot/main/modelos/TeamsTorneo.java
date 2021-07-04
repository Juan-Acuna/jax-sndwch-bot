package xyz.sandwichbot.main.modelos;

import xyz.sandwichbot.conexion.anotaciones.PrivateFieldsAllowed;

@PrivateFieldsAllowed
public class TeamsTorneo {
	private long id_team;
	private long id_torneo;
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
