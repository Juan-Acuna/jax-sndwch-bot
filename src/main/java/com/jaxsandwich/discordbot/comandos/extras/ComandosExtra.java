package com.jaxsandwich.discordbot.comandos.extras;

import java.util.List;

import com.jaxsandwich.discordbot.conexion.CommandManager;
import com.jaxsandwich.discordbot.main.SandwichBot;
import com.jaxsandwich.discordbot.main.modelos.Servidor;
import com.jaxsandwich.discordbot.main.util.ClienteHttp;
import com.jaxsandwich.discordbot.main.util.Comparador;
import com.jaxsandwich.discordbot.main.util.Tools;
import com.jaxsandwich.sandwichcord.annotations.*;
import com.jaxsandwich.sandwichcord.core.ResponseCommandManager;
import com.jaxsandwich.sandwichcord.core.Values;
import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.models.discord.GuildConfig;
import com.jaxsandwich.sandwichcord.models.packets.ResponseCommandPacket;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;

@ResponseCommandContainer
public class ComandosExtra {
	@ResponseCommand("send")
	public static void send(ResponseCommandPacket packet) throws Exception {
		MessageChannel channel = packet.getChannel();
		String input = packet.getMessageContent();
		Object[] args = packet.getArgs();
		Language lang = Language.ES;
		GuildConfig server;
		if(channel.getType()==ChannelType.TEXT) {
			server = packet.getGuildConfig();
			if(server!=null)
				lang=server.getLanguage();
		}
		int i = -1;
		String id = null;
		String caso = (String)args[0];
		switch(caso) {
		case "s":
			i = Integer.parseInt(input);//0 para extra command
			if(i>((String[])args[1]).length) {
				args[args.length-1] = (int)args[args.length-1] -1;
				if((int)args[args.length-1]>0) {
					channel.sendMessageEmbeds(Tools.stringToEmb(Values.value("jax-val-incorrecto", lang)+Values.formatedValue("jax-val-incorrecto-cont", lang, args[args.length-1]))).queue();
					packet.waitForResponse("send", ResponseCommandManager.NUMBER_WILDCARD, 40, 5,args);
					return;
				}
				channel.sendMessageEmbeds(Tools.stringToEmb(Values.value("jax-val-incorrecto", lang))).queue();
				return;
			}
			id = ((String[])args[1])[--i];
			Guild g  = packet.getBot().getJDA().getGuildById(id);
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle(Values.formatedValue("jax-texto-servidor", lang, g.getName()));
			eb.setDescription(Values.value("jax-seleccione-canal", lang));
			int l = 0;
			List<TextChannel> tl = g.getTextChannels();
			Object[] tids = new String[tl.size()];
			for(MessageChannel t : tl) {
				tids[l] = t.getId();
				eb.addField("[" + ++l + "] " + t.getName(),"",true);
			}
			packet.waitForResponse("send", ResponseCommandManager.NUMBER_WILDCARD, 40, 5,"c",tids,3);
			channel.sendMessageEmbeds(eb.build()).queue();
			break;
		case "c":
			i = Integer.parseInt(input);//0 para extra command
			if(i>((String[])args[1]).length) {
				args[args.length-1] = (int)args[args.length-1] -1;
				if((int)args[args.length-1]>0) {
					channel.sendMessageEmbeds(Tools.stringToEmb(Values.value("jax-val-incorrecto", lang)+Values.formatedValue("jax-val-incorrecto-cont", lang, args[args.length-1]))).queue();
					packet.waitForResponse("send", ResponseCommandManager.NUMBER_WILDCARD, 40, 5,args);
					return;
				}
				channel.sendMessageEmbeds(Tools.stringToEmb(Values.value("jax-val-incorrecto", lang))).queue();
				return;
			}
			id = ((String[])args[1])[--i];
			channel.sendMessageEmbeds(Tools.stringToEmb(Values.value("jax-send-pedir-msg", lang))).queue();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			MessageChannel m;
			try {
				m = packet.getBot().getJDA().getTextChannelById(id);
				m.sendTyping().queue();
			}catch(Exception ex) {
				try {
					packet.getBot().getJDA().openPrivateChannelById(id).queue();
					m = packet.getBot().getJDA().getPrivateChannelById(id);
					m.sendTyping().queue();
				}catch(Exception e2) {
					System.out.println("ERROR CASTEANDO CANAL");
					e2.printStackTrace();
					m=null;
					return;
				}
			}
			packet.waitForResponse("send", ResponseCommandManager.WILDCARD, 40 , 5,"m",m);
			break;
		case "m":
			try {
				TextChannel x = (TextChannel)args[1];
				x.sendMessage(input).queue();
			}catch(Exception e) {
				try {
					PrivateChannel x = (PrivateChannel)args[1];
					x.sendMessage(input).queue();
				}catch(Exception e2) {
					e.printStackTrace();
				}
			}
			break;
		}
	}
	@ResponseCommand("xv")
	public static void xv(ResponseCommandPacket packet) throws Exception {
		String input = packet.getMessageContent();
		Object[] args = packet.getArgs();
		int i = 0;
		i = Integer.parseInt(input);
		String hc = ClienteHttp.peticionHttp((String)args[--i]);
		if(hc == null) {
			System.out.println("ERROR");
			return;
		}
		String sd = Comparador.Encontrar("og:duration\" content=\"[0-9]{1,5}", hc);
		int sub = hc.indexOf("html5player.setVideoUrlHigh");
		hc = hc.substring(sub,sub+700);
		sub = hc.indexOf("');");
		hc = hc.substring(0,sub);
		String str1 = hc.replace("html5player.setVideoUrlHigh('","").replace("');","");
		if(sd!=null) {
			int d = Integer.parseInt(sd.replace("og:duration\" content=\"",""));
			packet.SendAndDestroy(str1, d+30);
			return;
		}
		packet.sendMessage(str1).queue();
	}
	@ResponseCommand("join")
	public static void join(ResponseCommandPacket packet) throws Exception {
		String input = packet.getMessageContent();
		Language lang = Language.ES;
		switch(input.toLowerCase()) {
		case "es":
			lang = Language.ES;
			break;
		case "en":
			lang = Language.EN;
			break;
		}
		Servidor g = new Servidor(packet.getGuild(),lang,packet.getBot());
		g.setAllowedCategory("NSFW", false);
		g.setAllowedCommand("Banear", false);
		g.setAllowedCommand("LimpiarChat", false);
		g.setAllowedCommand("Trollear", false);
		g.setAllowedCommand("Embed", false);
		g.setAllowedCommand("Funar", false);
		g.setAllowedCommand("VoteBan", false);
		g.push();
		CommandManager.insert(g, false);
		packet.getGuildsManager().registerGuild(g);
	}
	@ResponseFailedExecution(name = "join")
	public static void nojoin(ResponseCommandPacket packet) throws Exception {
		Servidor g = new Servidor(packet.getGuild(),Language.ES,packet.getBot());
		g.setAllowedCategory("NSFW", false);
		g.setAllowedCommand("Banear", false);
		g.setAllowedCommand("LimpiarChat", false);
		g.setAllowedCommand("Trollear", false);
		g.setAllowedCommand("Embed", false);
		g.setAllowedCommand("Funar", false);
		g.setAllowedCommand("VoteBan", false);
		g.push();
		CommandManager.insert(g, false);
		packet.getGuildsManager().registerGuild(g);
	}
	@ResponseSuccessExecution(name="join")
	public static void afterjoin(ResponseCommandPacket packet) {
		packet.sendMessage(((SandwichBot)packet.getBot()).getInfo(packet.getPreferredLang())).queue();
	}
}
