package com.jaxsandwich.discordbot.main.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONObject;

import com.jaxsandwich.discordbot.main.Constantes;
import com.jaxsandwich.discordbot.main.modelos.FuenteImagen;
import com.jaxsandwich.framework.core.Bot;
import com.jaxsandwich.framework.core.Values;
import com.jaxsandwich.framework.core.util.Language;
import com.jaxsandwich.framework.core.util.MessageUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class ControladorImagenes implements Runnable{

	protected Language lang = Language.ES;
	MessageChannel channel;
	boolean pausado = false;
	FuenteImagen fuente;
	String[] tags = null;
	boolean gif = true;
	boolean video =false;
	boolean autodes =false;
	int autodesTime = 15;
	boolean nsfw = false;
	boolean rand = false;
	static Random random;
	EmbedBuilder builder = null;
	MessageEmbed embed;
	Lock lock = new ReentrantLock();
	
	@Override
	public void run() {
		enviarImagen();
	}
	public void enviarImagen() {
		lock.lock();
		String lnk = "";
		try {
			if(pausado) {
				Thread.sleep(1000);
			}else {
				Thread.sleep(300);
			}
			if(!fuente.isApi()) {
				lnk = linkBooru();
				//System.out.println("lnk: " + lnk);
				if(lnk==null) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setFooter(Values.value("jax-no-cont", lang),Constantes.JaxSandwich.Imagenes.nonsfw);
					MessageUtils.SendAndDestroy(channel, eb.build(), 15);
					return;
				}
			}else{
				lnk = linkAPI();
			}
			//System.out.println("url: " + lnk);
			builder.setImage(lnk);
			lock.unlock();
			if(autodes) {
				if(lnk.endsWith(".mp4") || lnk.endsWith(".mpeg4") || lnk.endsWith(".webm") || lnk.endsWith(".avi") || lnk.endsWith(".wmv") || lnk.endsWith(".3gp")) {
					MessageUtils.SendAndDestroy(channel, lnk, autodesTime);
					return;
				}
				MessageUtils.SendAndDestroy(channel, builder.build(), autodesTime);
			}else {
				if(lnk.endsWith(".mp4") || lnk.endsWith(".mpeg4") || lnk.endsWith(".webm") || lnk.endsWith(".avi") || lnk.endsWith(".wmv") || lnk.endsWith(".3gp")) {
					channel.sendMessage(lnk).queue();
					return;
				}
				channel.sendMessageEmbeds(builder.build()).queue();
			}
		} catch (Exception e) {
			e.printStackTrace();
			lock.unlock();
		}
	}
	
	private String linkAPI() throws Exception {
		String hc = ClienteHttp.peticionHttp(fuente.getQueryUrl());
		if(hc == null) {
			throw new Exception();
		}
		JSONObject j = new JSONObject(hc);
		return j.getString(fuente.getImgPattern());
	}
	private String linkBooru() throws Exception {
		random = new Random(System.currentTimeMillis());
		try {
			int n = 0;
			if(fuente.getMaxPage()>1) {
				if(tags==null) {
					n = random.nextInt(fuente.getMaxPage());
				}else {
					n = random.nextInt((fuente.getMaxPage()/3)*2);
					n = n - tags.length*2;
					if(n<=0) {
						n=1;
					}
				}
			}else {
				n=1;
			}
			ArrayList<String> lnks = new ArrayList<>();
			String hc = "";
			int tries = 4;
			int nn = 15;
			do {
				String teststr = fuente.getQuery(n, tags, gif,video, rand);
				//System.out.println("query:"+teststr);
				hc = ClienteHttp.peticionHttp(teststr);//SUSTITUIR POR LINK
				//System.out.println("ptrn:"+fuente.getSelectionPattern());
				//System.out.println("name:"+fuente.getName());
				//System.out.println("HC:"+hc.substring(1000));
				lnks = Comparador.EncontrarTodos(fuente.getSelectionPattern(), hc);
				nn--;
				if(nn<=0) {
					nn=2;
				}
				tries--;
				n = random.nextInt(nn);
				if(tags!=null) {
					if(tries==2 && tags.length>=2) {
						tags[tags.length-1] = "";
					}else if(tries==1 && tags.length>=2) {
						tags[tags.length-2] = "";
					}
				}
				Thread.sleep(700);
			}while(lnks.size()<=0 && tries>=0);
			if(lnks.size()<=0) {
				return null;
			}
			int sel = random.nextInt(lnks.size());
			if(sel>=lnks.size()) {
				sel=lnks.size()-1;
			}else if(sel<0) {
				sel=1;
			}
			String qry = lnks.get(sel).replaceAll("href=\"", "");
			if(!(qry.startsWith("http://") || qry.startsWith("https://"))) {
				if(qry.startsWith("/")) {
					qry = fuente.getUrl() + qry;
				}else {
					qry = fuente.getUrl() + "/" + qry;
				}
			}
			hc = ClienteHttp.peticionHttp(qry);
			//System.out.println("qry: "+qry);
			//System.out.println("ptrnimg:"+fuente.getImgPattern());
			//System.out.println("HC:"+hc.substring(2000));
			String rtn = Comparador.Encontrar(fuente.getImgPattern(), hc);
			//System.out.println(rtn);
			return rtn.replaceAll("src=\"", "").replaceAll("\"", "");
		}catch(Exception e) {
			//System.out.println("Error: " + e.getLocalizedMessage());
			e.printStackTrace();
			//System.out.println("Fin error*******************");
			return null;
		}
	}
	/*protected String linkCustom(String url) throws Exception {//INCOMPLETO
		String hc = ClienteHttp.peticionHttp(url);
		if(hc == null) {
			throw new Exception();
		}
		JSONObject j = new JSONObject(hc);
		return j.getString("file");
	}*/
	
	public void enviarRestriccion(Bot bot) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.addField(Values.value("jax-no-nsfw-ft", lang), Values.value("jax-no-nsfw-fd", lang), true);
		eb.setFooter(Values.value("jax-no-nsfw-ftr", lang),bot.getSelfUser().getAvatarUrl());
		eb.setThumbnail(Constantes.JaxSandwich.Imagenes.nonsfw);
		eb.setColor(Color.red);
		channel.sendMessageEmbeds(eb.build()).queue();
	}
	
	/* CONSTRUCTORES */
	
	public ControladorImagenes(MessageChannel channel, FuenteImagen fuente2, EmbedBuilder builder) {
		this.channel = channel;
		this.fuente = fuente2;
		this.builder = builder;
	}
	public ControladorImagenes(MessageChannel channel, FuenteImagen fuente2, EmbedBuilder builder, boolean pausado) {
		this.channel = channel;
		this.pausado = pausado;
		this.fuente = fuente2;
		this.builder = builder;
	}
	
	
	/* GETTERS SETTERS */
	public void setLang(Language lang) {
		this.lang=lang;
	}
	public MessageChannel getChannel() {
		return channel;
	}
	public void setChannel(MessageChannel channel) {
		this.channel = channel;
	}
	public boolean isPausado() {
		return pausado;
	}
	public void setPausado(boolean pausado) {
		this.pausado = pausado;
	}
	public FuenteImagen getFuente2() {
		return fuente;
	}
	public void setFuente2(FuenteImagen fuente2) {
		this.fuente = fuente2;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public boolean isGif() {
		return gif;
	}
	public void setGif(boolean gif) {
		this.gif = gif;
	}
	public boolean isVideo() {
		return video;
	}
	public void setVideo(boolean video) {
		this.video = video;
	}
	public boolean isAutodes() {
		return autodes;
	}
	public void setAutodes(boolean autodes) {
		this.autodes = autodes;
	}
	public int getAutodesTime() {
		return autodesTime;
	}
	public void setAutodesTime(int autodesTime) {
		this.autodesTime = autodesTime;
	}
	public boolean isNsfw() {
		return nsfw;
	}
	public void setNsfw(boolean nsfw) {
		this.nsfw = nsfw;
	}
	public boolean isRand() {
		return rand;
	}
	public void setRand(boolean rand) {
		this.rand = rand;
	}
	public EmbedBuilder getBuilder() {
		return builder;
	}
	public void setBuilder(EmbedBuilder builder) {
		this.builder = builder;
	}
	
	
}
