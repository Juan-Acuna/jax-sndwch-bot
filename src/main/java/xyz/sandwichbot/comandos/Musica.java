package xyz.sandwichbot.comandos;

import java.util.ArrayList;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichbot.main.util.Comparador;
import xyz.sandwichbot.main.util.Tools;
import xyz.sandwichbot.main.util.lavaplayer.*;
import xyz.sandwichframework.annotations.Category;
import xyz.sandwichframework.annotations.Command;
import xyz.sandwichframework.annotations.Option;
import xyz.sandwichframework.core.AutoHelpCommand;
import xyz.sandwichframework.models.InputParameter;
import xyz.sandwichframework.models.InputParameter.InputParamType;

@Category(desc="Comandos de música. ¿Que?¿Acaso esperabas otra descripción?")
public class Musica {
	@Command(name="Reproducir")
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
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.AUTO_HELP_KEY)) {
					AutoHelpCommand.sendHelp(e.getChannel(), "Reproducir");
					return;
				}
			}else if(p.getType() == InputParamType.Custom){
				busqueda = p.getValueAsString();
			}
		}
		TextChannel tChannel = e.getTextChannel();
		Guild guild = e.getGuild();
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		Member self = guild.getMember(SandwichBot.ActualBot().getJDA().getSelfUser());
		GuildVoiceState selfVoiceState = self.getVoiceState();
		Member member = guild.getMember(e.getAuthor());
		GuildVoiceState memberVoiceState = member.getVoiceState();
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
		if(busqueda==null) {
			if(musicManager.scheduler.player.isPaused()) {
				musicManager.scheduler.player.setPaused(false);
				return;
			}
			e.getChannel().sendMessage("Debe ingresar una busqueda.").queue();
			return;
		}
		
		AudioManager audioManager = guild.getAudioManager();
		audioManager.openAudioConnection(vchannel);
		if(!isURL(busqueda)) {
			busqueda = "ytsearch:" + busqueda;
		}
		PlayerManager.getInstance().loadAndPlay(tChannel, busqueda);
	}
	
	@Command(name="Pausar")
	public static void pausar(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		TextChannel tChannel = e.getTextChannel();
		Guild guild = e.getGuild();
		Member self = guild.getMember(SandwichBot.ActualBot().getJDA().getSelfUser());
		GuildVoiceState selfVoiceState = self.getVoiceState();
		
		
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("No estoy en tu canal de voz").queue();
			return;
		}
		Member member = guild.getMember(e.getAuthor());
		GuildVoiceState memberVoiceState = member.getVoiceState();
		if(!memberVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("conectate a un canal de voz poh sacowea").queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			tChannel.sendMessage("tienes que estar conmigo en el canal de voz para pedirme musica (y otras cosas:smirk:)").queue();
			return;
		}
		
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		boolean b = musicManager.scheduler.player.isPaused();
		musicManager.scheduler.player.setPaused(!b);
		tChannel.sendMessage(Tools.stringToEmb(b?"Reproduciendo.":"Cancion pausada.")).queue();

	}
	
	@Command(name="Siguiente")
	public static void siguiente(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		TextChannel tChannel = e.getTextChannel();
		Guild guild = e.getGuild();
		Member self = guild.getMember(SandwichBot.ActualBot().getJDA().getSelfUser());
		GuildVoiceState selfVoiceState = self.getVoiceState();
		
		
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("No estoy en tu canal de voz").queue();
			return;
		}
		Member member = guild.getMember(e.getAuthor());
		GuildVoiceState memberVoiceState = member.getVoiceState();
		
		if(!memberVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("conectate a un canal de voz poh sacowea").queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			tChannel.sendMessage("tienes que estar conmigo en el canal de voz para pedirme musica (y otras cosas:smirk:)").queue();
			return;
		}
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		AudioPlayer player = musicManager.audioPlayer;
		
		if(player.getPlayingTrack() == null) {
			tChannel.sendMessage("No se esta reproduciendo ninguna canción.").queue();
			return;
		}
		tChannel.sendMessage(Tools.stringToEmb("Saltando...")).queue();
		AudioTrack a = musicManager.scheduler.nextTrack();
		if(a!=null) {
			tChannel.sendMessage(Tools.stringToEmb("Reproduciendo: " + a.getInfo().title));
		}
	}
	
	@Command(name="Detener")
	public static void detener(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		TextChannel tChannel = e.getTextChannel();
		Guild guild = e.getGuild();
		Member self = guild.getMember(SandwichBot.ActualBot().getJDA().getSelfUser());
		GuildVoiceState selfVoiceState = self.getVoiceState();
		
		
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("No estoy en tu canal de voz").queue();
			return;
		}
		Member member = guild.getMember(e.getAuthor());
		GuildVoiceState memberVoiceState = member.getVoiceState();
		if(!memberVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("conectate a un canal de voz poh sacowea").queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			tChannel.sendMessage("tienes que estar conmigo en el canal de voz para pedirme musica (y otras cosas:smirk:)").queue();
			return;
		}
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		musicManager.scheduler.player.stopTrack();
		musicManager.scheduler.queue.clear();
		tChannel.sendMessage(Tools.stringFieldToEmb("Canción detenida","(y lista de reproduccion eliminada.)")).queue();
	}
	
	@Command(name="Actual")
	public static void actual(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		TextChannel tChannel = e.getTextChannel();
		Guild guild = e.getGuild();
		Member self = guild.getMember(SandwichBot.ActualBot().getJDA().getSelfUser());
		GuildVoiceState selfVoiceState = self.getVoiceState();
		
		
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("No estoy en tu canal de voz").queue();
			return;
		}
		Member member = guild.getMember(e.getAuthor());
		GuildVoiceState memberVoiceState = member.getVoiceState();
		
		if(!memberVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("conectate a un canal de voz poh sacowea").queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			tChannel.sendMessage("tienes que estar conmigo en el canal de voz para pedirme musica (y otras cosas:smirk:)").queue();
			return;
		}
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		AudioTrack track = musicManager.audioPlayer.getPlayingTrack();
		if(track == null) {
			tChannel.sendMessage(Tools.stringToEmb("No se esta reproduciendo ninguna canción.")).queue();
			return;
		}
		AudioTrackInfo info = track.getInfo();
		tChannel.sendMessage(Tools.stringFieldToEmb("Canción actual: " + info.title, info.author +" | "+Tools.milliToTimeNoHours(info.length))).queue();
	}
	@Command(name="Cola")
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void cola(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		boolean autodes=false;
		int autodesTime =15;
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
					AutoHelpCommand.sendHelp(e.getChannel(), "Cola");
					return;
				}
			}
		}
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(e.getGuild());
		if(musicManager.scheduler.queueIsEmpty()) {
			SandwichBot.SendAndDestroy(e.getChannel(), Tools.stringToEmb("La cola esta vacía."), 15);
			return;
		}
		long estimado = musicManager.scheduler.player.getPlayingTrack().getDuration() - musicManager.scheduler.player.getPlayingTrack().getPosition();
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Elementos en cola:");
		AudioTrack[] a = musicManager.scheduler.getQueue();
		eb.addField("Siguiente (reproduciendo en "+ Tools.milliToTimeNoHours(estimado) +"):",">>> _*"+a[0].getInfo().title + "*_\n*" + a[0].getInfo().author + " | " + Tools.milliToTimeNoHours(a[0].getInfo().length)+"*", false);
		estimado += a[0].getDuration();
		for(int i = 1; i < a.length; i++) {
			eb.addField(a[i].getInfo().title,a[i].getInfo().author + " | " + Tools.milliToTimeNoHours(a[i].getInfo().length), false);
			estimado += a[i].getDuration();
		}
		System.out.println(estimado);
		eb.setFooter("Duracion del tiempo de reproducción estimado: " + Tools.milliToTimeNoHours(estimado));
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
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
			
	}
	
	private static boolean isURL(String txt) {
		String a = Comparador.Encontrar("http[s]{0,1}://[a-zA-Z0-9%+_-]{1,60}.[a-zA-Z0-9]{2,3}/", txt);
		return a!=null;
	}
}
