package xyz.snadwichbot.core;

import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichbot.models.*;

//import xyz.sandwichbot.annotations.*;

public class AutoHelpCommand {
	public static final String[] HELP_OPTIONS = {"ayuda","h","help","info","informacion","inf","aiuda","jelp"};
	public static void help(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		String extra = "\nPara saber mas sobre este comando, escriba %s%s %sayuda.";
		EmbedBuilder eb = new EmbedBuilder();
		BotRunner runner = BotRunner._self;
		eb.setTitle(runner.help_title);
		eb.setDescription(runner.help_description);
		eb.addField("", "Categorias", false);
		for(ModelCategory category : runner.categories) {
			eb.addField(category.getName(),category.getDesc(), false);
			for(ModelCommand command : category.getCommands()) {
				String als = "";
				if(command.getAlias().length>0) {
					for(String a : command.getAlias()) {
						als += ", " + a;
					}
					als=" _`[Alias: " + als.substring(1) + "]`_";
				}
				
				eb.addField("> " + (command.isEnabled()?"":"*(No disponible)* ") + command.getName() + als, ">>> " + command.getDesc() + (command.isEnabled()?String.format(extra,runner.commandsPrefix, command.getName().toLowerCase(),runner.optionsPrefix):""),false);
			}
		}
		e.getChannel().sendMessage(eb.build()).queue();
	}
	
	public static void sendHelp(MessageChannel channel, String command) {
		EmbedBuilder eb = new EmbedBuilder();
		BotRunner runner = BotRunner._self;
		for(ModelCommand cmd : runner.commands) {
			if(cmd.getName().toLowerCase().equals(command.toLowerCase())) {
				String als = "";
				if(cmd.getAlias().length>0) {
					for(String a : cmd.getAlias()) {
						als += ", " + a;
					}
					als=" _`[Alias: " + als.substring(1) + "]`_";
				}
				eb.setTitle(cmd.getName() + als + " | Categoría: " + cmd.getCategory().getName());
				eb.setDescription(cmd.getDesc());
				eb.addField("","PARAMETROS/OPCIONES:",false);
				if(cmd.getParameter()!=null) {
					eb.addField("> Parametro: " + cmd.getParameter(),">>> " + cmd.getParameterDesc(), false);
				}
				if(cmd.getOptions().size()>0) {
					for(ModelOption option : cmd.getOptions()) {
						String als2 = "";
						
						if(option.getAlias().length>0) {
							for(String a : option.getAlias()) {
								als2 += ", " + runner.optionsPrefix + a;
							}
							als2=" _`[Alias: " + als2.substring(1) + "]`_";
						}
						eb.addField("> " + (option.isEnabled()?"":"*(No disponible)* ") + runner.optionsPrefix + option.getName() + als2, ">>> " + option.getDesc(), false);
					}
				}
				break;
			}
		}
		channel.sendMessage(eb.build()).queue();
	}
}
