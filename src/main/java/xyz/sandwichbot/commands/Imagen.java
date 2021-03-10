package xyz.sandwichbot.commands;

import java.awt.Color;
import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichbot.annotations.*;
import xyz.sandwichbot.core.AutoHelpCommand;
import xyz.sandwichbot.main.Constantes;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichbot.main.util.ControladorImagenes;
import xyz.sandwichbot.main.util.FuenteImagen;
import xyz.sandwichbot.main.util.Tools;
import xyz.sandwichbot.models.InputParameter;
import xyz.sandwichbot.models.InputParameter.InputParamType;

@Category(desc="Imagenes...")
public class Imagen {
	@Command(name="Gatos",desc="Devuelve la imagen(una o más) de un gato al azar. Utiliza la API de [random.cat](http://random.cat).",alias={"mew","mw","miau","gato"})
	@Option(name="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="cantidad",desc="Indica la cantidad de mininos requeridos.\\nDEBE SER UN VALOR NUMERICO ENTRE 1 Y 100. Si ingresas mal este parametro no habrán gatos para ti.",alias={"c","cant","num"})
	public static void meow(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		ControladorImagenes gi;
		int cantidad =1;
		boolean autodes = false;
		int autodesTime = 15;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("cantidad")) {
					cantidad = p.getValueAsInt();
				}else if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "Gatos");
					return;
				}
			}
		}
		if(cantidad>100) {
			cantidad = 100;
		}else if(cantidad <= 0) {
			cantidad = 1;
		}
		EmbedBuilder eb = new EmbedBuilder();
		eb.setFooter("Meow:3",SandwichBot.ActualBot().getJDA().getSelfUser().getAvatarUrl());
		eb.setColor(Color.green);
		if(cantidad>=8) {
			gi = new ControladorImagenes(e.getChannel(), FuenteImagen.RandomCat, eb ,true);
		}else {
			gi = new ControladorImagenes(e.getChannel(), FuenteImagen.RandomCat, eb);
		}
		gi.setAutodes(autodes);
		gi.setAutodesTime(autodesTime);
		Thread hilo;		
		for(int i = 1; i<=cantidad;i++) {
			hilo = new Thread(gi);
			hilo.start();
		}
	}
	@Command(name="Otaku",desc="Devuelve imagenes de esta temática, sin incluir contenido pornografico. Nombre provisorio.",alias={"oku","otk"},enabled=false,visible=false)
	@Option(name="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	@Option(name="cantidad",desc="Indica la cantidad de mininos requeridos.\\nDEBE SER UN VALOR NUMERICO ENTRE 1 Y 100. Si ingresas mal este parametro no habrán gatos para ti.",alias={"c","cant","num"})
	public static void otaku(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		ControladorImagenes gi;
		int cantidad =1;
		boolean autodes = false;
		int autodesTime = 15;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equalsIgnoreCase("cantidad")) {
					cantidad = p.getValueAsInt();
				}else if(p.getKey().equalsIgnoreCase("autodestruir")) {
					autodes=true;
					if(!p.getValueAsString().equalsIgnoreCase("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "Otaku");
					return;
				}
			}
		}
		if(cantidad>100) {
			cantidad = 100;
		}else if(cantidad <= 0) {
			cantidad = 1;
		}
		EmbedBuilder eb = new EmbedBuilder();
		eb.setFooter("シャワーを浴びないでください",SandwichBot.ActualBot().getJDA().getSelfUser().getAvatarUrl());
		eb.setColor(Tools.stringColorCast("naranjo"));
		if(cantidad>=8) {
			gi = new ControladorImagenes(e.getChannel(), FuenteImagen.RandomCat, eb ,true);
		}else {
			gi = new ControladorImagenes(e.getChannel(), FuenteImagen.RandomCat, eb);
		}
		gi.setAutodes(autodes);
		gi.setAutodesTime(autodesTime);
		Thread hilo;		
		for(int i = 1; i<=cantidad;i++) {
			hilo = new Thread(gi);
			hilo.start();
		}
	}
}
