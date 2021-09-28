package com.jaxsandwich.discordbot.comandos;

import com.jaxsandwich.discordbot.main.util.Comparador;
import com.jaxsandwich.discordbot.main.util.Tools;
import com.jaxsandwich.discordbot.main.util.lavaplayer.*;
import com.jaxsandwich.sandwichcord.annotations.Category;
import com.jaxsandwich.sandwichcord.annotations.Command;
import com.jaxsandwich.sandwichcord.annotations.Option;
import com.jaxsandwich.sandwichcord.core.Values;
import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.models.CommandMode;
import com.jaxsandwich.sandwichcord.models.OptionInput;
import com.jaxsandwich.sandwichcord.models.OptionInput.OptionInputType;
import com.jaxsandwich.sandwichcord.models.packets.ReplyablePacket;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.managers.AudioManager;

@Category(desc="Comandos de música. ¿Que?¿Acaso esperabas otra descripción?", commandMode=CommandMode.CLASIC_AND_SLASH_COMMAND, guilds={"849120528530538536"})
@SuppressWarnings("rawtypes")
public class Musica {
	@Command(id="Reproducir")
	@Option(id="Busqueda",noStandar=true, desc = "Busqueda o URL de la cancion a reproducir.")
	@Option(id="ForzarLista",desc="fuerza al comando a interpretar el link como lista en caso de bug.",alias= {"fl"},type=OptionType.BOOLEAN)
	public static void reproducir(ReplyablePacket packet) {
		if(!packet.isFromGuild())
			return;
		packet.deferReply(true).setContent(".:ok_hand:").queue();
		//boolean autodes=false;
		//int autodesTime =15;
		boolean fl = false;
		String busqueda =null;
		packet.deferReply(true).queue();
		Language lang = packet.getPreferredLang();
		for(OptionInput p : packet.getOptions()) {
			if(p.getType() == OptionInputType.STANDAR) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					//autodes=true;
					if(p.getValueAsString()!=null) {
						//autodesTime = p.getValueAsInt();
					}
				}
				if(p.getKey().equalsIgnoreCase("forzarlista")){
					fl=true;
				}
			}else if(p.getType() == OptionInputType.NO_STANDAR){
				busqueda = p.getValueAsString();
			}
		}
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(packet.getGuild());
		Member self = packet.getBotAsMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();
		Member member = packet.getMember();
		GuildVoiceState memberVoiceState = member.getVoiceState();
		if(!memberVoiceState.inVoiceChannel()) {
			packet.sendMessage(Values.value("jax-i-audio-requerido", lang)).queue();
			return;
		}/*
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("No estoy en tu canal de voz").queue();
			return;
		}*/
		VoiceChannel vchannel = memberVoiceState.getChannel();
		if(selfVoiceState.inVoiceChannel() && (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel()))) {
			packet.sendMessage(Values.value("jax-musica-esperaturno", lang)).queue();
			return;
		}
		if(busqueda==null) {
			if(musicManager.scheduler.player.isPaused()) {
				musicManager.scheduler.player.setPaused(false);
				return;
			}
			packet.sendMessage(Values.value("jax-yt-ingresar-busqueda", lang)).queue();
			return;
		}
		
		AudioManager audioManager = packet.getGuild().getAudioManager();
		audioManager.openAudioConnection(vchannel);
		if(!isURL(busqueda) && !fl) {
			busqueda = "ytsearch:" + busqueda;
		}
		System.out.println("b:"+busqueda);
		PlayerManager.getInstance().loadAndPlay(packet.getTextChannel(), busqueda);
	}
	
	@Command(id="Pausar", enabled=true)
	public static void pausar(ReplyablePacket packet) {
		if(!packet.isFromGuild())
			return;
		packet.deferReply(true).setContent(".:ok_hand:").queue();
		Member self = packet.getBotAsMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();
		Language lang = packet.getPreferredLang();
		if(!selfVoiceState.inVoiceChannel()) {
			packet.sendMessage(Values.value("jax-musica-no-mismocanal", lang)).queue();
			return;
		}
		Member member = packet.getMember();
		GuildVoiceState memberVoiceState = member.getVoiceState();
		if(!memberVoiceState.inVoiceChannel()) {
			packet.sendMessage(Values.value("jax-i-audio-requerido", lang)).queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			packet.sendMessage(Values.value("jax-i-audio-requerido", lang)).queue();
			return;
		}
		
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(packet.getGuild());
		boolean b = musicManager.scheduler.player.isPaused();
		musicManager.scheduler.player.setPaused(!b);
		packet.sendMessage(Tools.stringToEmb(b?Values.value("jax-musica-reproduciendo", lang):Values.value("jax-musica-pausado", lang))).queue();

	}
	
	@Command(id="Siguiente", enabled=true)
	public static void siguiente(ReplyablePacket packet) {
		if(!packet.isFromGuild())
			return;
		packet.deferReply(true).setContent(".:ok_hand:").queue();
		Member self = packet.getBotAsMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();
		Language lang = packet.getPreferredLang();
		if(!selfVoiceState.inVoiceChannel()) {
			packet.sendMessage(Values.value("jax-musica-no-mismocanal", lang)).queue();
			return;
		}
		Member member = packet.getMember();
		GuildVoiceState memberVoiceState = member.getVoiceState();
		
		if(!memberVoiceState.inVoiceChannel()) {
			packet.sendMessage(Values.value("jax-i-audio-requerido", lang)).queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			packet.sendMessage(Values.value("jax-musica-no-mismocanal", lang)).queue();
			return;
		}
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(packet.getGuild());
		AudioPlayer player = musicManager.audioPlayer;
		
		if(player.getPlayingTrack() == null) {
			packet.sendMessage(Values.value("jax-musica-no-reproduciendo", lang)).queue();
			return;
		}
		packet.sendMessage(Tools.stringToEmb(Values.value("jax-musica-saltando", lang))).queue();
		AudioTrack a = musicManager.scheduler.nextTrack();
		if(a!=null) {
			packet.sendMessage(Tools.stringToEmb(Values.formatedValue("jax-musica-rep-ahora", lang, a.getInfo().title)));
		}
	}
	
	@Command(id="Detener", enabled=true)
	public static void detener(ReplyablePacket packet) {
		if(!packet.isFromGuild())
			return;
		packet.deferReply(true).setContent(".:ok_hand:").queue();
		Member self = packet.getBotAsMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();
		Language lang = packet.getPreferredLang();
		if(!selfVoiceState.inVoiceChannel()) {
			packet.sendMessage(Values.value("jax-musica-no-mismocanal", lang)).queue();
			return;
		}
		Member member = packet.getMember();
		GuildVoiceState memberVoiceState = member.getVoiceState();
		if(!memberVoiceState.inVoiceChannel()) {
			packet.sendMessage(Values.value("jax-i-audio-requerido", lang)).queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			packet.sendMessage(Values.value("jax-musica-esperaturno", lang)).queue();
			return;
		}
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(packet.getGuild());
		musicManager.scheduler.player.stopTrack();
		musicManager.scheduler.queue.clear();
		packet.sendMessage(Tools.stringFieldToEmb("Canción detenida","(y lista de reproduccion eliminada.)")).queue();
	}
	
	@Command(id="Actual", enabled=true)
	public static void actual(ReplyablePacket packet) {
		if(!packet.isFromGuild())
			return;
		packet.deferReply(true).setContent(".:ok_hand:").queue();
		Member self = packet.getBotAsMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();
		Language lang = packet.getPreferredLang();
		if(!selfVoiceState.inVoiceChannel()) {
			packet.sendMessage(Values.value("jax-musica-no-mismocanal", lang)).queue();
			return;
		}
		Member member = packet.getMember();
		GuildVoiceState memberVoiceState = member.getVoiceState();
		
		if(!memberVoiceState.inVoiceChannel()) {
			packet.sendMessage(Values.value("jax-i-audio-requerido", lang)).queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			packet.sendMessage(Values.value("jax-musica-esperaturno", lang)).queue();
			return;
		}
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(packet.getGuild());
		AudioTrack track = musicManager.audioPlayer.getPlayingTrack();
		if(track == null) {
			packet.sendMessage(Tools.stringToEmb("No se esta reproduciendo ninguna canción.")).queue();
			return;
		}
		AudioTrackInfo info = track.getInfo();
		packet.sendMessage(Tools.stringFieldToEmb("Canción actual: " + info.title, info.author +" | "+Tools.milliToTimeNoHours(info.length))).queue();
	}
	@Command(id="Cola", enabled=true)
	@Option(id="autodestruir",desc="Elimina el contenido después de los segundos indicados.",alias={"ad","autodes","autorm","arm"},type=OptionType.INTEGER)
	@Option(id="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"},type=OptionType.BOOLEAN)
	public static void cola(ReplyablePacket packet) {
		if(!packet.isFromGuild())
			return;
		packet.deferReply(true).setContent(".:ok_hand:").queue();
		boolean autodes=false;
		int autodesTime =15;
		boolean anon=false;
		for(OptionInput p : packet.getOptions()) {
			if(p.getType() == OptionInputType.STANDAR) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(p.getValueAsString()!=null) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}
			}
		}
		Language lang = packet.getPreferredLang();
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(packet.getGuild());
		if(musicManager.scheduler.queueIsEmpty()) {
			packet.SendAndDestroy(Tools.stringToEmb(Values.value("jax-musica-cola-vacia", lang)), 15);
			return;
		}
		long estimado = musicManager.scheduler.player.getPlayingTrack().getDuration() - musicManager.scheduler.player.getPlayingTrack().getPosition();
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(Values.value("jax-musica-cola-elementos-titulo", lang));
		AudioTrack[] a = musicManager.scheduler.getQueue();
		eb.addField(Values.formatedValue("jax-musica-cola-sig-estimado", lang, Tools.milliToTimeNoHours(estimado)),">>> _*"+a[0].getInfo().title + "*_\n*" + a[0].getInfo().author + " | " + Tools.milliToTimeNoHours(a[0].getInfo().length)+"*", false);
		estimado += a[0].getDuration();
		for(int i = 1; i < a.length; i++) {
			eb.addField(a[i].getInfo().title,a[i].getInfo().author + " | " + Tools.milliToTimeNoHours(a[i].getInfo().length), false);
			estimado += a[i].getDuration();
		}
		System.out.println(estimado);
		eb.setFooter(Values.formatedValue("jax-musica-cola-durrep-estimado", lang,Tools.milliToTimeNoHours(estimado)));
		if(anon) {
			packet.tryDeleteMessage();
		}
		if(autodes) {
			if(autodesTime<=0) {
				autodesTime=5;
			}else if(autodesTime>900) {
				autodesTime=900;
			}
			packet.SendAndDestroy(eb.build(), autodesTime);
		}else {
			packet.sendMessage(eb.build()).queue();
		}
	}
	private static boolean isURL(String txt) {
		String a = Comparador.Encontrar("http[s]{0,1}://[a-zA-Z0-9%.+_-]{1,60}.[a-zA-Z0-9]{2,3}/", txt);
		return a!=null;
	}
}
