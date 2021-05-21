package xyz.sandwichbot.comandos.extras;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichbot.main.util.Tools;
import xyz.sandwichframework.annotations.*;
import xyz.sandwichframework.core.ExtraCmdManager;

@ExtraCommandContainer
public class ComandosExtra {
	@ExtraCommandName("send")
	public static void send(String input ,MessageChannel channel, String authorId, Object...args) {
		System.out.println("comando send");
		int i = -1;
		String id = null;
		switch(args[0].toString()) {
		case "s":
			i = Integer.parseInt(input);//0 para extra command
			if(i>((String[])args[1]).length) {
				String er = "Valor incorrecto.";
				args[args.length-1] = (int)args[args.length-1] -1;
				if((int)args[args.length-1]>0) {
					channel.sendMessage(Tools.stringToEmb(er+". ententelo otra vez (reintentos: "+args[args.length-1]+")")).queue();
					ExtraCmdManager.getManager().registerExtraCmd("send", channel, authorId, ExtraCmdManager.NUMBER_WILDCARD, 40, 5,args);
					return;
				}
				channel.sendMessage(Tools.stringToEmb(er)).queue();
				return;
			}
			id = ((String[])args[1])[--i];
			Guild g  = SandwichBot.ActualBot().getJDA().getGuildById(id);
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Servidor *'"+g.getName() +"'*");
			eb.setDescription("Seleccione canal");
			int l = 0;
			List<TextChannel> tl = g.getTextChannels();
			Object[] tids = new String[tl.size()];
			for(MessageChannel t : tl) {
				tids[i] = t.getId();
				eb.addField("[" + ++l + "] " + t.getName(),"",true);
			}
			ExtraCmdManager.getManager().registerExtraCmd("send", channel, authorId, ExtraCmdManager.NUMBER_WILDCARD, 40, 5,"c",tids,3);
			channel.sendMessage(eb.build()).queue();
			break;
		case "c":
			System.out.println("canal");
			i = Integer.parseInt(input);//0 para extra command
			if(i>((String[])args[1]).length) {
				String er = "Valor incorrecto.";
				args[args.length-1] = (int)args[args.length-1] -1;
				if((int)args[args.length-1]>0) {
					channel.sendMessage(Tools.stringToEmb(er+". ententelo otra vez (reintentos: "+args[args.length-1]+")")).queue();
					ExtraCmdManager.getManager().registerExtraCmd("send", channel, authorId, ExtraCmdManager.NUMBER_WILDCARD, 40, 5,args);
					return;
				}
				channel.sendMessage(Tools.stringToEmb(er)).queue();
				return;
			}
			System.out.println(">>>>"+args[0]);
			for(String a : ((String[])args[1])) {
				System.out.println(">>>>"+a);
			}
			System.out.println(">>>>"+args[args.length-1]);
			id = ((String[])args[1])[--i];
			channel.sendMessage(Tools.stringToEmb("Escribe el mensage a enviar")).queue();
			if(channel instanceof TextChannel) {
				SandwichBot.ActualBot().getJDA().getTextChannelById(id).sendTyping().queue();
			}else if(channel instanceof PrivateChannel) {
				SandwichBot.ActualBot().getJDA().getPrivateChannelById(id).sendTyping().queue();
			}else {
				System.out.println("error castando canal");
			}
			ExtraCmdManager.getManager().registerExtraCmd("send", channel, authorId, ExtraCmdManager.WILDCARD, 40 , 5,"m",id);
			break;
		case "m":
			TextChannel x = SandwichBot.ActualBot().getJDA().getTextChannelById(args[1].toString());
			x.sendMessage(input).queue();
			break;
		}
	}
	
}
