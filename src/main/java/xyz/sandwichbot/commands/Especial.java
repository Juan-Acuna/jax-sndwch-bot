package xyz.sandwichbot.commands;

import java.util.ArrayList;

import org.json.JSONObject;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichbot.annotations.*;
import xyz.sandwichbot.core.AutoHelpCommand;
import xyz.sandwichbot.main.Constantes.JaxSandwich;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichbot.main.util.ClienteHttp;
import xyz.sandwichbot.main.util.Tools;
import xyz.sandwichbot.models.*;
import xyz.sandwichbot.models.InputParameter.InputParamType;

@Category(desc="Comandos especiales, ocultos y al que solo tienen acceso muy pocos usuarios. Controlan el comportamiento de Jax Sanswich.",visible=false)
public class Especial {
	@Command(name="REG",desc="registra usuarios especiales.")
	@Parameter(name="usr",desc="usr")
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void register(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		boolean autodes = false;
		int autodesTime=15;
		boolean anon=false;
		String usr = null;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "REG");
					return;
				}
			}else if(p.getType() == InputParamType.Custom){
				usr = p.getValueAsString();
			}
		}
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		if(e.getMessage().getMentionedUsers().size()<=0) {
			SandwichBot.SendAndDestroy(e.getChannel(),"Debe mencionar a un usuario para registrarlo.", autodesTime);
			return;
		}
		String hc = ClienteHttp.peticionHttp(JaxSandwich.JAX.R + "?hash=" + Tools.encriptSHA256(e.getAuthor().getId()) + "&sal=" + Tools.encriptSHA256(SandwichBot.ActualBot().getJAX()) + "&new=" + Tools.encriptSHA256(e.getMessage().getMentionedUsers().get(0).getId()));
		System.out.println(hc);
		JSONObject j = new JSONObject(hc);
		boolean res = j.getBoolean("res");
		if(!res) {
			SandwichBot.SendAndDestroy(e.getChannel(),"No se pudo registrar", autodesTime);
			return;
		}
		if(autodes) {
			if(autodesTime<=0) {
				autodesTime=5;
			}else if(autodesTime>900) {
				autodesTime=900;
			}
			SandwichBot.SendAndDestroy(e.getChannel(),"Registrado.", autodesTime);
		}else {
			e.getChannel().sendMessage("Registrado.").queue();
		}
	}
	/*
	
	@Command(name="SET",desc="Ajusta el parametro ",alias={})
	@Parameter(name="msg",desc="msg")
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void set(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		boolean autodes = false;
		int autodesTime=15;
		boolean anon=false;
		String msg = null;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "SET");
					return;
				}
			}else if(p.getType() == InputParamType.Custom){
				msg = p.getValueAsString();
			}
		}
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		System.out.println("msg:" + e.getMessage().getMentionedUsers().get(0).getId() + " | " + e.getMessage().getMentionedUsers().get(0).getName());
		if(autodes) {
			if(autodesTime<=0) {
				autodesTime=5;
			}else if(autodesTime>900) {
				autodesTime=900;
			}
			SandwichBot.SendAndDestroy(e.getChannel(),"msg: " + Tools.ToURLencoded(Tools.encriptSHA256(msg)) + " | user: " + Tools.ToURLencoded(Tools.encriptSHA256(e.getAuthor().getId())), autodesTime);
		}else {
			e.getChannel().sendMessage("msg: " +Tools.encriptSHA256(msg) + " | user: " + Tools.encriptSHA256(e.getAuthor().getId())).queue();		}
	}
	
	*/
}
