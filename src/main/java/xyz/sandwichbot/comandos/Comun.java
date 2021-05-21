package xyz.sandwichbot.comandos;

import java.awt.Color;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import xyz.sandwichbot.main.Constantes;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichbot.main.util.ClienteHttp;
import xyz.sandwichbot.main.util.Comparador;
import xyz.sandwichbot.main.util.Tools;
import xyz.sandwichbot.main.util.Tools.EarrapeSRC;
import xyz.sandwichbot.main.util.lavaplayer.GuildMusicManager;
import xyz.sandwichbot.main.util.lavaplayer.PlayerManager;
import xyz.sandwichframework.annotations.*;
import xyz.sandwichframework.core.AutoHelpCommand;
import xyz.sandwichframework.core.ExtraCmdManager;
import xyz.sandwichframework.models.InputParameter;
import xyz.sandwichframework.models.InputParameter.InputParamType;

@Category(desc="Comandos frecuentes con propósitos variados.")
public class Comun {
	@Command(name="Saludar",enabled=false)
	@Parameter(name="Nombre del objetivo",desc="Nombre del objetivo(ejemplo: Tulencio).\nSe permiten espacios. Todo texto que comience con un '-' no formará parte del nombre.")
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void saludar(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		String nombre = null;
		boolean autodes = false;
		int autodesTime=15;
		boolean anon=false;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.AUTO_HELP_KEY)) {
					AutoHelpCommand.sendHelp(e.getChannel(), "Saludar");
					return;
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
				SandwichBot.SendAndDestroy(e.getChannel(),"Wena po "+nombre+" ql!", autodesTime);
			}else {
				e.getChannel().sendMessage("Wena po "+nombre+" ql!").queue();
			}
			return;
		}
		EmbedBuilder eb = new EmbedBuilder();
		eb.setThumbnail(SandwichBot.ActualBot().getJDA().getSelfUser().getAvatarUrl());
		e.getChannel().sendMessage(eb.build()).queue();
		e.getChannel().sendMessage("Debe especificar un nombre.").queue();
	}
	@Command(name="YouTube")
	@Parameter(name="Nombre del objetivo",desc="Texto con el cual se realizará la busqueda en Youtube (ejemplo: s.youtube '[creampie](https://preppykitchen.com/cream-pie/)'... chucha, creo me equivoqué de página xD).\nSe permiten espacios. Todo texto que comience con un '-' no formara parte de la busqueda.")
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void youtube(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		String busqueda = null;
		boolean autodes = false;
		int autodesTime=15;
		boolean anon = false;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.AUTO_HELP_KEY)) {
					AutoHelpCommand.sendHelp(e.getChannel(), "YouTube");
					return;
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
			hc = new String(hc.getBytes(),StandardCharsets.UTF_8);
			ArrayList<String> ids = Comparador.EncontrarTodos(Comparador.Patrones.Youtube_Link, hc);
			ArrayList<String> tit = Comparador.EncontrarTodos(Comparador.Patrones.Youtube_Title, hc);
			if(ids.size()>0) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Resultados de la busqueda:");
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
					SandwichBot.SendAndDestroy(e.getChannel(),eb.build(), autodesTime);
				}else {
					e.getChannel().sendMessage(eb.build()).queue();
				}
				return;
			}
			SandwichBot.SendAndDestroy(e.getChannel(),"No se encontraron resultados):", 10);
			return;
		}else {
			SandwichBot.SendAndDestroy(e.getChannel(),"Debe especificar una busqueda.", 10);
		}
		
	}
	@Command(name="Invocar")
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void invocar(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		boolean autodes = false;
		int autodesTime = 15;
		boolean anon = false;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.AUTO_HELP_KEY)) {
					AutoHelpCommand.sendHelp(e.getChannel(), "Presentacion");
					return;
				}
			}
		}
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		Guild guild = e.getGuild();
		Member m = guild.getMember(SandwichBot.ActualBot().getJDA().getSelfUser());
		GuildVoiceState vs = m.getVoiceState();
		Member invocador = guild.getMember(e.getAuthor());
		VoiceChannel vchannel = invocador.getVoiceState().getChannel();
		
		if(vs.inVoiceChannel()) {
			if(vs.getChannel()==vchannel) {
				return;
			}
		}
		if(!invocador.getVoiceState().inVoiceChannel()) {
			//necesita estar en un canal de voz para reproducir musica
			if(autodes) {
				SandwichBot.SendAndDestroy(e.getChannel(),"primero te tienes que meter a un canal de voz para invocarme, no sea gil manit@",autodesTime);
			}else {
				e.getChannel().sendMessage("primero te tienes que meter a un canal de voz para invocarme, no sea gil manit@").queue();
			}
			return;
		}
		AudioManager audioManager = guild.getAudioManager();
		if(autodes) {
			SandwichBot.SendAndDestroy(e.getChannel(),"voy",autodesTime);
		}else {
			e.getChannel().sendMessage("voy").queue();
		}
		
		audioManager.openAudioConnection(vchannel);
	}
	@Command(name="Presentacion")//,enabled=false,visible=false)
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void info(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		boolean autodes = false;
		int autodesTime = 15;
		boolean anon = false;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.AUTO_HELP_KEY)) {
					AutoHelpCommand.sendHelp(e.getChannel(), "Presentacion");
					return;
				}
			}
		}
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		if(autodes) {
			SandwichBot.SendAndDestroy(e.getChannel(),SandwichBot.getInfo(e.getTextChannel().isNSFW()),autodesTime);
		}else {
			e.getChannel().sendMessage(SandwichBot.getInfo(e.getTextChannel().isNSFW())).queue();
		}
	}
	
	@Command(name="VoteBan",enabled=false)
	@Parameter(name="Nombre Objetivo(mención)",desc="Nombre(mención) del usuario a Banear. Se permiten mas de uno.")
	public static void voteban(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	
	@Command(name="Embed")
	@Parameter(name="Mensaje(opcional)",desc="Convierte el mensaje en un campo. Los campos estan conformados por un titulo y una descripción. Para indicar donde acaba un tituo y empieza una descripcion, escriba `{%}`. Este parametro es opcional y puede usarse junto con otras opciones.")
	@Option(name="titulo",desc="Corresponde al titulo del embed.",alias={"t","tit","title"})
	@Option(name="descripcion",desc="Corresponde a la descripción del embed.",alias={"d","desc"})
	@Option(name="campo",desc="Corresponde a un campo en el embed. Los campos estan conformados por un titulo y una descripción. Para indicar donde acaba un tituo y empieza una descripcion, escriba `{%}`.",alias={"f","field","cam"})
	@Option(name="footer",desc="Corresponde al footer del embed. El footer permite poner una imagen, para ello escriba la url de la imagen entre las etiquetas `{%img%}`.",alias={"ft","foo","ftr"})
	@Option(name="autor",desc="Corresponde al autor del embed. Si se deja en blanco, se usará el usuario que invocó el comando. Se puede incluir un link en el autor, escribiendolo entre las etiquetas `{%href%}`. También se puede incluir una imagen, para ello escriba la url de la imagen entre las etiquetas `{%img%}`.",alias={"a","author"})
	@Option(name="imagen",desc="Corresponde a la imagen del embed. Si se deja en blanco, se usará el usuario que invocó el comando.",alias={"i","img"})
	@Option(name="thumbnail",desc="Corresponde al thumbnail del embed. El thumbnail es ina imagen pequeña, creo que es como una miniatura o algo asi. Si se deja en blanco, usaré mi cara porque si, soy muy sexy como para no usarla:smirk:",alias={"tn","thumb","th","tum", "imagenchikita"})
	@Option(name="color",desc="Corresponde a el color del embed. Se debe proporcionar un valor hexadecimal de 3 o 6 caracteres sin contar el caracter '#'(ej: #fff). También están permitidos los siguientes colores predefinidos: blanco, negro, rojo, verde, azul, amarillo, gris, gris oscuro, cian y magenta (se debe escribir el nombre correctamente. Se permiten también en ingrlés).",alias={"c","col"})
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void embed(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		boolean autodes = false;
		int autodesTime = 15;
		boolean anon = false;
		EmbedBuilder eb = new EmbedBuilder();
		//ArrayList<String> escapados = new ArrayList<String>();
		ArrayList<String> campos = new ArrayList<String>();
		
		String titulo = null;
		String descripcion = null;
		String autor = null;
		String mensaje = "";
		String color = "gray";
		String footer = null;
		String img = null;
		String thumb = null;
		String footer_img = null;
		
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
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
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.AUTO_HELP_KEY)) {
					AutoHelpCommand.sendHelp(e.getChannel(), "Embed");
					return;
				}
			}else if(p.getType() == InputParamType.Custom){
				mensaje = p.getValueAsString();
			}/*else if(p.getType() == InputParamType.Invalid){
				escapados.add(p.getKey() + p.getValueAsString());
			}*/
		}
		//CONVERTIR STRING A COLOR (?)
		
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
				System.out.println("nombre: '"+aNombre+"'");
				System.out.println("href: '"+aHref+"'");
				System.out.println("img: '"+aImg+"'");
				System.out.println("autor: '"+autor+"'");
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
				thumb = SandwichBot.ActualBot().getJDA().getSelfUser().getAvatarUrl();
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
			SandwichBot.SendAndDestroy(e.getChannel(),eb.build(),autodesTime);
		}else {
			e.getChannel().sendMessage(eb.build()).queue();
		}
	}
	
	@Command(name="Funar")
	@Parameter(name="Nombre del objetivo",desc="nombre del usuario a ser funado (Puede ser una mención).")
	@Option(name="razon",desc="Corresponde a la razón de la funa. Si no se especifica, la razón sera 'por put@'.",alias={"r","reason"})
	@Option(name="recompensa",desc="Recompensa de la funa (en tokens:smirk:).",alias={"tokens","t"})
	@Option(name="autor",desc="Corresponde al autor de la funa. Si se deja en blanco, se usará el usuario que invocó el comando. Se puede incluir un link en el autor, escribiendolo entre las etiquetas `{%href%}`. También se puede incluir una imagen, para ello escriba la url de la imagen entre las etiquetas `{%img%}`. `SI NO SE ESPECIFICA ESTA OPCIÓN LA FUNA SERÁ ANONIMA.`",alias={"a","author"})
	@Option(name="imagen",desc="Corresponde a la imagen del embed. Si se deja vacío, su usará la imagen del usuario funado (SOLO si este cuenta con una y se usó una mencion para entregar su nombre). Use esta opcion para especificar la imagen por url, de lo contrario suba un archivo.",alias={"i","img"})
	@Option(name="color",desc="Corresponde a el color del embed. Se debe proporcionar un valor hexadecimal de 3 o 6 caracteres sin contar el caracter '#'(ej: #fff). También están permitidos los siguientes colores predefinidos: blanco, negro, rojo, verde, azul, amarillo, gris, gris oscuro, cian y magenta (se debe escribir el nombre correctamente. Se permiten también en ingrlés).",alias={"c","col"})
	@Option(name="genero",desc="Indica si a quien se funa es hombre o mujer. Los valores son:\nHOMBRE: m, masculino y hombre\nMUJER: f, femenino y mujer\nSi no se especifica esta opción, no se asumirá ninguno.",alias={"gen","g","sexo"})
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void funar(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
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
		
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
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
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.AUTO_HELP_KEY)) {
					AutoHelpCommand.sendHelp(e.getChannel(), "Funar");
					return;
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
		eb.setFooter("Para reclamar la recompensa, favor entregar al funao en la inter, será recibido por "+ Tools.getRandomGuy() +" o llame al +56 9 4983 0717.", Tools.toValidHttpUrl(footer_img));
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
			SandwichBot.SendAndDestroy(e.getChannel(),eb.build(),autodesTime);
		}else {
			e.getChannel().sendMessage(eb.build()).queue();
		}
	}
	
	@Command(name="Trollear")
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"},enabled=false,visible=false)
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	@Option(name="earrape",desc="Reproduce un audio que hace mierda el oido. Puedes especificarl la url con esta opción o dejarla vacía y yo haré el resto.",alias={"e","ear","errape"})
	public static void trollear(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		boolean autodes = false;
		int autodesTime = 15;
		boolean anon = false;
		EarrapeSRC er_src = new EarrapeSRC();
		boolean er = false;
		EmbedBuilder eb = new EmbedBuilder();
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
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
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.AUTO_HELP_KEY)) {
					AutoHelpCommand.sendHelp(e.getChannel(), "Trollear");
					return;
				}
			}
		}
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		TextChannel tChannel = e.getTextChannel();
		Guild guild = e.getGuild();
		Member member;
		Member self = guild.getMember(SandwichBot.ActualBot().getJDA().getSelfUser());
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
}
