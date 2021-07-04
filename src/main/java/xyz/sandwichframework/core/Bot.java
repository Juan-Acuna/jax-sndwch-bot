package xyz.sandwichframework.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.models.CommandPacket;
/**
 * Representa al Bot de Discord. Contiene lo básico para la construcción de un bot.
 * Represents the Discord Bot. Contains the basics for build a bot.
 * @author Juancho
 * @version 1.1
 */
public abstract class Bot extends ListenerAdapter{
	/**
	 * Identificador del Bot (útil en el caso del modo multi-bot). Se genera a partir de su Token de Discord.
	 * Bot identifier (useful in multi-bot mode). It's generated from its Discord Token.
	 */
	protected String tokenHash;
	/**
	 * Indica si el bot se encuentra registrado en el contenedor de bots dentro de la clase BotRunner.
	 * Indicates if the bot is already registered in the bots container inside the BotRunner class.
	 */
	private boolean registeredOnRunner = false;
	/**
	 * Instancia de {@link net.dv8tion.jda.api.JDA} (Librería en la cual se construye este framework).
	 * Instance of {@link net.dv8tion.jda.api.JDA}.
	 */
	protected JDA jda = null;
	/**
	 * Objeto para configurar y construir el {@link net.dv8tion.jda.api.JDA} del bot.
	 * Object to configure and build the {@link net.dv8tion.jda.api.JDA} of the bot.
	 */
	protected JDABuilder builder;
	/**
	 * Prefijo del bot. Se usa por defecto en todos los servidores({@link xyz.sandwichframework.models.discord.ModelGuild}) en los cuales no se ha especificado un prefijo.
	 * Bot prefix. Is used as defaultin all the guilds({@link xyz.sandwichframework.models.discord.ModelGuild}) which doesn't have a specific prefix).
	 */
	protected String prefix = ">";
	/**
	 * Prefijo de opción del bot. Se usa por defecto en todos los servidores({@link xyz.sandwichframework.models.discord.ModelGuild}) en los cuales no se ha especificado un prefijo de opción.
	 * Bot option prefix. Is used as default in all the guilds({@link xyz.sandwichframework.models.discord.ModelGuild}) which doesn't have a specific option prefix).
	 */
	protected String opt_prefix = "-";
	/**
	 * Indica si el bot esta encendido o apagado (enendido = escuchando comandos). Las categorías 'especiales' ({@link xyz.sandwichframework.annotations.Category}(special=true)) pueden ser escuchadas sin importar el estado de este atributo.
	 * Indicates if the bot is on or off (on = listenning to commands). The 'special' categories ({@link xyz.sandwichframework.annotations.Category}(special=true)) can be executed no matter the value of this attribute.
	 * */
	protected boolean on = false;
	/**
	 * Idioma nativo del bot. Por defecto inglés [EN].
	 * Native language of the bot. Default english [EN].
	 */
	protected Language def_lang = Language.EN;
	/**
	 * Activa un comando de ayuda automatico para el bot. Puede ser reemplazado por uno propio.
	 * Enables a automatic help command for the bot. Can be replaced by a custom command.
	 */
	protected boolean autoHelpEnabled = false;
	/**
	 * Indica si el bot escucha commandos(mensajes) enviados por este mismo.
	 * Indicates if the bot listen to commands(messages) sent by itself.
	 * */
	protected boolean ignoreSelfCommands = false;
	/**
	 * Indica si el bot escucha commandos(mensajes) enviados por otros bots.
	 * Indicates if the bot listen to commands(messages) sent by other bots.
	 * */
	protected boolean ignoreBotsCommands = false;
	/**
	 * Activa la simulación de typeo('Bot esta escribiendo...' en el cliente oficial de Discord) cada vez que se reciva un comando.
	 * Enables the typing simulation('Bot is typing...' in the Discord oficial client) every time the bot get a command.
	 * */
	protected boolean typingOnCommand = false;
	/**
	 * Indica si el bot escucha commandos(mensajes) enviados desde un WebHook.
	 * Indicates if the bot listen to commands(messages) sent through a WebHook.
	 * */
	protected boolean ignoreWebHook = true;
	/**
	 * Inidica si los comandos y categorías etiquetadas como 'NSFW' deben acultarse al usar el comando de ayuda automático.
	 * Indicates if the commands an categories tagged as 'NSFW' have to been hidden when using the automatic help command.
	 */
	protected boolean hide_nsfw_category = false;
	protected GuildsManager guildsManager;
	protected ExtraCmdManager extraCmdManager;
	protected AutoHelpCommand autoHelpCommand;
	
	protected Bot(String token, Language defaultLang) {
		this.tokenHash = BotRunner.getSHA256(token);
		this.def_lang=defaultLang;
		builder = JDABuilder.createDefault(token);
	}
	public final void runBot() throws Exception {
		if(!registeredOnRunner)
			throw new Exception("Unregistered Bot, can't start running a non-registered bot!");
		this.jda = builder.build();
		this.on=true;
	}
	final void setRegistered() {
		this.registeredOnRunner=true;
	}
	public GuildsManager getGuildsManager() {
		return guildsManager;
	}
	public ExtraCmdManager getExtraCmdManager() {
		return extraCmdManager;
	}
	public Language getDefaultLanguage() {
		return def_lang;
	}
	public void setDefaultLanguage(Language def_lang) {
		this.def_lang = def_lang;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getOptionsPrefix() {
		return opt_prefix;
	}
	public void setOptionsPrefix(String opt_prefix) {
		this.opt_prefix = opt_prefix;
	}
	public final boolean isAutoHelpCommandEnabled() {
		return autoHelpEnabled;
	}
	public final void setAutoHelpCommandEnabled(boolean auto) {
		this.autoHelpEnabled = auto;
	}
	public boolean isHideNSFWCategory() {
		return this.hide_nsfw_category;
	}
	public void setHideNSFWCategory(boolean hide) {
		this.hide_nsfw_category = hide;
	}
	public final boolean isOn() {
		return this.on;
	}
	public final void setOn(boolean b) {
		this.on=b;
	}
	public final JDABuilder getBuilder() {
		return builder;
	}
	public final JDA getJDA() {
		return jda;
	}
	public final User getSelfUser() {
		return this.jda.getSelfUser();
	}
	public final void setActivity(String activity) {
		this.jda.getPresence().setActivity(Activity.playing(activity));
	}
	public final void setActivity(Activity activity) {
		this.jda.getPresence().setActivity(activity);
	}
	public final Activity getActivity() {
		return this.jda.getPresence().getActivity();
	}
	public final void setStatus(OnlineStatus status) {
		this.jda.getPresence().setStatus(status);
	}
	public final OnlineStatus getStatus() {
		return this.jda.getPresence().getStatus();
	}
	public final void runAutoHelpCommand(CommandPacket packet) {
		this.autoHelpCommand.help(packet);
	}
	public final boolean isIgnoreSelfCommands() {
		return ignoreSelfCommands;
	}
	public final void setIgnoreSelfCommands(boolean ignore) {
		this.ignoreSelfCommands = ignore;
	}
	public final boolean isIgnoreBotsCommands() {
		return ignoreBotsCommands;
	}
	public final void setIgnoreBotsCommands(boolean ignore) {
		this.ignoreBotsCommands = ignore;
	}
	public final boolean isTypingOnCommand() {
		return typingOnCommand;
	}
	public final void setTypingOnCommand(boolean enable) {
		this.typingOnCommand = enable;
	}
	public final boolean isIgnoreWebHook() {
		return ignoreWebHook;
	}
	public final void setIgnoreWebHook(boolean ignore) {
		this.ignoreWebHook = ignore;
	}
	public final String getHashToken() {
		return this.tokenHash;
	}
	protected final void runCommand(MessageReceivedEvent event) throws Exception {
		if(this.ignoreBotsCommands && event.getAuthor().isBot())
			return;
		if(this.ignoreSelfCommands && getSelfUser().equals(event.getAuthor()))
			return;
		BotRunner.run(event, this);
	}
	@Override
	public abstract void onGuildJoin(GuildJoinEvent e);
	@Override
	public abstract void onMessageReceived(MessageReceivedEvent e);
}
