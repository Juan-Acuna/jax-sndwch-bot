package xyz.sandwichframework.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import xyz.sandwichframework.annotations.*;
import xyz.sandwichframework.annotations.configure.*;
import xyz.sandwichframework.models.InputParameter;
import xyz.sandwichframework.models.ModelCategory;
import xyz.sandwichframework.models.ModelCommand;
import xyz.sandwichframework.models.ModelExtraCommand;
import xyz.sandwichframework.models.ModelOption;
import xyz.sandwichframework.models.discord.ModelGuild;
import xyz.sandwichframework.models.InputParameter.InputParamType;
import xyz.sandwichframework.models.Language;

public class BotRunner {
	//settings
	protected String commandsPrefix = ">";
	protected String optionsPrefix = "-";
	protected boolean autoHelpCommand = false;
	protected String help_title;
	protected String help_description;
	private String commands_package;
	protected boolean hide_nsfw_category=false;
	private boolean bot_on;
	protected Language def_lang = Language.EN;
	
	//objs
	protected List<ModelCategory> categories;
	protected List<ModelCommand> commands;
	private Set<ModelCategory> hcategories;
	//private HashSet<ModelCommand> hcommands;
	private Set<Class<?>> configs;
	protected Reflections reflections;
	protected static BotRunner _self = null;
	protected ArrayList<ModelExtraCommand> extraCmds;
	protected BotGuildsManager guildsManager;
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
	public boolean isBot_on() {
		return bot_on;
	}
	public void setBot_on(boolean bot_is_on) {
		this.bot_on = bot_is_on;
	}
	public Language getDefaultLanguage() {
		return def_lang;
	}
	public void setDefaultLanguage(Language def_lang) {
		this.def_lang = def_lang;
	}
	public BotGuildsManager getGuildsManager() {
		return guildsManager;
	}
	private BotRunner(Language def_lang) {
		this.def_lang= def_lang; 
		//commands_package=commandsPackage;
		//reflections = new Reflections(commands_package);
		commands = (List<ModelCommand>)Collections.synchronizedList(new ArrayList<ModelCommand>());
		hcategories = (Set<ModelCategory>)Collections.synchronizedSet(new HashSet<ModelCategory>());
		//hcommands = new HashSet<ModelCommand>();
		//extraCmds = new ArrayList<ModelExtraCommand>();
		this.guildsManager = BotGuildsManager.getManager();
		try {
			initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static BotRunner init(Language def_lang) {
		return _self = new BotRunner(def_lang);
	}
	public static BotRunner init() {
		return _self = new BotRunner(Language.EN);
	}
	/*private void registerExtras() {
		Class<?> extras = (Class<?>)((Set<Class<?>>) reflections.getTypesAnnotatedWith(ExtraCommandsContainer.class)).toArray()[0]; 
		for(Method m : extras.getDeclaredMethods()) {
			ExtraCommand ec = m.getDeclaredAnnotation(ExtraCommand.class);
			if(ec==null) {
				continue;
			}
			extraCmds.add(new ModelExtraCommand(ec.name(), m));
		}
	}*/
	private void initialize() throws Exception {
		Package[] pkgs = Package.getPackages();
		for(Package p : pkgs) {
			if(!(p.getName().startsWith("xyz.sandwichframework.") || p.getName().startsWith("sun.") || p.getName().startsWith("java.")
					|| p.getName().startsWith("com.google") || p.getName().startsWith("net.dv8tion.jda")
					|| p.getName().startsWith("javassist") || p.getName().startsWith("org.reflections")
					|| p.getName().startsWith("jdk.") || p.getName().startsWith("org.slf4j."))) { //PACKAGES QUE SE OCUPAN DENTRO DEL FRAMEWORK
				// AQUI VA LO QUE SE DEBE HACER CON CADA PACKAGE
				String[] str = p.getName().split("\\.");
				//System.out.println("PKG: " + p.getName() + "; str"+str.length);
				Reflections r = new Reflections(str[0] + "." + str[1]);
				// ESCANEO DE CATEGORIAS
				Set<Class<?>> cats = r.getTypesAnnotatedWith(Category.class);
				//System.out.println("PKG: " + str[0] + "." + str[1]);
				//System.out.println("CATS: " + cats.size());
				if(cats.size()>0) {
					ModelCategory cmdcategory;
					ModelCommand botcmd;
					for(Class<?> c : cats) {
						// AQUI VA LO QUE SE DEBE HACER CON CADA CLASE
						Method[] ms = c.getDeclaredMethods();
						Category catanno = c.getDeclaredAnnotation(Category.class);
						//cmdcategory = new ModelCategory(c.getSimpleName(), catanno.desc());
						cmdcategory = new ModelCategory(def_lang, (catanno.name().equals("NoID")?c.getSimpleName():catanno.name()));
						if(!catanno.desc().equals("NoDesc")) {
							cmdcategory.setDesc(def_lang, catanno.desc());
						}
						cmdcategory.setNsfw(catanno.nsfw());
						cmdcategory.setVisible(catanno.visible());
						cmdcategory.setSpecial(catanno.isSpecial());
						for(Method m : ms) {
							Command cmdanno = m.getDeclaredAnnotation(Command.class);
							if(cmdanno==null) {
								continue;
							}
							//botcmd = new ModelCommand(cmdanno.name(),cmdanno.desc(),cmdcategory,m);
							botcmd = new ModelCommand(def_lang, cmdanno.name(), cmdcategory, m);
							botcmd.setAlias(def_lang, cmdanno.alias());
							botcmd.setDesc(def_lang, cmdanno.desc());
							botcmd.setEnabled(cmdanno.enabled());
							botcmd.setVisible(cmdanno.visible());
							Parameter par = m.getDeclaredAnnotation(Parameter.class);
							if(par!=null) {
								botcmd.setParameter(par.name());
								botcmd.setParameterDesc(par.desc());
							}
							Option[] op = m.getDeclaredAnnotationsByType(Option.class);
							for(Option o : op) {
								botcmd.addOption(new ModelOption(o.name(), o.desc(), o.alias(),o.enabled(),o.visible()));
							}
							botcmd.sortOptions();
							commands.add(botcmd);
						}
						cmdcategory.sortCommands();
						hcategories.add(cmdcategory);
					}
					
				}
				configs = r.getTypesAnnotatedWith(Configuration.class);
				
				//break;
			}
		}
		categories = (List<ModelCategory>)Collections.synchronizedList(new ArrayList<>(hcategories));
		Collections.sort(categories);
		hcategories = null;
		if(configs.size()>0 && commands.size()>0) {
			for(Class<?> c : configs) {
				Language lang = c.getDeclaredAnnotation(Configuration.class).value();
				//Language lang = Enum.valueOf(Language.class, c.getDeclaredAnnotation(Configuration.class).value().toUpperCase().replaceAll("\\s","_").replaceAll("\\-","_"));
				Field[] fs = c.getDeclaredFields();
				for(Field f : fs) {
					if(f.getDeclaredAnnotation(CategoryID.class)!=null) {
						for(ModelCategory mc : categories) {
							if(mc.getId().equals(f.getDeclaredAnnotation(CategoryID.class).value())) {
								if(f.getDeclaredAnnotation(CategoryDescription.class)!=null) {
									mc.setDesc(lang, (String)f.get(null));
								}
								if(f.getDeclaredAnnotation(TranslatedName.class)!=null) {
									System.out.println("ID: "+mc.getId()+" |CAT: "+mc.getName(Language.ES)+" -> "+f.getDeclaredAnnotation(TranslatedName.class).value()+" >> "+lang.toString());
									mc.setName(lang, f.getDeclaredAnnotation(TranslatedName.class).value());
									System.out.println("ID: "+mc.getId()+" |CAT: "+mc.getName(Language.ES)+" -> "+f.getDeclaredAnnotation(TranslatedName.class).value()+" >> "+lang.toString());
								}
							}
						}
					}else if(f.getDeclaredAnnotation(CommandID.class)!=null) {
						for(ModelCommand mc : commands) {
							if(mc.getId().equals(f.getDeclaredAnnotation(CommandID.class).value())) {
								if(f.getDeclaredAnnotation(OptionID.class)!=null) {
									//mc.getOptions()
								}else if(f.getDeclaredAnnotation(ParameterID.class)!=null) {
									
								}else {
									if(f.getDeclaredAnnotation(CommandDescription.class)!=null) {
										mc.setDesc(lang, (String)f.get(null));
									}else if(f.getDeclaredAnnotation(CommandAliases.class)!=null) {
										mc.setAlias(lang, (String[])f.get(null));
									}
									if(f.getDeclaredAnnotation(TranslatedName.class)!=null) {
										mc.setName(lang, f.getDeclaredAnnotation(TranslatedName.class).value());
									}
								}
							}
						}
					}
					
				}
			}
		}
	}
	
	public void listenForCommand(MessageReceivedEvent e) throws Exception {
		String message = e.getMessage().getContentRaw();
		if(message.toLowerCase().startsWith(commandsPrefix)) {
			ModelGuild actualGuild = guildsManager.getGuild(e.getGuild().getId());
			String r = (message.split(" ")[0]).trim();
			if(autoHelpCommand) {
				for(String cs : AutoHelpCommand.HELP_OPTIONS) {//REPARAR AUTOAYUDA PARA VARIAR POR IDIOMA
					if(r.toLowerCase().equalsIgnoreCase(commandsPrefix + cs.toLowerCase())) {
						if(!bot_on) {
							e.getChannel().sendMessage("No estoy trabajando por ahora, vaya a wear a otro lado.").queue();
						}
						Thread runner;
						Method ayudacmd = AutoHelpCommand.class.getDeclaredMethod("help", MessageReceivedEvent.class, ArrayList.class);
						ArrayList<InputParameter> pars = findParametros(message);
						CommandRunner cr = new CommandRunner(ayudacmd, pars, e);
						runner = new Thread(cr);
						runner.start();
						return;
					}
				}
			}
			for(ModelCommand cmd : commands) {
				if(r.toLowerCase().equalsIgnoreCase(commandsPrefix + cmd.getName(actualGuild.getLanguage()).toLowerCase())){
					ArrayList<InputParameter> pars = findParametros(message,cmd);
					if(!cmd.isEnabled()) {
						if(!cmd.isVisible()) {
							return;
						}
						EmbedBuilder eb = new EmbedBuilder();
						eb.setTitle("Este comando no se encuentra habilitado. :pensive:");
						e.getChannel().sendMessage(eb.build()).queue();
						return;
					}
					if(!bot_on && !cmd.getCategory().isSpecial()) {
						e.getChannel().sendMessage("No estoy trabajando por ahora, vaya a wear a otro lado.").queue();
					}
					Thread runner;
					CommandRunner cr = new CommandRunner(cmd.getSource(), pars, e);
					runner = new Thread(cr);
					runner.start();
					return;
				}else {
					for(String a : cmd.getAlias(actualGuild.getLanguage())) {
						if(r.toLowerCase().equalsIgnoreCase(commandsPrefix + a.toLowerCase())) {
							ArrayList<InputParameter> pars = findParametros(message,cmd);
							if(!cmd.isEnabled()) {
								if(!cmd.isVisible()) {
									return;
								}
								EmbedBuilder eb = new EmbedBuilder();
								eb.setTitle("Este comando no se encuentra habilitado. :pensive:");
								e.getChannel().sendMessage(eb.build()).queue();
								return;
							}
							if(!bot_on && !cmd.getCategory().isSpecial()) {
								e.getChannel().sendMessage("No estoy trabajando por ahora, vaya a wear a otro lado.").queue();
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
		ModelGuild actualGuild = guildsManager.getGuild(e.getAuthor().getMutualGuilds().get(0).getId());
		String message = e.getMessage().getContentRaw();
		String r = (message.split(" ")[0]).trim();
		if(r.toLowerCase().startsWith(commandsPrefix)) {
			r = r.substring(commandsPrefix.length());
		}
		if(autoHelpCommand) {
			for(String cs : AutoHelpCommand.HELP_OPTIONS) {
				if(r.toLowerCase().equalsIgnoreCase(cs.toLowerCase())) {
					if(!bot_on) {
						e.getChannel().sendMessage("No estoy trabajando por ahora, vaya a wear a otro lado.").queue();
					}
					Thread runner;
					Method ayudacmd = AutoHelpCommand.class.getDeclaredMethod("help", MessageReceivedEvent.class, ArrayList.class);
					ArrayList<InputParameter> pars = findParametros(message);
					CommandRunner cr = new CommandRunner(ayudacmd, pars, e);
					runner = new Thread(cr);
					runner.start();
					return;
				}
			}
		}
		for(ModelCommand cmd : commands) {
			if(r.toLowerCase().equalsIgnoreCase(cmd.getName(actualGuild.getLanguage()).toLowerCase())){
				ArrayList<InputParameter> pars = findParametros(message,cmd);
				if(!cmd.isEnabled()) {
					if(!cmd.isVisible()) {
						return;
					}
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Este comando no se encuentra habilitado. :pensive:");
					e.getChannel().sendMessage(eb.build()).queue();
					return;
				}
				if(!bot_on && !cmd.getCategory().isSpecial()) {
					e.getChannel().sendMessage("No estoy trabajando por ahora, vaya a wear a otro lado.").queue();
				}
				Thread runner;
				CommandRunner cr = new CommandRunner(cmd.getSource(), pars, e);
				runner = new Thread(cr);
				runner.start();
				return;
			}else {
				for(String a : cmd.getAlias(actualGuild.getLanguage())) {
					if(r.toLowerCase().equalsIgnoreCase(a.toLowerCase())) {
						ArrayList<InputParameter> pars = findParametros(message,cmd);
						if(!cmd.isEnabled()) {
							if(!cmd.isVisible()) {
								return;
							}
							EmbedBuilder eb = new EmbedBuilder();
							eb.setTitle("Este comando no se encuentra habilitado. :pensive:");
							e.getChannel().sendMessage(eb.build()).queue();
							return;
						}
						if(!bot_on && !cmd.getCategory().isSpecial()) {
							e.getChannel().sendMessage("No estoy trabajando por ahora, vaya a wear a otro lado.").queue();
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
	private ArrayList<InputParameter> findParametros(String input){
		String[] s = input.split(" ");
		ArrayList<InputParameter> lista = new ArrayList<InputParameter>();
		InputParameter p = new InputParameter();
		for(int i=1;i<s.length;i++) {
			if(i==1) {
				p.setType(InputParamType.Custom);
				p.setKey("custom");
				p.setValue(s[i]);
			}else if((s[i]).startsWith(optionsPrefix)) {
				p=null;
				p = new InputParameter();
				if(s[i].toLowerCase().startsWith(optionsPrefix)) {
					p.setKey(s[i]);
					p.setType(InputParamType.Invalid);
					break;
				}
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