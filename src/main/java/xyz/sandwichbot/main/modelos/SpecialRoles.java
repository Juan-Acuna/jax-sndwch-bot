package xyz.sandwichbot.main.modelos;

import xyz.sandwichbot.conexion.anotaciones.PrimaryKey;
import xyz.sandwichbot.conexion.anotaciones.PrivateFieldsAllowed;

@PrivateFieldsAllowed
public class SpecialRoles {
	@PrimaryKey
	private String rol_function;
	private long rol_id;
	private long id_guild;
	public String getRol_function() {
		return rol_function;
	}
	public void setRol_function(String rol_function) {
		this.rol_function = rol_function;
	}
	public long getRol_id() {
		return rol_id;
	}
	public void setRol_id(long rol_id) {
		this.rol_id = rol_id;
	}
	public long getId_guild() {
		return id_guild;
	}
	public void setId_guild(long id_guild) {
		this.id_guild = id_guild;
	}
	
}
