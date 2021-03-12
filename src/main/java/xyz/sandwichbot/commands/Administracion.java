package xyz.sandwichbot.commands;

import java.util.ArrayList;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichbot.annotations.*;
import xyz.sandwichbot.models.InputParameter;

@Category(desc="Comandos de administración (requieren permisos de administrador, obvio)")
public class Administracion {

	@Command(name="exFunar",desc="Permite funar a un miembro del servidor (asigna un rol previamente configurado como 'rol Funado', con los privilegios predefinidos por los administradores del servidor. Cada vez que alguien asigne otro rol a este usuario, automaticamente se los voy a quitar)",enabled=false,visible=false)
	@Parameter(name="Nombre Objetivo(mención)",desc="Nombre(mención) del usuario a Funar. Se permiten mas de uno.")
	public static void exfunar(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	
	@Command(name="Banear",desc="Permite banear a un miembro del servidor por un tiempo determinado.",alias= {"ban","pajuera"},enabled=false)
	@Parameter(name="Nombre Objetivo(mención)",desc="Nombre(mención) del usuario a banear. Se permiten mas de uno.")
	public static void banear(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	
	@Command(name="LimpiarChat",desc="Elimina mensajes del chat donde se usó el comando.",alias= {"ban","pajuera"},enabled=false)
	@Parameter(name="Cantidad de mensajes",desc="Numero de mensajes a borrar del canal de texto. Debe ser un valor numerico valido entre 1 y 100, de lo contrario solo borraré el ultimo mnsaje(sin contar el del comando).")
	public static void cleanchat(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
	
	@Command(name="Configurar",desc="Comando con el que me puedes configurar... ya sabes, rol de funados, el voteban y quien sabe que otra verga. Si no se especifican parametros a configurar, devuelve la configuración actual.",alias= {"conf","config","configuracion"},enabled=false)
	public static void config(MessageReceivedEvent e, ArrayList<InputParameter> parametros) {
		
	}
}
