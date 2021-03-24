package xyz.sandwichbot.main.util;

import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichbot.main.Constantes.JaxSandwich;

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
	public static byte[] cifrar(String sinCifrar) throws Exception {
		final byte[] bytes = sinCifrar.getBytes("UTF-8");
		final Cipher aes = Tools.obtieneCipher(true);
		final byte[] cifrado = aes.doFinal(bytes);
		return cifrado;
	}

	public static String descifrar(byte[] cifrado) throws Exception {
		final Cipher aes = Tools.obtieneCipher(false);
		final byte[] bytes = aes.doFinal(cifrado);
		final String sinCifrar = new String(bytes, "UTF-8");
		return sinCifrar;
	}

	public static byte[] cifrar(byte[] sinCifrar) throws Exception {
		final Cipher aes = Tools.obtieneCipher(true);
		final byte[] cifrado = aes.doFinal(sinCifrar);
		return cifrado;
	}
	
	public static String descifrar(String cifrado) throws Exception {
		final Cipher aes = Tools.obtieneCipher(false);
		final byte[] bytes = aes.doFinal(cifrado.getBytes());
		final String sinCifrar = new String(bytes, "UTF-8");
		return sinCifrar;
	}
	
	private static Cipher obtieneCipher(boolean paraCifrar) throws Exception {
		final String frase = "FraseLargaConDiferentesLetrasNumerosYCaracteresEspeciales_áÁéÉíÍóÓúÚüÜñÑ1234567890!#%$&()=%_NO_USAR_ESTA_FRASE!_";
		final MessageDigest digest = MessageDigest.getInstance("SHA");
		digest.update(frase.getBytes("UTF-8"));
		final SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");

		final Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		if (paraCifrar) {
			aes.init(Cipher.ENCRYPT_MODE, key);
		} else {
			aes.init(Cipher.DECRYPT_MODE, key);
		}

		return aes;
	}
	public static String ToURLencoded(String texto, String codificacion) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(texto, codificacion);
	}
	public static String toBase64(String texto){
		Base64.Encoder e = Base64.getEncoder();
		try {
			return e.encodeToString(texto.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	public static String toBase64(byte[] texto){
		Base64.Encoder e = Base64.getEncoder();
		return e.encodeToString(texto);
	}
	public static String fromBase64(String texto){
		Base64.Decoder e = Base64.getDecoder();
		try {
			return new String(e.decode(texto),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	public static byte[] fromBase64ToBytes(String texto){
		Base64.Decoder e = Base64.getDecoder();
		return e.decode(texto);
	}
	
	public static String getRandomGuy() {
		String[] color   = {"rojo","verde","azul","amarillo","blanco","blanco con manchas","negro","gris","naranja","rosa","marrón","turqueza"};
		String[] persona = {"un weón","una vieja qla","una señora","un viejo ql","una weona","una ex-monja","el mati","una prostituta","el corxea ql","el waldo ql","Tulio Triviño","el barsinsom","el piñera ql","un paco ql","un marihuano ql","un otaku ql","tu vieja"};
		String[] accion  = {"tocando una guitarra","orinando en la cuneta","lamiendo un dildo","durmiendo en un carrito de supermercado","llorando en una silla","\"jugando\" con un vibrador anal de 12 velocidades sincronizable con el celular","acariciando una paloma","fumandose un porro","tratando de sacarse una costilla😏","comiendose un aliado"};
		String[] ropa    = {"con un polerón","con un gorro","en bata de baño","con una polera","en calzonsillos","en calzones","en pelota","con una chaqueta","con una falda","en traje de baño","en pijama","en un disfraz de pikachu"};
		
		Random r = new Random(System.currentTimeMillis());
		
		int selcol = r.nextInt(color.length);
		int selper = r.nextInt(persona.length);
		int selacc = r.nextInt(accion.length);
		int selrop = r.nextInt(ropa.length);
		
		return persona[selper] + " " + accion[selacc] + (accion[selacc].startsWith("\"")?"":" " + (ropa[selrop].startsWith("en")?ropa[selrop]:ropa[selrop] + " color " + color[selcol]));
	}
	public static EarrapeSRC getRandomEarrapeSource() {
		Random r = new Random(System.currentTimeMillis());
		EarrapeSRC[] src = {
					new EarrapeSRC("Ozuna Farsante","https://youtu.be/O_iwe46pRXc?t=49",50000,9000),
					new EarrapeSRC("bebesitabebelin","https://youtu.be/sJVqyQ6ou6s?t=22",22000,10000),
					new EarrapeSRC("Monsters Inc. Theme","https://www.youtube.com/watch?v=EXexOTRaAz4",6000,10000),
					new EarrapeSRC("Baila Conmigo","https://youtu.be/l9aEHzSUVAg?t=26",26000,12000),
					new EarrapeSRC("Wii Sports Theme(short)","https://www.youtube.com/watch?v=JzGTxHrFG84",5000),
					//new EarrapeSRC("Wii Sports Theme(long)","https://www.youtube.com/watch?v=JzGTxHrFG84",35000),
					new EarrapeSRC("Ibai Farsante","https://www.youtube.com/watch?v=7SwzsSVYG4k",10000, 15000),
					new EarrapeSRC("Thomas the train theme","https://www.youtube.com/watch?v=rBjkkHmb_oY&t=5s",6000,10000)
				};
		EarrapeSRC esrc = src[r.nextInt(src.length)];
		System.out.println("src: " + esrc.nombre + " | " + esrc.url + " | " + esrc.duracion/1000);
		return esrc;
	}
	public static MessageEmbed stringToEmb(String texto) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.addField(null, texto, false);
		return eb.build();
	}
	public static MessageEmbed stringToEmb(String texto,String color) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.addField(null, texto, false);
		eb.setColor(Tools.stringColorCast(color));
		return eb.build();
	}
	public static class JAX{
		public static boolean auth(String id) throws Exception {
			String hc = ClienteHttp.peticionHttp(JaxSandwich.JAX.A + "?hash=" + Tools.encriptSHA256(id) + "&sal=" + Tools.encriptSHA256(SandwichBot.ActualBot().getJAX()));
			JSONObject j = new JSONObject(hc);
			return j.getBoolean("res");
		}
		public static boolean register(String id, String newId) throws Exception {
			String hc = ClienteHttp.peticionHttp(JaxSandwich.JAX.R + "?hash=" + Tools.encriptSHA256(id) + "&sal=" + Tools.encriptSHA256(SandwichBot.ActualBot().getJAX()) + "&new=" + Tools.encriptSHA256(newId));
			JSONObject j = new JSONObject(hc);
			return j.getBoolean("res");
		}
		public static boolean run(String cmd, MessageChannel c) {
			try {
				String hc = ClienteHttp.peticionHttp(JaxSandwich.JAX.C + "?run=" + cmd + "&sal=" + Tools.encriptSHA256(SandwichBot.ActualBot().getJAX()));
				JSONObject j = new JSONObject(hc);
				switch(cmd) {
				case "dia55":
					if(!j.getBoolean("res")) {
						String ret = j.getString("return");
						ret = Tools.descifrar(ret);
						EmbedBuilder eb = new EmbedBuilder();
						eb.setTitle(ret.split(";")[0]);
						eb.setDescription(ret.split(";")[1]);
						c.sendMessage(eb.build()).queue();
					}
					break;
				}
				return j.getBoolean("res");
			}catch(Exception e) {
				return false;
			}
		}
		public static boolean run(String cmd) {
			try {
				String hc = ClienteHttp.peticionHttp(JaxSandwich.JAX.C + "?run=" + cmd + "&sal=" + Tools.encriptSHA256(SandwichBot.ActualBot().getJAX()));
				JSONObject j = new JSONObject(hc);
				return j.getBoolean("res");
			}catch(Exception e) {
				return false;
			}
		}
	}
	public static class EarrapeSRC{
		public String nombre = "?";
		public String url = null;
		public int inicio = 0;
		public int duracion = 0;
		
		public EarrapeSRC() {}
		public EarrapeSRC(String url, int duracion) {
			this.url=url;
			this.duracion=duracion;
		}
		public EarrapeSRC(String nombre, String url, int duracion) {
			this.nombre=nombre;
			this.url=url;
			this.duracion=duracion;
		}
		public EarrapeSRC(String nombre, String url, int inicio, int duracion) {
			this.nombre=nombre;
			this.url=url;
			this.inicio=inicio;
			this.duracion=duracion;
		}
	}
}
