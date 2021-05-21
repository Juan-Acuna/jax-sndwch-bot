package xyz.sandwichframework.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.entities.*;
import xyz.sandwichframework.models.ModelExtraCommand;

public class ExtraCmdManager {
	private static final String wildcard = "\\*/";
	private static final String string_wildcard = wildcard +"{s}";
	private static final String number_wildcard = wildcard + "{n}";
	
	public static final String[] WILDCARD = {wildcard};
	public static final String[] STRING_WILDCARD = {string_wildcard};
	public static final String[] NUMBER_WILDCARD = {number_wildcard};
	private static Map<MessageChannel, ExtraCmdObj> threads = (Map<MessageChannel, ExtraCmdObj>) Collections.synchronizedMap(new HashMap<MessageChannel, ExtraCmdObj>());
	private static ExtraCmdManager _instance = new ExtraCmdManager();
	
	private ExtraCmdManager() {}
	
	public static ExtraCmdManager getManager() {
		if(_instance!=null) {
			return _instance;
		}
		return _instance = new ExtraCmdManager();
	}
	
	public void registerExtraCmd(String extraCmdName, Message message, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		registerExtraCmd(extraCmdName, message.getChannel(),message.getAuthor().getId(),spectedValues,maxSeg,maxMsg,args);
	}
	public void registerExtraCmd(String extraCmdName, MessageChannel channel, String authorId, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
		ModelExtraCommand m = pickExtraCommand(extraCmdName);
		ExtraCmdObj o = new ExtraCmdObj(m, channel, authorId,spectedValues, maxSeg, maxMsg, args);
		threads.put(channel, o);
		new Thread(o).start();
	}
	
	public void CheckExtras(Message message) {
		if(threads.size()<=0) {
			return;
		}
		ExtraCmdObj o = threads.get(message.getChannel());
		if(o == null) {
			return;
		}
		o.PutMessage(message.getContentRaw());
	}
	
	protected class ExtraCmdObj implements Runnable{
		public MessageChannel channel;
		public String authorId = null; // user
		public String[] spectedValues = null;
		public int maxMsg = 5;
		public int maxSeg = 60; // 30
		private String cmd = null;
		private ModelExtraCommand action;
		private int msgs = 0;
		private Object[] args = null;
		
		protected ExtraCmdObj() {}
		protected ExtraCmdObj(ModelExtraCommand action, MessageChannel channel, String authorId, String[] spectedValues, int maxSeg, int maxMsg, Object...args) {
			this.channel=channel;
			this.authorId=authorId;
			this.spectedValues=spectedValues;
			this.maxSeg=maxSeg;
			this.maxMsg=maxMsg;
			this.action = action;
			this.args=args;
		}
		
		public ModelExtraCommand getAction() {
			return action;
		}
		public void setAction(ModelExtraCommand action) {
			this.action = action;
		}
		@Override
		public void run(){
			float s = 0f;
			msgs = 0;
			boolean b = true;
			while(maxSeg > s && maxMsg > msgs && b) {
				if(Compare(cmd)) {
					action.Run(cmd, channel, authorId,args);
					b = false;
				}
				s += 0.5f;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(b) {
				die();
			}
			//System.out.println("EJECUCION TERMINADA:\nTIEMPO: "+s+"/"+maxSeg+", MENSAJES: "+msgs+"/"+maxMsg+", EJECUTADO: "+ (b?"No":"Si"));
			threads.remove(channel);
		}
		protected void PutMessage(String message) {
			cmd = message;
			msgs++;
		}
		private boolean Compare(String message) {
			if(message == null) {
				return false;
			}
			if(spectedValues[0].startsWith(wildcard)) {
				switch(spectedValues[0]) {
				case wildcard:
					return true;
				case number_wildcard:
					return message.matches("[0-9]{1,999}");
				case string_wildcard:
					return !message.matches("[0-9]{1,999}");
				}
			}
			for(String a : spectedValues) {
				if(a.equalsIgnoreCase(message)){
					return true;
				}
			}
			cmd = null;
			return false;
		}
	}
	protected void die() {
		
	}
	private ModelExtraCommand pickExtraCommand(String name) {
		return BotRunner._self.xcommands.get(name);
	}
}
