package xyz.sandwichbot.main;


import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class main {
	
	//public static JDA bot;
	
	public static void main(String[] args) throws Exception {

		SandwichBot Bot = SandwichBot.create(System.getenv().get("DISCORD_TOKEN"));
		Bot.run();
		Bot.getJDA().getPresence().setActivity(Activity.playing("Trollear"));
		Bot.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
	}

}
