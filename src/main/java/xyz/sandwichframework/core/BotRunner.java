package xyz.sandwichframework.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichframework.annotations.*;
import xyz.sandwichframework.annotations.configure.*;
import xyz.sandwichframework.annotations.text.ValueID;
import xyz.sandwichframework.annotations.text.ValuesContainer;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.models.ModelCategory;
import xyz.sandwichframework.models.ModelCommand;
import xyz.sandwichframework.models.ModelExtraCommand;
import xyz.sandwichframework.models.ModelOption;
import xyz.sandwichframework.models.discord.ModelGuild;
/**
 * Clase intermediaria entre el bot(s) y el resto de elementos.
 * Intermediate class between the bot(s) and the others elements.
 * @author Juancho
 * @version 1.3
 */
public final class BotRunner {
	private Map<String, Bot> bots = Collections.synchronizedMap(new HashMap<String, Bot>());
	private Map<String, Map<Language, String>> tempVals;
	private Set<Class<?>> configs;
	private Language defaultLanguage = Language.EN;
	private boolean started = false;
	private boolean multibotmode = true;
	private static BotRunner instance = new BotRunner();
	private BotRunner() {
		configs = Collections.synchronizedSet(new HashSet<Class<?>>());
		tempVals = new HashMap<String, Map<Language, String>>();
	}
	public static BotRunner getInstance() {
		return BotRunner.instance;
	}
	public static void registerBot(Bot bot) throws Exception {
		if(!instance.started)
			throw new Exception("You can't register a bot before starting the Bot Runner. Please use "
					+ "'BotRunner.init()' or 'BotRunner.init(Language)' methods in order to register a bot.");
		bot.guildsManager = GuildsManager.startSercive(bot);
		bot.extraCmdManager = ExtraCmdManager.startService(bot);
		bot.autoHelpCommand = new AutoHelpCommand(bot);
		bot.builder.addEventListeners(bot);
		bot.setRegistered();
		instance.bots.put(bot.tokenHash, bot);
	}
	public static void singleBotModeInit(Bot bot) throws Exception {
		if(instance.started)
			throw new Exception("The Bot Runner is already initialized"+(instance.multibotmode?" in multi-bot mode!":"!"));
		instance.multibotmode=false;
		instance.started=true;
		instance.defaultLanguage=bot.getDefaultLanguage();
		bot.guildsManager = GuildsManager.startSercive(bot);
		bot.extraCmdManager = ExtraCmdManager.startService(bot);
		bot.autoHelpCommand = new AutoHelpCommand(bot);
		bot.builder.addEventListeners(bot);
		bot.setRegistered();
		instance.bots = null;
		instance.initialize();
	}
	public static void multiBotModeInit(Language defaultLang, Bot...bots) throws Exception {
		if(instance.started)
			throw new Exception("The Bot Runner is already initialized"+(instance.multibotmode?"!":" in single-bot mode!"));
		if(bots==null || bots.length<=0)
			throw new Exception("You must give at least one bot to register!");
		instance.multibotmode=true;
		instance.started=true;
		instance.defaultLanguage=defaultLang;
		if(bots.length==1)
			System.out.println("You only registered one bot. You can register more with BotRunner.registerBot(Bot) method. "
					+ "If you will only use one bot, you should use the BotRunner.singleBotModeInit(Bot) method.");
		for(Bot bot : bots) {
			BotRunner.registerBot(bot);
		}
		instance.initialize();
	}
	public static final Bot findBot(String tokenHash) throws Exception {
		if(instance.isMultiBotMode())
			throw new Exception("Can't use this method in single-bot mode!");
		return instance.bots.get(tokenHash);
	}
	public static final Bot getBotByToken(String token) throws Exception {
		if(instance.isMultiBotMode())
			throw new Exception("Can't use this method in single-bot mode!");
		return instance.bots.get(BotRunner.getSHA256(token));
	}
	public static final List<Bot> getBotList() throws Exception{
		if(instance.isMultiBotMode())
			throw new Exception("Can't use this method in single-bot mode!");
		return new ArrayList<Bot>(instance.bots.values());
	}
	static final String getSHA256(String texto) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder(2 * hash.length);
		    for (int i = 0; i < hash.length; i++) {
		        String hex = Integer.toHexString(0xff & hash[i]);
		        if(hex.length() == 1) {
		            hexString.append('0');
		        }
		        hexString.append(hex);
		    }
		    return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean isMultiBotMode() {
		return this.multibotmode;
	}
	private void initialize() throws Exception {
		Package[] pkgs = Package.getPackages();
		boolean u = true;
		for(Package p : pkgs) {
			String pn = p.getName();
			if(pn.startsWith("xyz.sandwichframework.")) {
				if(u) {
					pn = "xyz.sandwichframework.core.util.defaultvalues";
					u=false;
				}else {
					continue;
				}
			}
			if(!(pn.startsWith("sun.") || pn.startsWith("java.")
					|| pn.startsWith("com.google") || pn.startsWith("net.dv8tion.jda")
					|| pn.startsWith("javassist") || pn.startsWith("org.reflections")
					|| pn.startsWith("jdk.") || pn.startsWith("org.slf4j.")) || pn.equals("xyz.sandwichframework.core.util.defaultvalues")) { //PACKAGES QUE SE OCUPAN DENTRO DEL FRAMEWORK
				// AQUI VA LO QUE SE DEBE HACER CON CADA PACKAGE
				String[] str = p.getName().split("\\.");
				Reflections r = new Reflections(str[0] + "." + str[1]);
				Set<Class<?>> vals = r.getTypesAnnotatedWith(ValuesContainer.class);
				Set<Class<?>> xcs = r.getTypesAnnotatedWith(ExtraCommandContainer.class);
				Set<Class<?>> cats = r.getTypesAnnotatedWith(Category.class);
				Set<Class<?>> cfgs = r.getTypesAnnotatedWith(Configuration.class);
				if(vals.size()>0) {
					for(Class<?> c : vals) {
						Language l = c.getDeclaredAnnotation(ValuesContainer.class).value();
						Map<Language, String> m;
						Field[] fs = c.getDeclaredFields();
						for(Field f : fs) {
							ValueID id = f.getDeclaredAnnotation(ValueID.class);
							if(id!=null) {
								if(tempVals.get(id.value())!=null) {
									m = tempVals.get(id.value());
								}else {
									m = new HashMap<Language, String>();
									tempVals.put(id.value(), m);
								}
								m.put(l, (String) f.get(null));
							}
						}
					}
				}
				if(xcs.size()>0) {
					for(Class<?> c : xcs) {
						Method[] xcmds = c.getDeclaredMethods();
						if(xcmds.length>0) {
							ModelExtraCommand mxc;
							for(Method m : xcmds) {
								ExtraCmdExecutionName xn = m.getAnnotation(ExtraCmdExecutionName.class);
								ExtraCmdEachExecution xe = m.getAnnotation(ExtraCmdEachExecution.class);
								ExtraCmdAfterExecution xa = m.getAnnotation(ExtraCmdAfterExecution.class);
								ExtraCmdNoExecution xne = m.getAnnotation(ExtraCmdNoExecution.class);
								ExtraCmdFinallyExecution xf = m.getAnnotation(ExtraCmdFinallyExecution.class);
								mxc = new ModelExtraCommand();
								String n = null;
								if(xe!=null) {
									n = xe.name();
									mxc.setEach(m);
								}else if(xa!=null) {
									n = xa.name();
									mxc.setAfter(m);
								}else if(xne!=null) {
									n = xne.name();
									mxc.setNoExecuted(m);
								}else if(xf!=null) {
									n = xf.name();
									mxc.setFinally(m);
								}else if(n==null) {
									n = m.getName();
									if(xn!=null)
										n = xn.value();
									mxc.setAction(m);
								}
								mxc.setName(n);
								ModelExtraCommand.compute(mxc);
							}
						}
					}
				}
				if(cats.size()>0) {
					ModelCategory cmdcategory;
					ModelCommand botcmd;
					for(Class<?> c : cats) {
						Method[] ms = c.getDeclaredMethods();
						Category catanno = c.getDeclaredAnnotation(Category.class);
						cmdcategory = new ModelCategory(defaultLanguage, (catanno.name().equals("NoID")?c.getSimpleName():catanno.name()));
						if(!catanno.desc().equals("NoDesc")) {
							cmdcategory.setDesc(defaultLanguage, catanno.desc());
						}
						cmdcategory.setNsfw(catanno.nsfw());
						cmdcategory.setVisible(catanno.visible());
						cmdcategory.setSpecial(catanno.isSpecial());
						for(Method m : ms) {
							Command cmdanno = m.getDeclaredAnnotation(Command.class);
							if(cmdanno==null) {
								continue;
							}
							botcmd = new ModelCommand(defaultLanguage, cmdanno.name(), cmdcategory, m);
							botcmd.setAlias(defaultLanguage, cmdanno.alias());
							botcmd.setDesc(defaultLanguage, cmdanno.desc());
							botcmd.setEnabled(cmdanno.enabled());
							botcmd.setVisible(cmdanno.visible());
							Parameter par = m.getDeclaredAnnotation(Parameter.class);
							if(par!=null) {
								botcmd.setParameter(defaultLanguage, par.name());
								botcmd.setParameterDesc(defaultLanguage, par.desc());
							}
							Option[] op = m.getDeclaredAnnotationsByType(Option.class);
							for(Option o : op) {
								botcmd.addOption(new ModelOption(defaultLanguage, o.name(), o.desc(), o.alias(),o.enabled(),o.visible()));
							}
							botcmd.sortOptions();
							ModelCommand.compute(botcmd);
						}
						cmdcategory.sortCommands();
						ModelCategory.compute(cmdcategory);
					}
				}
				if(cfgs.size()>0) {
					for(Class<?> cc : cfgs) {
						configs.add(cc);
					}
				}
			}
		}
		Values.initialize(tempVals);
		if(configs.size()>0 && ModelCommand.getCommandCount()>0) {
			for(Class<?> c : configs) {
				Language lang = c.getDeclaredAnnotation(Configuration.class).value();
				Field[] fs = c.getDeclaredFields();
				for(Field f : fs) {
					if(f.getDeclaredAnnotation(CategoryID.class)!=null) {
						for(ModelCategory mc : ModelCategory.getAsList()) {
							if(mc.getId().equals(f.getDeclaredAnnotation(CategoryID.class).value())) {
								if(f.getDeclaredAnnotation(CategoryDescription.class)!=null) {
									mc.setDesc(lang, (String)f.get(null));
								}
								if(f.getDeclaredAnnotation(TranslatedName.class)!=null) {
									mc.setName(lang, f.getDeclaredAnnotation(TranslatedName.class).value());
								}
							}
						}
					}else if(f.getDeclaredAnnotation(CommandID.class)!=null) {
						for(ModelCommand mc : ModelCommand.getAsList()) {
							if(mc.getId().equals(f.getDeclaredAnnotation(CommandID.class).value())) {
								if(f.getDeclaredAnnotation(OptionID.class)!=null) {
									for(ModelOption mo : mc.getOptions()) {
										if(mo.getId().equals(f.getDeclaredAnnotation(OptionID.class).value())) {
											if(f.getDeclaredAnnotation(OptionDescription.class)!=null) {
												mo.setDesc(lang, (String)f.get(null));
											}else if(f.getDeclaredAnnotation(OptionAliases.class)!=null) {
												mo.setAlias(lang, (String[])f.get(null));
											}
											if(f.getDeclaredAnnotation(TranslatedName.class)!=null) {
												mo.setName(lang, f.getDeclaredAnnotation(TranslatedName.class).value());
											}
											break;
										}
									}
								}else if(f.getDeclaredAnnotation(CommandParameter.class)!=null) {
									if(f.getDeclaredAnnotation(ParameterDescription.class)!=null) {
										mc.setParameterDesc(lang, (String)f.get(null));
									}
									if(f.getDeclaredAnnotation(TranslatedName.class)!=null) {
										mc.setParameter(lang, f.getDeclaredAnnotation(TranslatedName.class).value());
									}
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
					}else if(f.getDeclaredAnnotation(MultiCommandIDOption.class)!=null) {
						for(String id : f.getDeclaredAnnotation(MultiCommandIDOption.class).value()) {
							for(ModelCommand mc : ModelCommand.getAsList()) {
								if(mc.getId().equals(id)) {
									if(f.getDeclaredAnnotation(OptionID.class)!=null) {
										for(ModelOption mo : mc.getOptions()) {
											if(mo.getId().equals(f.getDeclaredAnnotation(OptionID.class).value())) {
												if(f.getDeclaredAnnotation(OptionDescription.class)!=null) {
													mo.setDesc(lang, (String)f.get(null));
												}else if(f.getDeclaredAnnotation(OptionAliases.class)!=null) {
													mo.setAlias(lang, (String[])f.get(null));
												}
												if(f.getDeclaredAnnotation(TranslatedName.class)!=null) {
													mo.setName(lang, f.getDeclaredAnnotation(TranslatedName.class).value());
												}
												break;
											}
										}
									}
									break;
								}
							}
						}
					}else if(f.getDeclaredAnnotation(MultiCommandIDParameter.class)!=null) {
						for(String id : f.getDeclaredAnnotation(MultiCommandIDParameter.class).value()) {
							for(ModelCommand mc : ModelCommand.getAsList()) {
								if(mc.getId().equals(id)) {
									if(f.getDeclaredAnnotation(ParameterDescription.class)!=null) {
										mc.setParameterDesc(lang, (String)f.get(null));
									}
									if(f.getDeclaredAnnotation(TranslatedName.class)!=null) {
										mc.setParameter(lang, f.getDeclaredAnnotation(TranslatedName.class).value());
									}
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	public static void run(MessageReceivedEvent e, Bot bot) throws Exception {
		if(e.isWebhookMessage() && bot.isIgnoreWebHook())
			return;
		String prx = bot.getPrefix();
		String oprx = bot.getOptionsPrefix();
		boolean b = e.isFromGuild();
		String message = e.getMessage().getContentRaw();
		bot.getExtraCmdManager().CheckExtras(e);
		ModelGuild actualGuild = null;
		Language actualLang = bot.getDefaultLanguage();
		if(b) {
			actualGuild = bot.getGuildsManager().getGuild(e.getGuild().getIdLong());
			actualLang = actualGuild.getLanguage();
			if(actualGuild.getCustomPrefix()!=null)
				prx=actualGuild.getCustomPrefix();
			if(actualGuild.getCustomOptionsPrefix()!=null)
				oprx=actualGuild.getCustomOptionsPrefix();
		}
		if(message.toLowerCase().startsWith(prx) || !b) {
			String r = (message.split(" ")[0]).trim();
			if(bot.autoHelpEnabled) {
				if(r.toLowerCase().equalsIgnoreCase(prx + bot.autoHelpCommand.getName(actualLang)) || r.toLowerCase().equalsIgnoreCase(bot.autoHelpCommand.getName(actualLang))) {
					if(!bot.isOn()) {
						e.getChannel().sendMessage(Values.value("xyz-sndwch-def-t-boff", actualLang)).queue();
					}
					if(bot.isTypingOnCommand()) {
						try {
							e.getChannel().sendTyping().queue();
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					}
					CommandPacketBuilder builder = new CommandPacketBuilder(bot,e,actualLang,bot.autoHelpCommand,oprx);
					bot.runAutoHelpCommand(builder.build());
					return;
				}
				for(String cs : bot.autoHelpCommand.getAlias(actualLang)) {
					if(r.toLowerCase().equalsIgnoreCase(prx + cs.toLowerCase()) || r.toLowerCase().equalsIgnoreCase(cs.toLowerCase())) {
						if(!bot.isOn()) {
							e.getChannel().sendMessage(Values.value("xyz-sndwch-def-t-boff", actualLang)).queue();
						}
						if(bot.isTypingOnCommand()) {
							try {
								e.getChannel().sendTyping().queue();
							}catch(Exception ex) {
								ex.printStackTrace();
							}
						}
						CommandPacketBuilder builder = new CommandPacketBuilder(bot,e,actualLang,bot.autoHelpCommand,oprx);
						bot.runAutoHelpCommand(builder.build());
						return;
					}
				}
			}
			for(ModelCommand cmd : ModelCommand.getAsList()) {
				if(r.toLowerCase().equalsIgnoreCase(prx + cmd.getName(actualLang).toLowerCase()) || r.toLowerCase().equalsIgnoreCase(cmd.getName(actualLang).toLowerCase())){
					if(!cmd.isEnabled()) {
						if(!cmd.isVisible()) {
							return;
						}
						EmbedBuilder eb = new EmbedBuilder();
						eb.setTitle(Values.value("xyz-sndwch-def-t-ncmd", actualLang));
						e.getChannel().sendMessageEmbeds(eb.build()).queue();
						return;
					}
					if(!bot.isOn() && !cmd.getCategory().isSpecial()) {
						e.getChannel().sendMessage(Values.value("xyz-sndwch-def-t-boff", actualLang)).queue();
						return;
					}
					if(!ModelGuild.canRunThisCommand(actualGuild, cmd, e.getChannel(), e.getAuthor()))
						return;
					if(bot.isTypingOnCommand()) {
						try {
							e.getChannel().sendTyping().queue();
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					}
					CommandPacketBuilder builder = new CommandPacketBuilder(bot,e,actualLang,cmd,oprx);
					new Thread(new CommandRunner(cmd.getSource(),builder.build())).start();
					return;
				}else {
					for(String a : cmd.getAlias(actualLang)) {
						if(r.toLowerCase().equalsIgnoreCase(prx + a.toLowerCase()) || r.toLowerCase().equalsIgnoreCase(a.toLowerCase())) {
							if(!cmd.isEnabled()) {
								if(!cmd.isVisible()) {
									return;
								}
								EmbedBuilder eb = new EmbedBuilder();
								eb.setTitle(Values.value("xyz-sndwch-def-t-ncmd", actualLang));
								e.getChannel().sendMessageEmbeds(eb.build()).queue();
								return;
							}
							if(!bot.isOn() && !cmd.getCategory().isSpecial()) {
								e.getChannel().sendMessage(Values.value("xyz-sndwch-def-t-boff", actualLang)).queue();
								return;
							}
							if(!ModelGuild.canRunThisCommand(actualGuild, cmd, e.getChannel(), e.getAuthor()))
								return;
							if(bot.isTypingOnCommand()) {
								try {
									e.getChannel().sendTyping().queue();
								}catch(Exception ex) {
									ex.printStackTrace();
								}
							}
							CommandPacketBuilder builder = new CommandPacketBuilder(bot,e,actualLang,cmd,oprx);
							new Thread(new CommandRunner(cmd.getSource(),builder.build())).start();
							return;
						}
					}
				}
			}
		}
	}
}
