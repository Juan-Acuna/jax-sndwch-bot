package xyz.sandwichbot.main.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import xyz.sandwichbot.main.Constantes;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichbot.main.main;

public class MultiFuck implements Runnable{
	NSFWSource fuente = NSFWSource.RealBooru;
	MessageChannel channel;
	boolean pausado = false;
	String[] tags = null;
	boolean gif = true;
	boolean video =false;
	boolean autodes =false;
	int autodesTime = 15;
	boolean rand = false;
	Lock condon = new ReentrantLock();
	private static Random random;
	
	public MultiFuck(MessageChannel channel) {
		this.channel=channel;
	}
	public MultiFuck(MessageChannel channel, boolean pausado) {
		this.channel=channel;
		this.pausado=pausado;
	}
	public NSFWSource getFuente() {
		return fuente;
	}
	public void setFuente(String fuente) throws Exception {
		if(fuente.equalsIgnoreCase("realbooru")) {
			this.fuente=NSFWSource.RealBooru;
		}else if(fuente.equalsIgnoreCase("konachan")) {
			this.fuente=NSFWSource.Konachan;
		}else if(fuente.equalsIgnoreCase("3dbooru")) {
			this.fuente=NSFWSource._3DBooru;
		}else if(fuente.equalsIgnoreCase("gelbooru")) {
			this.fuente=NSFWSource.GelBooru;
		}else if(fuente.equalsIgnoreCase("danbooru")) {
			this.fuente=NSFWSource.DanBooru;
		}else if(fuente.equalsIgnoreCase("konachannet")) {
			this.fuente=NSFWSource.KonachanNet;
		}else if(fuente.equalsIgnoreCase("lbooru")) {
			this.fuente=NSFWSource.LBooru;
		}else if(fuente.equalsIgnoreCase("r34")) {
			this.fuente=NSFWSource.R34;
		}else if(fuente.equalsIgnoreCase("xbooru")) {
			this.fuente=NSFWSource.XBooru;
		}else {
			throw new Exception("fuente desconocida");
		}
	}
	public void setAutoDes(boolean b) {
		this.autodes=b;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public void setGif(boolean gif) {
		this.gif = gif;
	}
	public void setAutodesTime(int time) {
		this.autodesTime=time;
	}
	public boolean isVideo() {
		return video;
	}
	public void setVideo(boolean video) {
		this.video = video;
	}
	public boolean isRand() {
		return rand;
	}
	public void setRand(boolean rand) {
		this.rand = rand;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		enviarImagen();
	}
	public String esCOGERimagen() {
		condon.lock();
		long seed = System.currentTimeMillis();
		random = new Random(seed);
		try {
			if(pausado) {
				Thread.sleep(1300);
			}else {
				Thread.sleep(400);
			}
			int n = 0;
			if(tags==null) {
				n = random.nextInt(201);
			}else {
				n = random.nextInt(100);
				n = n - tags.length*2;
				if(n<=0) {
					n=1;
				}
			}
			//System.out.println(n);
			ArrayList<String> lnks = new ArrayList<>();
			String hc = "";
			int tries = 4;
			int nn = 15;
			do {
				if(fuente==NSFWSource.RealBooru) {
					hc = ClienteHttp.peticionHttp(Constantes.RecursoExterno.NSFW.toRB_link(n,gif,video, rand,tags));
					lnks = Comparador.EncontrarTodos(Comparador.Patrones.RB_ImageQuery, hc);
				}else {
					hc = ClienteHttp.peticionHttp(Constantes.RecursoExterno.NSFW.toBooru_link(n,fuente,tags));
					System.out.println("HC:"+hc);
					//lnks = Comparador.EncontrarTodos(Comparador.Patrones.RB_ImageQuery, hc);
				}
				nn--;
				if(nn<=0) {
					nn=1;
				}
				tries--;
				n = random.nextInt(nn);
				if(tries==2 && tags.length>=2) {
					tags[tags.length-1] = "";
				}else if(tries==1 && tags.length>=2) {
					tags[tags.length-2] = "";
				}
				//System.out.println("n: "+n+",tries: "+tries);
				Thread.sleep(700);
			}while(lnks.size()<=0 && tries>=0);
			int sel = random.nextInt(lnks.size());
			if(sel>=lnks.size()) {
				sel=lnks.size()-1;
			}else if(sel<=0) {
				sel=1;
			}
			hc = ClienteHttp.peticionHttp(lnks.get(sel));
			/*do {
				hc = ClienteHttp.peticionHttp(Secret.RecursoExterno.NSFW.REALBOORU(n,gif,tags));
				lnks = Comparador.EncontrarTodos(Comparador.Patrones.NSFWImageBase, hc);
				n = n-1;
				if(n<=0) {
					n=1;
				}
				tries--;
				System.out.println("n: "+n+",tries: "+tries);
			}while(lnks ==null || tries<=0);*/
			condon.unlock();
			if(fuente==NSFWSource.RealBooru) {
				return Comparador.Encontrar(Comparador.Patrones.RB_Image, hc);
			}else {
				return Comparador.Encontrar(Comparador.Patrones.RB_Image, hc);
			}
		}catch(Exception e) {
			condon.unlock();
			return null;
		}
	}
	
	public void enviarImagen() {
		String lnk = esCOGERimagen();
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(Color.red);
		if(lnk==null) {
			eb.setFooter("No se encontró contenido (#PichulaTriste), intentalo nuevamente",Constantes.JaxSandwich.Imagenes.nonsfw);
			SandwichBot.SendAndDestroy(channel, eb.build(), 15);
			return;
		}
		//System.out.println(lnk);
		eb.setFooter("Tranquil@ cochin@, no diré quien eres 🙊😏😏",SandwichBot.ActualBot().getJDA().getSelfUser().getAvatarUrl());
		eb.setImage(lnk);
		if(autodes) {
			//System.out.println("conautodes");
			if(lnk.endsWith(".mp4") || lnk.endsWith(".mpeg4") || lnk.endsWith(".webm") || lnk.endsWith(".avi") || lnk.endsWith(".wmv") || lnk.endsWith(".3gp")) {
				channel.sendMessage(lnk).queue((message) -> message.delete().queueAfter(autodesTime, TimeUnit.SECONDS));
				return;
			}
			channel.sendMessage(eb.build()).queue((message) -> message.delete().queueAfter(autodesTime, TimeUnit.SECONDS));
		}else {
			if(lnk.endsWith(".mp4") || lnk.endsWith(".mpeg4") || lnk.endsWith(".webm") || lnk.endsWith(".avi") || lnk.endsWith(".wmv") || lnk.endsWith(".3gp")) {
				channel.sendMessage(lnk).queue();
				return;
			}channel.sendMessage(eb.build()).queue();
		}
	}
	public void enviarRestriccion() {
		EmbedBuilder eb = new EmbedBuilder();
		eb.addField("¡Deja esa cosa horrorosa o verás!....", "Este canal no permite este tipo de contenido :smirk:", true);
		eb.setFooter("Busca un canal con la etiqueta \"NSFW\" y yo mism@ te quito la ropa 🍑🍆😈👉👌😏",SandwichBot.ActualBot().getJDA().getSelfUser().getAvatarUrl());
		eb.setThumbnail(Constantes.JaxSandwich.Imagenes.nonsfw);
		eb.setColor(Color.red);
		channel.sendMessage(eb.build()).queue();
	}
}