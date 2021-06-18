package xyz.sandwichbot.configuracion;

import xyz.sandwichframework.annotations.text.*;
import xyz.sandwichframework.core.util.Language;

@Values(Language.ES)
public class Textos_en {

	@ValueID("jax-info-titulo")
	public static final String INFO_TITULO = "Hello everybody!";

	@ValueID("jax-info-desc")
	public static final String INFO_DESC = "My name is :sandwich:Jax Sandwich and as you can see, I'm a sandwich "
			+"\nRight to the point: I'm barely in development, so I can't do too much, but I hope you like my content:wink:."
			+"\n*¡I'm a multipurpose bot!*";

	@ValueID("jax-info-f1-t")
	public static final String INFO_F11 = "¡There's something else!";

	@ValueID("jax-info-f1-d")
	public static final String INFO_F12 = "To know what the sh't I can do, type '%shelp'";

	@ValueID("jax-info-f2")
	public static final String INFO_F2 = ">>> VERSION: 0.1.4";

	@ValueID("jax-info-footer")
	public static final String INFO_FOOTER = "DISCLAIMER: I'm not the owner of any trademark or graphics resources "
			+"provided by this bot. All the contents own to the original source where the bot got them. "
			+"This bot is just for entertainment and doesn't make profits with the content.";

	@ValueID("jax-no-cont")
	public static final String IMG_NO_CONT = "No content found, try again.";

	@ValueID("jax-no-nsfw-ft")
	public static final String IMG_NO_NSFW_FT = "¡I got you!";

	@ValueID("jax-no-nsfw-fd")
	public static final String IMG_NO_NSFW_FD = "This channel doesn't allow this kind of content :smirk:";

	@ValueID("jax-no-nsfw-ftr")
	public static final String IMG_NO_NSFW_F = "Look for a channel with the \"NSFW\" tag and I'll show you what you want 🍑🍆😈👉👌😏";

	@ValueID("jax-val-incorrecto")
	public static final String XC_VAL_INC = "Invalid value.";
	
	@ValueID("jax-val-incorrecto-cont")
	public static final String XC_VAL_INC_C = ". Try again (tries: %s)";

	@ValueID("jax-send-pedir-msg")
	public static final String XC_PED_MEN = "Enter the message";
	
	@ValueID("jax-texto-servidor")
	public static final String SERV = "Guild *'%s'*";

	@ValueID("jax-seleccione-canal")
	public static final String SEL_CAN = "Select channel";

	@ValueID("jax-yt-resultado-busqueda")
	public static final String C_C_Y = "Search results:";

	@ValueID("jax-yt-no-resultados")
	public static final String C_C_Y_2 = "No results:pensive:";

	@ValueID("jax-yt-ingresar-busqueda")
	public static final String C_C_Y_3 = "You must enter a search.";

	@ValueID("jax-i-audio-requerido")
	public static final String C_C_I = "first you have to join a voice channel to call me, don't waste my time.";

	@ValueID("jax-i-voy")
	public static final String C_C_I_2 = "wait";

	@ValueID("jax-musica-esperaturno")
	public static final String C_C_M_ = "sorry, wait for your turn, or come and ask me for music (and other things:smirk:)";

	@ValueID("jax-musica-no-mismocanal")
	public static final String C_C_M2 = "I'm not in your vice channel.";

	@ValueID("jax-musica-reproduciendo")
	public static final String C_C_M_R = "Playing";

	@ValueID("jax-musica-pausado")
	public static final String C_C_M_R2 = "Song paused.";

	@ValueID("jax-musica-no-reproduciendo")
	public static final String C_C_M_R3 = "I'm not playing any song.";

	@ValueID("jax-musica-saltando")
	public static final String C_C_M_R4 = "Skipping...";

	@ValueID("jax-musica-rep-ahora")
	public static final String C_C_M_R5 = "Playing: %s";
	
}
