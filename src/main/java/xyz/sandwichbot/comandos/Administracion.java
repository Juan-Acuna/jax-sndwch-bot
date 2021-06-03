package xyz.sandwichbot.comandos;

import java.util.ArrayList;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichframework.annotations.*;
import xyz.sandwichframework.models.InputParameter;

@Category()
public class Administracion {
	
	/*
	@Command(name="exFunar",desc="Permite funar a un miembro del servidor (asigna un rol previamente configurado como 'rol Funado', con los privilegios predefinidos por los administradores del servidor. Cada vez que alguien asigne otro rol a este usuario, automaticamente se los voy a quitar)",enabled=false,visible=false)
	@Parameter(name="Nombre Objetivo(mención)",desc="Nombre(mención) del usuario a Funar. Se permiten mas de uno.")
	public static void exfunar(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}*/
	
	@Command(name="Banear",enabled=false)
	@Parameter(name="Nombre Objetivo(mención)",desc="Nombre(mención) del usuario a banear. Se permiten mas de uno.")
	@Option(name="tiempo",desc="Tiempo durante el cual el o los miembros sera baneado del servidor. La unidad de tiempo por defecto es en horas.")
	@Option(name="unidad",desc="Permite seleccionar la unidad de tiempo para la opción `tiempo`.\n\n`S` - Segundos\n`M` - Minutos\n`H` - Horas (por defecto)\n`D` - Dias")
	public static void banear(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	
	@Command(name="LimpiarChat",enabled=false)
	@Parameter(name="Cantidad de mensajes",desc="Numero de mensajes a borrar del canal de texto. Debe ser un valor numerico valido entre 1 y 100, de lo contrario solo borraré el ultimo mensaje(sin contar el del comando).")
	public static void cleanchat(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	
	@Command(name="Configurar",enabled=false)
	@Option(name="comandos",desc="Esta opción habilita el menú de configuración de los comandos en este servidor.")
	@Option(name="roles",desc="Esta opción habilita el menú de configuración de roles en este servidor.")
	@Option(name="canales",desc="Esta opción habilita el menú de configuración de canales en este servidor.")
	@Option(name="alarmas",desc="Esta opción habilita el menú de configuración de alarmas en este servidor.")
	public static void config(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
}
