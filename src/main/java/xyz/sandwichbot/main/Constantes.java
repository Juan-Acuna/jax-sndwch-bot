package xyz.sandwichbot.main;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

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
	//RECURSOS EXTERNOS
	public static class RecursoExterno{
		public static final String LINK_RANDOM_CAT = "http://aws.random.cat/meow";
		public static final String LINK_YOUTUBE_BASE = "https://www.youtube.com";
		public static final String LINK_YOUTUBE_QUERY = "https://www.youtube.com/results?search_query=";
		public static final String LINK_WIKIDEX_BASE = "https://www.wikidex.net";
		public static String toWDEX_PKMN(String pkmn) {
			
			return "";
		}
		public static String toWDEX_PKMN_ID() {

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
			public static final String LINK_XV_BASE = "https://www.xvideos.com";
			public static String toXV_link(String busqueda) throws Exception {
				busqueda = URLEncoder.encode(busqueda, StandardCharsets.UTF_8.toString());
				String url = "https://www.xvideos.com/?k="+busqueda+"&top";
				return url;
			}
		}
	}
}
