package xyz.sandwichframework.core;

import java.lang.reflect.Constructor;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import xyz.sandwichframework.core.util.Language;

public class Bot extends ListenerAdapter{
	protected JDA jda = null;
	protected JDABuilder builder;
	private static Bot _instance = null;
	protected BotRunner runner;
	
	protected Bot(String token, Language defaultLang) {
		_instance=this;
		builder = JDABuilder.createDefault(token);
		builder.addEventListeners(this);
		runner = BotRunner.init(defaultLang);
	}/*
	public static Bot createDefault(String token, Language defaultLang) {
		return _instance = new Bot(token, defaultLang);
	}
	public Bot createCustom(String token, Language defaultLang, Class<? extends Bot> custom) throws Exception {
		Constructor<? extends Bot> c = custom.getDeclaredConstructor(String.class, Language.class);
		_instance = (Bot)c.newInstance(token, defaultLang);
		return _instance;
	}*/
	public void runBot() throws Exception {
		jda = builder.build();
		runner.setJDA(jda);
	}
	public JDABuilder getBuilder() {
		return builder;
	}
	public JDA getJDA() {
		return jda;
	}
	public boolean isBotOn() {
		return runner.isBot_on();
	}
	public void setBotOn(boolean b) {
		runner.setBot_on(b);
	}
	public static Bot actualBot() {
		return _instance;
	}
}
