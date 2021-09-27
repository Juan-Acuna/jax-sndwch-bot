package com.jaxsandwich.discordbot.main;

import java.util.List;

import com.jaxsandwich.discordbot.conexion.CommandManager;
import com.jaxsandwich.discordbot.conexion.ConexionMySQL;
import com.jaxsandwich.discordbot.configuracion.Global;
import com.jaxsandwich.discordbot.main.modelos.Fuente;
import com.jaxsandwich.discordbot.main.modelos.FuenteImagen;
import com.jaxsandwich.discordbot.main.modelos.Servidor;
import com.jaxsandwich.discordbot.main.util.Tools;
import com.jaxsandwich.sandwichcord.core.Bot;
import com.jaxsandwich.sandwichcord.core.Values;
import com.jaxsandwich.sandwichcord.core.util.Language;
import com.jaxsandwich.sandwichcord.models.CommandObject;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class SandwichBot extends Bot{
	public boolean presentarse = true;
	public SandwichBot(String token) {
		super(token,Language.ES);
		try {
			this.setPrefix(Global.BOT_PREFIX);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setOptionsPrefix("-");
		this.setAutoHelpCommandEnabled(true);
		this.setHideNSFWCategory(true);
		this.setIgnoreSelfCommands(true);
		this.setTypingOnCommand(true);
		this.setIgnoreWebHook(true);
	}
	@Override
	public void onGuildJoin(GuildJoinEvent e) {
		if(!this.isOn()) {
			Servidor g = new Servidor(e.getGuild(),Language.ES,this);
			try {
				this.guildConfigManager.registerGuild(g);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return;
		}
		TextChannel c = e.getGuild().getDefaultChannel();
		c.sendMessageEmbeds(Tools.stringFieldToEmb("Select language / Selecciona idioma", "[ES] Español (soporte completo)\n[EN] English (half supported)\n\nType 'es' or 'en' to continue.\nEscriba 'es' o 'en' para continuar.")).queue();
		String[] s = {"es","en"};
		try {
			this.responseCommandManager.waitForResponse("join", e, s, 150, 50).setAfterArrgs(c).setNoExecutedArrgs(c);
		} catch (Exception e1) {
			Servidor g = new Servidor(e.getGuild(),Language.ES,this);
			try {
				this.guildConfigManager.registerGuild(g);
			} catch (Exception e2) {
				e1.printStackTrace();
			}
			return;
		}
	}
	public void onBotReady(ReadyEvent event) {
		try {
			ConexionMySQL.conectar();
			CommandManager.setConexion(ConexionMySQL.getConexion());
			List<Servidor> l = CommandManager.selectAll(Servidor.class);
			for(Servidor g : l) {
				g.pull();
			}
			this.getGuildConfigManager().loadData(l);
			FuenteImagen.load(CommandManager.selectAll(FuenteImagen.class));
			Fuente.load(CommandManager.selectAll(Fuente.class));
			for(CommandObject cmd : CommandObject.getAsList()) {
				System.out.print("\nComando: "+cmd.getName(def_lang));
				for(String alias : cmd.getAlias(def_lang)) {
					System.out.print("["+alias+"]");
				}
				System.out.print("A:"+cmd.isEnabled());
				System.out.print("V:"+cmd.isVisible());
			}
			System.out.println("\nEN");
			for(CommandObject cmd : CommandObject.getAsList()) {
				System.out.print("\nCommand: "+cmd.getName(Language.EN));
				for(String alias : cmd.getAlias(Language.EN)) {
					System.out.print("["+alias+"]");
				}
				System.out.print("A:"+cmd.isEnabled());
				System.out.print("V:"+cmd.isVisible());
			}
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public MessageEmbed getInfo(Language lang) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(Values.value("jax-info-titulo", lang));
		eb.setDescription(Values.value("jax-info-desc", lang));
		eb.addField(Values.value("jax-info-f1-t", lang), Values.formatedValue("jax-info-f1-d", lang,this.getPrefix()), false);
		eb.addBlankField(false);
		eb.addField(Values.formatedValue("jax-info-f2", lang, Global.BOT_VERSION), "", false);
		eb.setFooter(Values.value("jax-info-footer", lang));
		eb.setColor(Tools.stringColorCast("ddd955"));
		return eb.build();
	}
	public boolean conectarVoz(Language lang, Member miembro, TextChannel canal, AudioManager audioManager) {
		Member m = canal.getGuild().getMember(this.getSelfUser());
		GuildVoiceState vs = m.getVoiceState();
		VoiceChannel vchannel = miembro.getVoiceState().getChannel();
		if(vs.inVoiceChannel()) {
			if(vs.getChannel()==vchannel) {
				return true;
			}
		}
		if(!miembro.getVoiceState().inVoiceChannel()) {
			canal.sendMessage(Values.value("jax-i-audio-requerido", lang)).queue();
			return false;
		}
		audioManager.openAudioConnection(vchannel);
		return true;
	}
}
