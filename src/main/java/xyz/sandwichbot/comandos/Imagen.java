package xyz.sandwichbot.comandos;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichbot.main.Constantes;
import xyz.sandwichbot.main.modelos.FuenteImagen;
import xyz.sandwichbot.main.util.ControladorImagenes;
import xyz.sandwichbot.main.util.Tools;
import xyz.sandwichframework.annotations.*;
import xyz.sandwichframework.models.CommandPacket;
import xyz.sandwichframework.models.InputParameter;
import xyz.sandwichframework.models.InputParameter.InputParamType;

@Category(desc="Imagenes...")
public class Imagen {
	@Command(name="Gatos",desc="Devuelve la imagen(una o más) de un gato al azar. Utiliza la API de [random.cat](http://random.cat).",alias={"mew","mw","miau","gato"})
	@Option(name="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="cantidad",desc="Indica la cantidad de mininos requeridos.\\nDEBE SER UN VALOR NUMERICO ENTRE 1 Y 100. Si ingresas mal este parametro no habrán gatos para ti.",alias={"c","cant","num"})
	public static void meow(CommandPacket packet) throws Exception {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		ControladorImagenes gi;
		int cantidad =1;
		boolean autodes = false;
		int autodesTime = 15;
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("cantidad")) {
					cantidad = p.getValueAsInt();
				}else if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}
			}
		}
		if(cantidad>100) {
			cantidad = 100;
		}else if(cantidad <= 0) {
			cantidad = 1;
		}
		EmbedBuilder eb = new EmbedBuilder();
		eb.setFooter("Meow:3",packet.getBot().getSelfUser().getAvatarUrl());
		eb.setColor(Color.green);
		gi = new ControladorImagenes(e.getChannel(),FuenteImagen.find(FuenteImagen.RandomCat), eb ,(cantidad>=8));
		gi.setAutodes(autodes);
		gi.setAutodesTime(autodesTime);
		Thread hilo;		
		for(int i = 1; i<=cantidad;i++) {
			hilo = new Thread(gi);
			hilo.start();
		}
	}
	@Command(name="Otaku",desc="Devuelve imagenes de esta temática, sin incluir contenido pornografico. Nombre provisorio.",alias={"oku","otk"},enabled=true,visible=false)
	@Option(name="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="cantidad",desc="Indica la cantidad de mininos requeridos.\\nDEBE SER UN VALOR NUMERICO ENTRE 1 Y 100. Si ingresas mal este parametro tendrás que ir a bañarte.",alias={"c","cant","num"})
	@Option(name="tags",desc="Etiquetas que describen el contenido esperado. Pueden ser una o mas separadas por comas (','). No abuses de estas porque mientras mas especifica es la busqueda, menos resultados obtenidos. Se permiten espacios entre etiquetas.",alias={"t","tg","tgs"})
	@Option(name="gif",desc="Indica si el contenido es animado o no. Si no se especifica esta opción, por defecto el contenido es animado (equivalente a '-gif true')",alias={"g","gf","animado","anim"})
	@Option(name="video",desc="Esta opcion indica que el recurso devuelto debe ser un video. Si se usa junto con la opcion '-gif', esta ultima sera ignorada.",alias={"v","vid","mp4"})
	@Option(name="random",desc="Establece que los recursos devueltos deben ser videos e imagenes estaticas o animadas de manera aleatoria. Si se usa junto con las opciones '-gif' o '-video', estas seran ignoradas.",alias={"r","rdm","rand","azar"})
	@Option(name="anonimo",desc="Elimina el mensaje con el que se invoca el comando.",alias={"an","anon","annonymous"})
	public static void otaku(CommandPacket packet) {
		MessageReceivedEvent e = packet.getMessageReceivedEvent();
		ControladorImagenes gi;
		int cantidad =1;
		boolean gif = true;
		String[] tags = null;
		boolean video = false;
		boolean autodes = false;
		int autodesTime = 15;
		boolean anon = false;
		boolean random = false;
		for(InputParameter p : packet.getParameters()) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("cantidad")) {
					cantidad = p.getValueAsInt();
				}else if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("gif")) {
					gif= p.getValueAsBoolean(Constantes.VALORES.TRUE);
				}else if(p.getKey().equalsIgnoreCase("tags")) {
					tags = p.getValueAsString().replaceAll("\\s",",").split(",");
				}else if(p.getKey().equalsIgnoreCase("video")) {
					video = true;
					gif=false;
				}else if(p.getKey().equalsIgnoreCase("random")) {
					random = true;
				}else if(p.getKey().equalsIgnoreCase("anonimo")) {
					anon = true;
				}
			}
		}
		if(anon) {
			packet.getTextChannel().purgeMessagesById(e.getMessageId());
		}
		if(cantidad>100) {
			cantidad = 100;
		}else if(cantidad <= 0) {
			cantidad = 1;
		}
		EmbedBuilder eb = new EmbedBuilder();
		eb.setFooter("シャワーを浴びないでください",packet.getBot().getSelfUser().getAvatarUrl());
		eb.setColor(Tools.stringColorCast("naranjo"));
		if(cantidad>=8) {
			gi = new ControladorImagenes(e.getChannel(), FuenteImagen.find(FuenteImagen.SafeBooru), eb ,true);
		}else {
			gi = new ControladorImagenes(e.getChannel(), FuenteImagen.find(FuenteImagen.SafeBooru), eb);
		}
		gi.setGif(gif);
		gi.setTags(tags);
		gi.setVideo(video);
		gi.setRand(random);
		gi.setAutodes(autodes);
		gi.setAutodesTime(autodesTime);
		Thread hilo;		
		for(int i = 1; i<=cantidad;i++) {
			hilo = new Thread(gi);
			hilo.start();
		}
	}
}
