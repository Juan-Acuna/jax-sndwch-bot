package xyz.sandwichbot.main.util.lavaplayer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

public class TrackScheduler extends AudioEventAdapter{
	public AudioPlayer player;
	public BlockingQueue<AudioTrack> queue;
	public TrackScheduler(AudioPlayer player) {
		this.player=player;
		this.queue=new LinkedBlockingQueue<>();
	}
	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if(endReason.mayStartNext) {
			nextTrack();
		}
	}
	
	public AudioTrack nextTrack() {
		AudioTrack a = this.queue.poll();
		this.player.startTrack(a, false);
		return a;
	}
	public boolean queue(AudioTrack track) {
		if(!this.player.startTrack(track, true)) {
			this.queue.offer(track);
			return false;
		}
		return true;
	}
}
