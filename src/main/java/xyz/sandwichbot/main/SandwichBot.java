package xyz.sandwichbot.main;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import xyz.snadwichbot.core.BotRunner;

public class SandwichBot extends ListenerAdapter{
	
	private JDA jda;
	private JDABuilder builder;
	private static SandwichBot _instancia = null;
	private BotRunner runner;
	private SandwichBot(String token) {
		builder = JDABuilder.createDefault(token);
		builder.addEventListeners(this);
		runner = BotRunner.init("xyz.sandwichbot.commands");
		runner.setPrefix("s.");
		runner.setOptionsPrefix("-");
		runner.setHelp_title("Hola, soy Sandwich!");
		runner.setHelp_description("Me presento: mi nombre es Bot Sandwich, pero puedes llamarme `cuando quieras bb`:kissing_heart:.\n"
				+"Por ahora estoy en desarrollo así que no puedo hacer mucho.\nAquí estan mis comandos `(se "
				+"debe anteponer 's.' antes del comando a usar, ejemplo: 's.comando')`:");
		runner.setAutoHelpCommand(true);
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
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		/*int contCat = 0;
		if(e.getMessage().getContentRaw().startsWith(CommandManager.PREFIJO)) {
			for(String[][] comandos : CommandManager.Lista) {
				for(String[] comando : comandos) {
					for(String alias : comando) {
						if(e.getMessage().getContentRaw().startsWith(CommandManager.PREFIJO + alias)) {
							EjecutarComando(e,contCat,comando[0]);
							return;
						}
					}
				}
				contCat++;
			}
		}*/
		try {
			runner.listenForCommand(e);
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
	/*
	private void EjecutarComando(MessageReceivedEvent e, int contCat, String comando) {
		try {
			ArrayList<Parametro> p = CommandManager.ObtenerParametros(e.getMessage().getContentRaw());
			Class<?> cmds = Class.forName("com.juancho.sandwich.CommandManager");
	        Class<?>[] categorias = cmds.getDeclaredClasses();
			Class<?> categoria = Class.forName("com.juancho.comandos." + categorias[contCat].getSimpleName().substring(2));
			//System.out.println("CATEGORIA: "+categorias[contCat].getSimpleName().substring(2)+", CLASE: "+categorias[contCat].getSimpleName()+", ID: "+contCat);
	        Method[] metodos = categoria.getDeclaredMethods();
	        //System.out.println(e.getMessage().getContentRaw());
	        //System.out.println("\n****************************\n");
	        /*for(int i=0;i<metodos.length;i++) {
	        	System.out.println("M: " + metodos[i].getName()+", P: "+i);
	        }/
	        for(Method m : metodos) {
	        	if(m.getName().equals(comando)) {
	        		//System.out.println("METODO: "+ m.getName() +", INVOCADO: "+comando);
	        		m.invoke(null, e, p);
	        	}
	        }
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}*/
}
