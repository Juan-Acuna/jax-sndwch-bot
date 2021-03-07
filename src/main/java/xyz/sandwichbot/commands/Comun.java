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
}
