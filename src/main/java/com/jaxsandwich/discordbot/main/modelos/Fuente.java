package com.jaxsandwich.discordbot.main.modelos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaxsandwich.discordbot.conexion.anotaciones.*;

@PrivateFieldsAllowed
public class Fuente {
	@ExcludeField
	private static Map<String, Fuente> cont = Collections.synchronizedMap(new HashMap<String, Fuente>());
	@PrimaryKey
	private String nombre;
	private String src;
	
	@ExcludeField
	public static final String LOL_INVOCADOR = "lol-invocador";
	@ExcludeField
	public static final String PKMN_POKEMON = "pkmn-pokemon";
	@ExcludeField
	public static final String PKMN_LISTA = "pkmn-lista";
	public Fuente() { }
	public Fuente(String nombre, String src) {
		this.nombre=nombre;
		this.src=src;
	}
	public static Fuente find(String nombre) {
		return Fuente.cont.get(nombre);
	}
	public static void compute(Fuente fuente) {
		cont.put(fuente.nombre, fuente);
	}
	public static ArrayList<Fuente> getAsList() {
		ArrayList<Fuente> l = new ArrayList<Fuente>(cont.values());
		return l;
	}
	public static int getCount() {
		return cont.size();
	}
	public static void load(List<Fuente> l) {
		for(Fuente f : l) {
			Fuente.cont.put(f.nombre,f);
		}
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	
}
