package xyz.sandwichbot.commands;

import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichbot.annotations.*;
import xyz.sandwichbot.main.util.ClienteHttp;
import xyz.sandwichbot.main.util.Comparador;
import xyz.sandwichbot.models.InputParameter;
import xyz.sandwichbot.models.InputParameter.InputParamType;
import xyz.snadwichbot.core.AutoHelpCommand;

@Category(desc="Comandos dedicados a videojuegos en general.")
public class VideoJuegos {
	@Command(name="pokedex",desc="Busca y devuelve informacion relativa a un pokémon.",alias= {"pkmn","pkm","dex","pd","poke"})
	public static void pokedex(MessageReceivedEvent e, ArrayList<InputParameter> parametros) throws Exception {
		boolean autodes = false;
		int autodesTime = 15;
		String pkmn = null;
		int pkmnId = 0;
		for(InputParameter p : parametros) {
			if(p.getType() == InputParamType.Standar) {
				if(p.getKey().equals(AutoHelpCommand.HELP_OPTIONS[0])) {
					AutoHelpCommand.sendHelp(e.getChannel(), "saludar");
					return;
				}else if(p.getKey().equals("autodestruir")){
					autodes=true;
					if(!p.getValueAsString().equals("none")) {
						autodesTime = p.getValueAsInt();
					}
				}if(p.getKey().equals("id")){
					pkmnId = p.getValueAsInt();
				}
			}else if(p.getType() == InputParamType.Custom){
				pkmn = p.getValueAsString();
			}
		}
		if(pkmn!=null) {
			//busca ese pokemon
			pkmn = pkmn.replace(" ","_");
			System.out.println("https://www.wikidex.net/wiki/"+pkmn);
			String hc = ClienteHttp.peticionHttp("https://www.wikidex.net/wiki/"+pkmn);
			System.out.println("HC:"+hc);
			if(Comparador.Coincidir("Has seguido un enlace a una página que aún no existe.", hc)) {
				//pkmn no existe
			}
			String nombre ="";
			String tipo ="";
			String id = "";
			String imagen ="";
			String habilidad="";
			nombre = Comparador.Encontrar(Comparador.Patrones.Pokemon_Nombre, hc);
			System.out.println("nombre: "+nombre);
			id = Comparador.Encontrar(Comparador.Patrones.Pokemon_Id, hc);
			imagen = Comparador.Encontrar(Comparador.Patrones.Pokemon_Imagen, hc);
			System.out.println("imagen:" + imagen);
			habilidad = Comparador.Encontrar(Comparador.Patrones.Pokemon_Habilidad, hc);
			System.out.println(habilidad);
			ArrayList<String> tipos = Comparador.EncontrarTodos(Comparador.Patrones.Pokemon_Tipo,hc);
			if(tipos.size()>0) {
				tipo = "`" + tipos.get(0).replace("href=\"/wiki/Tipo_","").replace("_"," ") + "`";
				tipo = ": " + tipo;
				if(tipos.size()==2) {
					tipo += "|`" + tipos.get(1).replace("href=\"/wiki/Tipo_","").replace("_"," ") + "`";
					tipo = "s" + tipo;
				}
			}
			//limpiando los residuos de los datos
			nombre = nombre.replace("nombrepokemon\" class=\"titulo\">","").replace("<"," ").replace("_"," ");
			id = id.replaceAll("numeronacional\">","").replace("_"," ");
			//imagen = imagen.replace("","");
			if(habilidad!=null) {
				habilidad = habilidad.replace("Habilidad</a></th><td><a href=\"/wiki/","").replace("_"," ");
			}else {
				String hab = Comparador.Encontrar(Comparador.Patrones.Pokemon_Habilidades, hc);
				System.out.println("hab:"+hab);
				String[] habs = hab.split("<br>");
				System.out.println("habs:"+habs.length);
				habilidad = habs[0].replace("Habilidades</a></th><td><a href=\"/wiki/[a-zA-Z0-9ñÑ_ .:áÁéÉíÍóÓúÚü%]{2,25}\" title=\"[a-zA-Z0-9ñÑ_ .:áÁéÉíÍóÓúÚü%]{2,25}\">","").replace("</a>","").replace("_"," ");
				habilidad += " | " + habs[1].replace("<a href=\"/wiki/","").replace("_"," ");
			}
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle(nombre + "   |  NAC# "+ id);
			eb.addField("Tipo" + tipo,"Habilidad(es): " + habilidad, false);
			eb.setImage(imagen);
			e.getChannel().sendMessage(eb.build()).queue();
		}else {
			if(pkmnId>0) {
				//if() reservado para las regiones
				//busca pkmn por id
				
			}
			//lista las regiones
		}
	}
}
