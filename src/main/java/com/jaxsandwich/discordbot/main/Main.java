package com.jaxsandwich.discordbot.main;

import java.util.List;

import com.jaxsandwich.discordbot.conexion.CommandManager;
import com.jaxsandwich.discordbot.conexion.ConexionMySQL;
import com.jaxsandwich.discordbot.configuracion.Global;
import com.jaxsandwich.discordbot.main.modelos.Fuente;
import com.jaxsandwich.discordbot.main.modelos.FuenteImagen;
import com.jaxsandwich.discordbot.main.modelos.Servidor;
import com.jaxsandwich.sandwichcord.core.BotRunner;

import net.dv8tion.jda.api.OnlineStatus;

public class Main {
	
	public static void main(String[] args) throws Exception {

		SandwichBot Bot = new SandwichBot(Global.DISCORD_TOKEN);
		BotRunner.singleBotModeInit(Bot);
		Bot.runBot();
		Bot.setActivity("Trollear");
		Bot.setStatus(OnlineStatus.ONLINE);
		ConexionMySQL.conectar();
		CommandManager.setConexion(ConexionMySQL.getConexion());
		List<Servidor> l = CommandManager.selectAll(Servidor.class);
		for(Servidor g : l) {
			g.pull();
		}
		Bot.getGuildsManager().loadData(l);
		FuenteImagen.load(CommandManager.selectAll(FuenteImagen.class));
		Fuente.load(CommandManager.selectAll(Fuente.class));
	}

}
