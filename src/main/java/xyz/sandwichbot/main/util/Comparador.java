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
		public static final String Pokemon_LinkId = "[0-9]{1,4}</td><td><a href=\"/wiki/[a-zA-Z2_.È:]{3,12}";
		public static final String Pokemon_Imagen = "https://images.wikidexcdn.net/mwuploads/wikidex/thumb/[a-zA-Z0-9]{1,3}/[a-zA-Z0-9]{1,3}/latest/[0-9]{12,15}/[a-zA-Z2Ò—_ .:·¡È…ÌÕÛ”˙⁄¸%]{3,17}.png/200px-[a-zA-Z2Ò—_ .:·¡È…ÌÕÛ”˙⁄¸%]{3,17}.png";
		public static final String Pokemon_Nombre = "nombrepokemon\" class=\"titulo\">[a-zA-Z2Ò—_ .:·¡È…ÌÕÛ”˙⁄¸]{3,17}<";
		public static final String Pokemon_Id = "numeronacional\">[0-9]{2,4}";
		public static final String Pokemon_Tipo = "href=\"/wiki/Tipo_[a-zA-Z]{4,10}";
		public static final String Pokemon_Habilidad = "Habilidad</a></th><td><a href=\"/wiki/[a-zA-Z0-9Ò—_ .:·¡È…ÌÕÛ”˙⁄¸%]{2,25}";
		public static final String Pokemon_Habilidades = "Habilidades</a></th><td><a href=\"/wiki/(.{2,20})\" title=\"(.{2,20})\">(.{2,20})</a><br><a href=\"/wiki/(.{2,20})";
		
		public static final String XV_Link = "/video[0-9]{3,10}/([a-zA-Z0-9_]{1,100})";
		public static final String RB_ImageQuery = "https://realbooru.com/index.php\\?page=post\\&s=view\\&id=[0-9]{5,7}";
		public static final String RB_Image = "https://realbooru.com//images/[0-9]{1,5}/(.{5,50})\\.[a-zA-Z0-9]{2,5}";
		
		
		
		public static final String[] YoutubeTitle_Cleanner = {"\"title\":\\{\"runs\":\\[\\{\"text\":\"","\"\\}\\],\"accessibility\""};
	}
	
	public static boolean Coincidir(String patron, String texto) {
		return Pattern.matches(patron, texto);
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
