package xyz.sandwichbot.main.util.lavaplayer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;

import net.dv8tion.jda.api.audio.AudioSendHandler;

public class AudioPlayerSendHandler implements AudioSendHandler{
	private final AudioPlayer audioPlayer;
	private final ByteBuffer buffer;
	private final MutableAudioFrame frame;
	
	
	public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
		super();
		this.audioPlayer = audioPlayer;
		this.buffer = ByteBuffer.allocate(1024);
		this.frame = new MutableAudioFrame();
		this.frame.setBuffer(buffer);
	}

	@Override
	public boolean canProvide() {
		// TODO Auto-generated method stub
		return this.audioPlayer.provide(this.frame);
	}

	@Override
	public ByteBuffer provide20MsAudio() {
		// TODO Auto-generated method stub
		final Buffer tmp = ((Buffer)this.buffer).flip();
		return (ByteBuffer) tmp;
	}
	@Override
	public boolean isOpus() {
		return true;
	}
}
