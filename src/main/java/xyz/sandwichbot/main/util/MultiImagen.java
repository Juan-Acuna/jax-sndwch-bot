package xyz.sandwichbot.main.util;

import java.awt.Color;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONObject;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import xyz.sandwichbot.main.Constantes;
import xyz.sandwichbot.main.SandwichBot;

public class MultiImagen implements Runnable{
	MessageChannel channel;
	boolean pausado = false;
	Lock lock = new ReentrantLock();
	
	public MultiImagen(MessageChannel channel) {
		this.channel=channel;
	}
	public MultiImagen(MessageChannel channel, boolean pausado) {
		this.channel=channel;
		this.pausado=pausado;
	}
	@Override
	public void run() {
		enviarImagen();
	}
	public void enviarImagen() {
		lock.lock();
		try {
			if(pausado) {
				Thread.sleep(1000);
			}else {
				Thread.sleep(300);
			}
			String hc;
			hc = ClienteHttp.peticionHttp(Constantes.RecursoExterno.LINK_RANDOM_CAT);
			EmbedBuilder eb = new EmbedBuilder();
			JSONObject j = new JSONObject(hc);
			eb.setFooter("Meow:3",SandwichBot.ActualBot().getJDA().getSelfUser().getAvatarUrl());
			eb.setColor(Color.green);
			eb.setImage(j.getString("file"));
			channel.sendMessage(eb.build()).queue();
			lock.unlock();
		} catch (Exception e) {
			lock.unlock();
			e.printStackTrace();
		}
		
	}
}
