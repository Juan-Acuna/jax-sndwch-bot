package xyz.sandwichframework.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.dv8tion.jda.api.entities.Guild;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.models.discord.ModelGuild;

public class BotGuildsManager {
	
	private static BotGuildsManager _instancia = new BotGuildsManager();
	private ArrayList<ModelGuild> guilds;
	
	private BotGuildsManager() {
		guilds = new ArrayList<ModelGuild>();
	}
	public static BotGuildsManager getManager() {
		if(_instancia!=null) {
			return _instancia;
		}
		return _instancia = new BotGuildsManager();
	}
	public boolean registerGuild(ModelGuild guild) {
		if(guilds.indexOf(guild)<0) {
			guilds.add(guild);
			return true;
		}
		return false;
	}
	public void registerGuild(Guild guild, Language lang) {
		ModelGuild g = new ModelGuild(guild.getId(),guild.getName(), lang);
		guilds.add(g);
	}
	public boolean registerGuild(String id, String lastKnownName, Language lang) {
		ModelGuild g = new ModelGuild(id,lastKnownName,lang);
		if(guilds.indexOf(g)<0) {
			guilds.add(g);
			return true;
		}
		return false;
	}
	public ModelGuild getGuild(String id) {
		int idx = guilds.indexOf(new ModelGuild(id));
		if(idx>=0) {
			return guilds.get(idx);
		}
		return null;
	}
	public void loadData(List<ModelGuild> data) {
		for(ModelGuild g : data) {
			registerGuild(g);
		}
	}
	public void loadData(ModelGuild[] data) {
		for(ModelGuild g : data) {
			registerGuild(g);
		}
	}
	public int registeredGuildsCount() {
		return guilds.size();
	}
	public int joinedGuildsCount() {
		int i=0;
		for(ModelGuild g : guilds) {
			if(g.isJoined()) {
				i++;
			}
		}
		return i;
	}
}
