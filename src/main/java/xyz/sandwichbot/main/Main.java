package xyz.sandwichbot.main;

import java.util.List;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import xyz.sandwichbot.conexion.CommandManager;
import xyz.sandwichbot.conexion.ConexionMySQL;
import xyz.sandwichbot.configuracion.Global;
import xyz.sandwichbot.main.modelos.Guild;
import xyz.sandwichframework.core.BotGuildsManager;

public class Main {
	
	public static void main(String[] args) throws Exception {

		SandwichBot Bot = SandwichBot.create(Global.DISCORD_TOKEN);
		Bot.runBot();
		Bot.getJDA().getPresence().setActivity(Activity.playing("Trollear"));
		Bot.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
		ConexionMySQL.conectar();
		CommandManager.setConexion(ConexionMySQL.getConexion());
		List<Guild> l = CommandManager.selectAll(Guild.class);
		for(Guild g : l) {
			g.pull();
		}
		BotGuildsManager.getManager().loadData(l);
		
	}

}
