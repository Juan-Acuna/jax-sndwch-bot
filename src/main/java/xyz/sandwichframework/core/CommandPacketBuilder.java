package xyz.sandwichframework.core;

import java.util.ArrayList;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichframework.core.util.Language;
import xyz.sandwichframework.models.CommandBase;
import xyz.sandwichframework.models.CommandPacket;
import xyz.sandwichframework.models.ExtraCmdPacket;
import xyz.sandwichframework.models.InputParameter;
import xyz.sandwichframework.models.ModelCommand;
import xyz.sandwichframework.models.ModelOption;
import xyz.sandwichframework.models.InputParameter.InputParamType;
import xyz.sandwichframework.models.discord.ModelGuild;
/*
 * 
 * 
 */
public class CommandPacketBuilder {
	protected Bot bot;
	protected boolean authorOnly = false;
	private ArrayList<InputParameter> parameters = null;
	protected MessageReceivedEvent messageReceivedEvent;
	protected ModelGuild guild = null;
	protected MessageChannel channel = null;
	private String input = null;
	private String authorId;
	private Object[] args;
	private Object[] eachArgs = null;
	private Object[] afterArgs = null;
	private Object[] noArgs = null;
	private Object[] finallyArgs = null;
	public CommandPacketBuilder() { }
	public CommandPacketBuilder(Bot bot, ModelGuild guild, MessageChannel channel, String authorId) {
		this.bot=bot;
		this.guild=guild;
		this.channel=channel;
		this.authorId=authorId;
	}
	public CommandPacketBuilder(Bot bot, MessageReceivedEvent event, Language actualLang, CommandBase cmd, String oprxt) {
		this.bot=bot;
		this.messageReceivedEvent=event;
		this.channel=event.getChannel();
		this.parameters = this.findParameters(actualLang, event.getMessage().getContentRaw(), cmd, oprxt);
	}
	public CommandPacket build() {
		return new CommandPacket(this.bot,this.parameters,this.messageReceivedEvent);
	}
	public ExtraCmdPacket buildExtraPacket() {
		return new ExtraCmdPacket(this.bot, this.guild, this.messageReceivedEvent,this.authorOnly, this.args);
	}
	public Bot getBot() {
		return bot;
	}
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	public ArrayList<InputParameter> getParameters() {
		return parameters;
	}
	public void setParameters(ArrayList<InputParameter> parameters) {
		this.parameters = parameters;
	}
	public MessageReceivedEvent getMessageReceivedEvent() {
		return messageReceivedEvent;
	}
	public void setMessageReceivedEvent(MessageReceivedEvent messageReceived) {
		this.messageReceivedEvent = messageReceived;
		this.channel=messageReceived.getChannel();
		this.input=messageReceived.getMessage().getContentRaw();
	}
	public ModelGuild getGuild() {
		return guild;
	}
	public void setGuild(ModelGuild guild) {
		this.guild = guild;
	}
	public MessageChannel getChannel() {
		return channel;
	}
	public void setChannel(MessageChannel channel) {
		if(messageReceivedEvent!=null)
			return;
		this.channel = channel;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public Object[] getEachArgs() {
		return eachArgs;
	}
	public void setEachArgs(Object[] eachArgs) {
		this.eachArgs = eachArgs;
	}
	public Object[] getAfterArgs() {
		return afterArgs;
	}
	public void setAfterArgs(Object[] afterArgs) {
		this.afterArgs = afterArgs;
	}
	public Object[] getNoArgs() {
		return noArgs;
	}
	public void setNoArgs(Object[] noArgs) {
		this.noArgs = noArgs;
	}
	public Object[] getFinallyArgs() {
		return finallyArgs;
	}
	public void setFinallyArgs(Object[] finallyArgs) {
		this.finallyArgs = finallyArgs;
	}
	public boolean isAuthorOnly() {
		return authorOnly;
	}
	public void setAuthorOnly(boolean authorOnly) {
		this.authorOnly = authorOnly;
	}
	private ArrayList<InputParameter> findParameters(Language lang, String input,CommandBase command, String oprx){
		String[] s = input.split(" ");
		ArrayList<InputParameter> lista = new ArrayList<InputParameter>();
		InputParameter p = new InputParameter();
		for(int i=1;i<s.length;i++) {
			if((s[i]).startsWith(oprx)) {
				p=null;
				p = new InputParameter();
				for(ModelOption mo : command.getOptions()) {
					if(s[i].toLowerCase().equalsIgnoreCase(oprx + mo.getName(lang))) {
						p.setKey(mo.getName(lang));
						p.setType(InputParamType.Standar);
						break;
					}else {
						for(String a : mo.getAlias(lang)) {
							if(s[i].toLowerCase().equalsIgnoreCase(oprx+a)) {
								p.setKey(mo.getName(lang));
								p.setType(InputParamType.Standar);
								break;
							}
						}
					}
				}
				if(p.getType() == InputParamType.Custom) {
					/*for(String hs : AutoHelpCommand.getHelpOptions(lang)) {
						if(s[i].equalsIgnoreCase(oprx+hs)) {
							p.setKey(AutoHelpCommand.AUTO_HELP_KEY);
							p.setType(InputParamType.Standar);
							break;
						}
					}*/
					if(p.getType() == InputParamType.Custom) {
						p.setType(InputParamType.Invalid);
						p.setKey(s[i]);
					}
				}
			}else if(i==1) {
				p.setType(InputParamType.Custom);
				p.setKey("custom");
				p.setValue(s[i]);
			}else if(p.getValueAsString()!=null){
				p.setValue(p.getValueAsString()+" "+s[i]);
			}else {
				p.setValue(s[i]);
			}
			if(lista.size()>0) {
				if(lista.lastIndexOf(p) == -1) {
					lista.add(p);
				}
			}else {
				lista.add(p);
			}
		}
		return lista;
	}
}
