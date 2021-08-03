package com.jaxsandwich.discordbot.comandos.extras;

import java.util.List;

import com.jaxsandwich.discordbot.conexion.CommandManager;
import com.jaxsandwich.discordbot.main.SandwichBot;
import com.jaxsandwich.discordbot.main.util.ClienteHttp;
import com.jaxsandwich.discordbot.main.util.Comparador;
import com.jaxsandwich.discordbot.main.util.Tools;
import com.jaxsandwich.sandwichcord.annotations.*;
import com.jaxsandwich.sandwichcord.annotations.configure.ExtraCmdAfterExecution;
import com.jaxsandwich.sandwichcord.annotations.configure.ExtraCmdExecutionName;
import com.jaxsandwich.sandwichcord.annotations.configure.ExtraCmdNoExecution;
import com.jaxsandwich.sandwichcord.core.ExtraCmdManager;
import com.jaxsandwich.sandwichcord.core.Values;
import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.core.util.MessageUtils;
import com.jaxsandwich.sandwichcord.models.ExtraCmdPacket;
import com.jaxsandwich.sandwichcord.models.discord.GuildConfig;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;

@ExtraCommandContainer
public class ComandosExtra {
	@ExtraCmdExecutionName("send")
	public static void send(ExtraCmdPacket packet) {
		MessageChannel channel = packet.getChannel();
		String input = packet.getMessageContent();
		Object[] args = packet.getArgs();
		String authorId = packet.getAuthorId();
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
					packet.getExtraCmdManager().waitForExtraCmd("send", channel, authorId, ExtraCmdManager.NUMBER_WILDCARD, 40, 5,args);
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
			packet.getExtraCmdManager().waitForExtraCmd("send", channel, authorId, ExtraCmdManager.NUMBER_WILDCARD, 40, 5,"c",tids,3);
			channel.sendMessageEmbeds(eb.build()).queue();
			break;
		case "c":
			i = Integer.parseInt(input);//0 para extra command
			if(i>((String[])args[1]).length) {
				args[args.length-1] = (int)args[args.length-1] -1;
				if((int)args[args.length-1]>0) {
					channel.sendMessageEmbeds(Tools.stringToEmb(Values.value("jax-val-incorrecto", lang)+Values.formatedValue("jax-val-incorrecto-cont", lang, args[args.length-1]))).queue();
					packet.getExtraCmdManager().waitForExtraCmd("send", channel, authorId, ExtraCmdManager.NUMBER_WILDCARD, 40, 5,args);
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
			packet.getExtraCmdManager().waitForExtraCmd("send", channel, authorId, ExtraCmdManager.WILDCARD, 40 , 5,"m",m);
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
	public static void xv(ExtraCmdPacket packet) throws Exception {
		MessageChannel channel = packet.getChannel();
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
			MessageUtils.SendAndDestroy(channel, str1, d+30);
			return;
		}
		channel.sendMessage(str1).queue();
	}
	public static void join(ExtraCmdPacket packet) throws Exception {
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
		com.jaxsandwich.discordbot.main.modelos.Servidor g = new com.jaxsandwich.discordbot.main.modelos.Servidor(packet.getTextChannel().getGuild(),lang);
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
	@ExtraCmdNoExecution(name="join")
	public static void nojoin(ExtraCmdPacket packet) throws Exception {
		com.jaxsandwich.discordbot.main.modelos.Servidor g = new com.jaxsandwich.discordbot.main.modelos.Servidor(packet.getTextChannel().getGuild(),Language.ES);
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
	@ExtraCmdAfterExecution(name="join")
	public static void afterjoin(ExtraCmdPacket packet) {
		GuildConfig server = packet.getGuildConfig();
		packet.getTextChannel().sendMessageEmbeds(((SandwichBot)packet.getBot()).getInfo(server.getLanguage())).queue();
	}
}
