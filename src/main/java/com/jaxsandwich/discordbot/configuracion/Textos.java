package com.jaxsandwich.discordbot.configuracion;

import com.jaxsandwich.sandwichcord.core.LanguageConfiguration;
import com.jaxsandwich.sandwichcord.core.util.Language;

public class Textos extends LanguageConfiguration{
	public Textos() {
		super();
		/* ESPAÑOL*/
		prepararCategoriasES();
		prepararComandosES();
		
		/* ENGLISH */
		prepararCategoriasEN();
		prepararComandosEN();
		/**/
		prepararTextosES();
	}
	private void prepararCategoriasES() {
		this.addCategoryDescription(Language.ES, "Administracion", "Comandos de administración (requieren permisos de administrador, obvio).");
		this.addCategoryDescription(Language.ES, "Comun", "Comandos frecuentes con propósitos variados.");
		this.addCategoryDescription(Language.ES, "Imagen", "Imagenes...");
		this.addCategoryDescription(Language.ES, "Musica", "Comandos de música. ¿Que?¿Acaso esperabas otra descripción?");
		this.addCategoryDescription(Language.ES, "NSFW", "Una fina colección de los mejores comandos de este servidor. Comandos vitales para una sociedad civilizada y culta.");
		this.addCategoryDescription(Language.ES, "VideoJuegos", "Comandos dedicados a videojuegos en general.");
	}
	private void prepararComandosES() {
		this.addCommandDescription(Language.ES, "Banear", "Permite banear a un miembro del servidor por un tiempo determinado.");
		this.addCommandDescription(Language.ES, "LimpiarChat", "Elimina mensajes del canal de texto donde se usó el comando.");
		this.addCommandDescription(Language.ES, "Configurar", "Comando con el que me puedes configurar... ya sabes, como interactuo con el servidor, bloqueo/habilitacion de comandos y quien sabe que otra verga. Si no se especifican parametros a configurar, devuelve la configuración actual.");
		this.addCommandDescription(Language.ES, "YouTube", "Realiza la busqueda solicitada y devuelve una lista con los primeros resultados encontrados(Aún no soy capaz de reproducirlos, denme tiempo:pensive:).");
		this.addCommandDescription(Language.ES, "Invocar", "Comando extraño e inútil. Hace que me conecte a los canales de texto y voz del invocador");
		this.addCommandDescription(Language.ES, "Presentacion", "Comando para saber más de mi (no es lo mismo que el de ayuda).");
		this.addCommandDescription(Language.ES, "VoteBan", "Inicia una votacion para banear a un usuario del servidor(temporalmente). *ESTE COMANDO REQUIERE SER ACTIVADO PREVIAMENTE POR UN ADMINISTRADOR DEL SERVIDOR.*");
		this.addCommandDescription(Language.ES, "Embed", "Devuelve el mensaje introducido por el usuario en forma de 'Embed' (mensaje decorado con un estilo especial de discord). para usar el caracter '-'(caracter de opciones) se debe anteponer \\ para reconocerlo como texto. Para generar enlaces ([como este](http://google.com)), escriba el título del enlace entre las etiquetas `{%link%}`, seguido de la url entre las etiquetas `{%href%}` (ejemplo:`{%link%}Google{%link%}{%href%}google.com{%href%}`). Los links solo funcionan en la descripción del embed y en las descripciones de los campos.");
		this.addCommandDescription(Language.ES, "Funar", "Funa a un usuario.");
		this.addCommandDescription(Language.ES, "Trollear", "Comando para trollear, no tiene más ciencia. Para especificar un objetivo, mencionalo al comienzo del comando, de lo contrario el trolleo te lo llevas tu:smirk:");
		this.addCommandDescription(Language.ES, "Gatos", "Devuelve la imagen(una o más) de un gato al azar. Utiliza la API de [random.cat](http://random.cat).");
		this.addCommandDescription(Language.ES, "Otaku", "Devuelve imagenes de esta temática, sin incluir contenido pornografico. Nombre provisorio.");
		this.addCommandDescription(Language.ES, "Reproducir", "Reproduce música obtenida desde una fuente de internet (por defecto YouTube.com)");
		this.addCommandDescription(Language.ES, "Pausar", "Pausa la reproducción actual.");
		this.addCommandDescription(Language.ES, "Siguiente", "Salta a la siguiente canción en la cola actual. Si no quedan canciones, la reproducción se termina.");
		this.addCommandDescription(Language.ES, "Detener", "Detiene la reproducción actual.");
		this.addCommandDescription(Language.ES, "Actual", "Indica la canción que se esta reproduciendo actualmente.");
		this.addCommandDescription(Language.ES, "Cola", "Muestra la lista de canciones en la cola.");
		this.addCommandDescription(Language.ES, "NSFW", "Como si no supieras que hace este comando cochin@ :wink::smirk:");
		this.addCommandDescription(Language.ES, "Xvideos", "Realiza la busqueda solicitada y devuelve una lista con los primeros resultados encontrados(Aún no soy capaz de reproducirlos, denme tiempo:pensive:).");
		this.addCommandDescription(Language.ES, "OtakuNSFW", "Es como el de NSFW clásico... Pero con monas chinas :wink::smirk:");
		this.addCommandDescription(Language.ES, "Rule34", "Internet esta lleno de reglas, esta es la más importante.");
		this.addCommandDescription(Language.ES, "Pokedex", "Busca y devuelve informacion relativa a un pokémon.");
		//this.addCommandDescription(Language.ES, null, null);
		/**/
		this.addCommandAliases(Language.ES, "Banear", "ban","pajuera");
		this.addCommandAliases(Language.ES, "LimpiarChat", "cls","limpiar","clear");
		this.addCommandAliases(Language.ES, "Configurar", "conf","config","configuracion");
		this.addCommandAliases(Language.ES, "YouTube", "yt","y","yutu","video","videos","llutu");
		this.addCommandAliases(Language.ES, "Invocar", "inv","llamar","ven");
		this.addCommandAliases(Language.ES, "Presentacion", "informacion","info","inf");
		this.addCommandAliases(Language.ES, "VoteBan", "vb","vban");
		this.addCommandAliases(Language.ES, "Embed", "emb");
		this.addCommandAliases(Language.ES, "Funar", "funa","fn");
		this.addCommandAliases(Language.ES, "Trollear", "troll","trolear","trl");
		this.addCommandAliases(Language.ES, "Gatos", "mew","mw","miau","gato");
		this.addCommandAliases(Language.ES, "Otaku", "oku","otk");
		this.addCommandAliases(Language.ES, "Reproducir", "p","r","play","pl");
		this.addCommandAliases(Language.ES, "Pausar", "pausa","pause","espera","pp");
		this.addCommandAliases(Language.ES, "Siguiente", "sig","saltar","skip","sk");
		this.addCommandAliases(Language.ES, "Detener", "stop","det","callate");
		this.addCommandAliases(Language.ES, "Actual", "act","sonando","reproduciendo","np");
		this.addCommandAliases(Language.ES, "Cola", "col","canciones","lista","fila");
		this.addCommandAliases(Language.ES, "NSFW", "ns","por","uff","porno","prn","nopor","cochinadas","18","+18","7u7");
		this.addCommandAliases(Language.ES, "Xvideos", "xv","xvid","xxxv","videosnopor");
		this.addCommandAliases(Language.ES, "OtakuNSFW", "hentai","otakuns","ons","o18","h18","otakuporn");
		this.addCommandAliases(Language.ES, "Rule34", "r34","34");
		this.addCommandAliases(Language.ES, "Pokedex", "pkmn","pkm","dex","pd","poke");
		//this.addCommandAliases(Language.ES, null, null);
	}
	private void prepararCategoriasEN() {
		this.addCategoryNameTranslation(Language.EN, "Administracion", "Administration");
		this.addCategoryNameTranslation(Language.EN, "Comun", "Common");
		this.addCategoryNameTranslation(Language.EN, "Imagen", "Images");
		this.addCategoryNameTranslation(Language.EN, "Musica", "Music");
		this.addCategoryNameTranslation(Language.EN, "VideoJuegos", "VideoGames");
		/**/
		
		this.addCategoryDescription(Language.EN, "Administracion", "Administration commands (administrator permissions are required, obviously).");
		this.addCategoryDescription(Language.EN, "Comun", "Useful commands with varied purposes.");
		this.addCategoryDescription(Language.EN, "Imagen", "Images...");
		this.addCategoryDescription(Language.EN, "Musica", "Music commands. What?. Were you waiting for another description?");
		this.addCategoryDescription(Language.EN, "NSFW", "Important commands that save our lives... Just kidding! This category is full of +18 content!");
		this.addCategoryDescription(Language.EN, "VideoJuegos", "Commands related to videogames and whatever.");
	}
	private void prepararComandosEN() {
		this.addCommandNameTranslation(Language.EN, "Banear", "Ban");
		this.addCommandNameTranslation(Language.EN, "LimpiarChat", "ClearChat");
		this.addCommandNameTranslation(Language.EN, "Configurar", "Settings");
		this.addCommandNameTranslation(Language.EN, "Invocar", "Invoke");
		this.addCommandNameTranslation(Language.EN, "Presentacion", "Presentation");
		this.addCommandNameTranslation(Language.EN, "Funar", "Wanted");
		this.addCommandNameTranslation(Language.EN, "Trollear", "Troll");
		this.addCommandNameTranslation(Language.EN, "Gatos", "Cats");
		this.addCommandNameTranslation(Language.EN, "Reproducir", "Play");
		this.addCommandNameTranslation(Language.EN, "Pausar", "Pause");
		this.addCommandNameTranslation(Language.EN, "Siguiente", "Skip");
		this.addCommandNameTranslation(Language.EN, "Detener", "Stop");
		this.addCommandNameTranslation(Language.EN, "Actual", "Playing");
		this.addCommandNameTranslation(Language.EN, "Cola", "Queue");
		/**/
		this.addCommandDescription(Language.EN, "Banear", "Allows to ban a member of the guildConfig for a determined time.");
		this.addCommandDescription(Language.EN, "LimpiarChat", "Delete messages sent in the actual text channel");
		this.addCommandDescription(Language.EN, "Configurar", "With this one you can configure me... you know, how I interact with the guildConfig, allowing/disabling command and somo other sh'ts. Use without parameters to return the actual settings.");
		this.addCommandDescription(Language.EN, "YouTube", "Search videos on YouTube, then shows the results (I can't play them yet, I need more time:pensive:).");
		this.addCommandDescription(Language.EN, "Invocar", "The weirdest & useless command. This makes me connect to the summoner´s actual voice channel.");
		this.addCommandDescription(Language.EN, "Presentacion", "Use this command to know more about me (this is not like the help command).");
		this.addCommandDescription(Language.EN, "VoteBan", "Starts a vote to ban a member from this guild(for a determined time). *THIS COMMAND MUST BE ACTIVATED BY A GUILD'S ADMINISTRATOR.*");
		this.addCommandDescription(Language.EN, "Embed", "Returns the sent message as an 'Embed' (message decorated with a special discord style). In order to use the character '-'(options character) you have to type '\\' before to recognize like part of the text. If you wanna make links ([like this](http://google.com)), write the title of the link between the tags `{%link%}`, then put the URL between the `{%href%}` tags (example:`{%link%}Google{%link%}{%href%}google.com{%href%}`). The links only works in the description of the embed and in the field's descriptions.");
		this.addCommandDescription(Language.EN, "Funar", "Returns a wanted poster for a member from this guild.");
		this.addCommandDescription(Language.EN, "Trollear", "Command to do some trolling stuff. To select a target(member), mention him/her at the start or you will recieve the trolling:smirk:");
		this.addCommandDescription(Language.EN, "Gatos", "Return one (or more) image(s) of a random cat. Uses the [random.cat](http://random.cat) API to work.");
		this.addCommandDescription(Language.EN, "Otaku", "Return images related to anime and whatever(p*rn not included). Temporary name.");
		this.addCommandDescription(Language.EN, "Reproducir", "Plays music from internet (default source: YouTube.com)");
		this.addCommandDescription(Language.EN, "Pausar", "Pauses the song if playing.");
		this.addCommandDescription(Language.EN, "Siguiente", "Skip the sont to the next in queue if this isn't empty.");
		this.addCommandDescription(Language.EN, "Detener", "Stops playing and cleans the queue.");
		this.addCommandDescription(Language.EN, "Actual", "Shows information of the actual song which is playing.");
		this.addCommandDescription(Language.EN, "Cola", "Shows the songs in the queue.");
		this.addCommandDescription(Language.EN, "NSFW", "You know what this command does. Don't ask and enjoy it!:wink::smirk:");
		this.addCommandDescription(Language.EN, "Xvideos", "It's like the 'YouTube' command, but this one is funnier(I can't play them yet, I need more time:pensive:).");
		this.addCommandDescription(Language.EN, "OtakuNSFW", "It's like the clasic NSFW... but for otakus:wink::smirk:");
		this.addCommandDescription(Language.EN, "Rule34", "The most important rule of internet. Don't forget that!.");
		this.addCommandDescription(Language.EN, "Pokedex", "Searchs information about a pokémon.");
		//this.addCommandDescription(Language.EN, null, null);
		/**/
		this.addCommandAliases(Language.EN, "Banear", "gofuk");
		this.addCommandAliases(Language.EN, "LimpiarChat", "cls","clear");
		this.addCommandAliases(Language.EN, "Configurar", "conf","config","configuration");
		this.addCommandAliases(Language.EN, "YouTube", "yt","y","yutu","video","videos");
		this.addCommandAliases(Language.EN, "Invocar", "inv","summon","cmon");
		this.addCommandAliases(Language.EN, "Presentacion", "information","info","inf");
		this.addCommandAliases(Language.EN, "VoteBan", "vb","vban");
		this.addCommandAliases(Language.EN, "Embed", "emb");
		this.addCommandAliases(Language.EN, "Funar", "want","wnt");
		this.addCommandAliases(Language.EN, "Trollear", "trolling","trl");
		this.addCommandAliases(Language.EN, "Gatos", "mew","mw","kittens","kttns");
		this.addCommandAliases(Language.EN, "Otaku", "oku","otk");
		this.addCommandAliases(Language.EN, "Reproducir", "p","play","pl");
		this.addCommandAliases(Language.EN, "Pausar", "pp");
		this.addCommandAliases(Language.EN, "Siguiente", "next","nxt","sk");
		this.addCommandAliases(Language.EN, "Detener", "sp","stp","shutup");
		this.addCommandAliases(Language.EN, "Actual", "np","nowp","nowplaying","pyng");
		this.addCommandAliases(Language.EN, "Cola", "que","q","songs");
		this.addCommandAliases(Language.EN, "NSFW", "porn","prn","18","+18","7u7");
		this.addCommandAliases(Language.EN, "Xvideos", "xv","xvid","xxxv","videosnopor");
		this.addCommandAliases(Language.EN, "OtakuNSFW", "hentai","otakuns","oprn","o18","h18","otakuporn");
		this.addCommandAliases(Language.EN, "Rule34", "r34","34");
		this.addCommandAliases(Language.EN, "Pokedex", "pkmn","pkm","dex","pd","poke");
		//this.addCommandAliases(Language.EN, null, null);
	}
	private void prepararTextosES() {
		this.addStringValue(Language.ES, "jax-info-titulo", "Wena l@s cabr@s del server!");
		this.addStringValue(Language.ES, "jax-info-desc", "Me presento: me llamo :sandwich:Jax Sandwich y como podrán observar, soy un sandwich "
				+"\nHora de ir directo al grano: Apenas estoy en desarrollo, así que no hago mucho, pero estoy seguro que disfrutarás mi contenido:wink:."
				+"\n*¡Soy un bot que hace varias cosas!*");
		this.addStringValue(Language.ES, "jax-info-f1-t", "¡Ah, lo olvidaba!");
		this.addStringValue(Language.ES, "jax-info-f1-d", "Para saber que verga puedo hacer, escribe '%sayuda'");
		this.addStringValue(Language.ES, "jax-info-f2", ">>> VERSION: %s\nPara más información acerca de este bot, "
				+"visita: sitio web aún no disponible. Te me esperas.");
		this.addStringValue(Language.ES, "jax-info-footer", "DISCLAIMER: No soy dueño de ninguna de las marcas ni de los recursos gráficos "
				+"provistos por este bot. Todo ese contenido le pertenece a las fuentes originales donde fueron obtenidas. "
				+"Este bot es solo para entretenimiento y no lucra con su contenido.");
		this.addStringValue(Language.ES, "jax-no-cont", "No se encontró contenido (#PichulaTriste), intentalo nuevamente");
		this.addStringValue(Language.ES, "jax-no-nsfw-ft", "¡Deja esa cosa horrorosa o verás!....");
		this.addStringValue(Language.ES, "jax-no-nsfw-fd", "Este canal no permite este tipo de contenido :smirk:");
		this.addStringValue(Language.ES, "jax-no-nsfw-ftr", "Busca un canal con la etiqueta \"NSFW\" y yo mismo te quito la ropa 🍑🍆😈👉👌😏 (es chiste ni te creas)");
		this.addStringValue(Language.ES, "jax-val-incorrecto", "Valor inválido.");
		this.addStringValue(Language.ES, "jax-val-incorrecto-cont", ". intentelo otra vez (reintentos: %s)");
		this.addStringValue(Language.ES, "jax-send-pedir-msg", "Escribe el mensage a enviar");
		this.addStringValue(Language.ES, "jax-texto-servidor", "Servidor *'%s'*");
		this.addStringValue(Language.ES, "jax-seleccione-canal", "Seleccione canal");
		this.addStringValue(Language.ES, "jax-yt-resultado-busqueda", "Resultados de la busqueda:");
		this.addStringValue(Language.ES, "jax-yt-no-resultados", "No se encontraron resultados:pensive:");
		this.addStringValue(Language.ES, "jax-yt-ingresar-busqueda", "Debe ingresar una busqueda.");
		this.addStringValue(Language.ES, "jax-i-audio-requerido", "primero te tienes que meter a un canal de voz para invocarme, no sea gil manit@");
		this.addStringValue(Language.ES, "jax-i-voy", "voy");
		this.addStringValue(Language.ES, "jax-musica-esperaturno", "sorry pero espera tu turno, o puedes venir y pedirme musica (y otras cosas:smirk:)");
		this.addStringValue(Language.ES, "jax-musica-no-mismocanal", "No estoy en tu canal de voz");
		this.addStringValue(Language.ES, "jax-musica-reproduciendo", "Reproduciendo");
		this.addStringValue(Language.ES, "jax-musica-pausado", "Cancion pausada.");
		this.addStringValue(Language.ES, "jax-musica-no-reproduciendo", "No se esta reproduciendo ninguna canción.");
		this.addStringValue(Language.ES, "jax-musica-saltando", "Saltando...");
		this.addStringValue(Language.ES, "jax-musica-rep-ahora", "Reproduciendo: %s");
		this.addStringValue(Language.ES, "jax-conf-inf-titulo", "Configuración actual del servidor: %s");
		this.addStringValue(Language.ES, "jax-conf-inf-desc", "");
		this.addStringValue(Language.ES, "jax-conf-inf-pref-t", "Prefijo comandos:");
		this.addStringValue(Language.ES, "jax-conf-inf-opt-pref-t", "Prefijo opciones:");
		this.addStringValue(Language.ES, "jax-conf-inf-pref-n", "Por defecto.");
		this.addStringValue(Language.ES, "jax-conf-inf-alwd-cmd", "Comandos bloqueados:");
		this.addStringValue(Language.ES, "jax-conf-inf-alwd-cat", "Categorias bloqueadas:");
		this.addStringValue(Language.ES, "jax-conf-inf-alwd-rol", "Roles bloqueados:");
		this.addStringValue(Language.ES, "jax-conf-inf-alwd-chn", "Canales bloqueados:");
		this.addStringValue(Language.ES, "jax-conf-inf-alwd-mbr", "Miembros bloqueados:");
		this.addStringValue(Language.ES, "jax-conf-inf-n-alwd-cmd", "Comandos permitidos:");
		this.addStringValue(Language.ES, "jax-conf-inf-n-alwd-cat", "Categorias permitidas:");
		this.addStringValue(Language.ES, "jax-conf-inf-n-alwd-rol", "Roles permitidos:");
		this.addStringValue(Language.ES, "jax-conf-inf-n-alwd-chn", "Canales permitidos:");
		this.addStringValue(Language.ES, "jax-conf-inf-n-alwd-mbr", "Miembros permitidos:");
		this.addStringValue(Language.ES, "jax-conf-inf-alwd-v", "Sin registrar.");
		this.addStringValue(Language.ES, "jax-conf-inf-lang-t", "Idioma:");
		this.addStringValue(Language.ES, "jax-conf-inf-lang-d", "Español [%s]");
		this.addStringValue(Language.ES, "jax-conf-no-admin", "Se requieren permisos de administrador para usar este comando.");
		this.addStringValue(Language.ES, "jax-conf-act-conf", "Configuración del servidor actualizada.");
		this.addStringValue(Language.ES, "jax-conf-pref-no-sp", "El prefijo no puede contener espacios.");
		this.addStringValue(Language.ES, "jax-conf-ing-car-nc", "Debe ingresar al menos un caracter(que no sea de control).");
		this.addStringValue(Language.ES, "jax-conf-opt-pref-no-sp", "El prefijo de opciones no puede contener espacios.");
		this.addStringValue(Language.ES, "jax-conf-ing-objetivo", "Debe ingresar al menos un objetivo.");
		this.addStringValue(Language.ES, "jax-conf-?-titulo", "Uso de parámetros/opciones de configuración");
		this.addStringValue(Language.ES, "jax-conf-?-f1-t", "> Prefijo <nuevo prefijo>{1,7}");
		this.addStringValue(Language.ES, "jax-conf-?-f1-d", "```\nConsulte la configuración para saber cual es el prefijo actual.```");
		this.addStringValue(Language.ES, "jax-conf-?-f2-t", "> Opcion <nuevo prefijo opcion>{1,7}");
		this.addStringValue(Language.ES, "jax-conf-?-f2-d", "```\nConsulte la configuración para saber cual es el prefijo de opción actual.```");
		this.addStringValue(Language.ES, "jax-conf-?-f3-t", "> Idioma <ES|EN>");
		this.addStringValue(Language.ES, "jax-conf-?-f3-d", "```\nConsulte la configuración para saber cual es el idioma actual.```");
		this.addStringValue(Language.ES, "jax-conf-?-f4-t", "> Bloquear <comando|categoría*|@rol|@canal|@miembro> [,+]");
		this.addStringValue(Language.ES, "jax-conf-?-f4-d", "```Bloquea al objetivo en este servidor."
				+ "\nSi el objetivo es un comando/categoría/miembro/rol este no podrá usar comandos en este servidor."
				+ "\nSi el objetivo es un canal en este no se podrán usar comandos."
				+ "\nConsulte la configuracion para saber cuales estan bloqueados.```");
		this.addStringValue(Language.ES, "jax-conf-?-f5-t", "> Desbloquear <comando|categoría*|@rol|@canal|@miembro> [,+]");
		this.addStringValue(Language.ES, "jax-conf-?-f5-d", "``` desbloquea al objetivo en este servidor."
				+ "\nSi el objetivo es un comando/categoría/miembro/rol este podrá usar comandos en este servidor."
				+ "\nSi el objetivo es un canal en este se podrán usar comandos."
				+ "\nConsulte la configuracion para saber cuales estan desbloqueados.```");
		this.addStringValue(Language.ES, "jax-conf-?-footer", "* 'categoría' hace referencia a una categoría de comandos y no a una categoría en discord, estas son tratadas como canales."
				+ "\nEl simbolo '{n,m}' indica los limites de caracteres del parametro, donde 'n' es el minimo y 'm' el máximo."
				+ "\nEl texto entre el caracter '<' y '>' hacen referencia a un parametro obligatorio."
				+ "\nEl caracter '|' significa que puede ser una opción o la otra."
				+ "\nEl caracter '@' siginifica que corresponde a una mención."
				+ "\nEl simbolo '[,+]' significa que opcionalmente se pueden agregar mas parametros separados por comas.'");
		this.addStringValue(Language.ES, "jax-conf-conf-no-aplicada", "No puedo aplicar la configuración en este momento:pensive:");
		this.addStringValue(Language.ES, "jax-musica-cola-vacia", "La cola esta vacía.");
		this.addStringValue(Language.ES, "jax-musica-cola-elementos-titulo", "Elementos en cola:");
		this.addStringValue(Language.ES, "jax-musica-cola-sig-estimado", "Siguiente (reproduciendo en %s):");
		this.addStringValue(Language.ES, "jax-musica-cola-durrep-estimado", "Duracion del tiempo de reproducción estimado: %s");
		this.addStringValue(Language.ES, "jax-nsfw-img-footer", "Tranquil@ cochin@, no diré quien eres ");
		this.addStringValue(Language.ES, "jax-generico-txt-opcion-s", "[Opción %s]");
		this.addStringValue(Language.ES, "jax-nsfw-otk-fuente-nf", "Fuente '%s' no encontrada. Voy a usar la fuente por defecto(http://Konachan.com)");
		this.addStringValue(Language.ES, "jax-lol-championsData-ranked", "Clasificatorias\nTotal");
		this.addStringValue(Language.ES, "jax-lol-championsData-soloqueue", "Clasificatorias Solo/Dúo");
		this.addStringValue(Language.ES, "jax-lol-championsData-flex", "Clasificatorias Flexibles");
		this.addStringValue(Language.ES, "jax-lol-championsData-all-queues", "Todas las colas\nTotal");
		this.addStringValue(Language.ES, "jax-lol-no-invocador", "Debe ingresar un nombre de invocador.");
		this.addStringValue(Language.ES, "jax-gen-txt-region-invalida", "Región inválida.");
		this.addStringValue(Language.ES, "jax-lol-info-invocador-titulo", "Estadísticas de invocador: %s");
		this.addStringValue(Language.ES, "jax-lol-invocador-division", "**División: %s**");
		this.addStringValue(Language.ES, "jax-lol-invocador-win-loss", "Victorias: %s | Derrotas: %s");
		this.addStringValue(Language.ES, "jax-lol-estadisticas-partidas-t", "__**Estadísticas de partidas %s**__\n");
	}
	private void prepararTextosEN() {
		
	}
}
