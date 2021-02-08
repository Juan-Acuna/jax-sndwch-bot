package xyz.sandwichbot.commands;

import java.util.ArrayList;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichbot.annotations.*;
import xyz.sandwichbot.core.AutoHelpCommand;
import xyz.sandwichbot.main.Constantes;
import xyz.sandwichbot.main.util.MultiImagen;
import xyz.sandwichbot.models.InputParameter;
import xyz.sandwichbot.models.InputParameter.InputParamType;

@Category()
public class Imagen {
	@Command(name="Gatos",desc="Devuelve la imagen(una o más) de un gato al azar. Utiliza la API de [random.cat](http://random.cat).",alias={"mew","mw","miau","gato"})
	@Option(name="cantidad",desc="Indica la cantidad de mininos requeridos.\\nDEBE SER UN VALOR NUMERICO ENTRE 1 Y 100. Si ingresas mal este parametro no habrán gatos para ti.",alias={"c","cant","num"})
	public static void meow(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		MultiImagen mi;
		int cantidad =1;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equals(Constantes.PARAMETROS.CANTIDAD[0])) {
					cantidad = p.getValueAsInt();
				}else if(p.getKey().equals(AutoHelpCommand.HELP_OPTIONS[0])) {
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
		if(cantidad>=8) {
			mi = new MultiImagen(e.getChannel(),true);
		}else {
			mi = new MultiImagen(e.getChannel());
		}
		Thread hilo;		
		for(int i = 1; i<=cantidad;i++) {
			hilo = new Thread(mi);
			hilo.start();
		}
	}
}
