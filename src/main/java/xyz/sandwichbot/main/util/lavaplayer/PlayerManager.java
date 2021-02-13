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
	private final Map<Long, GuildMusicManager> musicManagers;
	private final AudioPlayerManager audioPlayerManager;
	
	public PlayerManager() {
		this.musicManagers = new HashMap<>();
		this.audioPlayerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(audioPlayerManager);
		AudioSourceManagers.registerLocalSource(audioPlayerManager);
	}
	public GuildMusicManager getMusicManager(Guild guild) {
		return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
			final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
			guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
			
			return guildMusicManager;
		});
	}
	public static PlayerManager getInstance() {
		if(_instance==null) {
			return new PlayerManager();
		}
		return _instance;
	}
	public void loadAndPlay(TextChannel channel, String trackUrl){
		final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());
		this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				System.out.println("uniendo a la cola");
				musicManager.scheduler.queue(track);
				System.out.println("ya se unio a la cola");
				channel.sendMessage("Reproduciendo: *")
				.append(track.getInfo().title)
				.append("* de *")
				.append(track.getInfo().author + "*")
				.queue();
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				final List<AudioTrack> tracks = playlist.getTracks();
				if(playlist.isSearchResult()) {
					trackLoaded(tracks.get(0));
					return;
				}
				channel.sendMessage("AGREGANDO A LA COLA: *")
				.append(String.valueOf(tracks.size()))
				.append("* canciones de la lista *")
				.append(playlist.getName() + "*")
				.queue();
				for(final AudioTrack track : tracks) {
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
