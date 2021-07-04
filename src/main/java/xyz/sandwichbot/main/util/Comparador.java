package xyz.sandwichbot.main.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Comparador {
	private static Pattern p;
	private static Matcher m;
	
	public static class Patrones{
		public static final String Youtube_Link = "/watch\\?v=(.{11})";
		public static final String Youtube_Title = "\"title\":\\{\"runs\":\\[\\{\"text\":\"(.{1,100})\"\\}\\],\"accessibility\"";
		public static final String Wikidex_R_Link = "/wiki/Lista_de_Pok%C3%A9mon_seg%C3%BAn_la_Pok%C3%A9dex_de_[a-zA-Z]{5,15}";
		public static final String Pokemon_LinkId = "[0-9]{1,4}</td><td><a href=\"/wiki/[a-zA-Z2_.%:]{3,12}";
		
		/* POKEMON */
		//BASICO
		public static final String Pokemon_Nombre = "<title>[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35} - WikiDex, la enciclopedia Pokémon<";
		public static final String Pokemon_Id = ">#<span style=\"font-size:[0-9]{1,4}%;\">[0-9]{3}<";
		public static final String Pokemon_Imagen = "og:image\" content=\"https://images.wikidexcdn.net/mwuploads/wikidex/[0-9a-zA-Z/]{1,15}/latest/[0-9]{6,16}/[0-9a-zA-ZñÑ_% .:áéíóúÁÉÍÓÚ]{1,35}.png/[0-9]{1,4}px-[0-9a-zA-ZñÑ_% .:áéíóúÁÉÍÓÚ]{1,35}.png";
		public static final String Pokemon_Imagen2 = "og:image\" content=\"https://images.wikidexcdn.net/mwuploads/wikidex/[0-9a-zA-Z/]{1,15}/latest/[0-9]{6,16}/[0-9a-zA-ZñÑ_% .:áéíóúÁÉÍÓÚ]{1,35}.png";
		public static final String Pokemon_Tipo = ">Tipo</a></th><td><a href=\"/wiki/Tipo_[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}";
		public static final String Pokemon_Tipos = "title=\"Tipo\">Tipos</a></th><td><a href=\"/wiki/Tipo_[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}\" title=\"Tipo [0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}\"><img alt=\"Tipo [0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}.gif\" src=\"https://images.wikidexcdn.net/mwuploads/wikidex/[0-9a-zA-Z]{1,3}/[0-9a-zA-Z]{1,3}/latest/[0-9]{6,16}/Tipo_[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}.gif\" decoding=\"async\" [0-9a-zA-Z \"=]{2,45} /></a> <a href=\"/wiki/Tipo_[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}";
		public static final String Pokemon_Habilidad = ">Habilidad</a></th><td><a href=\"/wiki/[0-9a-zA-ZñÑ_ .%:áéíóúÁÉÍÓÚ]{1,35}";
		public static final String Pokemon_Habilidades = ">Habilidades</a></th><td><a href=\"/wiki/[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}\" title=\"[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}\">[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}</a><br /><a href=\"/wiki/[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}";
		
		//STATS Y OTROS
		public static final String Pokemon_Grito = "https://images.wikidexcdn.net/mwuploads/wikidex/[0-9a-zA-Z]{1,3}/[0-9a-zA-Z]{1,3}/latest/[0-9]{6,16}/Grito_de_[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}.ogg";
		
		
		//IMAGENES
		public static final String Pokemon_3D_7 = "https://images.wikidexcdn.net/mwuploads/wikidex/[0-9a-zA-Z/]{1,15}/latest/[0-9a-zA-Z]{1,16}/[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}_XY.gif";
		public static final String Pokemon_3D_7s= "https://images.wikidexcdn.net/mwuploads/wikidex/[0-9a-zA-Z/]{1,15}/latest/[0-9a-zA-Z]{1,16}/[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}_XY_variocolor.gif";
		public static final String Pokemon_3D_8 = "https://images.wikidexcdn.net/mwuploads/wikidex/[0-9a-zA-Z/]{1,15}/latest/[0-9a-zA-Z]{1,16}/[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}_EpEc.gif/[0-9]{1,4}px-[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}_EpEc.gif";
		public static final String Pokemon_3D_8s= "https://images.wikidexcdn.net/mwuploads/wikidex/[0-9a-zA-Z/]{1,15}/latest/[0-9a-zA-Z]{1,16}/[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}_EpEc_variocolor.gif/[0-9]{1,4}px-[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}_EpEc_variocolor.gif";
		
		/* POKEMON */
		
		public static final String XV_Link = "/video[0-9]{3,10}/([a-zA-Z0-9_]{1,100})";
		
		public static final String[] YoutubeTitle_Cleanner = {"\"title\":\\{\"runs\":\\[\\{\"text\":\"","\"\\}\\],\"accessibility\""};
	}
	
	public static boolean Coincide(String patron, String texto) {
		p = Pattern.compile(patron);
		m = p.matcher(texto);
		return m.find();
	}
	
	public static String Encontrar(String patron, String texto) {
		texto = texto.replaceAll("\\&amp;", "&");
		p = Pattern.compile(patron);
		m = p.matcher(texto);
		if(m.find()) {
			return m.group(0);
		}
		return null;
	}
	public static ArrayList<String> EncontrarTodos(String patron, String texto) {
		texto = texto.replaceAll("\\&amp;", "&");
		p = Pattern.compile(patron);
		m = p.matcher(texto);
		ArrayList<String> lista = new ArrayList<String>();
		while(m.find()) {
			lista.add(m.group(0));
		}
		return lista;
	}
	public static String Limpiar(String[] patrones, String texto) {
		for(String p : patrones) {
			texto = texto.replaceAll(p, "");
		}
		return texto;
	}
	public static ArrayList<String> LimpiarTodos(String[] patrones, ArrayList<String> lista) {
		ArrayList<String> l = new ArrayList<String>();
		for(int i=0;i<lista.size();i++) {
			for(String p : patrones) {
				l.add(lista.get(i).replaceAll(p, ""));
			}
		}
		return l;
	}
}
