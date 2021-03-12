package xyz.sandwichbot.main.util;

import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

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
		if(array==null) {
			return "";
		}
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
		}else {
			return null;
		}
		String[] arr = new String[l.size()];
		for(int i = 0; i < l.size(); i++) {
			arr[i] = l.get(i);
		}
		return arr;
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
		if(patron==null) {
			return texto;
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
	public static String tryToMarkDownLink(String texto) {
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
			str = Tools.replaceAllFromString("\\][\\s]{0,100}\\(", str, "](");
			str = Tools.replaceAllFromString(Tools.quotePattern(l, cars), texto, str);
			return str;
		}
		return texto;
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
	public static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	public static String encriptSHA256(String texto) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
			return Tools.bytesToHex(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String ToURLencoded(String texto)
	{
		try {
			return URLEncoder.encode(texto, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String ToURLencoded(String texto, String codificacion) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(texto, codificacion);
	}
	public static String getRandomGuy() {
		String[] color   = {"rojo","verde","azul","amarillo","blanco","blanco con manchas","negro","gris","naranja","rosa","marrón","turqueza"};
		String[] persona = {"un weón","una vieja qla","una señora","un viejo ql","una weona","una ex-monja","el mati","el corxea ql","el waldo ql","el barsinsom","el piñera ql","un paco ql","un marihuano ql"};
		String[] accion  = {"tocando una guitarra","orinando en la cuneta","lamiendo un dildo","durmiendo en un carrito de supermercado","llorando en una silla","\"jugando\" con un vibrador anal de 12 velocidades sincronizable con el celular","acariciando una paloma","fumandose un porro","tratando de sacarse una costilla😏","comiendose un aliado"};
		String[] ropa    = {"con un polerón","con un gorro","en bata","con una polera","en calzonsillos","en calzones","en pelota","con una chaqueta","con una falda","en traje de baño","en pijama","en un disfraz de pikachu"};
		
		Random r = new Random(System.currentTimeMillis());
		
		int selcol = r.nextInt(color.length);
		int selper = r.nextInt(persona.length);
		int selacc = r.nextInt(accion.length);
		int selrop = r.nextInt(ropa.length);
		
		return persona[selper] + " " + accion[selacc] + (accion[selacc].startsWith("\"")?"":" " + (ropa[selrop].startsWith("en")?ropa[selrop]:ropa[selrop] + " color " + color[selcol]));
	}
}
