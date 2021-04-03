package xyz.sandwichbot.commands;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichbot.annotations.*;
import xyz.sandwichbot.core.AutoHelpCommand;
import xyz.sandwichbot.main.Constantes;
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
		if(!Tools.JAX.register(e.getAuthor().getId(), e.getMessage().getMentionedUsers().get(0).getId())) {
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
	
	@Command(name="SET",desc="Ajusta el parametro indicado.")
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	@Option(name="apodo",desc="Cambia el apodo del bot en el servidor actual. Si se usa en un chat privado no tiene efecto.",alias={"nombre","n"})
	@Option(name="conexion",desc="Indica el estado de conexión del bot. Los valores son:\n0 - Desconectado\n1 - Conectado\n2 - Ausente\n3 - No molestar\n4 - Invisible",alias={"status","st","online"})
	@Option(name="imagen",desc="Establece la imagen del Bot",alias={"img","i","avatar"},enabled=false)
	@Option(name="actividad",desc="Cambia la actividad que esta realizando el bot.",alias={"ac","estado","jugando"})
	@Option(name="mutear",desc="Mutea el bot en el servidor actual. Si se usa en un chat privado no tiene efecto.",alias={"mute","m"},enabled=false)
	@Option(name="ensordecer",desc="Ensordece al bot en el servidor actual. Si se usa en un chat privado no tiene efecto.",alias={"sordo","e","ensor"},enabled=false)
	@Option(name="switch",desc="Enciende o apaga el bot dependiendo del estado actual. Esto afecta a todos los servidores.",alias={"sw"})
	@Option(name="presentarse",desc="Enciende o apaga el mensaje de llegada dependiendo del estado actual. Esto afecta a todos los servidores.",alias={"pres","inf","swi"})
	public static void set(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		if(!Tools.JAX.auth(e.getAuthor().getId())) {
			return;
		}
		boolean autodes = false;
		int autodesTime=15;
		boolean anon=false;
		
		String img = null;
		boolean muteado = false;
		boolean sordo = false;
		boolean on = SandwichBot.ActualBot().isBotOn();
		
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}else if(p.getKey().equalsIgnoreCase("imagen")) {
					if(!p.getValueAsString().equals("none")) {
						//=Tools.toValidHttpUrl(p.getValueAsString());  ASIGNAR AVATAR
					}
				}else if(p.getKey().equalsIgnoreCase("apodo")) {
					if(e.getChannelType() == ChannelType.PRIVATE) {
						continue;
					}
					e.getGuild().getMember(SandwichBot.ActualBot().getJDA().getSelfUser()).modifyNickname(p.getValueAsString());
				}else if(p.getKey().equalsIgnoreCase("avatar")) {
					
					img = p.getValueAsString();
				}else if(p.getKey().equalsIgnoreCase("conexion")) {
					switch(p.getValueAsInt()) {
					case 0:
						SandwichBot.ActualBot().getJDA().getPresence().setStatus(OnlineStatus.OFFLINE);
						break;
					case 1:
						SandwichBot.ActualBot().getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
						break;
					case 2:
						SandwichBot.ActualBot().getJDA().getPresence().setStatus(OnlineStatus.IDLE);
						break;
					case 3:
						SandwichBot.ActualBot().getJDA().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
						break;
					case 4:
						SandwichBot.ActualBot().getJDA().getPresence().setStatus(OnlineStatus.INVISIBLE);
						break;
					}
				}else if(p.getKey().equalsIgnoreCase("actividad")) {
					SandwichBot.ActualBot().getJDA().getPresence().setActivity(Activity.playing(p.getValueAsString()));
				}else if(p.getKey().equalsIgnoreCase("mutear")) {
					muteado = p.getValueAsBoolean(Constantes.VALORES.TRUE);
				}else if(p.getKey().equalsIgnoreCase("ensordecer")) {
					sordo = p.getValueAsBoolean(Constantes.VALORES.TRUE);
				}else if(p.getKey().equalsIgnoreCase("switch")) {
					SandwichBot.ActualBot().setBotOn(!on);
				}else if(p.getKey().equalsIgnoreCase("presentarse")) {
					SandwichBot.ActualBot().presentarse = SandwichBot.ActualBot().presentarse;
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "SET");
					return;
				}
			}
		}
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		if(autodes) {
			if(autodesTime<=0) {
				autodesTime=5;
			}else if(autodesTime>900) {
				autodesTime=900;
			}
			SandwichBot.SendAndDestroy(e.getChannel(),"msg", autodesTime);
		}else {
			e.getChannel().sendMessage("msg").queue();
		}
	}
	
	@Command(name="jax",visible=false)
	@Parameter(name="",desc="")
	public static void jax(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		if(Tools.JAX.auth(e.getAuthor().getId())) {
			String enc = Tools.toBase64(Tools.cifrar(parametros.get(0).getValueAsString()));
			e.getChannel().sendMessage("Mensaje original: " + parametros.get(0)).queue();
			e.getChannel().sendMessage("Mensaje encriptado: " + enc).queue();
			e.getChannel().sendMessage("Mensaje desencriptado: " + Tools.descifrar(Tools.fromBase64ToBytes(enc))).queue();
		}
		//Random r = new Random(System.currentTimeMillis());
		
	}
	@Command(name="SEND",desc="Envia un mensaje al canal del servidor especificado.",visible=false)
	@Parameter(name="Mensaje",desc="Corresponde al mensaje a enviar. Debe ir antes de cualquier opción. Para usar el caracter de opcions '-' como parte del mensaje, debe anteponer el caracter '\\'.")
	@Option(name="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	//@Option(name="servidor",desc="Id dl servidor donde se encuentra el canal en el cual se enviara el mensaje.",alias={"s","server","ser","serv"})
	@Option(name="canal",desc="Id del canal en el cual se enviara el mensaje.",alias={"c","channel","can"})
	public static void send(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		if(Tools.JAX.auth(e.getAuthor().getId())) {
			boolean autodes = false;
			int autodesTime=15;
			String msg = null;
			//String servidor = null;
			String canal = null;
			for(InputParameter p : parametros) {
				if(p.getType() == InputParamType.Standar) {
					if(p.getKey().equalsIgnoreCase("autodestruir")){
						autodes=true;
						if(!p.getValueAsString().equalsIgnoreCase("none")) {
							autodesTime = p.getValueAsInt();
						}
					}else if(p.getKey().equalsIgnoreCase("canal")) {
						canal = p.getValueAsString();
					}/*else if(p.getKey().equalsIgnoreCase("servidor")) {
						servidor = p.getValueAsString();
					}*/else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
						AutoHelpCommand.sendHelp(e.getChannel(), "MSG");
						return;
					}
				}else if(p.getType() == InputParamType.Custom){
					msg = p.getValueAsString();
				}
			}
			List<Guild> glds = e.getAuthor().getJDA().getMutualGuilds(SandwichBot.ActualBot().getJDA().getSelfUser());
			System.out.println("glds: " + glds.size());
			int i = 0;
			for(Guild g : glds) {
				e.getChannel().sendMessage("[" + ++i + "] " + g.getName()).queue();
			}
			if(canal==null) {
				return;
			}
			TextChannel c = SandwichBot.ActualBot().getJDA().getTextChannelById(canal);
			if(c == null) {
				e.getChannel().sendMessage("Ese canal no existe o yo no estoy en ese servidor.").queue();
				return;
			}
			if(autodes) {
				if(autodesTime<=0) {
					autodesTime=5;
				}else if(autodesTime>900) {
					autodesTime=900;
				}
				SandwichBot.SendAndDestroy(c,msg, autodesTime);
			}else {
				c.sendMessage(msg).queue();
			}
			e.getChannel().sendMessage("Mensaje enviado al canal "+c.getName()+"["+c.getId()+"], en el servidor "+c.getGuild().getName()+"["+c.getGuild().getId()+"].").queue();
		}
	}
}
