package com.jaxsandwich.discordbot.comandos;

import java.io.IOException;
import java.net.URLDecoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jaxsandwich.discordbot.main.Constantes;
import com.jaxsandwich.discordbot.main.modelos.Fuente;
import com.jaxsandwich.discordbot.main.util.ClienteHttp;
import com.jaxsandwich.discordbot.main.util.Comparador;
import com.jaxsandwich.discordbot.main.util.Tools;
import com.jaxsandwich.sandwichcord.annotations.*;
import com.jaxsandwich.sandwichcord.core.Values;
import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.core.util.LanguageHandler;
import com.jaxsandwich.sandwichcord.core.util.MessageUtils;
import com.jaxsandwich.sandwichcord.models.CommandPacket;
import com.jaxsandwich.sandwichcord.models.InputParameter;
import com.jaxsandwich.sandwichcord.models.InputParameter.InputParamType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Category(desc="Comandos dedicados a videojuegos en general.")
public class VideoJuegos {
	@Command(id="Lol",enabled=true)
	@Option(id="perfil",desc="Muestra información del invocador. Se requiere especificar una región. Si no se especifica por defecto es 'LAS'.",alias={"stats","p"})
	@Option(id="region",desc="Especifica la región. Contexto necesario para el comando. Regiones disponibles:\n`LAS`, `LAN`, `NA`, `EUNE`, `EUW`, `BR`, `OCE`, `KR`, `RU`, `TR` y `JP`\nSi no se especifica `LAS` se usa por defecto.",alias={"r"})
	@Option(id="jugando",desc="Muestra información de la partida actual del invocador(si este se encuentra en una). Se requiere especificar una región. Si no se especifica por defecto es 'LAS'.",alias={"j"},enabled=false)
	public static void lol(CommandPacket packet) throws IOException {
		Language lang = Language.ES;
		if(packet.isFromGuild())
			lang = packet.getGuildConfig().getLanguage();
		String invocador = null;
		String region = "LAS";
		EmbedBuilder eb;
		for(InputParameter p : packet.getParameters()) {
			if(p.getType()==InputParamType.Standar) {
				if(p.getKey().equals("perfil")) {
					if(p.getValueAsString()!=null) {
						invocador = p.getValueAsString();
					}else {
						eb = new EmbedBuilder();
						eb.setTitle(Values.value("jax-lol-no-invocador", lang));
						eb.setColor(Tools.stringColorCast("rojo"));
						packet.getTextChannel().sendMessageEmbeds(eb.build()).queue();
						return;
					}
				}else if(p.getKey().equals("region")){
					if(regionValida(p.getValueAsString())) {
						region = p.getValueAsString();
					}else {
						eb = new EmbedBuilder();
						eb.setTitle("jax-gen-txt-region-invalida");
						eb.setColor(Tools.stringColorCast("rojo"));
						packet.getTextChannel().sendMessageEmbeds(eb.build()).queue();
						return;
					}
				}
			}
		}
		if(invocador!=null) {
			String url = "https://www.leagueofgraphs.com{lang}/summoner/{reg}/{summoner}";
			url = url.replace("{summoner}",invocador).replace("{reg}", region.toLowerCase());
			if(lang==Language.EN) {
				url = url.replace("{lang}","");
			}else {
				url = url.replace("{lang}","/"+LanguageHandler.getLanguageParent(lang).name().toLowerCase());
			}
			Document d = Jsoup.connect(url).userAgent("Mozilla").get();
			eb = new EmbedBuilder();
			eb.setTitle(Values.formatedValue("jax-lol-info-invocador-titulo", lang,invocador));
			eb.setThumbnail("http:"+d.selectFirst("img[alt="+invocador+"]").attr("src"));
			eb.addField(Values.formatedValue("jax-lol-invocador-division", lang, d.selectFirst("div.leagueTier").text()),
					"*PL: "+d.selectFirst("span.leaguePoints").text()+"*",true);
			eb.addField("**"+d.selectFirst("span.queue").text()+"**",
					Values.formatedValue("jax-lol-invocador-win-loss", lang, d.selectFirst("span.winsNumber").text(), d.selectFirst("span.lossesNumber").text()),true);
			eb.addField("",Values.formatedValue("jax-lol-estadisticas-partidas-t", lang, d.selectFirst("a[data-dropdown='drop-30days'] > div.filterHeader").text().toLowerCase()),false);
			Elements basicStats = d.selectFirst("div#profileBasicStats").selectFirst("div.tabs-content").select("div[data-tab-id]");
			String str = null;
			for(Element e : basicStats) {
				str = "";
				for(Element el : e.select("div.pie-chart-container")) {
					str += "\n"+el.selectFirst("div.pie-chart-title").text()+": "+el.selectFirst("div[id~=graphDD[0-9]{1,2}]").text();
				}
				eb.addField(Values.value("jax-lol-"+e.attr("data-tab-id"), lang),
					">>> "+str.substring(1),true);
			}
			packet.getTextChannel().sendMessageEmbeds(eb.build()).queue();
		}
		
	}
	@Command(id="LolCampeon",enabled=false)
	public static void campeon(CommandPacket packet) {
		
	}
	@Command(id="ValorantStats",enabled=false)
	public static void valorantstat(CommandPacket packet) {
		
	}
	@Command(id="Pokedex",desc="Busca y devuelve informacion relativa a un pokémon.",alias= {"pkmn","pkm","dex","pd","poke"})
	@Parameter(name="Pokémon objetivo",desc="Nombre del pokémon que se desea buscar. El nombre debe estar bien escrito y se permiten espacios. Todo texto después de un '-' no formará parte del nombre.")
	@Option(id="numero",desc="Permite buscar al pokémon por su número identificador en la pokédex nacional. En caso de ser ingresado el nombre, este parametro se ignora. DEBE SER UN NÚMERO POSITIVO NO MAYOR A LA CANTIDAD DE POKÉMON EXISTENTES EN LA POKÉDEX.",alias={"id","nac","identificador","n","num","nacional"})
	@Option(id="variocolor",desc="Retorna pokémon, cuya imagen es reemplazada por su versión variocolor. La imagen devuelta es un render 3D animado. Si se usa la opción '-3D', esta última es ignorada.",alias={"shiny","s","sny","shaini","chino","vc","vario"})
	@Option(id="3D",desc="Retorna pokémon, cuya imagen es reemplazada por un render 3D animado de la criatura.",alias={"3","render","real","rl"})
	@Option(id="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(id="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void pokedex2(CommandPacket packet) throws Exception {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		Document doc = null;
		boolean autodes = false;
		int autodesTime = 15;
		String pkmn = null;
		int pkmnId = -1;
		boolean shiny = false;
		boolean _3d = false;
		boolean anon = false;
		boolean grito = false; 
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(p.getValueAsString()!=null) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("numero")){
					pkmnId = p.getValueAsInt();
				}else if(p.getKey().equalsIgnoreCase("3D")){
					_3d=true;
				}else if(p.getKey().equalsIgnoreCase("variocolor")){
					shiny = true;
				}else if(p.getKey().equalsIgnoreCase("anonimo")){
					anon = true;
				}
			}else if(p.getType() == InputParamType.Custom){
				pkmn = p.getValueAsString();
			}
		}
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		if(pkmn==null && pkmnId<0) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Debe ingresar el nombre de un pokémon o su número en la pokédex nacional con la opcion '-numero' (para mas info use el comando ayuda) para usar este comando.");
			e.getChannel().sendMessageEmbeds(eb.build()).queue();
			return;
		}else if(pkmn==null && (pkmnId>898 || pkmnId==0)){
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Número identificador de pokémon fuera de rango. El mínimo permitido es 1 (Bulbasaur) y mayor permitido es 898 (Calyrex)");
			e.getChannel().sendMessageEmbeds(eb.build()).queue();
			return;
		}else if(pkmn==null && pkmnId>0){
			String nac = "";
			if(pkmnId<=9) {
				nac += "00" + pkmnId;
			}else if(pkmnId<=99) {
				nac += "0" + pkmnId;
			}else {
				nac += "" + pkmnId;
			}
			doc = Jsoup.connect(Fuente.find(Fuente.PKMN_LISTA).getSrc()).userAgent("Mozilla").get();
			pkmn = doc.selectFirst("td:contains("+nac+")").nextElementSibling().getElementsByTag("a").first().text();
			if(pkmn==null) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Error de servidor");
				e.getChannel().sendMessageEmbeds(eb.build()).queue();
				return;
			}
		}
		//busca ese pokemon
		pkmn = pkmn.replace(" ","_");
		try {
			doc = Jsoup.connect(Fuente.find(Fuente.PKMN_POKEMON).getSrc()+pkmn).userAgent("Mozilla").get();
		}catch(Exception ex) {
			//pokemon no existe
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("El pokémon '" + pkmn.replace("_", " ") + "' no existe. Asegurate de escribirlo bien.");
			e.getChannel().sendMessageEmbeds(eb.build()).queue();
			return;
		}
		String nombre = doc.selectFirst("div#nombrepokemon").text();
		String id = doc.selectFirst("span#enlacenumeropokemon > span").text();
		String imagen = null;
		String tipo = null;
		Elements els = doc.select("tr[title=Tipos a los que pertenece] > td > a");
		if(els.size()==1) {
			tipo = ": " + els.first().attr("title").replace("Tipo ","") + Constantes.Pkm.getTipo(els.first().attr("title"));
		}else {
			tipo = "s: " +els.get(0).attr("title").replace("Tipo ","") + Constantes.Pkm.getTipo(els.get(0).attr("title")) + " | " + els.get(1).attr("title").replace("Tipo ","") + Constantes.Pkm.getTipo(els.get(0).attr("title"));
		}
		String habilidad=null;
		els = doc.select("tr[title=Habilidades que puede conocer] > td > a");
		if(els.size()==1) {
			habilidad = ": " + els.first().text();
		}else {
			habilidad = "es: " +els.get(0).text() + " | " + els.get(1).text();
		}
		els = doc.select("tr[title=Habilidad oculta] > td > a");
		String oculta = els.last().text();
		String escalado = "500px";
		if(_3d) {
			try {
				imagen = doc.selectFirst("a[href~=/wiki/Archivo:(.{3,16})_EpEc.gif] > img").attr("src");
			}catch(Exception ex) {
				imagen = doc.selectFirst("a[href~=/wiki/Archivo:(.{3,16})_XY.gif] > img").attr("src");
			}
			escalado = "300px";
		}else if(shiny) {
			try {
				imagen = doc.selectFirst("a[href~=/wiki/Archivo:(.{3,16})_EpEc_variocolor.gif] > img").attr("src");
			}catch(Exception ex) {
				imagen = doc.selectFirst("a[href~=/wiki/Archivo:(.{3,16})_XY_variocolor.gif] > img").attr("src");
			}
		}else {
			imagen = doc.selectFirst("a[href^=/wiki/Archivo:] > img[alt^=Ilustración de]").attr("src");
		}
		imagen = imagen.replaceFirst("[0-9]{1,4}px", escalado);//id="mwe_player_0"
		String gritoSource = null;
		if(grito) {
			gritoSource = doc.selectFirst("audio#mwe_player_0 > source").attr("src");
		}
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(nombre + "   |  NAC# "+ id);
		eb.addField("Tipo" + tipo,"Habilidad" + habilidad, false);
		eb.addField("Habilidad Oculta: " + oculta,"",false);
		eb.setImage(imagen);
		if(autodes) {
			MessageUtils.SendAndDestroy(e.getChannel(), eb.build(), autodesTime);
		}else {
			e.getChannel().sendMessageEmbeds(eb.build()).queue();
		}
		if(gritoSource!=null) {
			
		}
	}
	private static boolean regionValida(String reg) {
		if(reg==null)
			return false;
		if(reg.length()>4 || reg.length()<2)
			return false;
		reg = reg.toUpperCase();
		if(reg.equals("LAN"))
			return true;
		int s = reg.compareTo("LAN");
		if(s<0) {
			if(reg.equals("EUW"))
				return true;
			s = reg.compareTo("EUW");
			if(s<0) {
				return reg.equals("BR") || reg.equals("EUNE");
			}else {
				return reg.equals("JP") || reg.equals("KR");
			}
		}else{
			if(reg.equals("OCE"))
				return true;
			s = reg.compareTo("OCE");
			if(s<0) {
				return reg.equals("LAS") || reg.equals("NA");
			}else {
				return reg.equals("RU") || reg.equals("TR");
			}
		}
	}
}
