package com.jaxsandwich.discordbot.configuracion;

import com.jaxsandwich.framework.annotations.Configuration;
import com.jaxsandwich.framework.annotations.configure.*;
import com.jaxsandwich.framework.core.util.Language;

@Configuration(Language.EN)
public class ConfigComandos_en {
	
	/*    CATEGORIA : ADMINISTRACION    */
	@CategoryID("Administracion")
	@TranslatedName("Administration")
	@CategoryDescription
	public static final String ADMIN_DESC = "Administration commands (administrator permissions are required, obviously).";
	
	@CommandID("Banear")
	@TranslatedName("Ban")
	@CommandDescription
	public static final String BANEAR_DESC = "Allows to ban a member of the guildConfig for a determined time.";
	
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
	public static final String CONF_DESC = "With this one you can configure me... you know, how I interact with the guildConfig, allowing/disabling command and somo other sh'ts. Use without parameters to return the actual settings.";
	
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
	@TranslatedName("Invoke")
	@CommandDescription
	public static final String INVOCAR_DESC = "The weirdest & useless command. This makes me connect to the summoner´s actual voice channel.";
	
	@CommandID("Invocar")
	@CommandAliases
	public static final String[] INVOCAR_ALIAS = {"inv","summon","cmon"};
	
	@CommandID("Presentacion")
	@TranslatedName("Presentation")
	@CommandDescription
	public static final String PRESENTACION_DESC = "Use this command to know more about me (this is not like the help command).";
	
	@CommandID("Presentacion")
	@CommandAliases
	public static final String[] PRESENTACION_ALIAS = {"information","info","inf"};
	
	@CommandID("VoteBan")
	@CommandDescription
	public static final String VOTEBAN_DESC = "Starts a vote to ban a member from this guildConfig(for a determined time). *THIS COMMAND MUST BE ACTIVATED BY A GUILD'S ADMINISTRATOR.*";
	
	@CommandID("VoteBan")
	@CommandAliases
	public static final String[] VOTEBAN_ALIAS = {"vb","vban"};
	
	@CommandID("Embed")
	@CommandDescription
	public static final String EMBED_DESC = "Returns the sent message as an 'Embed' (message decorated with a special discord style). In order to use the character '-'(options character) you have to type '\\' before to recognize like part of the text. If you wanna make links ([like this](http://google.com)), write the title of the link between the tags `{%link%}`, then put the URL between the `{%href%}` tags (example:`{%link%}Google{%link%}{%href%}google.com{%href%}`). The links only works in the description of the embed and in the field's descriptions.";
	
	@CommandID("Embed")
	@CommandAliases
	public static final String[] EMBED_ALIAS = {"emb"};
	
	@CommandID("Funar")
	@TranslatedName("Wanted")
	@CommandDescription
	public static final String FUNAR_DESC = "Returns a wanted poster for a member from this guildConfig.";
	
	@CommandID("Funar")
	@CommandAliases
	public static final String[] FUNAR_ALIAS = {"want","wnt"};
	
	@CommandID("Trollear")
	@TranslatedName("Troll")
	@CommandDescription
	public static final String TROLLEAR_DESC = "Command to do some trolling stuff. To select a target(member), mention him/her at the start or you will recieve the trolling:smirk:";
	
	@CommandID("Trollear")
	@CommandAliases
	public static final String[] TROLLEAR_ALIAS = {"trolling","trl"};

	/*   CATEGORIA : IMAGEN   */
	@CategoryID("Imagen")
	@TranslatedName("Images")
	@CategoryDescription
	public static final String IMAGEN_DESC = "Images...";
	
	@CommandID("Gatos")
	@TranslatedName("Cats")
	@CommandDescription
	public static final String GATOS_DESC = "Return one (or more) image(s) of a random cat. Uses the [random.cat](http://random.cat) API to work.";
	
	@CommandID("Gatos")
	@CommandAliases
	public static final String[] GATOS_ALIAS = {"mew","mw","kittens","kttns"};
	
	@CommandID("Otaku")
	@CommandDescription
	public static final String OTAKU_DESC = "Return images related to anime and whatever(p*rn not included). Temporary name.";
	
	@CommandID("Otaku")
	@CommandAliases
	public static final String[] OTAKU_ALIAS = {"oku","otk"};
	
	/*   CATEGORIA : MUSICA   */
	@CategoryID("Musica")
	@TranslatedName("Music")
	@CategoryDescription
	public static final String MUSICA_DESC = "Music commands. What?. Were you waiting for another description?";
	
	@CommandID("Reproducir")
	@TranslatedName("Play")
	@CommandDescription
	public static final String REP_DESC = "Plays music from internet (default source: YouTube.com)";
	
	@CommandID("Reproducir")
	@CommandAliases
	public static final String[] REP_ALIAS = {"p","play","pl"};
	
	@CommandID("Pausar")
	@TranslatedName("Pause")
	@CommandDescription
	public static final String PAUSAR_DESC = "Pauses the song if playing.";
	
	@CommandID("Pausar")
	@CommandAliases
	public static final String[] PAUSAR_ALIAS = {"pp"};

	@CommandID("Siguiente")
	@TranslatedName("Skip")
	@CommandDescription
	public static final String SIG_DESC = "Skip the sont to the next in queue if this isn't empty.";
	
	@CommandID("Siguiente")
	@CommandAliases
	public static final String[] SIG_ALIAS = {"next","nxt","sk"};

	@CommandID("Detener")
	@TranslatedName("Stop")
	@CommandDescription
	public static final String DET_DESC = "Stops playing and cleans the queue.";
	
	@CommandID("Detener")
	@CommandAliases
	public static final String[] DET_ALIAS = {"sp","stp","shutup"};

	@CommandID("Actual")
	@TranslatedName("Playing")
	@CommandDescription
	public static final String ACT_DESC = "Shows information of the actual song which is playing.";
	
	@CommandID("Actual")
	@CommandAliases
	public static final String[] ACT_ALIAS = {"np","nowp","nowplaying","pyng"};

	@CommandID("Cola")
	@TranslatedName("Queue")
	@CommandDescription
	public static final String _DESC = "Shows the songs in the queue.";
	
	@CommandID("Cola")
	@CommandAliases
	public static final String[] _ALIAS = {"que","q","songs"};
	
	/*    CATEGORIA : NSFW    */
	@CategoryID("NSFW")
	@CategoryDescription
	public static final String CNSFW_DESC = "Important commands that save our lives... Just kidding! This category is full of +18 content!";
	
	@CommandID("NSFW")
	@CommandDescription
	public static final String NSFW_DESC = "You know what this command does. Don't ask and enjoy it!:wink::smirk:";
	
	@CommandID("NSFW")
	@CommandAliases
	public static final String[] NSFW_ALIAS = {"porn","prn","18","+18","7u7"};
	
	@CommandID("Xvideos")
	@CommandDescription
	public static final String XV_DESC = "It's like the 'YouTube' command, but this one is funnier(I can't play them yet, I need more time:pensive:).";
	
	@CommandID("Xvideos")
	@CommandAliases
	public static final String[] XV_ALIAS = {"xv","xvid","xxxv","videosnopor"};
	
	@CommandID("OtakuNSFW")
	@CommandDescription
	public static final String ONSFW_DESC = "It's like the clasic NSFW... but for otakus:wink::smirk:";
	
	@CommandID("OtakuNSFW")
	@CommandAliases
	public static final String[] ONSFW_ALIAS = {"hentai","otakuns","oprn","o18","h18","otakuporn"};
	
	@CommandID("Rule34")
	@CommandDescription
	public static final String R34_DESC = "The most important rule of internet. Don't forget that!.";
	
	@CommandID("Rule34")
	@CommandAliases
	public static final String[] R34_ALIAS = {"r34","34"};
	
	/*    CATEGORIA : VIDEOJUEGOS    */
	@CategoryID("VideoJuegos")
	@TranslatedName("VideoGames")
	@CategoryDescription
	public static final String VJ_DESC = "Commands related to videogames and whatever.";
	
	@CommandID("Pokedex")
	@CommandDescription
	public static final String PKDEX_DESC = "Searchs information about a pokémon.";
	
	@CommandID("Pokedex")
	@CommandAliases
	public static final String[] PKDEX_ALIAS = {"pkmn","pkm","dex","pd","poke"};
	
	
	
	
}