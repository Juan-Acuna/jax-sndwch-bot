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
	@Command(name="Saludar",desc="Da un cálido saludo a un amigo(aún no funciona con menciones:pensive:)",alias={"s","saluda","putea","putear"})
	@Parameter(name="Nombre del objetivo",desc="Nombre del objetivo(ejemplo: Tulencio).\nSe permiten espacios. Todo texto que comience con un '-' no formará parte del nombre.")
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	public static void saludar(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		String nombre = null;
		boolean autodes = false;
		int autodesTime=15;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equals("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equals("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equals(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "Saludar");
					return;
				}
			}else if(p.getType() == InputParamType.Custom){
				nombre = p.getValueAsString();
			}
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
	public static void youtube(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		String busqueda = null;
		boolean autodes = false;
		int autodesTime=15;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equals("autodestruir")) {
					autodes=true;
					if(!p.getValueAsString().equals("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equals(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "YouTube");
					return;
				}
			}else if(p.getType() == InputParamType.Custom){
				busqueda = p.getValueAsString();
			}
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
			e.getChannel().sendMessage("No se encontraron resultados):").queue();
			return;
		}else {
			e.getChannel().sendMessage("Debe especificar una busqueda.").queue();
		}
		
	}
	@Command(name="Invocar",desc="Comano extraño pero útil. Hace que me conecte a los canales de texto y voz del invocador",alias= {"invoke","llamar","ven"})
	public static void invocar(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
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
		if(Musica.enUso) {
			//no puedo, ya estoy en uso
			//posiblemente mande a otros bots de musica
			return;
		}
		if(!invocador.getVoiceState().inVoiceChannel()) {
			//necesita estar en un canal de voz para reproducir musica
			e.getChannel().sendMessage("primero te tienes que meter a un canal de voz para invocarme, no sea gil manit@").queue();
			return;
		}
		AudioManager audioManager = guild.getAudioManager();
		e.getChannel().sendMessage("voy").queue();
		audioManager.openAudioConnection(vchannel);
	}
	@Command(name="Presentacion",desc="Comando para saber más de mi (no es lo mismo que el de ayuda).",alias= {"informacion","info","inf"})//,enabled=false,visible=false)
	public static void info(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
		
		
		
		e.getChannel().sendMessage(getInfo()).queue();
	}
	public static MessageEmbed getInfo() {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Wena l@s cabr@s del server!");
		eb.setDescription("Me presento: me llamo :sandwich:Jax Sandwich y como podrás observar, soy un sandwich "
		+"(lleno de mayonesa igual que tú :smirk::peach:).\nSoy bastante joven, apenas tengo unas dos semanas de vida "
		+"(soy más ilegal que tu loli preferida) y esta es apenas mi primera vez siendo hostead@\nSi, lo se, use una @, "
		+"pos resulta que soy un bot de discord, así que basicamente no tengo sexo(a menos que tu quieras bb:kissing_heart:)."
		+"\nHora de ir directo al grano: no hago mucho, pero estoy segur@ que disfrutarás mi contenido:wink::smirk:.");
		eb.addField("¡Ah, lo olvidaba!", "Para saber que verga puedo hacer, escribe '"+SandwichBot.ActualBot().getPrefijo()+"ayuda'🍑", false);
		eb.addBlankField(false);
		eb.addBlankField(false);
		eb.addField(">>> VERSION: 0.0.1_SNAPSHOT, primera versión online estable.\nPara más información acerca de este bot, "
				+"visita:~~me el ano~~ sitio web aún no disponible.", "", false);
		eb.setFooter("DISCLAIMER: No soy dueño de ninguno de los recursos gráficos ni el contenido relativo a pokémon "
		+"provistos por este bot. Todo ese contenido le pertenece a las fuentes originales donde fueron obtenidas. "
		+"Este bot es solo para entretenimiento y no lucra con su contenido.");
		eb.setColor(Color.yellow);
		return eb.build();
	}
}
