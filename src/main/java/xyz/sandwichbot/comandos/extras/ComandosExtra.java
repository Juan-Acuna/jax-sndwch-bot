package xyz.sandwichbot.comandos.extras;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichbot.main.util.ClienteHttp;
import xyz.sandwichbot.main.util.Comparador;
import xyz.sandwichbot.main.util.Tools;
import xyz.sandwichframework.annotations.*;
import xyz.sandwichframework.annotations.configure.ExtraCmdAfterExecution;
import xyz.sandwichframework.annotations.configure.ExtraCmdExecutionName;
import xyz.sandwichframework.annotations.configure.ExtraCmdNoExecution;
import xyz.sandwichframework.core.BotGuildsManager;
import xyz.sandwichframework.core.ExtraCmdManager;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.core.util.MessageUtils;
import xyz.sandwichframework.models.discord.ModelGuild;

@ExtraCommandContainer
public class ComandosExtra {
	@ExtraCmdExecutionName("send")
	public static void send(String input ,MessageChannel channel, String authorId, Object...args) {
		int i = -1;
		String id = null;
		String caso = (String)args[0];
		switch(caso) {
		case "s":
			i = Integer.parseInt(input);//0 para extra command
			if(i>((String[])args[1]).length) {
				String er = "Valor incorrecto.";
				args[args.length-1] = (int)args[args.length-1] -1;
				if((int)args[args.length-1]>0) {
					channel.sendMessage(Tools.stringToEmb(er+". intentelo otra vez (reintentos: "+args[args.length-1]+")")).queue();
					ExtraCmdManager.getManager().registerExtraCmd("send", channel, authorId, ExtraCmdManager.NUMBER_WILDCARD, 40, 5,args);
					return;
				}
				channel.sendMessage(Tools.stringToEmb(er)).queue();
				return;
			}
			id = ((String[])args[1])[--i];
			Guild g  = SandwichBot.actualBot().getJDA().getGuildById(id);
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Servidor *'"+g.getName() +"'*");
			eb.setDescription("Seleccione canal");
			int l = 0;
			List<TextChannel> tl = g.getTextChannels();
			Object[] tids = new String[tl.size()];
			for(MessageChannel t : tl) {
				tids[l] = t.getId();
				eb.addField("[" + ++l + "] " + t.getName(),"",true);
			}
			ExtraCmdManager.getManager().registerExtraCmd("send", channel, authorId, ExtraCmdManager.NUMBER_WILDCARD, 40, 5,"c",tids,3);
			channel.sendMessage(eb.build()).queue();
			break;
		case "c":
			i = Integer.parseInt(input);//0 para extra command
			if(i>((String[])args[1]).length) {
				String er = "Valor incorrecto.";
				args[args.length-1] = (int)args[args.length-1] -1;
				if((int)args[args.length-1]>0) {
					channel.sendMessage(Tools.stringToEmb(er+". intentelo otra vez (reintentos: "+args[args.length-1]+")")).queue();
					ExtraCmdManager.getManager().registerExtraCmd("send", channel, authorId, ExtraCmdManager.NUMBER_WILDCARD, 40, 5,args);
					return;
				}
				channel.sendMessage(Tools.stringToEmb(er)).queue();
				return;
			}
			id = ((String[])args[1])[--i];
			channel.sendMessage(Tools.stringToEmb("Escribe el mensage a enviar")).queue();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			MessageChannel m;
			try {
				m = SandwichBot.actualBot().getJDA().getTextChannelById(id);
				m.sendTyping().queue();
			}catch(Exception ex) {
				try {
					SandwichBot.actualBot().getJDA().openPrivateChannelById(id).queue();
					m = SandwichBot.actualBot().getJDA().getPrivateChannelById(id);
					m.sendTyping().queue();
				}catch(Exception e2) {
					System.out.println("ERROR CASTEANDO CANAL");
					e2.printStackTrace();
					m=null;
					return;
				}
			}
			ExtraCmdManager.getManager().registerExtraCmd("send", channel, authorId, ExtraCmdManager.WILDCARD, 40 , 5,"m",m);
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
	public static void xv(String input ,MessageChannel channel, String authorId, Object...args) throws Exception {
		int i = 0;//og:duration" content="736"
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
	public static void join(String input ,MessageChannel channel, String authorId, Object...args) {
		Language lang = Language.ES;
		switch(input.toLowerCase()) {
		case "es":
			lang = Language.ES;
			break;
		case "en":
			lang = Language.EN;
			break;
		}
		ModelGuild g = BotGuildsManager.getManager().registerGuild(((TextChannel)channel).getGuild(),lang);
		//BLOQUEAR COMANDOS NS
		g.setAllowedCategory("NSFW", false);
		g.setAllowedCategory("Especial", false);
		g.setAllowedCategory("Administracion", false);
		g.setAllowedCommand("Trollear", false);
		g.setAllowedCommand("Embed", false);
		g.setAllowedCommand("Funar", false);
		g.setAllowedCommand("VoteBan", false);
	}
	@ExtraCmdNoExecution(name="join")
	public static void nojoin(MessageChannel channel,Object...args) {
		ModelGuild g = BotGuildsManager.getManager().registerGuild(((TextChannel)channel).getGuild(),Language.ES);
		//BLOQUEAR COMANDOS NS
		g.setAllowedCategory("NSFW", false);
		g.setAllowedCategory("Especial", false);
		g.setAllowedCategory("Administracion", false);
		g.setAllowedCommand("Trollear", false);
		g.setAllowedCommand("Embed", false);
		g.setAllowedCommand("Funar", false);
		g.setAllowedCommand("VoteBan", false);
	}
	@ExtraCmdAfterExecution(name="join")
	public static void afterjoin(MessageChannel channel,Object...args) {
		ModelGuild server = BotGuildsManager.getManager().getGuild(((TextChannel)channel).getGuild().getId());
		((TextChannel)channel).sendMessage(SandwichBot.getInfo(server.getLanguage())).queue();
	}
}
