package com.jaxsandwich.discordbot.configuracion;

import com.jaxsandwich.framework.annotations.text.*;
import com.jaxsandwich.framework.core.util.Language;

@ValuesContainer(Language.EN)
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
	public static final String SERV = "Servidor *'%s'*";

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

	@ValueID("jax-conf-inf-titulo")
	public static final String CAC = "Actual settings for guildConfig: %s";

	@ValueID("jax-conf-inf-desc")
	public static final String CaCg = "";

	@ValueID("jax-conf-inf-pref-t")
	public static final String CACg2 = "Command prefix:";

	@ValueID("jax-conf-inf-opt-pref-t")
	public static final String CACg3 = "Options prefix:";

	@ValueID("jax-conf-inf-pref-n")
	public static final String CACg2b = "Default.";

	@ValueID("jax-conf-inf-alwd-cmd")
	public static final String CACa = "Denied commands:";

	@ValueID("jax-conf-inf-alwd-cat")
	public static final String CACa2 = "Denied categories:";

	@ValueID("jax-conf-inf-alwd-rol")
	public static final String CACa3 = "Denied roles:";

	@ValueID("jax-conf-inf-alwd-chn")
	public static final String CACa4 = "Denied channels:";

	@ValueID("jax-conf-inf-alwd-mbr")
	public static final String CACa5 = "Denied members:";

	@ValueID("jax-conf-inf-n-alwd-cmd")
	public static final String CACan = "Allowed commands:";

	@ValueID("jax-conf-inf-n-alwd-cat")
	public static final String CACan2 = "Allowed categories:";

	@ValueID("jax-conf-inf-n-alwd-rol")
	public static final String CACan3 = "Allowed roles:";

	@ValueID("jax-conf-inf-n-alwd-chn")
	public static final String CACan4 = "Allowed channels:";

	@ValueID("jax-conf-inf-n-alwd-mbr")
	public static final String CACan5 = "Allowed members:";

	@ValueID("jax-conf-inf-alwd-v")
	public static final String CACa_ = "Not set.";

	@ValueID("jax-conf-inf-lang-t")
	public static final String CACl_t = "Language:";

	@ValueID("jax-conf-inf-lang-d")
	public static final String CACl_d = "English [%s]";

	@ValueID("jax-conf-no-admin")
	public static final String CAna = "Administrator permission is required to use this command.";

	@ValueID("jax-conf-act-conf")
	public static final String CCACT = "Settings updated!";

	@ValueID("jax-conf-pref-no-sp")
	public static final String C0 = "Prefix can't be empty.";

	@ValueID("jax-conf-ing-car-nc")
	public static final String C1 = "You have to type at least one character(no control character).";

	@ValueID("jax-conf-opt-pref-no-sp")
	public static final String C2 = "Options prefix can't be empty!";

	@ValueID("jax-conf-ing-objetivo")
	public static final String C3 = "You have to enter a target.";

	@ValueID("jax-conf-?-titulo")
	public static final String C4 = "How to use the options/parameters in settings.";

	@ValueID("jax-conf-?-f1-t")
	public static final String C5 = "> Prefix <new prefix>{1,7}";

	@ValueID("jax-conf-?-f1-d")
	public static final String C5d = "```\nSee the settings to know the actual prefix.```";

	@ValueID("jax-conf-?-f2-t")
	public static final String C6 = "> Option <new options prefix>{1,7}";

	@ValueID("jax-conf-?-f2-d")
	public static final String C6d = "```\nSee the settings to know the actual options prefix.```";

	@ValueID("jax-conf-?-f3-t")
	public static final String C7 = "> Language <ES|EN>";

	@ValueID("jax-conf-?-f3-d")
	public static final String C7d = "```\nSee the settings to know the actual language.```";

	@ValueID("jax-conf-?-f4-t")
	public static final String C8 = "> Deny <command|category*|@role|@channel|@member>[,+]";

	@ValueID("jax-conf-?-f4-d")
	public static final String C8d = "```If the target is a command|category|role|member this one can't use commands in this guildConfig."
			+ "\nIf the target is a channel, deny everybody to use commands in this channel."
			+ "\nSee the settings to know which are allowed/denied.```";

	@ValueID("jax-conf-?-f5-t")
	public static final String C9 = "> Allow <command|category*|@role|@channel|@member>[,+]";

	@ValueID("jax-conf-?-f5-d")
	public static final String C9d = "```If the target is a command|category|role|member this one can use commands in this guildConfig."
			+ "\nIf the target is a channel, allows to everybody to use commands in this channel."
			+ "\nSee the settings to know which are allowed/denied.```";

	@ValueID("jax-conf-?-footer")
	public static final String C10 = "* 'category' means a command category, not Discord category. Discord categories are treated as channels."
			+ "\nThe text '{n,m}' indicates the limits of characters length, where'n' is for min & 'm' is for max."
			+ "\nThe text between '<' & '>' is a required parameter."
			+ "\nThe character '|' means \"one option or the other\"."
			+ "\nThe character '@' means that a mention is required."
			+ "\nThe text '[,+]' means that you can (optionally) more parameters comma separated.'";

	@ValueID("jax-conf-conf-no-aplicada")
	public static final String C11 = "I can't apply the changes in this moment:pensive:";

	@ValueID("jax-musica-cola-vacia")
	public static final String C12 = "The queue is empty.";

	@ValueID("jax-musica-cola-elementos-titulo")
	public static final String C13 = "Elements in queue:";

	@ValueID("jax-musica-cola-sig-estimado")
	public static final String C14 = "Next (playing in %s):";

	@ValueID("jax-musica-cola-durrep-estimado")
	public static final String C15 = "Estimated playing duration: %s";

	@ValueID("jax-nsfw-img-footer")
	public static final String C16 = "Don't worry, I won't say who you are ";

	@ValueID("jax-generico-txt-opcion-s")
	public static final String C17 = "[Option %s]";

	@ValueID("jax-nsfw-otk-fuente-nf")
	public static final String C18 = "Source '%s' not found. I'll use the default source (http://Konachan.com)";

	@ValueID("jax-lol-championsData-ranked")
	public static final String C19 = "Ranked";

	@ValueID("jax-lol-championsData-soloqueue")
	public static final String C20 = "Soloqueue";

	@ValueID("jax-lol-championsData-flex")
	public static final String C22 = "Flex";

	@ValueID("jax-lol-championsData-all-queues")
	public static final String C23 = "All queues";

	@ValueID("jax-lol-no-invocador")
	public static final String C24 = "You have to enter a summoner name.";

	@ValueID("jax-gen-txt-region-invalida")
	public static final String C25 = "Invalid region.";

	@ValueID("jax-lol-info-invocador-titulo")
	public static final String C26 = "Summoner's stats: %s";

	@ValueID("jax-lol-invocador-division")
	public static final String C27 = "**Tier: %s**";

	@ValueID("jax-lol-invocador-win-loss")
	public static final String C28 = "Wins: %s | Losses: %s";

	@ValueID("jax-lol-estadisticas-partidas-t")
	public static final String C29 = "__**%s queues stats**__\n";

	//@ValueID("jax-")
	//public static final String C30 = "";

	//@ValueID("jax-")
	//public static final String C31 = "";
	
}
