package xyz.sandwichbot.commands;

import java.awt.Color;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import xyz.sandwichbot.annotations.*;
import xyz.sandwichbot.core.AutoHelpCommand;
import xyz.sandwichbot.main.Constantes;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichbot.main.util.ClienteHttp;
import xyz.sandwichbot.main.util.Comparador;
import xyz.sandwichbot.main.util.MultiFuck;
import xyz.sandwichbot.models.InputParameter;
import xyz.sandwichbot.models.InputParameter.InputParamType;

@Category(desc="Una fina colección de los mejores comandos de este servidor."
+" Comandos vitales para una sociedad civilizada y culta.",nsfw=true)
public class NSFW {
	@Command(name="NSFW",desc="Como si no supieras que hace este comando cochin@ :wink::smirk:",alias= {"ns","por","uff","porno","prn","nopor","nopo","porn","cochinadas","18","+18","7u7"})
	@Option(name="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="cantidad",desc="Indica la cantidad de imagenes que devolverá el comando. DEBE SER UN VALOR NUMÉRICO ENTRE 1 Y 100 (se que quieres más, pero tu mano se va a hacer mierda...me preocupo por ti manit@). Si ingresas mal este número te quedarás sin placer:smirk:",alias={"c","cant","num"})
	@Option(name="tags",desc="Etiquetas que describen el contenido esperado. Pueden ser una o mas separadas por comas (','). No abuses de estas porque mientras mas especifica es la busqueda, menos resultados obtenidos. Se permiten espacios entre etiquetas.",alias={"t","tg","tgs"})
	@Option(name="gif",desc="Indica si el contenido es animado o no. Si no se especifica esta opción, por defecto el contenido es animado (equivalente a '-gif true')",alias={"g","gf","animado","anim"})
	@Option(name="video",desc="Esta opcion indica que el recurso devuelto debe ser un video. Si se usa junto con la opcion '-gif', esta ultima sera ignorada.",alias={"v","vid","mp4"})
	@Option(name="random",desc="Establece que los recursos devueltos deben ser videos e imagenes estaticas o animadas de manera aleatoria. Si se usa junto con las opciones '-gif' o '-video', estas seran ignoradas.",alias={"r","rdm","rand","azar"})
	public static void nsfw(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		e.getChannel().purgeMessagesById(e.getMessageId());
		int cantidad = 1;
		//String fuente = null;
		boolean gif = true;
		String[] tags = null;
		boolean video = false;
		boolean autodes = false;
		int autodesTime = 15;
		boolean random = false;
		for(InputParameter p : parametros) {
			//System.out.println(p.getClave()+"-"+p.getTipo());
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("cantidad")) {
					cantidad = p.getValueAsInt();
				}else if(p.getKey().equalsIgnoreCase("gif")) {
					gif= p.getValueAsBoolean(Constantes.VALORES.TRUE);
				}else if(p.getKey().equalsIgnoreCase("tags")) {
					tags = p.getValueAsString().replaceAll("\\s",",").split(",");
				}else if(p.getKey().equalsIgnoreCase("video")) {
					video = true;
					gif=false;
				}else if(p.getKey().equalsIgnoreCase("random")) {
					random = true;
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "NSFW");
					return;
				}
			}
		}
		if(cantidad>100) {
			cantidad = 100;
		}else if(cantidad<=0) {
			cantidad=1;
		}
		MultiFuck fk;
		if(cantidad>=8) {
			fk = new MultiFuck(e.getChannel(),true);
		}else {
			fk = new MultiFuck(e.getChannel());
		}
		fk.setGif(gif);
		fk.setTags(tags);
		fk.setVideo(video);
		fk.setAutoDes(autodes);
		fk.setAutodesTime(autodesTime);
		fk.setRand(random);
		Thread fuck;
		try {
			if(!e.getTextChannel().isNSFW()){
				fk.enviarRestriccion();
				return;
			}
		}catch(Exception ex) {
			
		}
		for(int i = 1; i<=cantidad;i++) {
			fuck = new Thread(fk);
			fuck.start();
		}
	}
	@Command(name="Xvideos",desc="Realiza la busqueda solicitada y devuelve una lista con los primeros resultados encontrados(Aún no soy capaz de reproducirlos, denme tiempo:pensive:).",alias={"xv","xvid","xxxv","videosnopor"})
	@Parameter(name="Nombre del objetivo",desc="Texto con el cual se realizará la busqueda en xvideos (ejemplo: 'creampie'... esta vez no me equivoqué de página :smirk:).\nSe permiten espacios. Todo texto que comience con un '-' no formara parte de la busqueda.")
	@Option(name="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	public static void xvideos(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		e.getChannel().purgeMessagesById(e.getMessageId());
		String busqueda = null;
		boolean autodes = false;
		int autodesTime=15;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "Xvideos");
					return;
				}
			}else if(p.getType() == InputParamType.Custom){
				busqueda = p.getValueAsString();
			}
		}
		if(busqueda!=null) {
			String hc = ClienteHttp.peticionHttp(Constantes.RecursoExterno.NSFW.toXV_link(busqueda));
			hc = new String(hc.getBytes(),StandardCharsets.UTF_8);
			ArrayList<String> ids = Comparador.EncontrarTodos(Comparador.Patrones.XV_Link, hc);
			//ArrayList<String> tit = new ArrayList<String>();
			String tit="";
			if(ids.size()>0) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Resultados de la busqueda:");
				eb.setColor(Color.DARK_GRAY);
				for(int i=0;i<ids.size() && i < 24;i=i+2) {
					tit=ids.get(i).substring(15).replace("_", "");
					eb.addField("", ">>> [" + tit +"](" + Constantes.RecursoExterno.NSFW.LINK_XV_BASE + ids.get(i) + ")", true);
				}
				if(autodes) {
					if(autodesTime<=0) {
						autodesTime=5;
					}else if(autodesTime>900) {
						autodesTime=900;
					}
					SandwichBot.SendAndDestroy(e.getChannel(),eb.build(), autodesTime);
				}else {
					e.getChannel().sendMessage(eb.build()).queue();
				}
				return;
			}
			e.getChannel().sendMessage("No se encontraron resultados:pensive:").queue();
			return;
		}else {
			e.getChannel().sendMessage("Debe especificar una busqueda.").queue();
		}
		
	}
	@Command(name="OtakuNSFW",desc="Es como el de NSFW clásico... Pero con monas chinas :wink::smirk:",alias= {"hentai","otakuns","ons","o18","h18","otakuporn","monaschinas"},enabled=true,visible=false)
	@Option(name="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="cantidad",desc="Indica la cantidad de imagenes que devolverá el comando. DEBE SER UN VALOR NUMÉRICO ENTRE 1 Y 100 (se que quieres más, pero tu mano se va a hacer mierda...me preocupo por ti manit@). Si ingresas mal este número te quedarás sin placer:smirk:",alias={"c","cant","num"})
	@Option(name="tags",desc="Etiquetas que describen el contenido esperado. Pueden ser una o mas separadas por comas (','). No abuses de estas porque mientras mas especifica es la busqueda, menos resultados obtenidos. Se permiten espacios entre etiquetas.",alias={"t","tg","tgs"})
	@Option(name="fuente",desc="Indica la fuente de origen del contenido a mostrar.\nFuentes permitidas:\n-  [Konachan.com](https://konachan.com)\n-  3Dbooru\n-  Gelbooru\n-  Danbooru\n-  [Konachan.net](https://konachan.net)\n-  Lolibooru\n-  Rule34\n-  XBooru",alias={"f","source","origen"})
	public static void otakus(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		e.getChannel().purgeMessagesById(e.getMessageId());
		int cantidad = 1;
		String fuente = "konachan";
		String[] tags = null;
		boolean autodes = false;
		int autodesTime = 15;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("cantidad")) {
					cantidad = p.getValueAsInt();
				}else if(p.getKey().equalsIgnoreCase("tags")) {
					tags = p.getValueAsString().replaceAll("\\s",",").split(",");
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "OtakuNSFW");
					return;
				}
			}
		}
		if(cantidad>100) {
			cantidad = 100;
		}else if(cantidad<=0) {
			cantidad=1;
		}
		MultiFuck fk;
		if(cantidad>=8) {
			fk = new MultiFuck(e.getChannel(),true);
		}else {
			fk = new MultiFuck(e.getChannel());
		}
		fk.setTags(tags);
		fk.setAutoDes(autodes);
		fk.setAutodesTime(autodesTime);
		fk.setFuente(fuente);
		Thread fuck;
		try {
			if(!e.getTextChannel().isNSFW()){
				fk.enviarRestriccion();
				return;
			}
		}catch(Exception ex) {
			
		}
		for(int i = 1; i<=cantidad;i++) {
			fuck = new Thread(fk);
			fuck.start();
		}
	}
}
