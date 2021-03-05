package xyz.sandwichbot.commands;

import xyz.sandwichbot.annotations.*;

@Category(desc="Comandos de administración (requieren permisos de administrador, obvio)")
public class Administracion {

	@Command(name="Funar",desc="Permite funar a un miembro del servidor (debe estar previamente configurado el rol 'Funado')",alias= {"funa","fn"},enabled=false)
	@Parameter(name="Nombre Objetivo(mención)",desc="Nombre(mención) del usuario a Funar. Se permiten mas de uno.")
	public static void funar() {
		
	}
}
