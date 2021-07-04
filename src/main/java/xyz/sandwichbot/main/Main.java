package xyz.sandwichbot.main;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import net.dv8tion.jda.api.OnlineStatus;
import xyz.sandwichbot.conexion.CommandManager;
import xyz.sandwichbot.conexion.ConexionMySQL;
import xyz.sandwichbot.configuracion.Global;
import xyz.sandwichbot.main.modelos.Fuente;
import xyz.sandwichbot.main.modelos.FuenteImagen;
import xyz.sandwichbot.main.modelos.Guild;
import xyz.sandwichframework.core.BotRunner;
import xyz.sandwichframework.core.util.Language;

public class Main {
	
	public static void main(String[] args) throws Exception {

		SandwichBot Bot = new SandwichBot(Global.DISCORD_TOKEN);
		BotRunner.singleBotModeInit(Bot);
		Bot.runBot();
		Bot.setActivity("Trollear");
		Bot.setStatus(OnlineStatus.ONLINE);
		ConexionMySQL.conectar();
		CommandManager.setConexion(ConexionMySQL.getConexion());
		List<Guild> l = CommandManager.selectAll(Guild.class);
		for(Guild g : l) {
			g.pull();
		}
		Bot.getGuildsManager().loadData(l);
		FuenteImagen.load(CommandManager.selectAll(FuenteImagen.class));
		Fuente.load(CommandManager.selectAll(Fuente.class));
		/*Fuente[] f = new Fuente[2];
		f[0] = new Fuente("pkmn-lista","https://www.wikidex.net/wiki/Lista_de_Pok%C3%A9mon");
		f[1] = new Fuente("pkmn-pokemon","https://www.wikidex.net/wiki/");
		f[2] = new Fuente("","");
		f[3] = new Fuente("","");
		f[4] = new Fuente("","");
		f[5] = new Fuente("","");*/
	}

}
