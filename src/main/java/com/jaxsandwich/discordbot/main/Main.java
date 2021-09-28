package com.jaxsandwich.discordbot.main;

import com.jaxsandwich.discordbot.configuracion.Global;
import com.jaxsandwich.discordbot.configuracion.Textos;
import com.jaxsandwich.sandwichcord.core.BotRunner;

import net.dv8tion.jda.api.OnlineStatus;

public class Main {
	
	public static void main(String[] args) throws Exception {

		BotRunner.addConfiguration(new Textos());
		SandwichBot Bot = new SandwichBot(Global.DISCORD_TOKEN);
		Constantes.bot=Bot;
		BotRunner.init(Bot);
		Bot.setForceGuildsReset(false);
		Bot.runBot();
		Bot.setActivity("Trollear");
		Bot.setStatus(OnlineStatus.ONLINE);
	}

}
