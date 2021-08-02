package com.jaxsandwich.discordbot.comandos;

import java.awt.Color;
import java.util.ArrayList;

import com.jaxsandwich.discordbot.main.Constantes;
import com.jaxsandwich.discordbot.main.modelos.FuenteImagen;
import com.jaxsandwich.discordbot.main.modelos.Servidor;
import com.jaxsandwich.discordbot.main.util.ClienteHttp;
import com.jaxsandwich.discordbot.main.util.Comparador;
import com.jaxsandwich.discordbot.main.util.ControladorImagenes;
import com.jaxsandwich.framework.annotations.*;
import com.jaxsandwich.framework.core.ExtraCmdManager;
import com.jaxsandwich.framework.core.Values;
import com.jaxsandwich.framework.core.util.Language;
import com.jaxsandwich.framework.core.util.MessageUtils;
import com.jaxsandwich.framework.models.CommandPacket;
import com.jaxsandwich.framework.models.InputParameter;
import com.jaxsandwich.framework.models.InputParameter.InputParamType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Category(desc="Una fina colección de los mejores comandos de este servidor."
+" Comandos vitales para una sociedad civilizada y culta.",nsfw=true)
public class NSFW {
	@Command(id="NSFW",desc="Como si no supieras que hace este comando cochin@ :wink::smirk:",alias= {"ns","por","uff","porno","prn","nopor","nopo","porn","cochinadas","18","+18","7u7"})
	@Option(id="cantidad",desc="Indica la cantidad de imagenes que devolverá el comando. DEBE SER UN VALOR NUMÉRICO ENTRE 1 Y 100 (se que quieres más, pero tu mano se va a hacer mierda...me preocupo por ti manit@). Si ingresas mal este número te quedarás sin placer:smirk:",alias={"c","cant","num"})
	@Option(id="tags",desc="Etiquetas que describen el contenido esperado. Pueden ser una o mas separadas por comas (','). No abuses de estas porque mientras mas especifica es la busqueda, menos resultados obtenidos. Se permiten espacios entre etiquetas.",alias={"t","tg","tgs"})
	@Option(id="gif",desc="Indica si el contenido es animado o no. Si no se especifica esta opción, por defecto el contenido es animado (equivalente a '-gif true')",alias={"g","gf","animado","anim"})
	@Option(id="video",desc="Esta opcion indica que el recurso devuelto debe ser un video. Si se usa junto con la opcion '-gif', esta ultima sera ignorada.",alias={"v","vid","mp4"})
	@Option(id="random",desc="Establece que los recursos devueltos deben ser videos e imagenes estaticas o animadas de manera aleatoria. Si se usa junto con las opciones '-gif' o '-video', estas seran ignoradas.",alias={"r","rdm","rand","azar"})
	@Option(id="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	//@Option(name="creditos",desc="Da credito a quien invocó e comando. Es algo asi como lo opuesto de 'anonimo'.",alias={"au","cr","credito","autor","nonanon"})
	public static void nsfw(CommandPacket packet) throws Exception {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		e.getChannel().purgeMessagesById(e.getMessageId());
		Servidor servidor = (Servidor) packet.getModelGuild();
		Language lang = Language.ES;
		if(packet.isFromGuild())
			lang=servidor.getLanguage();
		int cantidad = 1;
		//String fuente = null;
		boolean gif = true;
		String[] tags = null;
		boolean video = false;
		boolean autodes = false;
		int autodesTime = 15;
		boolean random = false;
		boolean noanon = false;
		for(InputParameter p : packet.getParameters()) {
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
				}else if(p.getKey().equalsIgnoreCase("creditos")) {
					//noanon = true;
				}else if(p.getKey().equalsIgnoreCase("video")) {
					video = true;
					gif=false;
				}else if(p.getKey().equalsIgnoreCase("random")) {
					random = true;
				}
			}
		}
		if(cantidad>100) {
			cantidad = 100;
		}else if(cantidad<=0) {
			cantidad=1;
		}
		ControladorImagenes gi;
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(Color.red);
		eb.setFooter((noanon?"":Values.value("jax-nsfw-img-footer", lang))+"🙊😏😏",packet.getBot().getSelfUser().getAvatarUrl());
		gi = new ControladorImagenes(e.getChannel(),FuenteImagen.find(FuenteImagen.RealBooru),eb,(cantidad>=8));
		gi.setGif(gif);
		gi.setTags(tags);
		gi.setVideo(video);
		gi.setAutodes(autodes);
		gi.setAutodesTime(autodesTime);
		gi.setRand(random);
		Thread fuck;
		try {
			if(!e.getTextChannel().isNSFW()){
				gi.enviarRestriccion(packet.getBot());
				return;
			}
		}catch(Exception ex) {
			
		}
		/*if(noanon) {
			EmbedBuilder eb2 = new EmbedBuilder();
			String str = " imagen" + (cantidad>1?"es":"");
			if(random) {
				str = " imagen" + (cantidad>1?"es":"") + " y video" + (cantidad>1?"s":"") + " aleatorio" + (cantidad>1?"s":"");
			}else if(video) {
				str = " video" + (cantidad>1?"s":"");
			}else if(gif) {
				str = " imagen" + (cantidad>1?"es":"") + " animada" + (cantidad>1?"s":"");
			}
			eb2.setColor(Color.red);
			eb2.addField("A petición de " + e.getAuthor().getName(), cantidad + str + (tags==null?".":" con las siguientes etiquetas: `" + Tools.arrayToString(tags) +"`."), false);
			if(autodes) {
				MessageUtils.SendAndDestroy(e.getChannel(), eb2.build(), cantidad * autodesTime + 3);
			}else {
				e.getChannel().sendMessageEmbeds(eb2.build()).queue();
			}
		}*/
		
		for(int i = 1; i<=cantidad;i++) {
			fuck = new Thread(gi);
			fuck.start();
		}
	}
	@Command(id="Xvideos",desc="Realiza la busqueda solicitada y devuelve una lista con los primeros resultados encontrados(Aún no soy capaz de reproducirlos, denme tiempo:pensive:).",alias={"xv","xvid","xxxv","videosnopor"})
	@Parameter(name="Busqueda",desc="Texto con el cual se realizará la busqueda en xvideos (ejemplo: 'creampie'... esta vez no me equivoqué de página :smirk:).\nSe permiten espacios. Todo texto que comience con un '-' no formara parte de la busqueda.")
	@Option(id="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	public static void xvideos(CommandPacket packet) throws Exception {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		packet.getChannel().purgeMessagesById(e.getMessageId());//<span class="duration">23 min
		Servidor servidor = (Servidor) packet.getModelGuild();
		Language lang = Language.ES;
		if(packet.isFromGuild())
			lang=servidor.getLanguage();
		String busqueda = null;
		boolean autodes = false;
		int autodesTime=15;
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}
			}else if(p.getType() == InputParamType.Custom){
				busqueda = p.getValueAsString();
			}
		}
		if(busqueda!=null) {
			String hc = ClienteHttp.peticionHttp(Constantes.RecursoExterno.NSFW.toXV_link(busqueda));
			ArrayList<String> ids = Comparador.EncontrarTodos(Comparador.Patrones.XV_Link, hc);
			//ArrayList<String> tit = new ArrayList<String>();
			String tit="";
			Object[] ss = new String[13];
			int ii = 0;
			if(ids.size()>0) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle(Values.value("jax-yt-resultado-busqueda", lang));
				eb.setColor(Color.DARK_GRAY);
				for(int i=0;i<ids.size() && i < 24;i=i+2) {
					tit=ids.get(i).substring(15).replace("_", " ");
					tit = (tit.length()>34?tit.substring(0,32)+"...":tit);
					ss[ii++] = Constantes.RecursoExterno.NSFW.LINK_XV_BASE + ids.get(i);
					eb.addField(Values.formatedValue("jax-generico-txt-opcion-s", lang, ii+""), ">>> [" + tit +"](" + Constantes.RecursoExterno.NSFW.LINK_XV_BASE + ids.get(i) + ")", true);
				}
				if(autodes) {
					if(autodesTime<=0) {
						autodesTime=5;
					}else if(autodesTime>900) {
						autodesTime=900;
					}
					MessageUtils.SendAndDestroy(e.getChannel(),eb.build(), autodesTime);
				}else {
					e.getChannel().sendMessageEmbeds(eb.build()).queue();
				}
				packet.getExtraCmdManager().waitForExtraCmd("xv", e.getMessage(), ExtraCmdManager.NUMBER_WILDCARD,50, 5, ss);
				return;
			}
			e.getChannel().sendMessage(Values.value("jax-yt-no-resultados", lang)).queue();
			return;
		}else {
			e.getChannel().sendMessage(Values.value("jax-yt-ingresar-busqueda", lang)).queue();
		}
		
	}
	@Command(id="OtakuNSFW",desc="Es como el de NSFW clásico... Pero con monas chinas :wink::smirk:",alias= {"hentai","otakuns","ons","o18","h18","otakuporn"})
	@Option(id="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(id="cantidad",desc="Indica la cantidad de imagenes que devolverá el comando. DEBE SER UN VALOR NUMÉRICO ENTRE 1 Y 100 (se que quieres más, pero tu mano se va a hacer mierda...me preocupo por ti manit@). Si ingresas mal este número te quedarás sin placer:smirk:",alias={"c","cant","num"})
	@Option(id="tags",desc="Etiquetas que describen el contenido esperado. Pueden ser una o mas separadas por comas (','). No abuses de estas porque mientras mas especifica es la busqueda, menos resultados obtenidos. Se permiten espacios entre etiquetas.",alias={"t","tg","tgs"})
	@Option(id="fuente",desc="Indica la fuente de origen del contenido a mostrar.\nFuentes permitidas:\n- [Konachan.com](https://konachan.com)\n- [Gelbooru](https://gelbooru.com)\n- [Danbooru](https://danbooru.donmai.us)\n- [XBooru](https://xbooru.com)\n - [Yandere](https://yande.re)",alias={"f","source","origen"})
	//@Option(name="creditos",desc="Da credito a quien invocó e comando. Es algo asi como lo opuesto de 'anonimo'.",alias={"au","cr","credito","autor","nonanon"})
	public static void otakus(CommandPacket packet) throws Exception {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		Servidor servidor = (Servidor) packet.getModelGuild();
		Language lang = Language.ES;
		if(packet.isFromGuild())
			lang=servidor.getLanguage();
		packet.getChannel().purgeMessagesById(e.getMessageId());
		int cantidad = 1;
		String fuente = "konachan";
		String[] tags = null;
		boolean autodes = false;
		int autodesTime = 15;
		boolean noanon=false;
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("cantidad")) {
					cantidad = p.getValueAsInt();
				}else if(p.getKey().equalsIgnoreCase("creditos")) {
					noanon = true;
				}else if(p.getKey().equalsIgnoreCase("fuente")) {
					fuente = p.getValueAsString();
				}else if(p.getKey().equalsIgnoreCase("tags")) {
					tags = p.getValueAsString().replaceAll("\\s",",").split(",");
				}
			}
		}
		if(cantidad>100) {
			cantidad = 100;
		}else if(cantidad<=0) {
			cantidad=1;
		}
		FuenteImagen fi = FuenteImagen.find(FuenteImagen.Konachan);
		if(fuente.equalsIgnoreCase("konachan") || fuente.equalsIgnoreCase("konachan.com") || fuente.equalsIgnoreCase("k")) {//konachan
			fi = FuenteImagen.find(FuenteImagen.Konachan);
		}else if(fuente.equalsIgnoreCase("danbooru") || fuente.equalsIgnoreCase("db") || fuente.equalsIgnoreCase("danboru") || fuente.equalsIgnoreCase("d")  || fuente.equalsIgnoreCase("danbooru.donmai.us")) {//danbooru
			fi = FuenteImagen.find(FuenteImagen.DanBooru);
		}else if(fuente.equalsIgnoreCase("gelbooru") || fuente.equalsIgnoreCase("g") || fuente.equalsIgnoreCase("gelbooru.com") || fuente.equalsIgnoreCase("gelboru") || fuente.equalsIgnoreCase("gelboru.com") || fuente.equalsIgnoreCase("gel")) {//gelbooru
			fi = FuenteImagen.find(FuenteImagen.GelBooru);
		}else if(fuente.equalsIgnoreCase("xbooru") || fuente.equalsIgnoreCase("x") || fuente.equalsIgnoreCase("xbooru.com") || fuente.equalsIgnoreCase("xboru") || fuente.equalsIgnoreCase("xboru.com")) {//xbooru
			fi = FuenteImagen.find(FuenteImagen.XBooru);
		}else if(fuente.equalsIgnoreCase("yandere") || fuente.equalsIgnoreCase("y") || fuente.equalsIgnoreCase("yande.re") || fuente.equalsIgnoreCase("yande") || fuente.equalsIgnoreCase("ere")) {//yandere
			fi = FuenteImagen.find(FuenteImagen.Yandere);
		}
		/*else if(fuente.equalsIgnoreCase("3dbooru") || fuente.equalsIgnoreCase("3d") || fuente.equalsIgnoreCase("behoimi.org") || fuente.equalsIgnoreCase("3")  || fuente.equalsIgnoreCase("3dboru")) {//3dbooru
			fi = FuenteImagen._3DBooru;
		}*/
		else {
			packet.getChannel().sendMessage(Values.formatedValue("jax-nsfw-otk-fuente-nf", lang,fuente)).queue();
		}
		ControladorImagenes gi;
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(Color.red);
		eb.setFooter((noanon?"":Values.value("jax-nsfw-img-footer", lang)) + "🙊😏😏",packet.getBot().getSelfUser().getAvatarUrl());
		gi = new ControladorImagenes(e.getChannel(),fi,eb,(cantidad>=8));
		gi.setTags(tags);
		gi.setAutodes(autodes);
		gi.setAutodesTime(autodesTime);
		/*if(noanon) {
			EmbedBuilder eb2 = new EmbedBuilder();
			String str = " imagen" + (cantidad>1?"es":"");
			eb2.setColor(Color.red);
			eb2.addField("A petición de " + e.getAuthor().getName(), cantidad + str + (tags==null?".":" con las siguientes etiquetas: `" + Tools.arrayToString(tags) +"`."), false);
			eb2.addField("Fuente de la" + (cantidad>1?"s":"") + " imagen" + (cantidad>1?"es":"") + ": " + fi.getName(),".",false);
			if(autodes) {
				MessageUtils.SendAndDestroy(e.getChannel(), eb2.build(), cantidad * autodesTime + 3);
			}else {
				e.getChannel().sendMessageEmbeds(eb2.build()).queue();
			}
		}*/
		Thread fuck;
		try {
			if(!packet.getTextChannel().isNSFW()){
				gi.enviarRestriccion(packet.getBot());
				return;
			}
		}catch(Exception ex) {
			
		}
		for(int i = 1; i<=cantidad;i++) {
			fuck = new Thread(gi);
			fuck.start();
		}
	}
	@Command(id="Rule34",desc="Internet esta lleno de reglas, esta es la más importante.",alias= {"r34","34"})
	@Option(id="cantidad",desc="Indica la cantidad de imagenes que devolverá el comando. DEBE SER UN VALOR NUMÉRICO ENTRE 1 Y 100 (se que quieres más, pero tu mano se va a hacer mierda...me preocupo por ti manit@). Si ingresas mal este número te quedarás sin placer:smirk:",alias={"c","cant","num"})
	@Option(id="tags",desc="Etiquetas que describen el contenido esperado. Pueden ser una o mas separadas por comas (','). No abuses de estas porque mientras mas especifica es la busqueda, menos resultados obtenidos. Se permiten espacios entre etiquetas.",alias={"t","tg","tgs"})
	@Option(id="gif",desc="Indica si el contenido es animado o no. Si no se especifica esta opción, por defecto el contenido es animado (equivalente a '-gif true')",alias={"g","gf","animado","anim"})
	@Option(id="video",desc="Esta opcion indica que el recurso devuelto debe ser un video. Si se usa junto con la opcion '-gif', esta ultima sera ignorada.",alias={"v","vid","mp4"})
	@Option(id="random",desc="Establece que los recursos devueltos deben ser videos e imagenes estaticas o animadas de manera aleatoria. Si se usa junto con las opciones '-gif' o '-video', estas seran ignoradas.",alias={"r","rdm","rand","azar"})
	@Option(id="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	//@Option(name="creditos",desc="Da credito a quien invocó e comando. Es algo asi como lo opuesto de 'anonimo'.",alias={"au","cr","credito","autor","nonanon"})
	public static void r34(CommandPacket packet) throws Exception {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		Servidor servidor = (Servidor) packet.getModelGuild();
		Language lang = Language.ES;
		if(packet.isFromGuild())
			lang=servidor.getLanguage();
		packet.getTextChannel().purgeMessagesById(e.getMessageId());
		int cantidad = 1;
		boolean gif = true;
		String[] tags = null;
		boolean video = false;
		boolean autodes = false;
		int autodesTime = 15;
		boolean random = false;
		boolean noanon = false;
		for(InputParameter p : packet.getParameters()) {
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
				}else if(p.getKey().equalsIgnoreCase("creditos")) {
					noanon = true;
				}else if(p.getKey().equalsIgnoreCase("video")) {
					video = true;
					gif=false;
				}else if(p.getKey().equalsIgnoreCase("random")) {
					random = true;
				}
			}
		}
		if(cantidad>100) {
			cantidad = 100;
		}else if(cantidad<=0) {
			cantidad=1;
		}
		ControladorImagenes gi;
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(Color.red);
		eb.setFooter((noanon?"":Values.value("jax-nsfw-img-footer", lang))+"🙊😏😏",packet.getBot().getSelfUser().getAvatarUrl());
		gi = new ControladorImagenes(packet.getChannel(),FuenteImagen.find(FuenteImagen.R34),eb,(cantidad>=8));
		gi.setGif(gif);
		gi.setTags(tags);
		gi.setVideo(video);
		gi.setAutodes(autodes);
		gi.setAutodesTime(autodesTime);
		gi.setRand(random);
		Thread fuck;
		try {
			if(!packet.getTextChannel().isNSFW()){
				gi.enviarRestriccion(packet.getBot());
				return;
			}
		}catch(Exception ex) {
			
		}
		/*if(noanon) {
			EmbedBuilder eb2 = new EmbedBuilder();
			String str = " imagen" + (cantidad>1?"es":"");
			if(random) {
				str = " imagen" + (cantidad>1?"es":"") + " y video" + (cantidad>1?"s":"") + " aleatorio" + (cantidad>1?"s":"");
			}else if(video) {
				str = " video" + (cantidad>1?"s":"");
			}else if(gif) {
				str = " imagen" + (cantidad>1?"es":"") + " animada" + (cantidad>1?"s":"");
			}
			eb2.setColor(Color.red);
			eb2.addField("A petición de " + e.getAuthor().getName(), cantidad + str + (tags==null?".":" con las siguientes etiquetas: `" + Tools.arrayToString(tags) +"`."), false);
			if(autodes) {
				MessageUtils.SendAndDestroy(e.getChannel(), eb2.build(), cantidad * autodesTime + 3);
			}else {
				e.getChannel().sendMessageEmbeds(eb2.build()).queue();
			}
		}*/
		for(int i = 1; i<=cantidad;i++) {
			fuck = new Thread(gi);
			fuck.start();
		}
	}
}
