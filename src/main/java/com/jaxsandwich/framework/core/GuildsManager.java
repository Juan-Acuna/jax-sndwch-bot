package com.jaxsandwich.framework.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaxsandwich.framework.core.util.Language;
import com.jaxsandwich.framework.models.ModelCategory;
import com.jaxsandwich.framework.models.ModelCommand;
import com.jaxsandwich.framework.models.discord.ConfigGuild;

import net.dv8tion.jda.api.entities.Guild;
/**
 * Clase cuyo trabajo es manejar todos los servidores en los que el bot ha sido invitado.(ex BotGuildsManager)
 * Class whic job is manage all the guilds that the bot has been invited.(ex BotGuildsManager)
 * @author Juancho
 * @version 1.2
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
	 * 
	 * 
	 */
	private boolean singleGuildMode = false;
	private ConfigGuild defaultConfig = null;
	/**
	 * Constructor privado de GuildsManager.
	 * Private constructor of GuildsManager.
	 */
	private GuildsManager(Bot bot) {
		this.guilds = Collections.synchronizedMap(new HashMap<Long, ConfigGuild>());
		this.bot=bot;
		if(this.bot.isSingleGuildMode()) {
			this.singleGuildMode=true;
			this.defaultConfig = new ConfigGuild();
			if(this.bot.isHideNSFWCategory())
				this.configNSFWProtection();
		}
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
	 * @throws Exception 
	 */
	public boolean registerGuild(ConfigGuild guild) throws Exception {
		if(this.singleGuildMode)
			throw new Exception("Can't register guilds in single-guild mode!");
		if(guilds.get(guild.getId())==null) {
			guilds.put(guild.getId(),guild);
			return true;
		}
		return false;
	}
	/**
	 * Agrega un servidor al contenedor.
	 * Adds a guild to the container.
	 * @throws Exception 
	 */
	public ConfigGuild registerGuild(Guild guild, Language lang) throws Exception {
		if(this.singleGuildMode)
			throw new Exception("Can't register guilds in single-guild mode!");
		ConfigGuild g = new ConfigGuild(guild, lang);
		if(guilds.get(guild.getIdLong())==null) {
			guilds.put(guild.getIdLong(), g);
			return g;
		}else {
			return guilds.get(guild.getIdLong());
		}
	}
	/**
	 * Agrega un servidor al contenedor.<br>
	 * Adds a guild to the container.
	 * @throws Exception 
	 */
	public ConfigGuild registerGuild(long id, Language lang) throws Exception {
		if(this.singleGuildMode)
			throw new Exception("Can't register guilds in single-guild mode!");
		ConfigGuild g = new ConfigGuild(bot.getJDA().getGuildById(id), lang);
		if(guilds.get(id)==null) {
			guilds.put(id, g);
			return g;
		}else {
			return guilds.get(id);
		}
	}
	public ConfigGuild getConfig(long id) {
		if(this.singleGuildMode){
			try {
				this.defaultConfig.setReferencedGuild(bot.getJDA().getGuildById(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return this.defaultConfig;
		}
		return guilds.get(id);
	}
	public ConfigGuild getConfig(Guild g) {
		if(this.singleGuildMode){
			try {
				this.defaultConfig.setReferencedGuild(g);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return this.defaultConfig;
		}
		return guilds.get(g.getIdLong());
	}
	public void loadData(List<? extends ConfigGuild> data) throws Exception {
		for(ConfigGuild g : data) {
			registerGuild(g);
		}
	}
	public void loadData(ConfigGuild[] data) throws Exception {
		for(ConfigGuild g : data) {
			registerGuild(g);
		}
	}
	public int registeredGuildsCount() throws Exception {
		if(this.singleGuildMode)
			throw new Exception("There is no registered guilds in single-guild mode!");
		return guilds.size();
	}
	public int joinedGuildsCount() throws Exception {
		if(this.singleGuildMode)
			throw new Exception("There is no registered guilds in single-guild mode!");
		int i=0;
		for(long id : guilds.keySet()) {
			if(guilds.get(id).isJoined()) {
				i++;
			}
		}
		return i;
	}
	private void configNSFWProtection() {
		for(ModelCategory c : ModelCategory.getAsList()) {
			if(c.isNsfw()) {
				this.defaultConfig.setAllowedCategory(c.getName(defaultConfig.getLanguage()), false);
			}else {
				for(ModelCommand mc : c.getCommands()) {
					if(mc.isNsfw()) {
						this.defaultConfig.setAllowedCommand(mc.getName(defaultConfig.getLanguage()), false);
					}
				}
			}
		}
	}
}
