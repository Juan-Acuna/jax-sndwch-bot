package xyz.sandwichbot.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import xyz.sandwichbot.commands.Comun;
import xyz.sandwichbot.commands.Musica;
import xyz.sandwichbot.core.BotRunner;
import xyz.sandwichbot.models.InputParameter;
import xyz.sandwichbot.models.InputParameter.InputParamType;

public class SandwichBot extends ListenerAdapter{
	
	private JDA jda;
	private JDABuilder builder;
	private static SandwichBot _instancia = null;
	private BotRunner runner;
	private String prefijo;
	private String prefijoOpcion;
	private SandwichBot(String token) {
		builder = JDABuilder.createDefault(token);
		builder.addEventListeners(this);
		runner = BotRunner.init("xyz.sandwichbot.commands");
		prefijo = "s.";
		runner.setPrefix(prefijo);
		runner.setOptionsPrefix("-");
		runner.setHelp_title("Hola, soy Sandwich!");
		runner.setHelp_description("Mi nombre es Jax Sandwich, pero puedes llamarme `cuando quieras bb`:kissing_heart:.\n"
				+"Por ahora estoy en desarrollo así que no puedo hacer mucho.\nAquí estan mis comandos `(se "
				+"debe anteponer '"+runner.getPrefix()+"' antes del comando a usar, ejemplo: '"+runner.getPrefix()+"comando')`:");
		runner.setAutoHelpCommand(true);
		runner.setHide_nsfw_category(true);
	}
	public static SandwichBot create(String token) {
		return _instancia = new SandwichBot(token);
	}
	public static SandwichBot ActualBot() {
		return _instancia;
	}
	public void run() throws Exception {
		jda = builder.build();
	}
	public JDABuilder getBuilder() {
		return builder;
	}
	public JDA getJDA() {
		return jda;
	}
	public String getPrefijo() {
		return prefijo;
	}
	public String getPrefijoOpcion() {
		return prefijoOpcion;
	}
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		try {
			runner.listenForCommand(e);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@Override
	public void onGuildJoin(GuildJoinEvent e) {
		TextChannel c = e.getGuild().getDefaultChannel();
		c.sendMessage(getInfo(c.isNSFW())).queue();
	}
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent e) {
		try {
			runner.listenForPrivateCommand(e);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public static void SendAndDestroy(MessageChannel c, MessageEmbed emb, int time) {
		c.sendMessage(emb).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	public static void SendAndDestroy(MessageChannel c, String msg,int time) {
		c.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	public static MessageEmbed getInfo(boolean nsfw) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Wena l@s cabr@s del server!");
		eb.setDescription("Me presento: me llamo :sandwich:Jax Sandwich y como podrán observar, soy un sandwich "
		+"(lleno de mayonesa igual que tú :smirk:)."
		+"\nHora de ir directo al grano: Apenas estoy en desarrollo, así que no hago mucho, pero estoy seguro que disfrutarás mi contenido:wink::smirk:."
		+(nsfw?"\n*Soy un bot que hace varias cosas, ¡Pero me especializo en contenido nopor!* (y se que eso te encanta:smirk:)":"\n*¡Soy un bot que hace varias cosas!*"));
		eb.addField("¡Ah, lo olvidaba!", "Para saber que verga puedo hacer, escribe '"+SandwichBot.ActualBot().getPrefijo()+"ayuda'🍑", false);
		eb.addBlankField(false);
		eb.addBlankField(false);
		eb.addField(">>> VERSION: 0.1.3\nPara más información acerca de este bot, "
				+"visita: sitio web aún no disponible. Te me esperas.", "", false);
		eb.setFooter("DISCLAIMER: No soy dueño de ninguna de las marcas ni de los recursos gráficos "
		+"provistos por este bot. Todo ese contenido le pertenece a las fuentes originales donde fueron obtenidas. "
		+"Este bot es solo para entretenimiento y no lucra con su contenido.");
		eb.setColor(Color.yellow);
		return eb.build();
	}
}
