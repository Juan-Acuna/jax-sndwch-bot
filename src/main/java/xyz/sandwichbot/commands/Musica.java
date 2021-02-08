package xyz.sandwichbot.commands;

import java.util.ArrayList;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import xyz.sandwichbot.annotations.Category;
import xyz.sandwichbot.annotations.Command;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichbot.models.InputParameter;

@Category(desc="Comandos de música. ¿Que?¿Acaso esperabas otra descripción?")
public class Musica {
	public static boolean enUso = false;
	
	@Command(name="play",desc="Reproduce musica obtenida desde una fuente de internet (por defecto YouTube.com)",alias= {"p","r","reproducir","pl"},enabled=false)
	public static void reproducir(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		//TextChannel txtChannel = e.getTextChannel();
		Guild guild = e.getGuild();
		Member m = guild.getMember(SandwichBot.ActualBot().getJDA().getSelfUser());
		GuildVoiceState vs = m.getVoiceState();
		if(vs.inVoiceChannel()) {
			//agrega cancion a la reproduccion
			return;
		}
		if(Musica.enUso) {
			//no puedo, ya estoy en uso
			//posiblemente mande a otros bots de musica
			return;
		}
		Member invocador = guild.getMember(e.getAuthor());
		if(!invocador.getVoiceState().inVoiceChannel()) {
			//necesita estar en un canal de voz para reproducir musica
			return;
		}
		
		AudioManager audioManager = guild.getAudioManager();
		VoiceChannel vchannel = invocador.getVoiceState().getChannel();
		e.getChannel().sendMessage("voy").queue();
		audioManager.openAudioConnection(vchannel);
	}
	
	
	public static void pausar() {
		
	}
	
	public static void detener() {
		
	}
	
	public static void buscar() {
		
	}
	
	public static void cola() {
		
	}
	
}
