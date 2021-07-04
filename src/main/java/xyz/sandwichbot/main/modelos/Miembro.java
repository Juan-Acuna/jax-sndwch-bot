package xyz.sandwichbot.main.modelos;

import xyz.sandwichbot.conexion.anotaciones.PrimaryKey;
import xyz.sandwichbot.conexion.anotaciones.PrivateFieldsAllowed;

@PrivateFieldsAllowed
public class Miembro {
	@PrimaryKey
	private long id_miembro;
	private String alwd_cmds;
	private String alwd_cats;
	private long xp;
	private long jaxcoin;
	private long id_guild;
	private long id_team;
	public long getId_miembro() {
		return id_miembro;
	}
	public void setId_miembro(long id_miembro) {
		this.id_miembro = id_miembro;
	}
	public String getAlwd_cmds() {
		return alwd_cmds;
	}
	public void setAlwd_cmds(String alwd_cmds) {
		this.alwd_cmds = alwd_cmds;
	}
	public String getAlwd_cats() {
		return alwd_cats;
	}
	public void setAlwd_cats(String alwd_cats) {
		this.alwd_cats = alwd_cats;
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
	public long getId_guild() {
		return id_guild;
	}
	public void setId_guild(long id_guild) {
		this.id_guild = id_guild;
	}
	public long getId_team() {
		return id_team;
	}
	public void setId_team(long id_team) {
		this.id_team = id_team;
	}
	
}
