package com.jaxsandwich.discordbot.configuracion;

import com.jaxsandwich.sandwichcord.annotations.Configuration;
import com.jaxsandwich.sandwichcord.annotations.configure.*;
import com.jaxsandwich.sandwichcord.core.util.Language;

@Configuration(Language.ES)
public class ConfigComandos {
	
	/*    CATEGORIA : ADMINISTRACION    */
	@CategoryID("Administracion")
	@CategoryDescription
	public static final String ADMIN_DESC = "Comandos de administración (requieren permisos de administrador, obvio).";
	
	@CommandID("Banear")
	@CommandDescription
	public static final String BANEAR_DESC = "Permite banear a un miembro del servidor por un tiempo determinado.";
	
	@CommandID("Banear")
	@CommandAliases
	public static final String[] BANEAR_ALIAS = {"ban","pajuera"};
	
	@CommandID("LimpiarChat")
	@CommandDescription
	public static final String LIMPIAR_DESC = "Elimina mensajes del canal de texto donde se usó el comando.";
	
	@CommandID("LimpiarChat")
	@CommandAliases
	public static final String[] LIMPIAR_ALIAS = {"cls","limpiar","clear"};
	
	@CommandID("Configurar")
	@CommandDescription
	public static final String CONF_DESC = "Comando con el que me puedes configurar... ya sabes, como interactuo con el servidor, bloqueo/habilitacion de comandos y quien sabe que otra verga. Si no se especifican parametros a configurar, devuelve la configuración actual.";
	
	@CommandID("Configurar")
	@CommandAliases
	public static final String[] CONF_ALIAS = {"conf","config","configuracion"};
	

	/*   CATEGORIA : COMUN   */
	@CategoryID("Comun")
	@CategoryDescription
	public static final String COMUN_DESC = "Comandos frecuentes con propósitos variados.";
	
	@CommandID("Saludar")
	@CommandDescription
	public static final String SALUDAR_DESC = "Da un cálido saludo a un amigo(aún no funciona con menciones:pensive:)";
	
	@CommandID("Saludar")
	@CommandAliases
	public static final String[] SALUDAR_ALIAS = {"s","saluda","putea","putear"};
	
	@CommandID("YouTube")
	@CommandDescription
	public static final String YOUTUBE_DESC = "Realiza la busqueda solicitada y devuelve una lista con los primeros resultados encontrados(Aún no soy capaz de reproducirlos, denme tiempo:pensive:).";
	
	@CommandID("YouTube")
	@CommandAliases
	public static final String[] YOUTUBE_ALIAS = {"yt","y","yutu","video","videos","llutu"};
	
	@CommandID("Invocar")
	@CommandDescription
	public static final String INVOCAR_DESC = "Comando extraño e inútil. Hace que me conecte a los canales de texto y voz del invocador";
	
	@CommandID("Invocar")
	@CommandAliases
	public static final String[] INVOCAR_ALIAS = {"inv","llamar","ven"};
	
	@CommandID("Presentacion")
	@CommandDescription
	public static final String PRESENTACION_DESC = "Comando para saber más de mi (no es lo mismo que el de ayuda).";
	
	@CommandID("Presentacion")
	@CommandAliases
	public static final String[] PRESENTACION_ALIAS = {"informacion","info","inf"};
	
	@CommandID("VoteBan")
	@CommandDescription
	public static final String VOTEBAN_DESC = "Inicia una votacion para banear a un usuario del servidor(temporalmente). *ESTE COMANDO REQUIERE SER ACTIVADO PREVIAMENTE POR UN ADMINISTRADOR DEL SERVIDOR.*";
	
	@CommandID("VoteBan")
	@CommandAliases
	public static final String[] VOTEBAN_ALIAS = {"vb","vban"};
	
	@CommandID("Embed")
	@CommandDescription
	public static final String EMBED_DESC = "Devuelve el mensaje introducido por el usuario en forma de 'Embed' (mensaje decorado con un estilo especial de discord). para usar el caracter '-'(caracter de opciones) se debe anteponer \\ para reconocerlo como texto. Para generar enlaces ([como este](http://google.com)), escriba el título del enlace entre las etiquetas `{%link%}`, seguido de la url entre las etiquetas `{%href%}` (ejemplo:`{%link%}Google{%link%}{%href%}google.com{%href%}`). Los links solo funcionan en la descripción del embed y en las descripciones de los campos.";
	
	@CommandID("Embed")
	@CommandAliases
	public static final String[] EMBED_ALIAS = {"emb"};
	
	@CommandID("Funar")
	@CommandDescription
	public static final String FUNAR_DESC = "Funa a un usuario.";
	
	@CommandID("Funar")
	@CommandAliases
	public static final String[] FUNAR_ALIAS = {"funa","fn"};
	
	@CommandID("Trollear")
	@CommandDescription
	public static final String TROLLEAR_DESC = "Comando para trollear, no tiene más ciencia. Para especificar un objetivo, mencionalo al comienzo del comando, de lo contrario el trolleo te lo llevas tu:smirk:";
	
	@CommandID("Trollear")
	@CommandAliases
	public static final String[] TROLLEAR_ALIAS = {"troll","trolear","trl"};

	/*   CATEGORIA : IMAGEN   */
	@CategoryID("Imagen")
	@CategoryDescription
	public static final String IMAGEN_DESC = "Imagenes...";
	
	@CommandID("Gatos")
	@CommandDescription
	public static final String GATOS_DESC = "Devuelve la imagen(una o más) de un gato al azar. Utiliza la API de [random.cat](http://random.cat).";
	
	@CommandID("Gatos")
	@CommandAliases
	public static final String[] GATOS_ALIAS = {"mew","mw","miau","gato"};
	
	@CommandID("Otaku")
	@CommandDescription
	public static final String OTAKU_DESC = "Devuelve imagenes de esta temática, sin incluir contenido pornografico. Nombre provisorio.";
	
	@CommandID("Otaku")
	@CommandAliases
	public static final String[] OTAKU_ALIAS = {"oku","otk"};
	
	/*   CATEGORIA : MUSICA   */
	@CategoryID("Musica")
	@CategoryDescription
	public static final String MUSICA_DESC = "Comandos de música. ¿Que?¿Acaso esperabas otra descripción?";
	
	@CommandID("Reproducir")
	@CommandDescription
	public static final String REP_DESC = "Reproduce música obtenida desde una fuente de internet (por defecto YouTube.com)";
	
	@CommandID("Reproducir")
	@CommandAliases
	public static final String[] REP_ALIAS = {"p","r","play","pl"};
	
	@CommandID("Pausar")
	@CommandDescription
	public static final String PAUSAR_DESC = "Pausa la reproducción actual.";
	
	@CommandID("Pausar")
	@CommandAliases
	public static final String[] PAUSAR_ALIAS = {"pausa","pause","espera","pp"};

	@CommandID("Siguiente")
	@CommandDescription
	public static final String SIG_DESC = "Salta a la siguiente canción en la cola actual. Si no quedan canciones, la reproducción se termina.";
	
	@CommandID("Siguiente")
	@CommandAliases
	public static final String[] SIG_ALIAS = {"sig","saltar","skip","sk"};

	@CommandID("Detener")
	@CommandDescription
	public static final String DET_DESC = "Detiene la reproducción actual.";
	
	@CommandID("Detener")
	@CommandAliases
	public static final String[] DET_ALIAS = {"stop","det","callate"};

	@CommandID("Actual")
	@CommandDescription
	public static final String ACT_DESC = "Indica la canción que se esta reproduciendo actualmente.";
	
	@CommandID("Actual")
	@CommandAliases
	public static final String[] ACT_ALIAS = {"act","sonando","reproduciendo","np"};

	@CommandID("Cola")
	@CommandDescription
	public static final String _DESC = "Muestra la lista de canciones en la cola.";
	
	@CommandID("Cola")
	@CommandAliases
	public static final String[] _ALIAS = {"col","canciones","lista","fila"};
	
	
	/*    CATEGORIA : NSFW    */
	@CategoryID("NSFW")
	@CategoryDescription
	public static final String CNSFW_DESC = "Una fina colección de los mejores comandos de este servidor. Comandos vitales para una sociedad civilizada y culta.";
	
	@CommandID("NSFW")
	@CommandDescription
	public static final String NSFW_DESC = "Como si no supieras que hace este comando cochin@ :wink::smirk:";
	
	@CommandID("NSFW")
	@CommandAliases
	public static final String[] NSFW_ALIAS = {"ns","por","uff","porno","prn","nopor","nopo","porn","cochinadas","18","+18","7u7"};
	
	@CommandID("Xvideos")
	@CommandDescription
	public static final String XV_DESC = "Realiza la busqueda solicitada y devuelve una lista con los primeros resultados encontrados(Aún no soy capaz de reproducirlos, denme tiempo:pensive:).";
	
	@CommandID("Xvideos")
	@CommandAliases
	public static final String[] XV_ALIAS = {"xv","xvid","xxxv","videosnopor"};
	
	@CommandID("OtakuNSFW")
	@CommandDescription
	public static final String ONSFW_DESC = "Es como el de NSFW clásico... Pero con monas chinas :wink::smirk:";
	
	@CommandID("OtakuNSFW")
	@CommandAliases
	public static final String[] ONSFW_ALIAS = {"hentai","otakuns","ons","o18","h18","otakuporn"};
	
	@CommandID("Rule34")
	@CommandDescription
	public static final String R34_DESC = "Internet esta lleno de reglas, esta es la más importante.";
	
	@CommandID("Rule34")
	@CommandAliases
	public static final String[] R34_ALIAS = {"r34","34"};
	
	/*    CATEGORIA : VIDEOJUEGOS    */
	@CategoryID("VideoJuegos")
	@CategoryDescription
	public static final String VJ_DESC = "Comandos dedicados a videojuegos en general.";
	
	@CommandID("Pokedex")
	@CommandDescription
	public static final String PKDEX_DESC = "Busca y devuelve informacion relativa a un pokémon.";
	
	@CommandID("Pokedex")
	@CommandAliases
	public static final String[] PKDEX_ALIAS = {"pkmn","pkm","dex","pd","poke"};
	
	
	
	
}