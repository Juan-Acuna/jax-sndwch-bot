package xyz.sandwichbot.main.util.lavaplayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class PlayerManager {
	private static PlayerManager _instance;
	private Map<String, GuildMusicManager> musicManagers;
	private AudioPlayerManager audioPlayerManager;
	
	public PlayerManager() {
		this.musicManagers = new HashMap<>();
		this.audioPlayerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(audioPlayerManager);
		AudioSourceManagers.registerLocalSource(audioPlayerManager);
	}
	public GuildMusicManager getMusicManager(Guild guild) {
		/*return this.musicManagers.computeIfAbsent(guild.getId(), (guildId) -> {
			GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
			guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
			
			return guildMusicManager;
		});*/
		/*System.out.println("GUILDS REGISTRADOS:");
		for(String s : musicManagers.keySet()) {
			System.out.println(s + " | " + s.equals(guild.getId()) + " | " + musicManagers.containsKey(guild.getId()));
		}*/
		if(musicManagers.containsKey(guild.getId())) {
			return musicManagers.get(guild.getId());
		}else {
			GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
			guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
			musicManagers.put(guild.getId(), guildMusicManager);
			return musicManagers.get(guild.getId());
		}
	}
	public static PlayerManager getInstance() {
		if(_instance==null) {
			return _instance = new PlayerManager();
		}
		return _instance;
	}
	public void loadAndPlay(TextChannel channel, String trackUrl){
		final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());
		this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				boolean b = musicManager.scheduler.queue(track);
				System.out.println("track: "+track.getInfo().title);
				if(b) {
					channel.sendMessage("Reproduciendo: *")
					.append(track.getInfo().title)
					.append("* de *")
					.append(track.getInfo().author + "*")
					.queue();
				}else {
					channel.sendMessage("Agregando a la cola: *")
					.append(track.getInfo().title)
					.append("* de *")
					.append(track.getInfo().author + "*")
					.queue();
				}
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				List<AudioTrack> tracks = playlist.getTracks();
				if(playlist.isSearchResult()) {
					trackLoaded(tracks.get(0));
					return;
				}
				channel.sendMessage("Agregando cola de reproducción: *")
				.append(String.valueOf(tracks.size()))
				.append("* canciones de la lista *")
				.append(playlist.getName() + "*")
				.queue();
				for(AudioTrack track : tracks) {
					musicManager.scheduler.queue(track);
				}
			}

			@Override
			public void noMatches() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	public void playUrl(Guild guild, String trackUrl){
		final GuildMusicManager musicManager = this.getMusicManager(guild);
		this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				musicManager.scheduler.queue(track);
				System.out.println("track: "+track.getInfo().title);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				List<AudioTrack> tracks = playlist.getTracks();
				if(playlist.isSearchResult()) {
					trackLoaded(tracks.get(0));
					return;
				}
				for(AudioTrack track : tracks) {
					musicManager.scheduler.queue(track);
				}
			}

			@Override
			public void noMatches() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	public void playUrl(Guild guild, String trackUrl,int starts){
		final GuildMusicManager musicManager = this.getMusicManager(guild);
		this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				track.setPosition(starts);
				musicManager.scheduler.queue(track);
				System.out.println("track: "+track.getInfo().title);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				List<AudioTrack> tracks = playlist.getTracks();
				if(playlist.isSearchResult()) {
					trackLoaded(tracks.get(0));
					return;
				}
				for(AudioTrack track : tracks) {
					musicManager.scheduler.queue(track);
				}
			}

			@Override
			public void noMatches() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
