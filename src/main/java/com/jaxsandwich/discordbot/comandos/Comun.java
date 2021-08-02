package com.jaxsandwich.discordbot.comandos;

import java.awt.Color;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.jaxsandwich.discordbot.main.Constantes;
import com.jaxsandwich.discordbot.main.SandwichBot;
import com.jaxsandwich.discordbot.main.util.ClienteHttp;
import com.jaxsandwich.discordbot.main.util.Comparador;
import com.jaxsandwich.discordbot.main.util.Tools;
import com.jaxsandwich.discordbot.main.util.Tools.EarrapeSRC;
import com.jaxsandwich.discordbot.main.util.lavaplayer.PlayerManager;
import com.jaxsandwich.framework.annotations.*;
import com.jaxsandwich.framework.core.Values;
import com.jaxsandwich.framework.core.util.Language;
import com.jaxsandwich.framework.core.util.MessageUtils;
import com.jaxsandwich.framework.models.CommandPacket;
import com.jaxsandwich.framework.models.InputParameter;
import com.jaxsandwich.framework.models.InputParameter.InputParamType;
import com.jaxsandwich.framework.models.discord.ConfigGuild;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

@Category(desc="Comandos frecuentes con propósitos variados.")
public class Comun {
	@Command(id="Saludar",enabled=false)
	@Parameter(name="Nombre del objetivo",desc="Nombre del objetivo(ejemplo: Tulencio).\nSe permiten espacios. Todo texto que comience con un '-' no formará parte del nombre.")
	@Option(id="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(id="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void saludar(CommandPacket packet) {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		String nombre = null;
		boolean autodes = false;
		int autodesTime=15;
		boolean anon=false;
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(p.getValueAsString()!=null) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}
			}else if(p.getType() == InputParamType.Custom){
				nombre = p.getValueAsString();
			}
		}
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		if(nombre!=null) {
			if(autodes) {
				if(autodesTime<=0) {
					autodesTime=5;
				}else if(autodesTime>900) {
					autodesTime=900;
				}
				MessageUtils.SendAndDestroy(e.getChannel(),"Wena po "+nombre+" ql!", autodesTime);
			}else {
				e.getChannel().sendMessage("Wena po "+nombre+" ql!").queue();
			}
			return;
		}
		EmbedBuilder eb = new EmbedBuilder();
		eb.setThumbnail(packet.getBot().getSelfUser().getAvatarUrl());
		e.getChannel().sendMessageEmbeds(eb.build()).queue();
		e.getChannel().sendMessage("Debe especificar un nombre.").queue();
	}
	
	@Command(id="YouTube")
	@Parameter(name="Nombre del objetivo",desc="Texto con el cual se realizará la busqueda en Youtube (ejemplo: s.youtube '[creampie](https://preppykitchen.com/cream-pie/)'... chucha, creo me equivoqué de página xD).\nSe permiten espacios. Todo texto que comience con un '-' no formara parte de la busqueda.")
	@Option(id="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(id="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void youtube(CommandPacket packet) throws Exception {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		Language lang = packet.getPreferredLang();
		String busqueda = null;
		boolean autodes = false;
		int autodesTime=15;
		boolean anon = false;
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(p.getValueAsString()!=null) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}
			}else if(p.getType() == InputParamType.Custom){
				busqueda = p.getValueAsString();
			}
		}
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		if(busqueda!=null) {
			busqueda = URLEncoder.encode(busqueda, StandardCharsets.UTF_8.toString());
			String hc = ClienteHttp.peticionHttp(Constantes.RecursoExterno.LINK_YOUTUBE_QUERY+busqueda);
			ArrayList<String> ids = Comparador.EncontrarTodos(Comparador.Patrones.Youtube_Link, hc);
			ArrayList<String> tit = Comparador.EncontrarTodos(Comparador.Patrones.Youtube_Title, hc);
			if(ids.size()>0) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle(Values.value("jax-yt-resultado-busqueda", lang));
				eb.setColor(Color.DARK_GRAY);
				for(int i=0;i<ids.size() && i<24;i++) {
					if(i>=tit.size()) {
						break;
					}
					eb.addField("",">>> [" + tit.get(i).replace("\"title\":{\"runs\":[{\"text\":\"","").replace("\"}],\"accessibility\"","") + "](" + Constantes.RecursoExterno.LINK_YOUTUBE_BASE + ids.get(i) + ")", true);
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
				return;
			}
			MessageUtils.SendAndDestroy(e.getChannel(),Values.value("jax-yt-no-resultados", lang), 10);
			return;
		}else {
			MessageUtils.SendAndDestroy(e.getChannel(),Values.value("jax-yt-ingresar-busqueda", lang), 10);
		}
		
	}
	@Command(id="Invocar")
	@Option(id="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(id="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void invocar(CommandPacket packet) {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		boolean autodes = false;
		int autodesTime = 15;
		boolean anon = false;
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(p.getValueAsString()!=null) {
						autodesTime = p.getValueAsInt();
					}
				}if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}
			}
		}
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		Language lang = packet.getPreferredLang();
		if(!packet.isFromGuild()) {
			packet.getTextChannel().sendMessage("Solo se puede usar este comando en un servidor.").queue();
			return;
		}
		Guild guild = e.getGuild();
		Member invocador = guild.getMember(e.getAuthor());
		if(autodes) {
			MessageUtils.SendAndDestroy(e.getChannel(),Values.value("jax-i-voy", lang),autodesTime);
		}else {
			e.getChannel().sendMessage(Values.value("jax-i-voy", lang)).queue();
		}
		((SandwichBot)packet.getBot()).conectarVoz(lang, invocador,e.getTextChannel(), guild.getAudioManager());
	}
	@Command(id="Presentacion")
	@Option(id="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(id="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void info(CommandPacket packet) {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		boolean autodes = false;
		int autodesTime = 15;
		boolean anon = false;
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(p.getValueAsString()!=null) {
						autodesTime = p.getValueAsInt();
					}
				}if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}
			}
		}
		ConfigGuild server = packet.getModelGuild();
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		if(autodes) {
			MessageUtils.SendAndDestroy(e.getChannel(),((SandwichBot)packet.getBot()).getInfo(server.getLanguage()),autodesTime);
		}else {
			e.getChannel().sendMessageEmbeds(((SandwichBot)packet.getBot()).getInfo(server.getLanguage())).queue();
		}
	}
	
	@Command(id="VoteBan",enabled=false)
	@Parameter(name="Nombre Objetivo(mención)",desc="Nombre(mención) del usuario a Banear. Se permiten mas de uno.")
	public static void voteban(CommandPacket packet) {
		
	}
	
	@Command(id="Embed")
	@Parameter(name="Mensaje(opcional)",desc="Convierte el mensaje en un campo. Los campos estan conformados por un titulo y una descripción. Para indicar donde acaba un tituo y empieza una descripcion, escriba `{%}`. Este parametro es opcional y puede usarse junto con otras opciones.")
	@Option(id="titulo",desc="Corresponde al titulo del embed.",alias={"t","tit","title"})
	@Option(id="descripcion",desc="Corresponde a la descripción del embed.",alias={"d","desc"})
	@Option(id="campo",desc="Corresponde a un campo en el embed. Los campos estan conformados por un titulo y una descripción. Para indicar donde acaba un tituo y empieza una descripcion, escriba `{%}`.",alias={"f","field","cam"})
	@Option(id="footer",desc="Corresponde al footer del embed. El footer permite poner una imagen, para ello escriba la url de la imagen entre las etiquetas `{%img%}`.",alias={"ft","foo","ftr"})
	@Option(id="autor",desc="Corresponde al autor del embed. Si se deja en blanco, se usará el usuario que invocó el comando. Se puede incluir un link en el autor, escribiendolo entre las etiquetas `{%href%}`. También se puede incluir una imagen, para ello escriba la url de la imagen entre las etiquetas `{%img%}`.",alias={"a","author"})
	@Option(id="imagen",desc="Corresponde a la imagen del embed. Si se deja en blanco, se usará el usuario que invocó el comando.",alias={"i","img"})
	@Option(id="thumbnail",desc="Corresponde al thumbnail del embed. El thumbnail es ina imagen pequeña, creo que es como una miniatura o algo asi. Si se deja en blanco, usaré mi cara porque si, soy muy sexy como para no usarla:smirk:",alias={"tn","thumb","th","tum", "imagenchikita"})
	@Option(id="color",desc="Corresponde a el color del embed. Se debe proporcionar un valor hexadecimal de 3 o 6 caracteres sin contar el caracter '#'(ej: #fff). También están permitidos los siguientes colores predefinidos: blanco, negro, rojo, verde, azul, amarillo, gris, gris oscuro, cian y magenta (se debe escribir el nombre correctamente. Se permiten también en ingrlés).",alias={"c","col"})
	@Option(id="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(id="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void embed(CommandPacket packet) {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		boolean autodes = false;
		int autodesTime = 15;
		boolean anon = false;
		EmbedBuilder eb = new EmbedBuilder();
		ArrayList<String> campos = new ArrayList<String>();
		
		String titulo = null;
		String descripcion = null;
		String autor = null;
		String mensaje = null;
		String color = "gray";
		String footer = null;
		String img = null;
		String thumb = null;
		String footer_img = null;
		
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(p.getValueAsString()!=null) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}else if(p.getKey().equalsIgnoreCase("titulo")) {
					titulo=p.getValueAsString();
				}else if(p.getKey().equalsIgnoreCase("descripcion")) {
					descripcion=p.getValueAsString();
				}else if(p.getKey().equalsIgnoreCase("campo")) {
					campos.add(p.getValueAsString());
				}else if(p.getKey().equalsIgnoreCase("footer")) {
					footer = p.getValueAsString();
				}else if(p.getKey().equalsIgnoreCase("imagen")) {
					img = p.getValueAsString();
				}else if(p.getKey().equalsIgnoreCase("thumbnail")) {
					thumb = p.getValueAsString();
				}else if(p.getKey().equalsIgnoreCase("color")) {
					color = p.getValueAsString();
				}else if(p.getKey().equalsIgnoreCase("autor")) {
					autor = p.getValueAsString();
				}
			}else if(p.getType() == InputParamType.Custom){
				mensaje = p.getValueAsString();
			}
		}
		eb.setTitle(titulo);
		eb.setDescription(descripcion);
		if(autor!=null) {
			if(autor.equals("none")) {
				eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getAvatarUrl());
			}else {
				String aHref = Comparador.Encontrar("\\{%href%\\}(.{5,500})\\{%href%\\}",autor);
				String aImg = Comparador.Encontrar("\\{%img%\\}(.{5,500})\\{%img%\\}",autor);
				String aNombre = Tools.replaceFromString(aImg,Tools.replaceFromString(aHref,autor,""),"");
				aImg = Tools.toValidHttpUrl(Tools.replaceAllFromString("\\{%img%\\}", aImg, ""));
				aHref = Tools.toValidHttpUrl(Tools.replaceAllFromString("\\{%href%\\}", aHref, ""));
				eb.setAuthor(aNombre, aHref, aImg);
			}
		}
		eb.setFooter(footer, Tools.toValidHttpUrl(footer_img));
		if(img!=null) {
			if(img.equals("none")) {
				img = e.getAuthor().getAvatarUrl();
			}
		}
		eb.setImage(Tools.toValidHttpUrl(img));
		if(thumb!=null) {
			if(thumb.equals("none")) {
				thumb = packet.getBot().getSelfUser().getAvatarUrl();
			}
		}
		eb.setThumbnail(Tools.toValidHttpUrl(thumb));
		eb.setColor(Tools.stringColorCast(color));
		if(mensaje!=null) {
			String[] cs = mensaje.split("\\{%\\}", 2);
			if(cs.length>1) {
				eb.addField(cs[0], Tools.tryToMarkDownLink(cs[1]), false);
			}else {
				eb.addField(cs[0], "", false);
			}
		}
		for(String c : campos) {
			String[] cs = c.split("\\{%\\}", 2);
			if(cs.length>1) {
				eb.addField(cs[0], Tools.tryToMarkDownLink(cs[1]), false);
			}else {
				eb.addField(cs[0], "", false);
			}
		}
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		if(autodes) {
			MessageUtils.SendAndDestroy(e.getChannel(),eb.build(),autodesTime);
		}else {
			e.getChannel().sendMessageEmbeds(eb.build()).queue();
		}
	}
	
	@Command(id="Funar")
	@Parameter(name="Nombre del objetivo",desc="nombre del usuario a ser funado (Puede ser una mención).")
	@Option(id="razon",desc="Corresponde a la razón de la funa. Si no se especifica, la razón sera 'por put@'.",alias={"r","reason"})
	@Option(id="recompensa",desc="Recompensa de la funa (en tokens:smirk:).",alias={"tokens","t"})
	@Option(id="autor",desc="Corresponde al autor de la funa. Si se deja en blanco, se usará el usuario que invocó el comando. Se puede incluir un link en el autor, escribiendolo entre las etiquetas `{%href%}`. También se puede incluir una imagen, para ello escriba la url de la imagen entre las etiquetas `{%img%}`. `SI NO SE ESPECIFICA ESTA OPCIÓN LA FUNA SERÁ ANONIMA.`",alias={"a","author"})
	@Option(id="imagen",desc="Corresponde a la imagen del embed. Si se deja vacío, su usará la imagen del usuario funado (SOLO si este cuenta con una y se usó una mencion para entregar su nombre). Use esta opcion para especificar la imagen por url, de lo contrario suba un archivo.",alias={"i","img"})
	@Option(id="color",desc="Corresponde a el color del embed. Se debe proporcionar un valor hexadecimal de 3 o 6 caracteres sin contar el caracter '#'(ej: #fff). También están permitidos los siguientes colores predefinidos: blanco, negro, rojo, verde, azul, amarillo, gris, gris oscuro, cian y magenta (se debe escribir el nombre correctamente. Se permiten también en ingrlés).",alias={"c","col"})
	@Option(id="genero",desc="Indica si a quien se funa es hombre o mujer. Los valores son:\nHOMBRE: m, masculino y hombre\nMUJER: f, femenino y mujer\nSi no se especifica esta opción, no se asumirá ninguno.",alias={"gen","g","sexo"})
	@Option(id="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(id="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void funar(CommandPacket packet) {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		boolean autodes = false;
		int autodesTime = 15;
		boolean anon = false;
		EmbedBuilder eb = new EmbedBuilder();
		
		User mencionado = null;
		String genero = null;
		String nombre = null;
		String razon = null;
		String recompensa = null;
		String autor = null;
		String color = "negro";
		String img = null;
		String footer_img = null;
		
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(p.getValueAsString()!=null) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}else if(p.getKey().equalsIgnoreCase("razon")) {
					razon=p.getValueAsString();
				}else if(p.getKey().equalsIgnoreCase("recompensa")) {
					recompensa=p.getValueAsString();
				}else if(p.getKey().equalsIgnoreCase("imagen")) {
					img = p.getValueAsString();
				}else if(p.getKey().equalsIgnoreCase("color")) {
					color = p.getValueAsString();
				}else if(p.getKey().equalsIgnoreCase("genero")) {
					if(p.getValueAsString().equalsIgnoreCase("m") || p.getValueAsString().equalsIgnoreCase("masculino") || p.getValueAsString().equalsIgnoreCase("hombre")) {
						genero="m";
					}else if(p.getValueAsString().equalsIgnoreCase("f") || p.getValueAsString().equalsIgnoreCase("femenino") || p.getValueAsString().equalsIgnoreCase("mujer")) {
						genero = "f";
					}
				}else if(p.getKey().equalsIgnoreCase("autor")) {
					autor = p.getValueAsString();
				}
			}else if(p.getType() == InputParamType.Custom){
				nombre = p.getValueAsString();
			}
		}
		if(e.getMessage().getMentionedUsers().size()>=1) {
			mencionado = e.getMessage().getMentionedUsers().get(0);
			//se etiqueta
		}
		eb.setTitle("SE BUSCA");
		eb.setDescription("Se busca" +(genero==null?",":(genero.equals("f")?" a esta ctm":" a este ctm")) + " por " + (razon==null?"ser muy "+(genero==null?"put@":(genero.equals("f")?"puta":"puto")):razon) + ".");
		if(autor!=null) {
			if(autor.equals("none")) {
				eb.setAuthor(e.getAuthor().getName(), null, e.getAuthor().getAvatarUrl());
			}else {
				String aHref = Comparador.Encontrar("\\{%href%\\}(.{5,500})\\{%href%\\}",autor);
				String aImg = Comparador.Encontrar("\\{%img%\\}(.{5,500})\\{%img%\\}",autor);
				String aNombre = Tools.replaceFromString(aImg,Tools.replaceFromString(aHref,autor,""),"");
				aImg = Tools.toValidHttpUrl(Tools.replaceAllFromString("\\{%img%\\}", aImg, ""));
				aHref = Tools.toValidHttpUrl(Tools.replaceAllFromString("\\{%href%\\}", aHref, ""));
				eb.setAuthor(aNombre, aHref, aImg);
			}
		}else {
			eb.setAuthor("Anónimo");
		}
		eb.addField("VIV" + (genero==null?"@":(genero.equals("f")?"A":"O")) + " O MUERT" + (genero==null?"@":(genero.equals("f")?"A":"O")), ">>> Se ofrece recompensa de `" + recompensa + " Tokens` para quien logre su captura.", false);
		if(mencionado!=null) {
			nombre = mencionado.getAsMention();
		}
		eb.addField("", "`Responde al nombre de `" + nombre + ".", false);
		eb.setFooter("Para reclamar la recompensa, favor entregar "+(genero==null?"al funado":(genero.equals("f")?"a la funada":"al funado"))+" en la inter, será recibid"+ (genero==null?"@":(genero.equals("f")?"a":"o"))+" por "+ Tools.getRandomGuy() +" o llame al +56 9 4983 0717.", Tools.toValidHttpUrl(footer_img));
		if(img!=null) {
			if(img.equals("none") && mencionado != null) {
				img = mencionado.getAvatarUrl();
			}
		}else if(e.getMessage().getAttachments().size()>=1){
			if(e.getMessage().getAttachments().get(0).isImage()) {
				img = e.getMessage().getAttachments().get(0).getUrl();
			}
		}
		if(img!=null && !img.equals("none")) {
			eb.setImage(Tools.toValidHttpUrl(img));
		}else {
			eb.addField("", "``` \n\n\n Imagen no disponible. \n\n\n ```", false);
		}
		eb.setColor(Tools.stringColorCast(color));
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		if(autodes) {
			MessageUtils.SendAndDestroy(e.getChannel(),eb.build(),autodesTime);
		}else {
			e.getChannel().sendMessageEmbeds(eb.build()).queue();
		}
	}
	
	@Command(id="Trollear")
	//@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"},enabled=false,visible=false)
	@Option(id="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	@Option(id="earrape",desc="Reproduce un audio que hace mierda el oido. Puedes especificarl la url con esta opción o dejarla vacía y yo haré el resto.",alias={"e","ear","errape"})
	public static void trollear(CommandPacket packet) throws Exception {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		//boolean autodes = false;
		//int autodesTime = 15;
		boolean anon = false;
		EarrapeSRC er_src = new EarrapeSRC();
		boolean er = false;
		//EmbedBuilder eb = new EmbedBuilder();
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					//autodes=true;
					if(p.getValueAsString()!=null) {
						//autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}else if(p.getKey().equalsIgnoreCase("earrape")) {
					er=true;
					if(!p.getValueAsString().equals("none")) {
						er_src.url = p.getValueAsString().split("\\s")[0];
						if(p.getValueAsString().split("\\s")[1].matches("[0-9]{1,6}")) {
							er_src.duracion = Integer.parseInt(p.getValueAsString().split("\\s")[1]);
						}
					}
				}
			}
		}
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		Guild guild = e.getGuild();
		Member member;
		//Member self = guild.getMember(packet.getBot().getSelfUser());
		if(e.getMessage().getMentionedMembers().size()>0) {
			member = e.getMessage().getMentionedMembers().get(0);
		}else {
			member = guild.getMember(e.getAuthor());
		}
		GuildVoiceState memberVoiceState = member.getVoiceState();
		if(!memberVoiceState.inVoiceChannel()) {
			return;
		}
		VoiceChannel vchannel = memberVoiceState.getChannel();
		AudioManager audioManager = guild.getAudioManager();
		audioManager.openAudioConnection(vchannel);
		if(er) {
			if(er_src.url==null) {
				er_src = Tools.getRandomEarrapeSource();
			}
			PlayerManager.getInstance().playUrl(guild, Tools.toValidHttpUrl(er_src.url),er_src.inicio);
			
			if(er_src.duracion!=0) {
				Thread.sleep(er_src.duracion);
				PlayerManager.getInstance().getMusicManager(guild).scheduler.player.stopTrack();
				audioManager.closeAudioConnection();
			}
		}
	}
	@Command(id="RAE",enabled=false)
	public static void rae(CommandPacket packet) {
		
	}
	@Command(id="Test",visible=false,enabled=false)
	public static void test(CommandPacket packet) throws IOException{
		
	}
}
