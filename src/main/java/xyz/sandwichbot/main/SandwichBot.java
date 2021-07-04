package xyz.sandwichbot.main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import xyz.sandwichbot.main.modelos.Guild;
import xyz.sandwichbot.main.util.Tools;
import xyz.sandwichframework.core.Bot;
import xyz.sandwichframework.core.Values;
import xyz.sandwichframework.core.util.Language;

public class SandwichBot extends Bot{
	public boolean presentarse = true;
	public SandwichBot(String token) throws Exception {
		super(token,Language.ES);
		this.setPrefix("ss.");
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
			Guild g = new Guild(e.getGuild(),Language.ES);
			this.guildsManager.registerGuild(g);
			return;
		}
		TextChannel c = e.getGuild().getDefaultChannel();
		c.sendMessageEmbeds(Tools.stringFieldToEmb("Select language / Selecciona idioma", "[ES] Español (soporte completo)\n[EN] English (half supported)\n\nType 'es' or 'en' to continue.\nEscriba 'es' o 'en' para continuar.")).queue();
		String[] s = {"es","en"};
		this.extraCmdManager.registerExtraCmd("join", c, null, s, 150, 50).setAfterArrgs(c).setNoExecutedArrgs(c);
	}
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		try {
			runCommand(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public MessageEmbed getInfo(Language lang) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(Values.value("jax-info-titulo", lang));
		eb.setDescription(Values.value("jax-info-desc", lang));
		eb.addField(Values.value("jax-info-f1-t", lang), Values.formatedValue("jax-info-f1-d", lang,this.getPrefix()), false);
		eb.addBlankField(false);
		eb.addField(Values.value("jax-info-f2", lang), "", false);
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
