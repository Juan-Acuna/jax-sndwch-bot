package xyz.sandwichbot.commands;

import java.net.URI;
import java.util.ArrayList;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import xyz.sandwichbot.annotations.Category;
import xyz.sandwichbot.annotations.Command;
import xyz.sandwichbot.core.AutoHelpCommand;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichbot.main.util.Comparador;
import xyz.sandwichbot.main.util.lavaplayer.*;
import xyz.sandwichbot.models.InputParameter;
import xyz.sandwichbot.models.InputParameter.InputParamType;

@Category(desc="Comandos de música. ¿Que?¿Acaso esperabas otra descripción?",visible=false)
public class Musica {
	public static boolean enUso = false;
	
	@Command(name="Play",desc="Reproduce música obtenida desde una fuente de internet (por defecto YouTube.com)",alias= {"p","r","reproducir","pl"},enabled=false)
	public static void reproducir(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		boolean autodes=false;
		int autodesTime =15;
		String busqueda =null;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "Play");
					return;
				}
			}else if(p.getType() == InputParamType.Custom){
				busqueda = p.getValueAsString();
			}
		}
		if(busqueda==null) {
			e.getChannel().sendMessage("Debe ingresar una busqueda.").queue();
			return;
		}
		final TextChannel tChannel = e.getTextChannel();
		final Guild guild = e.getGuild();
		final Member self = guild.getMember(SandwichBot.ActualBot().getJDA().getSelfUser());
		final GuildVoiceState selfVoiceState = self.getVoiceState();
		
		
		
		final Member member = guild.getMember(e.getAuthor());
		final GuildVoiceState memberVoiceState = member.getVoiceState();
		if(!memberVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("primero te tienes que meter a un canal de voz para invocarme, no sea gil manit@").queue();
			return;
		}/*
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("No estoy en tu canal de voz").queue();
			return;
		}*/
		VoiceChannel vchannel = memberVoiceState.getChannel();
		if(selfVoiceState.inVoiceChannel() && (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel()))) {
			tChannel.sendMessage("sorry pero espera tu turno, o puedes venir y pedirme musica (y otras cosas:smirk:)").queue();
			return;
		}
		AudioManager audioManager = guild.getAudioManager();
		audioManager.openAudioConnection(vchannel);
		if(!isURL(busqueda)) {
			busqueda = "ytsearch:" + busqueda;
		}
		
		/*GuildMusicManager mng = PlayerControl.getInstance().getMusicManager(guild);
        //AudioPlayer player = mng.player;
        //TrackScheduler scheduler = mng.scheduler;
        
        PlayerControl.getInstance().loadAndPlay(mng, tChannel, busqueda, false);*/
		
		PlayerManager.getInstance().loadAndPlay(tChannel, busqueda);
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		//AudioPlayer player = musicManager.audioPlayer;
		
		
	}
	
	
	public static void pausar() {
		
	}
	/*
	@Command(name="Siguiente",desc="Salta a la siguiente canción en la cola actual. Si no quedan canciones, la reproducción se termina.",alias={"sk","saltar","skip"})
	public static void siguiente(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		final TextChannel tChannel = e.getTextChannel();
		final Guild guild = e.getGuild();
		final Member self = guild.getMember(SandwichBot.ActualBot().getJDA().getSelfUser());
		final GuildVoiceState selfVoiceState = self.getVoiceState();
		
		
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("No estoy en tu canal de voz").queue();
			return;
		}
		final Member member = guild.getMember(e.getAuthor());
		final GuildVoiceState memberVoiceState = member.getVoiceState();
		
		if(!memberVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("conectate a un canal de voz poh sacowea").queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			tChannel.sendMessage("tienes que estar conmigo en el canal de voz para pedirme musica (y otras cosas:smirk:)").queue();
			return;
		}
		final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		final AudioPlayer player = musicManager.audioPlayer;
		
		if(player.getPlayingTrack() == null) {
			tChannel.sendMessage("No se esta reproduciendo ninguna canción.").queue();
			return;
		}
		tChannel.sendMessage("Saltando...").queue();
		musicManager.scheduler.nextTrack();
	}
	
	@Command(name="Detener",desc="Detiene la reproducción actual.",alias={"stop","det"})
	public static void detener(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		final TextChannel tChannel = e.getTextChannel();
		final Guild guild = e.getGuild();
		final Member self = guild.getMember(SandwichBot.ActualBot().getJDA().getSelfUser());
		final GuildVoiceState selfVoiceState = self.getVoiceState();
		
		
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("No estoy en tu canal de voz").queue();
			return;
		}
		final Member member = guild.getMember(e.getAuthor());
		final GuildVoiceState memberVoiceState = member.getVoiceState();
		if(!memberVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("conectate a un canal de voz poh sacowea").queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			tChannel.sendMessage("tienes que estar conmigo en el canal de voz para pedirme musica (y otras cosas:smirk:)").queue();
			return;
		}
		
		final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		musicManager.scheduler.player.stopTrack();
		musicManager.scheduler.queue.clear();
		tChannel.sendMessage("Canción detenida (y lista de reproduccion eliminada.)").queue();
	}
	
	@Command(name="Actual",desc="Indica la canción que se esta reproduciendo actualmente.",alias= {"np","playing","cancion"})
	public static void actual(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		final TextChannel tChannel = e.getTextChannel();
		final Guild guild = e.getGuild();
		final Member self = guild.getMember(SandwichBot.ActualBot().getJDA().getSelfUser());
		final GuildVoiceState selfVoiceState = self.getVoiceState();
		
		
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("No estoy en tu canal de voz").queue();
			return;
		}
		final Member member = guild.getMember(e.getAuthor());
		final GuildVoiceState memberVoiceState = member.getVoiceState();
		
		if(!memberVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("conectate a un canal de voz poh sacowea").queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			tChannel.sendMessage("tienes que estar conmigo en el canal de voz para pedirme musica (y otras cosas:smirk:)").queue();
			return;
		}
		final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		final AudioPlayer player = musicManager.audioPlayer;
		final AudioTrack track = player.getPlayingTrack();
		if(track == null) {
			tChannel.sendMessage("No se esta reproduciendo ninguna canción.").queue();
			return;
		}
		final AudioTrackInfo info = track.getInfo();
		tChannel.sendMessageFormat("Canción actual: %s de %s (%s)",info.title,info.author,info.uri).queue();
	}*/
	
	public static void buscar() {
		
	}
	
	public static void cola() {
		
	}
	
	private static boolean isURL(String txt) {
		String a = Comparador.Encontrar("http[s]{0,1}://[a-zA-Z0-9%+_-]{1,60}.[a-zA-Z0-9]{2,3}/", txt);
		return a!=null;
	}
}
