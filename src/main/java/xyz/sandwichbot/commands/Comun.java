package xyz.sandwichbot.commands;

import java.awt.Color;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import xyz.sandwichbot.annotations.*;
import xyz.sandwichbot.core.AutoHelpCommand;
import xyz.sandwichbot.main.Constantes;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichbot.main.util.ClienteHttp;
import xyz.sandwichbot.main.util.Comparador;
import xyz.sandwichbot.main.util.Tools;
import xyz.sandwichbot.models.InputParameter;
import xyz.sandwichbot.models.InputParameter.InputParamType;

@Category(desc="Comandos frecuentes con propósitos variados.")
public class Comun {
	@Command(name="Saludar",desc="Da un cálido saludo a un amigo(aún no funciona con menciones:pensive:)",alias={"s","saluda","putea","putear"},enabled=false)
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
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
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
	@Command(name="YouTube",desc="Realiza la busqueda solicitada y devuelve una lista con los primeros resultados encontrados(Aún no soy capaz de reproducirlos, denme tiempo:pensive:).",alias={"yt","y","yutu","video","videos","llutu"})
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
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
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
	@Command(name="Invocar",desc="Comano extraño pero útil. Hace que me conecte a los canales de texto y voz del invocador",alias= {"invoke","llamar","ven"})
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
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
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
	@Command(name="Presentacion",desc="Comando para saber más de mi (no es lo mismo que el de ayuda).",alias= {"informacion","info","inf"})//,enabled=false,visible=false)
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
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
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
	
	@Command(name="VoteBan",desc="Inicia una votacion para banear a un usuario del servidor(temporalmente). *ESTE COMANDO REQUIERE SER ACTIVADO PREVIAMENTE POR UN ADMINISTRADOR DEL SERVIDOR.*",alias= {"vb","vban"},enabled=false)
	@Parameter(name="Nombre Objetivo(mención)",desc="Nombre(mención) del usuario a Banear. Se permiten mas de uno.")
	public static void voteban(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	
	@Command(name="Embed",desc="Devuelve el mensaje introducido por el usuario en forma de 'Embed' (mensaje decorado con un estilo especial de discord). para usar el caracter '-'(caracter de opciones) se debe anteponer \\ para reconocerlo como texto. Para generar enlaces ([como este](http://google.com)), escriba el título del enlace entre las etiquetas `{%link%}`, seguido de la url entre las etiquetas `{%href%}` (ejemplo:`{%link%}Google{%link%}{%href%}google.com{%href%}`). Los links solo funcionan en la descripción del embed y en las descripciones de los campos.",alias= {"emb"},enabled=true)
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
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
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
			System.out.println(cs.length);
			System.out.println(cs[1]);
			System.out.println(Tools.tryToMarkDownLink(cs[1]));
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
	
}
