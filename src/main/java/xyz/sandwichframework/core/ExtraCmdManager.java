package xyz.sandwichframework.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichframework.models.ExtraCmdPacket;
import xyz.sandwichframework.models.ModelExtraCommand;
import xyz.sandwichframework.models.discord.ModelGuild;
/**
 * Manejador de Comandos extra.
 * Manager of Extra commands.
 * @author Juancho
 * @version 1.0
 */
public class ExtraCmdManager {
	private static final String wildcard = "\\*/";
	private static final String string_wildcard = wildcard +"{s}";
	private static final String number_wildcard = wildcard + "{n}";
	
	public static final String[] WILDCARD = {wildcard};
	public static final String[] STRING_WILDCARD = {string_wildcard};
	public static final String[] NUMBER_WILDCARD = {number_wildcard};
	private static Map<MessageChannel, List<ExtraCmdObj>> threads = (Map<MessageChannel, List<ExtraCmdObj>>) Collections.synchronizedMap(new HashMap<MessageChannel, List<ExtraCmdObj>>());
	private Bot bot;
	
	private ExtraCmdManager(Bot bot) {
		this.bot = bot;
	}
	protected static ExtraCmdManager startService(Bot bot) {
		return new ExtraCmdManager(bot);
	}
	public ExtraCmdObj registerExtraCmd(String extraCmdName, Message message, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		return registerExtraCmd(extraCmdName, message.getChannel(),message.getAuthor().getId(),spectedValues,maxSeg,maxMsg,args);
	}
	public ExtraCmdObj registerExtraCmd(String extraCmdName, MessageChannel channel, String authorId, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		ModelExtraCommand m = pickExtraCommand(extraCmdName);
		ModelGuild g = null;
		if(channel.getType()!=ChannelType.PRIVATE) {
			g = bot.getGuildsManager().getGuild(((TextChannel)channel).getGuild().getIdLong());
		}
		CommandPacketBuilder cpb = new CommandPacketBuilder(bot, g, channel, authorId);
		cpb.setArgs(args);
		ExtraCmdObj o = new ExtraCmdObj(m,cpb, spectedValues, maxSeg, maxMsg);
		List<ExtraCmdObj> l = threads.get(channel);
		if(l==null) {
			l = Collections.synchronizedList(new ArrayList<ExtraCmdObj>());
			l.add(o);
			threads.put(channel, l);
		}else {
			threads.get(channel).add(o);
		}
		new Thread(o).start();
		return o;
	}
	public void CheckExtras(MessageReceivedEvent event) {
		if(threads.size()<=0)
			return;
		if(event.getAuthor().getId().equals(bot.getSelfUser().getId()))
			return;
		List<ExtraCmdObj> l = threads.get(event.getChannel());
		if(l == null) {
			return;
		}
		for(ExtraCmdObj o : l) {
			o.PutMessage(event);
		}
	}
	public class ExtraCmdObj implements Runnable{

		Lock lock = new ReentrantLock();
		public String[] spectedValues = null;
		public int maxMsg = 5;
		public int maxSeg = 60; // 30
		private MessageReceivedEvent event = null;
		private ModelExtraCommand action;
		private int msgs = 0;
		private CommandPacketBuilder builder;
		
		protected ExtraCmdObj(ModelExtraCommand action, ExtraCmdPacket packet,String[] spectedValues, int maxSeg, int maxMsg) {
			this.spectedValues=spectedValues;
			this.maxSeg=maxSeg;
			this.maxMsg=maxMsg;
			this.action = action;
		}
		protected ExtraCmdObj(ModelExtraCommand action, CommandPacketBuilder builder,String[] spectedValues, int maxSeg, int maxMsg) {
			this.spectedValues=spectedValues;
			this.maxSeg=maxSeg;
			this.maxMsg=maxMsg;
			this.action = action;
			this.builder=builder;
		}
		protected ModelExtraCommand getAction() {
			return action;
		}
		protected ExtraCmdObj setAction(ModelExtraCommand action) {
			this.action = action;
			return this;
		}
		@Override
		public void run(){
			float s = 0f;
			msgs = 0;
			boolean b = true;
			ExtraCmdPacket packet = builder.buildExtraPacket();
			while(maxSeg > s && maxMsg > msgs && b) {
				action.eachRun(packet);
				if(Compare(event)) {
					lock.lock();
					builder.setMessageReceivedEvent(event);
					packet = builder.buildExtraPacket();
					action.Run(packet);
					lock.unlock();
					b = false;
				}
				s += 0.5f;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(!b) {
				action.afterRun(packet);
			}else {
				action.NoRun(packet);
			}
			action.finallyRun(packet);
			threads.get(packet.getChannel()).remove(this);
		}
		protected void PutMessage(MessageReceivedEvent event) {
			this.event=event;
			if(this.builder.getAuthorId()==null)
				return;
			if(this.isAuthorOnly() && !this.builder.getAuthorId().equals(event.getAuthor().getId()))
				return;
			msgs++;
		}
		private boolean Compare(MessageReceivedEvent event) {
			if(event == null) {
				return false;
			}
			if(spectedValues[0].startsWith(wildcard)) {
				switch(spectedValues[0]) {
				case wildcard:
					return true;
				case number_wildcard:
					return event.getMessage().getContentRaw().matches("[0-9]{1,999}");
				case string_wildcard:
					return !event.getMessage().getContentRaw().matches("[0-9]{1,999}");
				}
			}
			for(String a : spectedValues) {
				if(a.equalsIgnoreCase(event.getMessage().getContentRaw())){
					return true;
				}
			}
			event = null;
			return false;
		}
		public ExtraCmdObj setEachArrgs(Object...args) {
			this.builder.setEachArgs(args);
			return this;
		}
		public ExtraCmdObj setAfterArrgs(Object...args) {
			this.builder.setAfterArgs(args);
			return this;
		}
		public ExtraCmdObj setNoExecutedArrgs(Object...args) {
			this.builder.setNoArgs(args);
			return this;
		}
		public ExtraCmdObj setFinallyArrgs(Object...args) {
			this.builder.setFinallyArgs(args);
			return this;
		}
		public ExtraCmdObj setAuthorOnly(boolean b) {
			this.builder.setAuthorOnly(b);
			return this;
		}
		public ExtraCmdObj asAuthorOnly() {
			this.builder.setAuthorOnly(true);
			return this;
		}
		public boolean isAuthorOnly() {
			return this.builder.isAuthorOnly();
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Arrays.deepHashCode(builder.getArgs());
			result = prime * result + ((builder.getAuthorId() == null) ? 0 : builder.getAuthorId().hashCode());
			result = prime * result + ((builder.getChannel() == null) ? 0 : builder.getChannel().getId().hashCode());
			result = prime * result + maxMsg;
			result = prime * result + maxSeg;
			result = prime * result + Arrays.hashCode(spectedValues);
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ExtraCmdObj other = (ExtraCmdObj) obj;
			if (!Arrays.deepEquals(builder.getArgs(), other.builder.getArgs()))
				return false;
			if (builder.getAuthorId() == null) {
				if (other.builder.getAuthorId() != null)
					return false;
			} else if (!builder.getAuthorId().equals(other.builder.getAuthorId()))
				return false;
			if (builder.getChannel() == null) {
				if (other.builder.getChannel() != null)
					return false;
			} else if (!builder.getChannel().getId().equals(other.builder.getChannel().getId()))
				return false;
			if (maxMsg != other.maxMsg)
				return false;
			if (maxSeg != other.maxSeg)
				return false;
			if (!Arrays.equals(spectedValues, other.spectedValues))
				return false;
			return true;
		}
		private ExtraCmdManager getEnclosingInstance() {
			return ExtraCmdManager.this;
		}
	}
	private ModelExtraCommand pickExtraCommand(String name) {
		return ModelExtraCommand.find(name);
	}
}
