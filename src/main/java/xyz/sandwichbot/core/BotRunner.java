package xyz.sandwichbot.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.reflections.Reflections;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import xyz.sandwichbot.annotations.*;
import xyz.sandwichbot.models.ModelCommand;
import xyz.sandwichbot.models.ModelOption;
import xyz.sandwichbot.models.InputParameter;
import xyz.sandwichbot.models.InputParameter.InputParamType;
import xyz.sandwichbot.models.ModelCategory;

public class BotRunner {
	//settings
	protected String commandsPrefix = ">";
	protected String optionsPrefix = "-";
	protected boolean autoHelpCommand = false;
	protected String help_title;
	protected String help_description;
	private String commands_package;
	protected boolean hide_nsfw_category=false;
	
	//objs
	protected ArrayList<ModelCategory> categories;
	protected ArrayList<ModelCommand> commands;
	protected Reflections reflections;
	protected static BotRunner _self = null;
	public String getPrefix() {
		return commandsPrefix;
	}
	public void setPrefix(String prefix) {
		this.commandsPrefix = prefix;
	}
	public boolean isAutoHelpCommand() {
		return autoHelpCommand;
	}
	public void setAutoHelpCommand(boolean autoHelpCommand) {
		this.autoHelpCommand = autoHelpCommand;
	}
	public String getOptionsPrefix() {
		return optionsPrefix;
	}
	public void setOptionsPrefix(String optionsPrefix) {
		this.optionsPrefix = optionsPrefix;
	}
	public String getHelp_title() {
		return help_title;
	}
	public void setHelp_title(String help_title) {
		this.help_title = help_title;
	}
	public String getHelp_description() {
		return help_description;
	}
	public void setHelp_description(String help_description) {
		this.help_description = help_description;
	}
	public boolean isHide_nsfw_category() {
		return hide_nsfw_category;
	}
	public void setHide_nsfw_category(boolean hide_nsfw_category) {
		this.hide_nsfw_category = hide_nsfw_category;
	}
	private BotRunner(String commandsPackage) {
		commands_package=commandsPackage;
		reflections = new Reflections(commands_package);
		categories = new ArrayList<ModelCategory>();
		commands = new ArrayList<ModelCommand>();
		registerCategories();
		
	}
	public static BotRunner init(String commandsPackage) {
		if(_self!=null) {
			return _self = new BotRunner(commandsPackage);
		}
		return _self = new BotRunner(commandsPackage);
	}
	private void registerCategories() {
		Set<Class<?>> cats = reflections.getTypesAnnotatedWith(Category.class);
		ModelCategory cmdcategory;
		ModelCommand botcmd;
		for(Class<?> c : cats) {
			Method[] ms = c.getDeclaredMethods();
			Category catanno = c.getDeclaredAnnotation(Category.class);
			cmdcategory = new ModelCategory(c.getSimpleName(), catanno.desc());
			cmdcategory.setNsfw(catanno.nsfw());
			cmdcategory.setVisible(catanno.visible());
			for(Method m : ms) {
				Command cmdanno = m.getDeclaredAnnotation(Command.class);
				if(cmdanno==null) {
					continue;
				}
				botcmd = new ModelCommand(cmdanno.name(),cmdanno.desc(),cmdcategory,m);
				botcmd.setAlias(cmdanno.alias());
				botcmd.setEnabled(cmdanno.enabled());
				botcmd.setVisible(cmdanno.visible());
				Parameter p = m.getDeclaredAnnotation(Parameter.class);
				if(p!=null) {
					botcmd.setParameter(p.name());
					botcmd.setParameterDesc(p.desc());
				}
				Option[] op = m.getDeclaredAnnotationsByType(Option.class);
				for(Option o : op) {
					botcmd.addOption(new ModelOption(o.name(), o.desc(), o.alias(),o.enabled(),o.visible()));
				}
				botcmd.sortOptions();
				commands.add(botcmd);
			}
			cmdcategory.sortCommands();
			categories.add(cmdcategory);
		}
		Collections.sort(categories);
	}
	
	public void listenForCommand(MessageReceivedEvent e) throws Exception {
		String message = e.getMessage().getContentRaw();
		if(message.toLowerCase().startsWith(commandsPrefix)) {
			String r = (message.split(" ")[0]).trim();
			if(autoHelpCommand) {
				for(String cs : AutoHelpCommand.HELP_OPTIONS) {
					if(r.toLowerCase().equalsIgnoreCase(commandsPrefix + cs.toLowerCase())) {
						ArrayList<InputParameter> pars = new ArrayList<InputParameter>();
						Thread runner;
						Method ayudacmd = AutoHelpCommand.class.getDeclaredMethod("help", MessageReceivedEvent.class, ArrayList.class);
						CommandRunner cr = new CommandRunner(ayudacmd, pars, e);
						runner = new Thread(cr);
						runner.start();
						return;
					}
				}
			}
			for(ModelCommand cmd : commands) {
				if(r.toLowerCase().equalsIgnoreCase(commandsPrefix + cmd.getName().toLowerCase())){
					/* BUSCAR PARAMETROS*/
					ArrayList<InputParameter> pars = findParametros(message,cmd);
					/* EJECUTAR COMANDO EN THREAD*/
					/*
					 * 
					 * CONFIRMAR QUE EL COMANDO NO ESTE DESHABILITADO!!.
					 * 
					 * 
					 * */
					if(!cmd.isEnabled()) {
						if(!cmd.isVisible()) {
							return;
						}
						EmbedBuilder eb = new EmbedBuilder();
						eb.setTitle("Este comando no se encuentra habilitado. :pensive:");
						e.getChannel().sendMessage(eb.build()).queue();
						return;
					}
					Thread runner;
					CommandRunner cr = new CommandRunner(cmd.getSource(), pars, e);
					runner = new Thread(cr);
					runner.start();
					return;
				}else {
					for(String a : cmd.getAlias()) {
						if(r.toLowerCase().equalsIgnoreCase(commandsPrefix + a.toLowerCase())) {
							/* BUSCAR PARAMETROS*/
							ArrayList<InputParameter> pars = findParametros(message,cmd);
							/* EJECUTAR COMANDO EN THREAD*/
							/*
							 * 
							 * CONFIRMAR QUE EL COMANDO NO ESTE DESHABILITADO!!.
							 * 
							 * 
							 * */
							if(!cmd.isEnabled()) {
								if(!cmd.isVisible()) {
									return;
								}
								EmbedBuilder eb = new EmbedBuilder();
								eb.setTitle("Este comando no se encuentra habilitado. :pensive:");
								e.getChannel().sendMessage(eb.build()).queue();
								return;
							}
							Thread runner;
							CommandRunner cr = new CommandRunner(cmd.getSource(), pars, e);
							runner = new Thread(cr);
							runner.start();
							return;
						}
					}
				}
			}
		}
	}
	public void listenForPrivateCommand(PrivateMessageReceivedEvent e) throws Exception {
		String message = e.getMessage().getContentRaw();
		String r = (message.split(" ")[0]).trim();
		if(r.toLowerCase().startsWith(commandsPrefix)) {
			r = r.substring(commandsPrefix.length());
		}
		if(autoHelpCommand) {
			for(String cs : AutoHelpCommand.HELP_OPTIONS) {
				if(r.toLowerCase().equalsIgnoreCase(cs.toLowerCase())) {
					ArrayList<InputParameter> pars = new ArrayList<InputParameter>();
					Thread runner;
					Method ayudacmd = AutoHelpCommand.class.getDeclaredMethod("help", MessageReceivedEvent.class, ArrayList.class);
					CommandRunner cr = new CommandRunner(ayudacmd, pars, e);
					runner = new Thread(cr);
					runner.start();
					return;
				}
			}
		}
		for(ModelCommand cmd : commands) {
			if(r.toLowerCase().equalsIgnoreCase(cmd.getName().toLowerCase())){
				/* BUSCAR PARAMETROS*/
				ArrayList<InputParameter> pars = findParametros(message,cmd);
				/* EJECUTAR COMANDO EN THREAD*/
				/*
				 * 
				 * CONFIRMAR QUE EL COMANDO NO ESTE DESHABILITADO!!.
				 * 
				 * 
				 * */
				if(!cmd.isEnabled()) {
					if(!cmd.isVisible()) {
						return;
					}
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Este comando no se encuentra habilitado. :pensive:");
					e.getChannel().sendMessage(eb.build()).queue();
					return;
				}
				Thread runner;
				CommandRunner cr = new CommandRunner(cmd.getSource(), pars, e);
				runner = new Thread(cr);
				runner.start();
				return;
			}else {
				for(String a : cmd.getAlias()) {
					if(r.toLowerCase().equalsIgnoreCase(a.toLowerCase())) {
						/* BUSCAR PARAMETROS*/
						ArrayList<InputParameter> pars = findParametros(message,cmd);
						/* EJECUTAR COMANDO EN THREAD*/
						/*
						 * 
						 * CONFIRMAR QUE EL COMANDO NO ESTE DESHABILITADO!!.
						 * 
						 * 
						 * */
						if(!cmd.isEnabled()) {
							if(!cmd.isVisible()) {
								return;
							}
							EmbedBuilder eb = new EmbedBuilder();
							eb.setTitle("Este comando no se encuentra habilitado. :pensive:");
							e.getChannel().sendMessage(eb.build()).queue();
							return;
						}
						Thread runner;
						CommandRunner cr = new CommandRunner(cmd.getSource(), pars, e);
						runner = new Thread(cr);
						runner.start();
						return;
					}
				}
			}
		}
	}
	private ArrayList<InputParameter> findParametros(String input,ModelCommand command){
		String[] s = input.split(" ");
		ArrayList<InputParameter> lista = new ArrayList<InputParameter>();
		InputParameter p = new InputParameter();
		for(int i=1;i<s.length;i++) {
			if((s[i]).startsWith(optionsPrefix)) {
				p=null;
				p = new InputParameter();
				for(ModelOption mo : command.getOptions()) {
					if(s[i].toLowerCase().equalsIgnoreCase(optionsPrefix + mo.getName())) {
						p.setKey(mo.getName());
						p.setType(InputParamType.Standar);
						break;
					}else {
						for(String a : mo.getAlias()) {
							if(s[i].toLowerCase().equalsIgnoreCase(optionsPrefix+a)) {
								p.setKey(mo.getName());
								p.setType(InputParamType.Standar);
								break;
							}
						}
					}
				}
				if(p.getType() == InputParamType.Custom) {
					for(String hs : AutoHelpCommand.HELP_OPTIONS) {
						if(s[i].equalsIgnoreCase(optionsPrefix+hs)) {
							p.setKey(AutoHelpCommand.HELP_OPTIONS[0]);
							p.setType(InputParamType.Standar);
							break;
						}
					}
					if(p.getType() == InputParamType.Custom) {
						p.setType(InputParamType.Invalid);
						p.setKey(s[i]);
					}
				}
			}else if(i==1) {
				p.setType(InputParamType.Custom);
				p.setKey("custom");
				p.setValue(s[i]);
			}else if(!p.getValueAsString().equalsIgnoreCase("none")){
				p.setValue(p.getValueAsString()+" "+s[i]);
			}else {
				p.setValue(s[i]);
			}
			if(lista.size()>0) {
				if(lista.lastIndexOf(p) == -1) {
					lista.add(p);
				}
			}else {
				lista.add(p);
			}
		}
		return lista;
	}
}
