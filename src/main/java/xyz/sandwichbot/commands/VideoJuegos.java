package xyz.sandwichbot.commands;

import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichbot.annotations.*;
import xyz.sandwichbot.core.AutoHelpCommand;
import xyz.sandwichbot.main.Constantes;
import xyz.sandwichbot.main.SandwichBot;
import xyz.sandwichbot.main.util.ClienteHttp;
import xyz.sandwichbot.main.util.Comparador;
import xyz.sandwichbot.models.InputParameter;
import xyz.sandwichbot.models.InputParameter.InputParamType;

@Category(desc="Comandos dedicados a videojuegos en general.")
public class VideoJuegos {
	@Command(name="pokedex",desc="Busca y devuelve informacion relativa a un pok�mon.",alias= {"pkmn","pkm","dex","pd","poke"},enabled=true)
	@Parameter(name="Pok�mon objetivo",desc="Nombre del pok�mon que se desea buscar. El nombre debe estar bien escrito y se permiten espacios. Todo texto despu�s de un '-' no formar� parte del nombre.")
	@Option(name="numero",desc="Permite buscar al pok�mon por su n�mero identificador en la pok�dex nacional. En caso de ser ingresado el nombre este parametro se ignora. DEBE SER UN N�MERO POSITIVO NO MAYOR A LA CANTIDAD DE POK�MON EXISTENTES EN LA POK�DEX.",alias={"id","nac","identificador","n","num","nacional"})
	@Option(name="variocolor",desc="Retorna pok�mon, cuya imagen es reemplazada por su versi�n variocolor. La imagen devuelta es un render 3D animado. Si se usa la opci�n '-3D', esta �ltima es ignorada.",alias={"shiny","s","sny","shaini","chino","vc","vario"})
	@Option(name="3D",desc="Retorna pok�mon, cuya imagen es reemplazada por un render 3D animado de la criatura.",alias={"3d","3","render","real","rl"})
	@Option(name="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminar� despu�s de 15 segundos",alias={"ad","autodes","autorm","arm"})
	public static void pokedex(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		boolean autodes = false;
		int autodesTime = 15;
		String pkmn = null;
		int pkmnId = -1;
		boolean shiny = false;
		boolean _3d = false;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equals(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "pokedex");
					return;
				}else if(p.getKey().equals("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equals("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equals("numero")){
					pkmnId = p.getValueAsInt();
				}else if(p.getKey().equals("3D")){
					_3d=true;
				}else if(p.getKey().equals("variocolor")){
					shiny = true;
				}
			}else if(p.getType() == InputParamType.Custom){
				pkmn = p.getValueAsString();
			}
		}
		if(pkmn==null && pkmnId<0) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Debe ingresar el nombre de un pok�mon o su n�mero en la pok�dex nacional con la opcion '-numero' (para mas info use '-ayuda') para usar este comando.");
			e.getChannel().sendMessage(eb.build()).queue();
			return;
		}else if(pkmn==null && (pkmnId>898 || pkmnId==0)){
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("N�mero identificador de pok�mon fuera de rango. El m�nimo permitido es 1 (Bulbasaur) y mayor permitido es 898 (Calyrex)");
			e.getChannel().sendMessage(eb.build()).queue();
			return;
		}else if(pkmn==null && pkmnId>0){
			pkmn = Constantes.Pkm.getPokemonFromId(pkmnId);
			if(pkmn==null) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Error de servidor");
				e.getChannel().sendMessage(eb.build()).queue();
				return;
			}
		}
		//busca ese pokemon
		pkmn = pkmn.replace(" ","-");
		String hc = "";
		try {
			hc = ClienteHttp.peticionHttp(Constantes.RecursoExterno.LINK_POKEMON_QUERY+pkmn);
		}catch(Exception ex) {
			//pokemon no existe
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("El pok�mon '" + pkmn.replace("-", " ") + "' no existe. Asegurate de escribirlo bien.");
			e.getChannel().sendMessage(eb.build()).queue();
		}
		String[] fuente = hc.split("Peso");
		boolean isGigamax = fuente.length>2;
		String nombre ="";
		String id = "";
		String imagen ="";
		String tipo ="";
		String habilidad="";
		nombre = Comparador.Encontrar(Comparador.Patrones.Pokemon_Nombre, hc).replace("<title>","").replace(" | Pok�dex</title>"," ");
		id = Comparador.Encontrar(Comparador.Patrones.Pokemon_Imagen, hc);
		System.out.println(id);
		id = id.substring(56,59);
		System.out.println(id);
		if(shiny) {
			String img = ClienteHttp.peticionHttp(Constantes.RecursoExterno.LINK_WIKIDEX_QUERY+pkmn.replace("-", "_"));
			imagen = Comparador.Encontrar(Comparador.Patrones.Pokemon_3D_8s, img);
			if(imagen==null) {
				imagen = Comparador.Encontrar(Comparador.Patrones.Pokemon_3D_7s, img);
			}
		}else if(_3d) {
			String img = ClienteHttp.peticionHttp(Constantes.RecursoExterno.LINK_WIKIDEX_QUERY+pkmn.replace("-", "_"));
			imagen = Comparador.Encontrar(Comparador.Patrones.Pokemon_3D_8, img);
			if(imagen == null) {
				imagen = Comparador.Encontrar(Comparador.Patrones.Pokemon_3D_7, img);
			}
		}else {
			imagen = Comparador.Encontrar(Comparador.Patrones.Pokemon_Imagen, hc);
		}
		ArrayList<String> tipos = Comparador.EncontrarTodos(Comparador.Patrones.Pokemon_Tipo, hc);
		if(tipos.size()==2) {
			String t = tipos.get(0).substring(tipos.get(0).indexOf(">")+1);
			t += " " + Constantes.Pkm.getTipo(t);
			tipo += "s: " + t;
			t = tipos.get(1).substring(tipos.get(1).indexOf(">")+1);
			t += " " + Constantes.Pkm.getTipo(t);
			tipo += " | " + t;
		}else {
			String t = tipos.get(0).substring(tipos.get(0).indexOf(">")+1);
			t += " " + Constantes.Pkm.getTipo(t);
			tipo += ": " + t;
		}
		ArrayList<String> habs = Comparador.EncontrarTodos(Comparador.Patrones.Pokemon_Habilidad, fuente[1]);
		if(habs.size()==2) {
			habilidad += "es:\n" + habs.get(0).replace("<a href=\"\" class=\"moreInfo\">                      <span class=\"attribute-value\">", "");
			habilidad += " | " + habs.get(1).replace("<a href=\"\" class=\"moreInfo\">                      <span class=\"attribute-value\">", "");
		}else {
			habilidad += ": " + habs.get(0).replace("<a href=\"\" class=\"moreInfo\">                      <span class=\"attribute-value\">", "");
		}
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(nombre + "   |  NAC# "+ id);
		eb.addField("Tipo" + tipo,"Habilidad" + habilidad, false);
		eb.setImage(imagen);
		/*if(isGigamax) {
			eb.setThumbnail(imagen.substring(0, imagen.length()-4)+"_f"+(fuente.length-1)+".png");
		}*/
		if(autodes) {
			SandwichBot.SendAndDestroy(e.getChannel(), eb.build(), autodesTime);
		}else {
			e.getChannel().sendMessage(eb.build()).queue();
		}
	}
}
