package xyz.sandwichbot.main.modelos;

import xyz.sandwichbot.conexion.CommandManager;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.models.discord.ModelGuild;

public class Guild extends ModelGuild{

	public Guild() {
		super();
	}
	
	public Guild(net.dv8tion.jda.api.entities.Guild guild, Language lang) {
		super();
		this.id=guild.getIdLong();
		this.language=lang;
		this.lastKnownName=guild.getName();
		push();
	}

	public long id_guild;
	public String lang;
	public String name;
	public String prefix;
	public String opt_prefix;
	public String alwd_cmds = null;
	public String alwd_cats = null;
	public String alwd_chnls = null;
	public String alwd_roles = null;
	public String alwd_mbrs = null;
	public short joined;
	
	public void push() {
		this.id_guild=this.id;
		this.lang=this.language.name();
		this.name=this.lastKnownName;
		this.prefix=this.customPrefix;
		this.opt_prefix=this.customOptionsPrefix;
		
		if(allowedCommands.size()>0) {
			this.alwd_cmds = "";
			for(String s : this.allowedCommands.keySet()) {
				this.alwd_cmds += s+":"+(this.allowedCommands.get(s)?"1":"0")+";";
			}
			this.alwd_cmds = this.alwd_cmds.substring(0, this.alwd_cmds.length()-1);
		}
		if(allowedCategories.size()>0) {
			this.alwd_cats="";
			for(String s : this.allowedCategories.keySet()) {
				this.alwd_cats += s+":"+(this.allowedCategories.get(s)?"1":"0")+";";
			}
			this.alwd_cats = this.alwd_cats.substring(0, this.alwd_cats.length()-1);
		}
		if(allowedChannels.size()>0) {
			this.alwd_chnls="";
			for(String s : this.allowedChannels.keySet()) {
				this.alwd_chnls += s+":"+(this.allowedChannels.get(s)?"1":"0")+";";
			}
			this.alwd_chnls = this.alwd_chnls.substring(0, this.alwd_chnls.length()-1);
		}
		if(allowedRoles.size()>0) {
			this.alwd_roles="";
			for(String s : this.allowedRoles.keySet()) {
				this.alwd_roles += s+":"+(this.allowedRoles.get(s)?"1":"0")+";";
			}
			this.alwd_roles = this.alwd_roles.substring(0, this.alwd_roles.length()-1);
		}
		if(allowedMembers.size()>0) {
			this.alwd_mbrs="";
			for(String s : this.allowedMembers.keySet()) {
				this.alwd_mbrs += s+":"+(this.allowedMembers.get(s)?"1":"0")+";";
			}
			this.alwd_mbrs = this.alwd_mbrs.substring(0, this.alwd_mbrs.length()-1);
		}
		this.joined=(short) (this.actuallyJoined?1:0);
	}
	
	public void pull() {
		this.id=this.id_guild;
		this.language=Enum.valueOf(Language.class, this.lang);
		this.lastKnownName=this.name;
		this.customPrefix=this.prefix;
		this.customOptionsPrefix=this.opt_prefix;
		
		if(this.alwd_cmds!=null) {
			for(String s : this.alwd_cmds.split(";")) {
				this.allowedCommands.put(s.split(":")[0], s.split(":")[1].equals("1"));
			}
		}
		if(alwd_cats!=null) {
			for(String s : this.alwd_cats.split(";")) {
				this.allowedCategories.put(s.split(":")[0], s.split(":")[1].equals("1"));
			}
		}
		if(alwd_chnls!=null) {
			for(String s : this.alwd_chnls.split(";")) {
				this.allowedChannels.put(s.split(":")[0], s.split(":")[1].equals("1"));
			}
		}
		if(alwd_roles!=null) {
			for(String s : this.alwd_roles.split(";")) {
				this.allowedRoles.put(s.split(":")[0], s.split(":")[1].equals("1"));
			}
		}
		if(alwd_mbrs!=null) {
			for(String s : this.alwd_mbrs.split(";")) {
				this.allowedMembers.put(s.split(":")[0], s.split(":")[1].equals("1"));
			}
		}
		this.actuallyJoined=this.joined==1;
	}
	public void refresh() {
		net.dv8tion.jda.api.entities.Guild g = SandwichBot.actualBot().getJDA().getGuildById(id);
		if(g==null) {
			this.actuallyJoined=false;
			push();
			try {
				CommandManager.update(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		String n = g.getName();
		if(!this.lastKnownName.equals(n)) {
			this.lastKnownName=n;
			push();
			try {
				CommandManager.update(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
