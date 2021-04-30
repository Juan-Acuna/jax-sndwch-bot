package xyz.sandwichbot.main;

import xyz.sandwichframework.annotations.Configuration;
import xyz.sandwichframework.annotations.configure.*;
import xyz.sandwichframework.core.util.Language;

@Configuration(Language.EN)
public class Configuracion_en {
	
	/*    CATEGORIA : ADMINISTRACION    */
	@CategoryID("Administracion")
	@TranslatedName("Administration")
	@CategoryDescription
	public static final String ADMIN_DESC = "Administration commands (administrator permissions are required, obviously).";
	
	@CommandID("Banear")
	@TranslatedName("Ban")
	@CommandDescription
	public static final String BANEAR_DESC = "Allows to ban a member of the guild for a determined time.";
	
	@CommandID("Banear")
	@CommandAliases
	public static final String[] BANEAR_ALIAS = {"gofuk"};
	
	@CommandID("LimpiarChat")
	@TranslatedName("ClearChat")
	@CommandDescription
	public static final String LIMPIAR_DESC = "Delete messages sent in the actual text channel";
	
	@CommandID("LimpiarChat")
	@CommandAliases
	public static final String[] LIMPIAR_ALIAS = {"cls","clear"};
	
	@CommandID("Configurar")
	@TranslatedName("Settings")
	@CommandDescription
	public static final String CONF_DESC = "With this one you can configure me... you know, the voteban command and somo other sh*ts. Use without parameters to return the actual settings.";
	
	@CommandID("Configurar")
	@CommandAliases
	public static final String[] CONF_ALIAS = {"conf","config","configuration"};
	

	/*   CATEGORIA : COMUN   */
	@CategoryID("Comun")
	@TranslatedName("Common")
	@CategoryDescription
	public static final String COMUN_DESC = "Useful commands with varied purposes.";
	
	@CommandID("Saludar")
	@TranslatedName("Greetings")
	@CommandDescription
	public static final String SALUDAR_DESC = "Gives a lovely 'helo' to a friend (not working with mentions yet:pensive:)";
	
	@CommandID("Saludar")
	@CommandAliases
	public static final String[] SALUDAR_ALIAS = {"hello","hi"};
	
	@CommandID("YouTube")
	@CommandDescription
	public static final String YOUTUBE_DESC = "Search videos on YouTube, then shows the results (I can't play them yet, I need more time:pensive:).";
	
	@CommandID("YouTube")
	@CommandAliases
	public static final String[] YOUTUBE_ALIAS = {"yt","y","yutu","video","videos"};
	
	@CommandID("Invocar")
	@CommandDescription
	public static final String INVOCAR_DESC = "Comano extraño e inutil. Hace que me conecte a los canales de texto y voz del invocador";
	
	@CommandID("Invocar")
	@CommandAliases
	public static final String[] INVOCAR_ALIAS = {"invoke","llamar","ven"};
	
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