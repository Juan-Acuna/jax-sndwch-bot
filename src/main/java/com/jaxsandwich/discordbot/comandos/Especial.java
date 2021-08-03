package com.jaxsandwich.discordbot.comandos;

import java.util.List;

import com.jaxsandwich.discordbot.main.Constantes;
import com.jaxsandwich.discordbot.main.SandwichBot;
import com.jaxsandwich.discordbot.main.util.Tools;
import com.jaxsandwich.sandwichcord.annotations.*;
import com.jaxsandwich.sandwichcord.core.ExtraCmdManager;
import com.jaxsandwich.sandwichcord.core.util.MessageUtils;
import com.jaxsandwich.sandwichcord.models.*;
import com.jaxsandwich.sandwichcord.models.InputParameter.InputParamType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Category(desc="Comandos especiales, ocultos y al que solo tienen acceso muy pocos usuarios. Controlan el comportamiento de Jax Sanswich.",visible=false)
public class Especial {
	@Command(id="REG",desc="registra usuarios especiales.")
	@Parameter(name="usr",desc="usr")
	@Option(id="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(id="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void register(CommandPacket packet) throws Exception {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		boolean autodes = false;
		int autodesTime=15;
		boolean anon=false;
		String usr = null;
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon=true;
				}
			}else if(p.getType() == InputParamType.Custom){
				usr = p.getValueAsString();
			}
		}
		if(anon) {
			e.getChannel().purgeMessagesById(e.getMessageId());
		}
		if(e.getMessage().getMentionedUsers().size()<=0) {
			MessageUtils.SendAndDestroy(e.getChannel(),"Debe mencionar a un usuario para registrarlo.", autodesTime);
			return;
		}
		if(!Tools.JAX.register(e.getAuthor().getId(), e.getMessage().getMentionedUsers().get(0).getId())) {
			MessageUtils.SendAndDestroy(e.getChannel(),"No se pudo registrar", autodesTime);
			return;
		}
		if(autodes) {
			if(autodesTime<=0) {
				autodesTime=5;
			}else if(autodesTime>900) {
				autodesTime=900;
			}
			MessageUtils.SendAndDestroy(e.getChannel(),"Registrado.", autodesTime);
		}else {
			e.getChannel().sendMessage("Registrado.").queue();
		}
	}
	
	@Command(id="SET",desc="Ajusta el parametro indicado.")
	@Option(id="autodestruir",desc="Elimina el contenido después de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(id="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	@Option(id="apodo",desc="Cambia el apodo del bot en el servidor actual. Si se usa en un chat privado no tiene efecto.",alias={"nombre","n"})
	@Option(id="conexion",desc="Indica el estado de conexión del bot. Los valores son:\n0 - Desconectado\n1 - Conectado\n2 - Ausente\n3 - No molestar\n4 - Invisible",alias={"status","st","online"})
	@Option(id="imagen",desc="Establece la imagen del Bot",alias={"img","i","avatar"},enabled=false)
	@Option(id="actividad",desc="Cambia la actividad que esta realizando el bot.",alias={"ac","estado","jugando"})
	@Option(id="mutear",desc="Mutea el bot en el servidor actual. Si se usa en un chat privado no tiene efecto.",alias={"mute","m"},enabled=false)
	@Option(id="ensordecer",desc="Ensordece al bot en el servidor actual. Si se usa en un chat privado no tiene efecto.",alias={"sordo","e","ensor"},enabled=false)
	@Option(id="switch",desc="Enciende o apaga el bot dependiendo del estado actual. Esto afecta a todos los servidores.",alias={"sw"})
	@Option(id="presentarse",desc="Enciende o apaga el mensaje de llegada dependiendo del estado actual. Esto afecta a todos los servidores.",alias={"pres","inf","swi"})
	public static void set(CommandPacket packet) throws Exception {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		if(!Tools.JAX.auth(e.getAuthor().getId())) {
			return;
		}
		boolean autodes = false;
		int autodesTime=15;
		boolean anon=false;
		
		String img = null;
		boolean muteado = false;
		boolean sordo = false;
		boolean on = packet.getBot().isOn();
		
		for(InputParameter p : packet.getParameters()) {
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
					e.getGuild().getMember(packet.getBot().getSelfUser()).modifyNickname(p.getValueAsString());
				}else if(p.getKey().equalsIgnoreCase("avatar")) {
					
					img = p.getValueAsString();
				}else if(p.getKey().equalsIgnoreCase("conexion")) {
					switch(p.getValueAsInt()) {
					case 0:
						packet.getBot().setStatus(OnlineStatus.OFFLINE);
						break;
					case 1:
						packet.getBot().setStatus(OnlineStatus.ONLINE);
						break;
					case 2:
						packet.getBot().setStatus(OnlineStatus.IDLE);
						break;
					case 3:
						packet.getBot().setStatus(OnlineStatus.DO_NOT_DISTURB);
						break;
					case 4:
						packet.getBot().setStatus(OnlineStatus.INVISIBLE);
						break;
					}
				}else if(p.getKey().equalsIgnoreCase("actividad")) {
					packet.getBot().setActivity(Activity.playing(p.getValueAsString()));
				}else if(p.getKey().equalsIgnoreCase("mutear")) {
					muteado = p.getValueAsBoolean(Constantes.VALORES.TRUE);
				}else if(p.getKey().equalsIgnoreCase("ensordecer")) {
					sordo = p.getValueAsBoolean(Constantes.VALORES.TRUE);
				}else if(p.getKey().equalsIgnoreCase("switch")) {
					packet.getBot().setOn(!on);
				}else if(p.getKey().equalsIgnoreCase("presentarse")) {
					((SandwichBot)packet.getBot()).presentarse= !((SandwichBot)packet.getBot()).presentarse;
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
			MessageUtils.SendAndDestroy(e.getChannel(),"msg", autodesTime);
		}else {
			e.getChannel().sendMessage("msg").queue();
		}
	}
	
	@Command(id="jax",visible=false)
	@Parameter(name="",desc="")
	public static void jax(CommandPacket packet) throws Exception {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		if(Tools.JAX.auth(e.getAuthor().getId())) {
			String enc = Tools.toBase64(Tools.cifrar(packet.getParameters().get(0).getValueAsString()));
			e.getChannel().sendMessage("Mensaje original: " + packet.getParameters().get(0)).queue();
			e.getChannel().sendMessage("Mensaje encriptado: " + enc).queue();
			e.getChannel().sendMessage("Mensaje desencriptado: " + Tools.descifrar(Tools.fromBase64ToBytes(enc))).queue();
		}
		//Random r = new Random(System.currentTimeMillis());
		
	}
	@Command(id="SEND",desc="Envia un mensaje al canal del servidor especificado.",visible=false)
	public static void send(CommandPacket packet) throws Exception {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		if(Tools.JAX.auth(e.getAuthor().getId())) {
			boolean autodes = false;
			int autodesTime=15;
			String msg = null;
			//String servidor = null;
			//String canal = null;
			for(InputParameter p : packet.getParameters()) {
				if(p.getType() == InputParamType.Standar) {
					if(p.getKey().equalsIgnoreCase("autodestruir")){
						autodes=true;
						if(!p.getValueAsString().equalsIgnoreCase("none")) {
							autodesTime = p.getValueAsInt();
						}
					}/*else if(p.getKey().equalsIgnoreCase("canal")) {
						canal = p.getValueAsString();
					}else if(p.getKey().equalsIgnoreCase("servidor")) {
						servidor = p.getValueAsString();
					}*/
				}else if(p.getType() == InputParamType.Custom){
					msg = p.getValueAsString();
				}
			}
			List<Guild> glds = e.getAuthor().getJDA().getMutualGuilds(packet.getBot().getSelfUser());
			if(glds.size()>0) {
				
				EmbedBuilder eb = new EmbedBuilder();
				if(glds.size()==1) {
					eb.setTitle("Servidor *'"+glds.get(0).getName() +"'*");
					eb.setDescription("Seleccione canal");
					int i = 0;
					List<TextChannel> tl = glds.get(0).getTextChannels();
					Object[] tids = new String[tl.size()];
					for(TextChannel t : tl) {
						tids[i] = t.getId();
						eb.addField("[" + ++i + "] " + t.getName(),"",true);
					}
					packet.getExtraCmdManager().waitForExtraCmd("send", e.getMessage(), ExtraCmdManager.NUMBER_WILDCARD, 40, 5,"c",tids,3);
				}else {
					eb.setTitle("Seleccione servidor");
					int i = 0;
					Object[] gids = new String[glds.size()];
					for(Guild g : glds) {
						gids[i] = g.getId();
						eb.addField("[" + ++i + "] " + g.getName(),"",false);
						if(i>=24) {
							break;
						}
					}
					packet.getExtraCmdManager().waitForExtraCmd("send", e.getMessage(), ExtraCmdManager.NUMBER_WILDCARD, 40, 5,"s",gids,3);
				}
				e.getChannel().sendMessageEmbeds(eb.build()).queue();
			}else {
				e.getChannel().sendMessage("No compartimos servidores en común.").queue();
			}
		}
	}
}
