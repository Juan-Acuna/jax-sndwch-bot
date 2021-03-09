package xyz.sandwichbot.main.util;

import java.awt.Color;
import java.util.ArrayList;

public class Tools {
	public static String arrayToString(String[] array) {
		return arrayToString(array, ",", true);
	}
	public static String arrayToString(String[] array, boolean space) {
		return arrayToString(array, ",", space);
	}
	public static String arrayToString(String[] array, String sep) {
		return arrayToString(array, sep, true);
	}
	public static String arrayToString(String[] array, String sep, boolean space) {
		array = clearArray(array);
		String str = "";
		for(String s : array) {
			str += sep + (space?" ":"") + s;
		}
		return str.substring(sep.length());
	}
	
	public static String arrayToString(int[] array) {
		return arrayToString(array, ",", true);
	}
	public static String arrayToString(int[] array, boolean space) {
		return arrayToString(array, ",", space);
	}
	public static String arrayToString(int[] array, String sep) {
		return arrayToString(array, sep, true);
	}
	public static String arrayToString(int[] array, String sep, boolean space) {
		String str = "";
		for(int s : array) {
			str += sep + (space?" ":"") + s;
		}
		return str.substring(sep.length());
	}
	
	public static String[] clearArray(String[] array) {
		ArrayList<String> l = new ArrayList<String>();
		if(array!=null) {
			for(String s : array) {
				if(s.trim().replace(" ","").length()<=0) {
					continue;
				}
				l.add(s);
			}
		}
		return (String[]) l.toArray();
	}
	public static String replaceFromString(String patron, String texto, String reemplazo) {
		if(reemplazo==null) {
			reemplazo = "";
		}
		if(patron==null) {
			return texto;
		}
		if(texto!=null) {
			return texto.replaceFirst(patron,reemplazo);
		}
		return null;
	}
	public static String replaceAllFromString(String patron, String texto, String reemplazo) {
		if(reemplazo==null) {
			reemplazo = "";
		}
		if(texto!=null) {
			return texto.replaceAll(patron,reemplazo);
		}
		return null;
	}
	public static String quotePattern(String patron, String[] caracteres) {
		for(String s : caracteres) {
			patron = patron.replaceAll("\\"+s, "\\\\"+s);
		}
		return patron;
	}
	public static String quotePattern(String patron, String caracter) {
		return patron = patron.replaceAll("\\"+caracter, "\\\\"+caracter);
	}
	public static String toMarkDownLink(String texto) {
		ArrayList<String> links = Comparador.EncontrarTodos("\\{%link%\\}(.{0,500})\\{%link%\\}[ ]{0,100}\\{%href%\\}(.{1,1000})\\{%href%\\}", texto);
		for(String l : links) {
			String ph = Comparador.Encontrar("[ ]{0,100}\\{%href%\\}(.{0,1000})\\{%href%\\}",l);
			ph = Tools.replaceAllFromString(" ", Tools.replaceAllFromString("\\s", ph, ""), "");
			ph = Tools.replaceAllFromString("\\{%href%\\}", ph, "");
			ph = "(" + Tools.toValidHttpUrl(ph) + ")";
			String[] cars = {"{","}"};
			String str = Tools.replaceFromString("\\{%link%\\}", l, "[");
			str = Tools.replaceFromString("\\{%link%\\}", str,"]");
			str = Tools.replaceFromString("\\{%href%\\}(.{0,1000})\\{%href%\\}", str,ph);
			str = Tools.replaceAllFromString(Tools.quotePattern(l, cars), texto, str);
			str = Tools.replaceAllFromString("\\][\\s]{0,100}\\(", str, "](");
			return str;
		}
		return "";
	}
	public static String toValidHttpUrl(String link) {
		if(link==null) {
			return null;
		}
		link.trim();
		if(link.startsWith("http")) {
			return link;
		}else if(link.startsWith(":")) {
			return "http" + link;
		}else if(link.startsWith("//")) {
			return "http:" + link;
		}else if(link.startsWith("www.")) {
			return "http://" + link;
		}else if(link.startsWith("ww.")) {
			return "http://w" + link;
		}else if(link.startsWith("w.")) {
			return "http://ww" + link;
		}else if(link.startsWith(".")) {
			return "http://www" + link;
		}else if(!link.startsWith("/") || !link.startsWith(".")) {
			return "http://" + link;
		}
		return null;
	}
	public static Color stringColorCast(String color) {
		color = color.toLowerCase();
		switch(color) {
		case "rojo":
		case "red":
			return Color.RED;
		case "verde":
		case "green":
			return Color.GREEN;
		case "azul":
		case "blue":
			return Color.BLUE;
		case "blanco":
		case "white":
			return Color.WHITE;
		case "negro":
		case "black":
			return Color.BLACK;
		case "naranjo":
		case "orange":
			return Color.ORANGE;
		case "amarillo":
		case "yellow":
			return Color.YELLOW;
		case "cian":
		case "cyan":
			return Color.CYAN;
		case "magenta":
			return Color.MAGENTA;
		case "gris":
		case "gray":
			return Color.GRAY;
		case "gris_oscuro":
		case "dark_gray":
			return Color.DARK_GRAY;
		default:
			if(color.length() <= 6 && Comparador.Coincide("[0-9a-fA-F#]{3,7}", color)) {
				if(!color.startsWith("#")) {
					color = "#" + color;
				}
				if(color.length()==4) {
					color = "#" + color.substring(1,2) + color.substring(1,2) + color.substring(2,3) + color.substring(2,3) + color.substring(3,4) + color.substring(3,4);
				}
				try {
					Color c = Color.decode(color);
					return c;
				}catch(Exception e) {
					return Color.GRAY;
				}
			}
			return Color.GRAY;
		}
	}
}
