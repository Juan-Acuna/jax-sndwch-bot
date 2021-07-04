package xyz.sandwichbot.main.modelos;

import xyz.sandwichbot.conexion.anotaciones.PrimaryKey;
import xyz.sandwichbot.conexion.anotaciones.PrivateFieldsAllowed;

@PrivateFieldsAllowed
public class MiembroTorneo {
	@PrimaryKey
	private long id_miembro;
	private long id_torneo;
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
