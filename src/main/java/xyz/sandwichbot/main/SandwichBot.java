package xyz.sandwichbot.main;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import xyz.sandwichbot.commands.Comun;
import xyz.sandwichbot.core.BotRunner;

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
		runner.setHelp_description("Me presento: mi nombre es Jax Sandwich, pero puedes llamarme `cuando quieras bb`:kissing_heart:.\n"
				+"Por ahora estoy en desarrollo así que no puedo hacer mucho.\nAquí estan mis comandos `(se "
				+"debe anteponer '"+runner.getPrefix()+"' antes del comando a usar, ejemplo: '"+runner.getPrefix()+".comando')`:");
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
		e.getGuild().getDefaultChannel().sendMessage(Comun.getInfo()).queue();
	}
	public static void SendAndDestroy(MessageChannel c, MessageEmbed emb, int time) {
		c.sendMessage(emb).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	public static void SendAndDestroy(MessageChannel c, String msg,int time) {
		c.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
}
