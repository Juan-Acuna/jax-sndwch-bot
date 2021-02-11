package xyz.sandwichbot.main;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import xyz.sandwichbot.main.util.ClienteHttp;
import xyz.sandwichbot.main.util.Comparador;

public class Constantes {
	//PACKAGE : "xyz.sandwichbot.commands"
	
	//REGISTRO DE ROLES
	
	//REGISTRO DE COMANDOS
	public static class COMANDOS{
		public static final String[] saludar = {"s","saluda","putea","putear"};
		public static final String[] youtube = {"yt","y","yutu","video","videos","llutu"};
		public static final String[] meow = {"mew","mw","miau","gato"};
		public static final String[] nsfw = {"ns","p","porno","prn","nopor","nopo","porn","cochinadas","18","+18","7u7"};
		public static final String[] ayuda = {"help","h"};
		public static final String[] version = {"v","ver"};
	}
	//REGISTRO DE PARAMETROS
	public static class PARAMETROS{
		//STANDAR
		public static final String[] CANTIDAD = {"c","cant","num"};
		public static final String[] GIF = {"g","gf","animado","anim"};
		public static final String[] TAGS = {"t","tg","tgs"};
		public static final String[] FUENTE = {"f","source","src"};
		public static final String[] BUSCAR = {"b","busca","search","find","busqueda"};
		//NULOS
		public static final String[] AYUDA = {"help","h"};
		public static final String[] VIDEO = {"v","vid","mp4"};
		public static final String[] RANDOM = {"r","rdm","rand","azar"};
		public static final String[] VERSION = {"ver"};
		public static final String[] ANONIMO = {"anon","ano","anonymous","anonym","secreto","secret"};
		public static final String[] AUTODESTROY = {"autodestruir","ad","autodes","autorm","arm"};
	}
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
		public static final String Fantasma = "👻";
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
	//RECURSOS EXTERNOS
	public static class RecursoExterno{
		public static final String LINK_RANDOM_CAT = "http://aws.random.cat/meow";
		public static final String LINK_YOUTUBE_BASE = "https://www.youtube.com";
		public static final String LINK_YOUTUBE_QUERY = "https://www.youtube.com/results?search_query=";
		public static final String LINK_WIKIDEX_QUERY = "https://www.wikidex.net/wiki/";
		public static final String LINK_POKEMON_QUERY ="https://www.pokemon.com/el/pokedex/";
		public static String toWDEX_PKMN(String pkmn) {
			
			return "";
		}
		
		public static String toWDEX_PKMN_REGION() {

			return "";
		}
		
		
		public static class NSFW{
			public static String toRB_link(int pid, boolean gif, boolean video, boolean random, String[] tags) throws Exception {
				String t = "mmale+female";
				if(tags!=null) {
					t = "";
					//System.out.println("TAGS: ");
					for(String s : tags) {
						if(s.trim().replace(" ","").length()<=0) {
							continue;
						}
						//System.out.println(s);
						t += "+" + URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
					}
				}
				//System.out.println("random:"+random);
				String tipo = "";
				if(random) {
					Random r = new Random(System.currentTimeMillis());
					int i = r.nextInt(18)+1;
					if(i==1 || i==4 || i==7 || i==10 || i==13 || i==16) {
						tipo="";
					}else if(i==2 || i==5 || i==8 || i==11 || i==14 || i==17) {
						tipo="video+";
					}else if(i==3 || i==6 || i==9 || i==12 || i==15 || i==18) {
						tipo="gif+";
					}
					//System.out.println("I:"+i);
				}else {
					if(video) {
						tipo = "video+";
						gif=false;
					}
					if(gif) {
						tipo="gif+";
					}
				}
				/**/
				String url = "https://realbooru.com/index.php?page=post&s=list&tags=" + tipo + t.substring(1) + "&pid="+pid;
				//System.out.println(url);
				return url;
			}
			public static String toXV_link(String busqueda) throws Exception {
				busqueda = URLEncoder.encode(busqueda, StandardCharsets.UTF_8.toString());
				String url = "https://www.xvideos.com/?k="+busqueda+"&top";
				return url;
			}
			public static String toO_link(int pid, int fuente, String[] tags) throws Exception {
				String t = "";
				if(tags!=null) {
					for(String s : tags) {
						if(s.trim().replace(" ","").length()<=0) {
							continue;
						}
						t += "+" + URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
					}
				}
				String url = "";
				switch(fuente) {
					case 0:
						break;
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
					case 5:
						break;
					case 6:
						break;
					case 7:
						break;
				}
				url +="uncensored" + t;
				return url;
			}
			public static final String LINK_XV_BASE = "https://www.xvideos.com";
			public static final String KONACHAN_QUERY = "https://konachan.com/post?tags=";
			public static final String _3DBOORU_QUERY = "";
			public static final String DANBOORU_QUERY = "";
			public static final String GELBOORU_QUERY = "";
			public static final String KONACHAN_NET_QUERY = "";
			public static final String LBOORU_QUERY = "";
			public static final String R34_QUERY = "";
			public static final String XBOORU_QUERY = "";
		}
	}
}
