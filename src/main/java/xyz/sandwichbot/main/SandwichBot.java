package xyz.sandwichbot.main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichbot.configuracion.Global;
import xyz.sandwichbot.main.util.Tools;
import xyz.sandwichframework.core.Bot;
import xyz.sandwichframework.core.ExtraCmdManager;
import xyz.sandwichframework.core.Values;
import xyz.sandwichframework.core.util.Language;

public class SandwichBot extends Bot{
	
	private static SandwichBot _instancia = null;
	private String prefijo;
	private String prefijoOpcion;
	private String JaxToken;
	
	public boolean presentarse = true;
	
	private SandwichBot(String token) {
		super(token,Language.ES_MX);
		JaxToken = Global.JAX_TOKEN;
		prefijo = "s.";
		runner.setPrefix(prefijo);
		runner.setOptionsPrefix("-");
		runner.setAutoHelpCommand(true);
		runner.setHide_nsfw_category(true);
		runner.setBot_on(true);
	}
	public static SandwichBot create(String token) {
		return _instancia = new SandwichBot(token);
	}
	public static SandwichBot actualBot() {
		return _instancia;
	}
	public String getPrefijo() {
		return prefijo;
	}
	public String getPrefijoOpcion() {
		return prefijoOpcion;
	}
	public String getJAX() {
		return JaxToken;
	}
	@Override
	public void onGuildJoin(GuildJoinEvent e) {
		if(!runner.isBot_on()) {
			xyz.sandwichbot.main.modelos.Guild g = new xyz.sandwichbot.main.modelos.Guild(e.getGuild(),Language.ES);
			runner.getGuildsManager().registerGuild(g);
			return;
		}
		TextChannel c = e.getGuild().getDefaultChannel();
		c.sendMessage(Tools.stringFieldToEmb("Select language / Selecciona idioma", "[ES] Español (soporte completo)\n[EN] English (half supported)\n\nType 'es' or 'en' to continue.\nEscriba 'es' o 'en' para continuar.")).queue();
		String[] s = {"es","en"};
		ExtraCmdManager.getManager().registerExtraCmd("join", c, null, s, 150, 50).setAfterArrgs(c).setNoExecutedArrgs(c);
	}
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if(SandwichBot.actualBot().getJDA().getSelfUser().equals(e.getAuthor())) {
			return;
		}
		try {
			runner.listenForCommand(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public static MessageEmbed getInfo(Language lang) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(Values.value("jax-info-titulo", lang));
		eb.setDescription(Values.value("jax-info-desc", lang));
		eb.addField(Values.value("jax-info-f1-t", lang), Values.formatedValue("jax-info-f1-d", lang,SandwichBot.actualBot().getPrefijo()), false);
		eb.addBlankField(false);
		eb.addField(Values.value("jax-info-f2", lang), "", false);
		eb.setFooter(Values.value("jax-info-footer", lang));
		eb.setColor(Tools.stringColorCast("ddd955"));
		return eb.build();
	}
}
