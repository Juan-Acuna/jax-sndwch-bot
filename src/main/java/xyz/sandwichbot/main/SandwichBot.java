package xyz.sandwichbot.main;

import java.util.concurrent.TimeUnit;

import javax.lang.model.element.Element;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import xyz.sandwichbot.main.util.Tools;
import xyz.sandwichframework.core.Bot;
import xyz.sandwichframework.core.BotGuildsManager;
import xyz.sandwichframework.core.BotRunner;
import xyz.sandwichframework.core.ExtraCmdManager;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.core.util.LanguageHandler;

public class SandwichBot extends Bot{
	
	private static SandwichBot _instancia = null;
	private String prefijo;
	private String prefijoOpcion;
	private String JaxToken;
	
	public boolean presentarse = true;
	
	private SandwichBot(String token) {
		super(token,Language.ES_MX);
		JaxToken = System.getenv().get("JAX_TOKEN");
		/*builder = JDABuilder.createDefault(token);
		builder.addEventListeners(this);
		runner = BotRunner.init(Language.ES_MX);*/
		prefijo = "s.";
		runner.setPrefix(prefijo);
		runner.setOptionsPrefix("-");
		runner.setHelp_title("Hola, soy Sandwich!");
		runner.setHelp_description("Mi nombre es Jax Sandwich, pero puedes llamarme `cuando quieras bb`:kissing_heart:.\n"
				+"Por ahora estoy en desarrollo así que no puedo hacer mucho.\nAquí estan mis comandos `(se "
				+"debe anteponer '"+runner.getPrefix()+"' antes del comando a usar, ejemplo: '"+runner.getPrefix()+"comando')`:");
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
			runner.getGuildsManager().registerGuild(e.getGuild(),Language.ES);
			return;
		}
		TextChannel c = e.getGuild().getDefaultChannel();
		c.sendMessage(Tools.stringFieldToEmb("Select language / Selecciona idioma", "[ES] Español (soporte completo)\n[EN] English (half supported)")).queue();
		String[] s = {"es","en"};
		ExtraCmdManager.getManager().registerExtraCmd("join", c, null, s, 150, 150).setAfterArrgs(c).setNoExecutedArrgs(c);
	}
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if(SandwichBot.actualBot().getJDA().getSelfUser().equals(e.getAuthor())) {
			return;
		}
		try {
			runner.listenForCommand(e);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static MessageEmbed getInfo(Language lang) {
		EmbedBuilder eb = new EmbedBuilder();
		String tit = null;
		String msg=null;
		String f1t=null;
		String f1d=null;
		String f2t=null;
		String footer=null;
		switch(LanguageHandler.getLanguageParent(lang)) {
		case ES:
			tit="Wena l@s cabr@s del server!";
			msg = "Me presento: me llamo :sandwich:Jax Sandwich y como podrán observar, soy un sandwich "
					+"(lleno de mayonesa igual que tú :smirk:)."
					+"\nHora de ir directo al grano: Apenas estoy en desarrollo, así que no hago mucho, pero estoy seguro que disfrutarás mi contenido:wink::smirk:."
					+"\n*¡Soy un bot que hace varias cosas!*";
			f1t = "¡Ah, lo olvidaba!";
			f1d="Para saber que verga puedo hacer, escribe '"+SandwichBot.actualBot().getPrefijo()+"ayuda'🍑";
			f2t=">>> VERSION: 0.1.4\nPara más información acerca de este bot, "
					+"visita: sitio web aún no disponible. Te me esperas.";
			footer="DISCLAIMER: No soy dueño de ninguna de las marcas ni de los recursos gráficos "
					+"provistos por este bot. Todo ese contenido le pertenece a las fuentes originales donde fueron obtenidas. "
					+"Este bot es solo para entretenimiento y no lucra con su contenido.";
			break;
		case EN:
			tit="Hello everybody!";
			msg = "My name is :sandwich:Jax Sandwich and as you can see, I'm a sandwich "
					+"(filled with mayonnaise, like you :smirk:)."
					+"\nRight to the point: I'm barely in development, so I can't do too much, but I hope you like my content:wink::smirk:."
					+"\n*¡I'm a multipurpose bot!*";
			f1t = "¡Ah, lo olvidaba[not translated yet]!";
			f1d="To know what the shit I can do, type '"+SandwichBot.actualBot().getPrefijo()+"help'🍑";
			f2t=">>> VERSION: 0.1.4";
			footer="DISCLAIMER: I'm not the owner of any trademark or graphics resources "
					+"provided by this bot. All the contents own to the original source where the bot got them. "
					+"This bot is just for entertainment and doesn't make profits with the content.";
			break;
		default:
			break;
		}
		eb.setTitle(tit);
		eb.setDescription(msg);
		eb.addField(f1t, f1d, false);
		eb.addBlankField(false);
		eb.addBlankField(false);
		eb.addField(f2t, "", false);
		eb.setFooter(footer);
		eb.setColor(Tools.stringColorCast("ddd955"));
		return eb.build();
	}
}
