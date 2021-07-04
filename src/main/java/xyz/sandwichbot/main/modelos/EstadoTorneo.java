package xyz.sandwichbot.main.modelos;

import xyz.sandwichbot.conexion.anotaciones.PrimaryKey;
import xyz.sandwichbot.conexion.anotaciones.PrivateFieldsAllowed;

@PrivateFieldsAllowed
public class EstadoTorneo {
	@PrimaryKey
	private short id_estado;
	private String nombre;
	public short getId_estado() {
		return id_estado;
	}
	public void setId_estado(short id_estado) {
		this.id_estado = id_estado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
