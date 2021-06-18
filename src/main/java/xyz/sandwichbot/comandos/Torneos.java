package xyz.sandwichbot.comandos;

import java.util.ArrayList;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichframework.annotations.*;
import xyz.sandwichframework.models.InputParameter;

@Category(desc="Comandos para organizar eventos, torneos y sorteos. Se requiere permiso de administrador.")
public class Torneos {
	@Command(name="Nuevo",enabled=false)
	public static void nuevo(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	@Command(name="Finalizar",enabled=false)
	public static void finTorneo(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	@Command(name="Participar",enabled=false)
	public static void participar(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	@Command(name="Abandonar",enabled=false)
	public static void abandonarTorneo(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	@Command(name="Registrar",enabled=false)
	public static void registrarTeam(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	@Command(name="Trofeos",enabled=false)
	public static void trofeos(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	@Command(name="Balance",enabled=false)
	public static void balance(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	@Command(name="Apostar",enabled=false)
	public static void apostar(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
}
