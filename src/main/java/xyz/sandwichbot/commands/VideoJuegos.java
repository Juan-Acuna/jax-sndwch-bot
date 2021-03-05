package xyz.sandwichbot.commands;

import java.net.URLDecoder;
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
	@Command(name="Pokedex",desc="Busca y devuelve informacion relativa a un pokémon.",alias= {"pkmn","pkm","dex","pd","poke"})
	@Parameter(name="Pokémon objetivo",desc="Nombre del pokémon que se desea buscar. El nombre debe estar bien escrito y se permiten espacios. Todo texto después de un '-' no formará parte del nombre.")
	@Option(name="numero",desc="Permite buscar al pokémon por su número identificador en la pokédex nacional. En caso de ser ingresado el nombre, este parametro se ignora. DEBE SER UN NÚMERO POSITIVO NO MAYOR A LA CANTIDAD DE POKÉMON EXISTENTES EN LA POKÉDEX.",alias={"id","nac","identificador","n","num","nacional"})
	@Option(name="variocolor",desc="Retorna pokémon, cuya imagen es reemplazada por su versión variocolor. La imagen devuelta es un render 3D animado. Si se usa la opción '-3D', esta última es ignorada.",alias={"shiny","s","sny","shaini","chino","vc","vario"})
	@Option(name="3D",desc="Retorna pokémon, cuya imagen es reemplazada por un render 3D animado de la criatura.",alias={"3","render","real","rl"})
	@Option(name="autodestruir",desc="Elimina el contenido despues de los segundos indicados. Si el tiempo no se indica, se eliminará después de 15 segundos",alias={"ad","autodes","autorm","arm"})
	public static void pokedex(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		boolean autodes = false;
		int autodesTime = 15;
		String pkmn = null;
		int pkmnId = -1;
		boolean shiny = false;
		boolean _3d = false;
		System.out.println("*****************************\nPARAMETROOS:");
		for(InputParameter p : parametros) {
			System.out.println(p.getKey() + " - " + p.getValueAsString() + " - " + p.getType());
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equals(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "Pokedex");
					return;
				}else if(p.getKey().equalsIgnoreCase("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equals("none")) {
						autodesTime = p.getValueAsInt();
					}
				}else if(p.getKey().equalsIgnoreCase("numero")){
					pkmnId = p.getValueAsInt();
				}else if(p.getKey().equalsIgnoreCase("3D")){
					_3d=true;
				}else if(p.getKey().equalsIgnoreCase("variocolor")){
					shiny = true;
				}
			}else if(p.getType() == InputParamType.Custom){
				pkmn = p.getValueAsString();
			}
		}
		System.out.println("***************************");
		if(pkmn==null && pkmnId<0) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Debe ingresar el nombre de un pokémon o su número en la pokédex nacional con la opcion '-numero' (para mas info use '-ayuda') para usar este comando.");
			e.getChannel().sendMessage(eb.build()).queue();
			return;
		}else if(pkmn==null && (pkmnId>898 || pkmnId==0)){
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Número identificador de pokémon fuera de rango. El mínimo permitido es 1 (Bulbasaur) y mayor permitido es 898 (Calyrex)");
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
		pkmn = pkmn.replace(" ","_");
		String hc="";
		try {
			hc = ClienteHttp.peticionHttp(Constantes.RecursoExterno.LINK_WIKIDEX_QUERY+pkmn);
		}catch(Exception ex) {
			//pokemon no existe
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("El pokémon '" + pkmn.replace("-", " ") + "' no existe. Asegurate de escribirlo bien.");
			e.getChannel().sendMessage(eb.build()).queue();
		}
		//System.out.println("HC2:"+hc.substring(93300, 95500));
		//String[] fuente = hc.split("Peso");
		//boolean isGigamax = fuente.length>2;
		String nombre ="";
		String id = "";
		String imagen ="";
		String tipo ="";
		String habilidad="";
		nombre =Comparador.Encontrar(Comparador.Patrones.Pokemon_Nombre, hc);
		id = Comparador.Encontrar(Comparador.Patrones.Pokemon_Id, hc);
		if(shiny) {
			imagen = Comparador.Encontrar(Comparador.Patrones.Pokemon_3D_8s, hc);
			if(imagen==null) {
				imagen = Comparador.Encontrar(Comparador.Patrones.Pokemon_3D_7s, hc);
			}
		}else if(_3d) {
			imagen = Comparador.Encontrar(Comparador.Patrones.Pokemon_3D_8, hc);
			if(imagen == null) {
				imagen = Comparador.Encontrar(Comparador.Patrones.Pokemon_3D_7, hc);
			}
		}else {
			imagen =Comparador.Encontrar(Comparador.Patrones.Pokemon_Imagen, hc);
			if(imagen==null) {
				imagen =Comparador.Encontrar(Comparador.Patrones.Pokemon_Imagen2, hc);
			}
			imagen = imagen.replace("og:image\" content=\"", "");
		}
		tipo =Comparador.Encontrar(Comparador.Patrones.Pokemon_Tipo, hc);
		if(tipo==null) {
			//tipo = tipo.toUpperCase().substring(0,1) + tipo.toLowerCase().substring(1);
			tipo =Comparador.Encontrar(Comparador.Patrones.Pokemon_Tipos, hc);
			String[] st = tipo.split("\" title=\"Tipo [0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}\"><img alt=\"Tipo [0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}.gif\" src=\"https://images.wikidexcdn.net/mwuploads/wikidex/[0-9a-zA-Z]{1,3}/[0-9a-zA-Z]{1,3}/latest/[0-9]{6,16}/Tipo_[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}.gif\" decoding=\"async\" [0-9a-zA-Z \"=]{2,45} /></a> <a href=\"/wiki/Tipo_");
			String t = URLDecoder.decode(st[0].replace("title=\"Tipo\">Tipos</a></th><td><a href=\"/wiki/Tipo_", ""),"UTF-8");
			tipo = "s: " + t.toUpperCase().substring(0,1) + t.toLowerCase().substring(1) + " " +Constantes.Pkm.getTipo(t);
			tipo += " | " + URLDecoder.decode(st[1].toUpperCase().substring(0,1),"UTF-8") + URLDecoder.decode(st[1].toLowerCase().substring(1),"UTF-8") + " " + Constantes.Pkm.getTipo(URLDecoder.decode(st[1],"UTF-8"));
			//" | "+st[1];
		}else {
			String t = URLDecoder.decode(tipo.replace(">Tipo</a></th><td><a href=\"/wiki/Tipo_", ""),"UTF-8");
			tipo = ": " + t.toUpperCase().substring(0,1) + t.toLowerCase().substring(1) + " " + Constantes.Pkm.getTipo(t);
		}
		habilidad=Comparador.Encontrar(Comparador.Patrones.Pokemon_Habilidad, hc);
		if(habilidad==null) {
			habilidad=Comparador.Encontrar(Comparador.Patrones.Pokemon_Habilidades, hc);
			String[] st = habilidad.split("\" title=\"[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}\">[0-9a-zA-Z%_ áéíóúÁÉÍÓÚ]{2,35}</a><br /><a href=\"/wiki/");
			habilidad = "es:\n"+URLDecoder.decode(st[0].replace(">Habilidades</a></th><td><a href=\"/wiki/", "").replace("_", " "),"UTF-8") +" | "+URLDecoder.decode(st[1].replace("_", " "),"UTF-8");
		}else {
			habilidad = ": " + URLDecoder.decode(habilidad.replace(">Habilidad</a></th><td><a href=\"/wiki/","").replace("_", " "),"UTF-8");
		}
		nombre = nombre.replace("<title>", "").replace(" - WikiDex, la enciclopedia Pokémon<", "");
		id=id.replace(">#<span style=\"font-size:", "").split("%;\">")[1].replace("<", "");
		System.out.println("IMAGENLACONCHADETUMADRE:"+imagen);
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
