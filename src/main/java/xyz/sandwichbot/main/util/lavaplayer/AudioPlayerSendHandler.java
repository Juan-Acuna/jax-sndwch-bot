package xyz.sandwichbot.main.util.lavaplayer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;

import net.dv8tion.jda.api.audio.AudioSendHandler;

public class AudioPlayerSendHandler implements AudioSendHandler{
	private AudioPlayer audioPlayer;
	private ByteBuffer buffer;
	private MutableAudioFrame frame;
	//private AudioFrame lastFrame;
	
	
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
		/*lastFrame =  audioPlayer.provide();
		return lastFrame != null;*/
	}

	@Override
	public ByteBuffer provide20MsAudio() {
		// TODO Auto-generated method stub
		Buffer tmp = ((Buffer)this.buffer).flip();
		return (ByteBuffer) tmp;
		//return ByteBuffer.wrap(lastFrame.getData());
	}
	@Override
	public boolean isOpus() {
		return true;
	}
}
