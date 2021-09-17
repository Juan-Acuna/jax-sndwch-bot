package com.jaxsandwich.discordbot.configuracion;

import com.jaxsandwich.sandwichcord.annotations.text.*;
import com.jaxsandwich.sandwichcord.core.util.Language;

@ValuesContainer(Language.ES)
public class Textos_es {

	@ValueID("jax-info-titulo")
	public static final String INFO_TITULO = "Wena l@s cabr@s del server!";

	@ValueID("jax-info-desc")
	public static final String INFO_DESC = "Me presento: me llamo :sandwich:Jax Sandwich y como podrán observar, soy un sandwich "
			+"\nHora de ir directo al grano: Apenas estoy en desarrollo, así que no hago mucho, pero estoy seguro que disfrutarás mi contenido:wink:."
			+"\n*¡Soy un bot que hace varias cosas!*";

	@ValueID("jax-info-f1-t")
	public static final String INFO_F11 = "¡Ah, lo olvidaba!";

	@ValueID("jax-info-f1-d")
	public static final String INFO_F12 = "Para saber que verga puedo hacer, escribe '%sayuda'";

	@ValueID("jax-info-f2")
	public static final String INFO_F2 = ">>> VERSION: 0.1.4\nPara más información acerca de este bot, "
			+"visita: sitio web aún no disponible. Te me esperas.";

	@ValueID("jax-info-footer")
	public static final String INFO_FOOTER = "DISCLAIMER: No soy dueño de ninguna de las marcas ni de los recursos gráficos "
			+"provistos por este bot. Todo ese contenido le pertenece a las fuentes originales donde fueron obtenidas. "
			+"Este bot es solo para entretenimiento y no lucra con su contenido.";

	@ValueID("jax-no-cont")
	public static final String IMG_NO_CONT = "No se encontró contenido (#PichulaTriste), intentalo nuevamente";

	@ValueID("jax-no-nsfw-ft")
	public static final String IMG_NO_NSFW_FT = "¡Deja esa cosa horrorosa o verás!....";

	@ValueID("jax-no-nsfw-fd")
	public static final String IMG_NO_NSFW_FD = "Este canal no permite este tipo de contenido :smirk:";

	@ValueID("jax-no-nsfw-ftr")
	public static final String IMG_NO_NSFW_F = "Busca un canal con la etiqueta \"NSFW\" y yo mismo te quito la ropa 🍑🍆😈👉👌😏 (es chiste ni te creas)";

	@ValueID("jax-val-incorrecto")
	public static final String XC_VAL_INC = "Valor inválido.";
	
	@ValueID("jax-val-incorrecto-cont")
	public static final String XC_VAL_INC_C = ". intentelo otra vez (reintentos: %s)";

	@ValueID("jax-send-pedir-msg")
	public static final String XC_PED_MEN = "Escribe el mensage a enviar";
	
	@ValueID("jax-texto-servidor")
	public static final String SERV = "Servidor *'%s'*";

	@ValueID("jax-seleccione-canal")
	public static final String SEL_CAN = "Seleccione canal";

	@ValueID("jax-yt-resultado-busqueda")
	public static final String C_C_Y = "Resultados de la busqueda:";

	@ValueID("jax-yt-no-resultados")
	public static final String C_C_Y_2 = "No se encontraron resultados:pensive:";

	@ValueID("jax-yt-ingresar-busqueda")
	public static final String C_C_Y_3 = "Debe ingresar una busqueda.";

	@ValueID("jax-i-audio-requerido")
	public static final String C_C_I = "primero te tienes que meter a un canal de voz para invocarme, no sea gil manit@";

	@ValueID("jax-i-voy")
	public static final String C_C_I_2 = "voy";

	@ValueID("jax-musica-esperaturno")
	public static final String C_C_M_ = "sorry pero espera tu turno, o puedes venir y pedirme musica (y otras cosas:smirk:)";

	@ValueID("jax-musica-no-mismocanal")
	public static final String C_C_M2 = "No estoy en tu canal de voz";

	@ValueID("jax-musica-reproduciendo")
	public static final String C_C_M_R = "Reproduciendo";

	@ValueID("jax-musica-pausado")
	public static final String C_C_M_R2 = "Cancion pausada.";

	@ValueID("jax-musica-no-reproduciendo")
	public static final String C_C_M_R3 = "No se esta reproduciendo ninguna canción.";

	@ValueID("jax-musica-saltando")
	public static final String C_C_M_R4 = "Saltando...";

	@ValueID("jax-musica-rep-ahora")
	public static final String C_C_M_R5 = "Reproduciendo: %s";

	@ValueID("jax-conf-inf-titulo")
	public static final String CAC = "Configuración actual del servidor: %s";

	@ValueID("jax-conf-inf-desc")
	public static final String CaCg = "";

	@ValueID("jax-conf-inf-pref-t")
	public static final String CACg2 = "Prefijo comandos:";

	@ValueID("jax-conf-inf-opt-pref-t")
	public static final String CACg3 = "Prefijo opciones:";

	@ValueID("jax-conf-inf-pref-n")
	public static final String CACg2b = "Por defecto.";

	@ValueID("jax-conf-inf-alwd-cmd")
	public static final String CACa = "Comandos bloqueados:";

	@ValueID("jax-conf-inf-alwd-cat")
	public static final String CACa2 = "Categorias bloqueadas:";

	@ValueID("jax-conf-inf-alwd-rol")
	public static final String CACa3 = "Roles bloqueados:";

	@ValueID("jax-conf-inf-alwd-chn")
	public static final String CACa4 = "Canales bloqueados:";

	@ValueID("jax-conf-inf-alwd-mbr")
	public static final String CACa5 = "Miembros bloqueados:";

	@ValueID("jax-conf-inf-n-alwd-cmd")
	public static final String CACan = "Comandos permitidos:";

	@ValueID("jax-conf-inf-n-alwd-cat")
	public static final String CACan2 = "Categorias permitidas:";

	@ValueID("jax-conf-inf-n-alwd-rol")
	public static final String CACan3 = "Roles permitidos:";

	@ValueID("jax-conf-inf-n-alwd-chn")
	public static final String CACan4 = "Canales permitidos:";

	@ValueID("jax-conf-inf-n-alwd-mbr")
	public static final String CACan5 = "Miembros permitidos:";

	@ValueID("jax-conf-inf-alwd-v")
	public static final String CACa_ = "Sin registrar.";

	@ValueID("jax-conf-inf-lang-t")
	public static final String CACl_t = "Idioma:";

	@ValueID("jax-conf-inf-lang-d")
	public static final String CACl_d = "Español [%s]";

	@ValueID("jax-conf-no-admin")
	public static final String CAna = "Se requieren permisos de administrador para usar este comando.";

	@ValueID("jax-conf-act-conf")
	public static final String CCACT = "Configuración del servidor actualizada.";

	@ValueID("jax-conf-pref-no-sp")
	public static final String C0 = "El prefijo no puede contener espacios.";

	@ValueID("jax-conf-ing-car-nc")
	public static final String C1 = "Debe ingresar al menos un caracter(que no sea de control).";

	@ValueID("jax-conf-opt-pref-no-sp")
	public static final String C2 = "El prefijo de opciones no puede contener espacios.";

	@ValueID("jax-conf-ing-objetivo")
	public static final String C3 = "Debe ingresar al menos un objetivo.";

	@ValueID("jax-conf-?-titulo")
	public static final String C4 = "Uso de parámetros/opciones de configuración";

	@ValueID("jax-conf-?-f1-t")
	public static final String C5 = "> Prefijo <nuevo prefijo>{1,7}";

	@ValueID("jax-conf-?-f1-d")
	public static final String C5d = "```\nConsulte la configuración para saber cual es el prefijo actual.```";

	@ValueID("jax-conf-?-f2-t")
	public static final String C6 = "> Opcion <nuevo prefijo opcion>{1,7}";

	@ValueID("jax-conf-?-f2-d")
	public static final String C6d = "```\nConsulte la configuración para saber cual es el prefijo de opción actual.```";

	@ValueID("jax-conf-?-f3-t")
	public static final String C7 = "> Idioma <ES|EN>";

	@ValueID("jax-conf-?-f3-d")
	public static final String C7d = "```\nConsulte la configuración para saber cual es el idioma actual.```";

	@ValueID("jax-conf-?-f4-t")
	public static final String C8 = "> Bloquear <comando|categoría*|@rol|@canal|@miembro> [,+]";

	@ValueID("jax-conf-?-f4-d")
	public static final String C8d = "```Bloquea al objetivo en este servidor."
			+ "\nSi el objetivo es un comando/categoría/miembro/rol este no podrá usar comandos en este servidor."
			+ "\nSi el objetivo es un canal en este no se podrán usar comandos."
			+ "\nConsulte la configuracion para saber cuales estan bloqueados.```";

	@ValueID("jax-conf-?-f5-t")
	public static final String C9 = "> Desbloquear <comando|categoría*|@rol|@canal|@miembro> [,+]";

	@ValueID("jax-conf-?-f5-d")
	public static final String C9d = "``` desbloquea al objetivo en este servidor."
			+ "\nSi el objetivo es un comando/categoría/miembro/rol este podrá usar comandos en este servidor."
			+ "\nSi el objetivo es un canal en este se podrán usar comandos."
			+ "\nConsulte la configuracion para saber cuales estan desbloqueados.```";

	@ValueID("jax-conf-?-footer")
	public static final String C10 = "* 'categoría' hace referencia a una categoría de comandos y no a una categoría en discord, estas son tratadas como canales."
			+ "\nEl simbolo '{n,m}' indica los limites de caracteres del parametro, donde 'n' es el minimo y 'm' el máximo."
			+ "\nEl texto entre el caracter '<' y '>' hacen referencia a un parametro obligatorio."
			+ "\nEl caracter '|' significa que puede ser una opción o la otra."
			+ "\nEl caracter '@' siginifica que corresponde a una mención."
			+ "\nEl simbolo '[,+]' significa que opcionalmente se pueden agregar mas parametros separados por comas.'";

	@ValueID("jax-conf-conf-no-aplicada")
	public static final String C11 = "No puedo aplicar la configuración en este momento:pensive:";

	@ValueID("jax-musica-cola-vacia")
	public static final String C12 = "La cola esta vacía.";

	@ValueID("jax-musica-cola-elementos-titulo")
	public static final String C13 = "Elementos en cola:";

	@ValueID("jax-musica-cola-sig-estimado")
	public static final String C14 = "Siguiente (reproduciendo en %s):";

	@ValueID("jax-musica-cola-durrep-estimado")
	public static final String C15 = "Duracion del tiempo de reproducción estimado: %s";

	@ValueID("jax-nsfw-img-footer")
	public static final String C16 = "Tranquil@ cochin@, no diré quien eres ";

	@ValueID("jax-generico-txt-opcion-s")
	public static final String C17 = "[Opción %s]";

	@ValueID("jax-nsfw-otk-fuente-nf")
	public static final String C18 = "Fuente '%s' no encontrada. Voy a usar la fuente por defecto(http://Konachan.com)";

	@ValueID("jax-lol-championsData-ranked")
	public static final String C19 = "Clasificatorias\nTotal";

	@ValueID("jax-lol-championsData-soloqueue")
	public static final String C20 = "Clasificatorias Solo/Dúo";

	@ValueID("jax-lol-championsData-flex")
	public static final String C22 = "Clasificatorias Flexibles";

	@ValueID("jax-lol-championsData-all-queues")
	public static final String C23 = "Todas las colas\nTotal";

	@ValueID("jax-lol-no-invocador")
	public static final String C24 = "Debe ingresar un nombre de invocador.";

	@ValueID("jax-gen-txt-region-invalida")
	public static final String C25 = "Región inválida.";

	@ValueID("jax-lol-info-invocador-titulo")
	public static final String C26 = "Estadísticas de invocador: %s";

	@ValueID("jax-lol-invocador-division")
	public static final String C27 = "**División: %s**";

	@ValueID("jax-lol-invocador-win-loss")
	public static final String C28 = "Victorias: %s | Derrotas: %s";

	@ValueID("jax-lol-estadisticas-partidas-t")
	public static final String C29 = "__**Estadísticas de partidas %s**__\n";

	//@ValueID("jax-")
	//public static final String C30 = "";

	//@ValueID("jax-")
	//public static final String C31 = "";

	
}
