package xyz.sandwichbot.main;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import xyz.sandwichbot.main.util.ClienteHttp;
import xyz.sandwichbot.main.util.Comparador;

public class Constantes {

	//REGISTRO DE VALORES STANDAR
	public static class VALORES{
		public static final String[] TRUE = {"true","t","1","y","yes","s","si","v"};
		public static final String[] FALSE = {"false","f","0","n","no","not"};
	}
	//OTROS
	public static class Pkm{
		public static final String Acero = "⚙";
		public static final String Agua = "💧";
		public static final String Bicho = "🐞";
		public static final String Dragon = "🐉";
		public static final String Electrico = "⚡";
		public static final String Fantasma = "👻";
		public static final String Fuego = "🔥";
		public static final String Hada = "🌟";
		public static final String Hielo = "❄";
		public static final String Lucha = "🥊";
		public static final String Normal = "🔘";
		public static final String Planta = "🌱";
		public static final String Psiquico = "🌀";
		public static final String Roca = "🪨";
		public static final String Siniestro = "🌑";
		public static final String Tierra = "⛰";
		public static final String Veneno = "☠";
		public static final String Volador = "🕊";
		public static String getTipo(String tipo) throws Exception {
			tipo = tipo.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
			tipo = tipo.replace("Á", "a").replace("É", "e").replace("Í", "i").replace("Ó", "o").replace("Ú", "u");
			tipo = tipo.toUpperCase().substring(0,1) + tipo.toLowerCase().substring(1);
			Field f = Constantes.Pkm.class.getDeclaredField(tipo);
			return (String)f.get(null);
		}
		public static String getPokemonFromId(int id){
			if(id<=0) {
				return null;
			}
			String nac = "";
			if(id<=9) {
				nac += "00" + id;
			}else if(id<=99) {
				nac += "0" + id;
			}else if(id>898){
				return null;
			}else {
				nac += "" + id;
			}
			String patron = "<td>"+nac+"</td><td><a href=\"/es/wiki/[0-9a-zA-Z%_ áéíóúÁÉÍÓÚñÑ]{2,35}";
			String hc = null;
			try {
				hc = ClienteHttp.peticionHttp("https://pokemon.fandom.com/es/wiki/Lista_de_Pok%C3%A9mon");
			}catch(Exception e) {
				e.printStackTrace();
			}
			String pkm = Comparador.Encontrar(patron, hc);
			pkm = pkm.replace("<td>"+nac+"</td><td><a href=\"/es/wiki/","");
			return pkm;
		}
	}
	public static class JaxSandwich{
		public static final String BaseURL ="https://jaxsandwichbot.000webhostapp.com";
		
		public static class Imagenes{
			public static final String nonsfw = BaseURL + "/img/nsfw/nonsfw.gif";
		}
		public static class JAX{
			public static final String R = BaseURL + "/jax/jax.token.register.php";
			public static final String A = BaseURL + "/jax/jax.commands.auth.php";
			public static final String C = BaseURL + "/jax/jax.commands.run.php";
		}
	}
	//RECURSOS EXTERNOS
	public static class RecursoExterno{
		public static final String LINK_RANDOM_CAT = "http://aws.random.cat/meow";
		public static final String LINK_YOUTUBE_BASE = "https://www.youtube.com";
		public static final String LINK_YOUTUBE_QUERY = "https://www.youtube.com/results?search_query=";
		public static final String LINK_WIKIDEX_QUERY = "https://www.wikidex.net/wiki/";
		public static final String LINK_POKEMON_QUERY ="https://www.pokemon.com/el/pokedex/";
		public static class NSFW{
			public static String toXV_link(String busqueda) throws Exception {
				busqueda = URLEncoder.encode(busqueda, StandardCharsets.UTF_8.toString());
				String url = "https://www.xvideos.com/?k="+busqueda+"&top";
				return url;
			}
			
			public static final String LINK_XV_BASE = "https://www.xvideos.com";
		}
	}
}
