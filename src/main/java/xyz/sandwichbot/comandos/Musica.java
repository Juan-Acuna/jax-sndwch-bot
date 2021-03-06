package xyz.sandwichbot.comandos;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import xyz.sandwichbot.main.util.Comparador;
import xyz.sandwichbot.main.util.Tools;
import xyz.sandwichbot.main.util.lavaplayer.*;
import xyz.sandwichframework.annotations.Category;
import xyz.sandwichframework.annotations.Command;
import xyz.sandwichframework.annotations.Option;
import xyz.sandwichframework.annotations.Parameter;
import xyz.sandwichframework.core.Values;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.core.util.MessageUtils;
import xyz.sandwichframework.models.CommandPacket;
import xyz.sandwichframework.models.InputParameter;
import xyz.sandwichframework.models.InputParameter.InputParamType;
import xyz.sandwichframework.models.discord.ModelGuild;

@Category(desc="Comandos de música. ¿Que?¿Acaso esperabas otra descripción?")
public class Musica {
	@Command(name="Reproducir")
	@Parameter(name="Busqueda/URL", desc = "Busqueda o URL de la cancion a reproducir.")
	public static void reproducir(CommandPacket packet) {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		boolean autodes=false;
		int autodesTime =15;
		String busqueda =null;
		Language lang = Language.ES;
		ModelGuild servidor;
		if(e.isFromGuild()) {
			servidor = packet.getModelGuild();
			if(servidor!=null)
				lang=servidor.getLanguage();
		}
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}
			}else if(p.getType() == InputParamType.Custom){
				busqueda = p.getValueAsString();
			}
		}
		TextChannel tChannel = e.getTextChannel();
		Guild guild = e.getGuild();
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		Member self = guild.getMember(packet.getBot().getSelfUser());
		GuildVoiceState selfVoiceState = self.getVoiceState();
		Member member = guild.getMember(e.getAuthor());
		GuildVoiceState memberVoiceState = member.getVoiceState();
		if(!memberVoiceState.inVoiceChannel()) {
			tChannel.sendMessage(Values.value("jax-i-audio-requerido", lang)).queue();
			return;
		}/*
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage("No estoy en tu canal de voz").queue();
			return;
		}*/
		VoiceChannel vchannel = memberVoiceState.getChannel();
		if(selfVoiceState.inVoiceChannel() && (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel()))) {
			tChannel.sendMessage(Values.value("jax-musica-esperaturno", lang)).queue();
			return;
		}
		if(busqueda==null) {
			if(musicManager.scheduler.player.isPaused()) {
				musicManager.scheduler.player.setPaused(false);
				return;
			}
			e.getChannel().sendMessage(Values.value("jax-yt-ingresar-busqueda", lang)).queue();
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
	public static void pausar(CommandPacket packet) {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		TextChannel tChannel = packet.getTextChannel();
		Guild guild = e.getGuild();
		Member self = guild.getMember(packet.getBot().getSelfUser());
		GuildVoiceState selfVoiceState = self.getVoiceState();
		Language lang = Language.ES;
		ModelGuild servidor;
		if(e.isFromGuild()) {
			servidor = packet.getModelGuild();
			if(servidor!=null)
				lang=servidor.getLanguage();
		}
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage(Values.value("jax-musica-no-mismocanal", lang)).queue();
			return;
		}
		Member member = guild.getMember(e.getAuthor());
		GuildVoiceState memberVoiceState = member.getVoiceState();
		if(!memberVoiceState.inVoiceChannel()) {
			tChannel.sendMessage(Values.value("jax-i-audio-requerido", lang)).queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			tChannel.sendMessage(Values.value("jax-i-audio-requerido", lang)).queue();
			return;
		}
		
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		boolean b = musicManager.scheduler.player.isPaused();
		musicManager.scheduler.player.setPaused(!b);
		tChannel.sendMessageEmbeds(Tools.stringToEmb(b?Values.value("jax-musica-reproduciendo", lang):Values.value("jax-musica-pausado", lang))).queue();

	}
	
	@Command(name="Siguiente")
	public static void siguiente(CommandPacket packet) {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		TextChannel tChannel = e.getTextChannel();
		Guild guild = e.getGuild();
		Member self = guild.getMember(packet.getBot().getSelfUser());
		GuildVoiceState selfVoiceState = self.getVoiceState();
		Language lang = Language.ES;
		ModelGuild servidor;
		if(e.isFromGuild()) {
			servidor = packet.getModelGuild();
			if(servidor!=null)
				lang=servidor.getLanguage();
		}
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage(Values.value("jax-musica-no-mismocanal", lang)).queue();
			return;
		}
		Member member = guild.getMember(e.getAuthor());
		GuildVoiceState memberVoiceState = member.getVoiceState();
		
		if(!memberVoiceState.inVoiceChannel()) {
			tChannel.sendMessage(Values.value("jax-i-audio-requerido", lang)).queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			tChannel.sendMessage(Values.value("jax-musica-no-mismocanal", lang)).queue();
			return;
		}
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		AudioPlayer player = musicManager.audioPlayer;
		
		if(player.getPlayingTrack() == null) {
			tChannel.sendMessage(Values.value("jax-musica-no-reproduciendo", lang)).queue();
			return;
		}
		tChannel.sendMessageEmbeds(Tools.stringToEmb(Values.value("jax-musica-saltando", lang))).queue();
		AudioTrack a = musicManager.scheduler.nextTrack();
		if(a!=null) {
			tChannel.sendMessageEmbeds(Tools.stringToEmb(Values.formatedValue("jax-musica-rep-ahora", lang, a.getInfo().title)));
		}
	}
	
	@Command(name="Detener")
	public static void detener(CommandPacket packet) {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		TextChannel tChannel = e.getTextChannel();
		Guild guild = e.getGuild();
		Member self = guild.getMember(packet.getBot().getSelfUser());
		GuildVoiceState selfVoiceState = self.getVoiceState();
		Language lang = Language.ES;
		ModelGuild servidor;
		if(e.isFromGuild()) {
			servidor = packet.getModelGuild();
			if(servidor!=null)
				lang=servidor.getLanguage();
		}
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage(Values.value("jax-musica-no-mismocanal", lang)).queue();
			return;
		}
		Member member = guild.getMember(e.getAuthor());
		GuildVoiceState memberVoiceState = member.getVoiceState();
		if(!memberVoiceState.inVoiceChannel()) {
			tChannel.sendMessage(Values.value("jax-i-audio-requerido", lang)).queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			tChannel.sendMessage(Values.value("jax-musica-esperaturno", lang)).queue();
			return;
		}
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		musicManager.scheduler.player.stopTrack();
		musicManager.scheduler.queue.clear();
		tChannel.sendMessageEmbeds(Tools.stringFieldToEmb("Canción detenida","(y lista de reproduccion eliminada.)")).queue();
	}
	
	@Command(name="Actual")
	public static void actual(CommandPacket packet) {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		TextChannel tChannel = e.getTextChannel();
		Guild guild = e.getGuild();
		Member self = guild.getMember(packet.getBot().getSelfUser());
		GuildVoiceState selfVoiceState = self.getVoiceState();
		Language lang = Language.ES;
		ModelGuild servidor;
		if(e.isFromGuild()) {
			servidor = packet.getModelGuild();
			if(servidor!=null)
				lang=servidor.getLanguage();
		}
		if(!selfVoiceState.inVoiceChannel()) {
			tChannel.sendMessage(Values.value("jax-musica-no-mismocanal", lang)).queue();
			return;
		}
		Member member = guild.getMember(e.getAuthor());
		GuildVoiceState memberVoiceState = member.getVoiceState();
		
		if(!memberVoiceState.inVoiceChannel()) {
			tChannel.sendMessage(Values.value("jax-i-audio-requerido", lang)).queue();
			return;
		}
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			tChannel.sendMessage(Values.value("jax-musica-esperaturno", lang)).queue();
			return;
		}
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
		AudioTrack track = musicManager.audioPlayer.getPlayingTrack();
		if(track == null) {
			tChannel.sendMessageEmbeds(Tools.stringToEmb("No se esta reproduciendo ninguna canción.")).queue();
			return;
		}
		AudioTrackInfo info = track.getInfo();
		tChannel.sendMessageEmbeds(Tools.stringFieldToEmb("Canción actual: " + info.title, info.author +" | "+Tools.milliToTimeNoHours(info.length))).queue();
	}
	@Command(name="Cola")
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void cola(CommandPacket packet) {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		boolean autodes=false;
		int autodesTime =15;
		boolean anon=false;
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}
			}
		}
		Language lang = Language.ES;
		ModelGuild servidor;
		if(e.isFromGuild()) {
			servidor = packet.getModelGuild();
			if(servidor!=null)
				lang=servidor.getLanguage();
		}
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(e.getGuild());
		if(musicManager.scheduler.queueIsEmpty()) {
			MessageUtils.SendAndDestroy(e.getChannel(), Tools.stringToEmb(Values.value("jax-musica-cola-vacia", lang)), 15);
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
			e.getChannel().purgeMessagesById(e.getMessageId());
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
			
	}
	
	private static boolean isURL(String txt) {
		String a = Comparador.Encontrar("http[s]{0,1}://[a-zA-Z0-9%+_-]{1,60}.[a-zA-Z0-9]{2,3}/", txt);
		return a!=null;
	}
}
