package xyz.sandwichbot.configuracion;

import xyz.sandwichframework.annotations.text.*;
import xyz.sandwichframework.core.util.Language;

@Values(Language.ES)
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
	public static final String XC_VAL_INC = "Valor incorrecto.";
	
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

}
