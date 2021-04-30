package xyz.sandwichframework.core;

import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichframework.models.*;
import xyz.sandwichframework.models.InputParameter.InputParamType;
import xyz.sandwichframework.models.discord.ModelGuild;

//import xyz.sandwichbot.annotations.*;

public class AutoHelpCommand {
	public static final String[] HELP_OPTIONS = {"ayuda","h","help","aiuda","jelp"};
	public static void help(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		ModelGuild actualGuild = BotGuildsManager.getManager().getGuild(e.getGuild().getId());
		String categoryQuery = null;
		for(InputParameter ip : parametros) {
			if(ip.getType()==InputParamType.Custom) {
				categoryQuery = ip.getValueAsString();
			}
		}
		String extra = "\nPara saber más sobre este comando, escriba %s%s %sayuda.";
		EmbedBuilder eb = new EmbedBuilder();
		BotRunner runner = BotRunner._self;
		if(categoryQuery!=null) {
			for(ModelCategory category : runner.categories) {
				if(category.getName(actualGuild.getLanguage()).equalsIgnoreCase(categoryQuery)) {
					eb.setTitle("Categoria: "+category.getName(actualGuild.getLanguage()));
					eb.setDescription(category.getDesc(actualGuild.getLanguage()));
					eb.addField("","*Comandos*",false);
					for(ModelCommand command : category.getCommands()) {
						if(!command.isVisible()) {
							continue;
						}
						String als = "";
						if(command.getAlias(actualGuild.getLanguage()).length>0) {
							for(String a : command.getAlias(actualGuild.getLanguage())) {
								als += ", " + a;
							}
							als=" _`[Alias: " + als.substring(1) + "]`_";
						}
						
						eb.addField("> " + (command.isEnabled()?"":"*(No disponible)* ") + command.getName(actualGuild.getLanguage()) + als, ">>> " + command.getDesc(actualGuild.getLanguage()) + (command.isEnabled()?String.format(extra,runner.commandsPrefix, command.getName(actualGuild.getLanguage()).toLowerCase(),runner.optionsPrefix):""),false);
					}
					break;
				}
			}
		}else {
			eb.setTitle(runner.help_title);
			eb.setDescription(runner.help_description);
			eb.addField("", "Categorias", false);
			for(ModelCategory category : runner.categories) {
				if(runner.hide_nsfw_category && category.isNsfw() && !e.getTextChannel().isNSFW()) {
					continue;
				}
				if(!category.isVisible()) {
					continue;
				}
				eb.addField(category.getName(actualGuild.getLanguage()),category.getDesc(actualGuild.getLanguage()), false);
				eb.addField("> Comandos en esta categoría","> " + category.getCommands().size(),false);
			}
		}
		e.getChannel().sendMessage(eb.build()).queue();
	}
	
	public static void sendHelp(MessageChannel channel, String command) {
		ModelGuild actualGuild = BotGuildsManager.getManager().getGuild(((TextChannel)channel).getGuild().getId());
		EmbedBuilder eb = new EmbedBuilder();
		BotRunner runner = BotRunner._self;
		for(ModelCommand cmd : runner.commands) {
			if(cmd.getName(actualGuild.getLanguage()).toLowerCase().equalsIgnoreCase(command.toLowerCase())) {
				String als = "";
				if(cmd.getAlias(actualGuild.getLanguage()).length>0) {
					for(String a : cmd.getAlias(actualGuild.getLanguage())) {
						als += ", " + a;
					}
					als=" _`[Alias: " + als.substring(1) + "]`_";
				}
				eb.setTitle(cmd.getName(actualGuild.getLanguage()) + als + " | Categoría: " + cmd.getCategory().getName(actualGuild.getLanguage()));
				eb.setDescription(cmd.getDesc(actualGuild.getLanguage()));
				eb.addField("","PARAMETROS/OPCIONES:",false);
				if(cmd.getParameter()!=null) {
					eb.addField("> Parametro: " + cmd.getParameter(),">>> " + cmd.getParameterDesc(), false);
				}
				if(cmd.getOptions().size()>0) {
					for(ModelOption option : cmd.getOptions()) {
						if(!option.isVisible()) {
							continue;
						}
						String als2 = "";
						
						if(option.getAlias().length>0) {
							for(String a : option.getAlias()) {
								als2 += ", " + runner.optionsPrefix + a;
							}
							als2=" _`[Alias: " + als2.substring(1) + "]`_";
						}
						eb.addField("> " + (option.isEnabled()?"":"*(No disponible)* ~") + runner.optionsPrefix + option.getName() + als2  + (option.isEnabled()?"":"~"), ">>> " + option.getDesc(), false);
					}
				}
				break;
			}
		}
		channel.sendMessage(eb.build()).queue();
	}
}
