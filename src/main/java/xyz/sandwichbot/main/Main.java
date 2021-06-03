package xyz.sandwichbot.main;


import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import xyz.sandwichframework.core.BotGuildsManager;
import xyz.sandwichframework.core.util.Language;

public class Main {
	
	//public static JDA bot;
	
	public static void main(String[] args) throws Exception {

		SandwichBot Bot = SandwichBot.create(System.getenv().get("DISCORD_TOKEN"));
		Bot.runBot();
		Bot.getJDA().getPresence().setActivity(Activity.playing("Trollear"));
		Bot.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
		BotGuildsManager.getManager().registerGuild("618241899089887254", "La Bratva", Language.ES);
		BotGuildsManager.getManager().registerGuild("621488799196774413", "Wakanda Knuckles", Language.ES);
	}

}
