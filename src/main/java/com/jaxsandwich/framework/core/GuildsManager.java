package com.jaxsandwich.framework.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaxsandwich.framework.core.util.Language;
import com.jaxsandwich.framework.models.discord.ConfigGuild;

import net.dv8tion.jda.api.entities.Guild;
/**
 * Clase cuyo trabajo es manejar todos los servidores en los que el bot ha sido invitado.(ex BotGuildsManager)
 * Class whic job is manage all the guilds that the bot has been invited.(ex BotGuildsManager)
 * @author Juancho
 * @version 1.1
 */
public class GuildsManager {
	/**
	 * Bot asociado a este GuildsManager.
	 * Bot associated to this GuildsManager.
	 */
	private Bot bot;
	/**
	 * Contenedor de {@link ConfigGuild}(No confundir con {@link Guild})
	 * Container of {@link ConfigGuild}(Do not confuse with {@link Guild})
	 */
	private Map<Long, ConfigGuild> guilds;
	/**
	 * Constructor privado de GuildsManager.
	 * Private constructor of GuildsManager.
	 */
	private GuildsManager(Bot bot) {
		guilds = Collections.synchronizedMap(new HashMap<Long, ConfigGuild>());
		this.bot=bot;
	}
	/**
	 * Inicia una nueva instancia de esta clase.
	 * Starts a new instance of this class.
	 */
	public static GuildsManager startSercive(Bot bot) {
		return new GuildsManager(bot);
	}
	/**
	 * Agrega un servidor al contenedor.
	 * Adds a guild to the container.
	 */
	public boolean registerGuild(ConfigGuild guild) {
		if(guilds.get(guild.getId())==null) {
			guilds.put(guild.getId(),guild);
			return true;
		}
		return false;
	}
	/**
	 * Agrega un servidor al contenedor.
	 * Adds a guild to the container.
	 */
	public ConfigGuild registerGuild(Guild guild, Language lang) {
		ConfigGuild g = new ConfigGuild(guild.getIdLong(),guild.getName(), lang);
		if(guilds.get(guild.getIdLong())==null) {
			guilds.put(guild.getIdLong(), g);
			return g;
		}else {
			return guilds.get(guild.getIdLong());
		}
	}
	/**
	 * Agrega un servidor al contenedor.
	 * Adds a guild to the container.
	 */
	public ConfigGuild registerGuild(long id, String lastKnownName, Language lang) {
		ConfigGuild g = new ConfigGuild(id,lastKnownName,lang);
		if(guilds.get(id)==null) {
			guilds.put(id, g);
			return g;
		}else {
			return guilds.get(id);
		}
	}
	public ConfigGuild getGuild(long id) {
		return guilds.get(id);
	}
	public void loadData(List<? extends ConfigGuild> data) {
		for(ConfigGuild g : data) {
			registerGuild(g);
		}
	}
	public void loadData(ConfigGuild[] data) {
		for(ConfigGuild g : data) {
			registerGuild(g);
		}
	}
	public int registeredGuildsCount() {
		return guilds.size();
	}
	public int joinedGuildsCount() {
		int i=0;
		for(long id : guilds.keySet()) {
			if(guilds.get(id).isJoined()) {
				i++;
			}
		}
		return i;
	}
}
