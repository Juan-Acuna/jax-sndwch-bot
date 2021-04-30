package xyz.sandwichframework.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.dv8tion.jda.api.entities.*;
import xyz.sandwichframework.models.ModelExtraCommand;

public class ExtraCmdManager {
	private static HashMap<TextChannel, ExtraCmdObj> threads = (HashMap<TextChannel, ExtraCmdObj>) Collections.synchronizedMap(new HashMap<TextChannel, ExtraCmdObj>());
	private static ExtraCmdManager _instance = new ExtraCmdManager();
	
	private ExtraCmdManager() {}
	
	public static ExtraCmdManager Do() {
		if(_instance!=null) {
			return _instance;
		}
		return _instance = new ExtraCmdManager();
	}
	
	public void AddCmdThread(String extraCmdName, Message message, String[] spectedValues) {
		threads.put(message.getTextChannel(), new ExtraCmdObj(message.getTextChannel(),message.getAuthor().getId(),spectedValues));
	}
	public void AddCmdThread(String extraCmdName, Message message, String[] spectedValues, int maxSeg) {
		threads.put(message.getTextChannel(), new ExtraCmdObj(message.getTextChannel(),message.getAuthor().getId(),spectedValues, maxSeg));
	}
	public void AddCmdThread(String extraCmdName, Message message, String[] spectedValues, int maxSeg, int maxMsg) {
		threads.put(message.getTextChannel(), new ExtraCmdObj(message.getTextChannel(),message.getAuthor().getId(),spectedValues, maxSeg, maxMsg));
	}
	
	public void CheckExtras(Message message) {
		ExtraCmdObj o = threads.get(message.getTextChannel());
		if(o == null) {
			return;
		}
		o.PutMessage(message.getContentRaw());
	}
	
	protected class ExtraCmdObj implements Runnable{
		public TextChannel channel;
		public String authorId; // user
		public String[] spectedValues;
		public int maxMsg = 5;
		public int maxSeg = 60; // 30
		private String cmd = null;
		private ModelExtraCommand action;
		
		protected ExtraCmdObj() {}
		protected ExtraCmdObj(TextChannel channel, String authorId, String[] spectedValues) {
			this.channel=channel;
			this.authorId=authorId;
			this.spectedValues=spectedValues;
		}
		protected ExtraCmdObj(TextChannel channel, String authorId, String[] spectedValues, int maxSeg) {
			this.channel=channel;
			this.authorId=authorId;
			this.spectedValues=spectedValues;
			this.maxSeg=maxSeg * 2;
		}
		protected ExtraCmdObj(TextChannel channel, String authorId, String[] spectedValues, int maxMsg, int maxSeg) {
			this.channel=channel;
			this.authorId=authorId;
			this.spectedValues=spectedValues;
			this.maxSeg=maxSeg * 2;
			this.maxMsg=maxMsg;
		}
		
		@Override
		public void run(){
			int s = 0;
			int m = 0;
			boolean b = true;
			while(maxSeg >= s && maxMsg >= m && b) {
				if(Compare(cmd)) {
					action.Run(cmd);
					b = true;
				}
				s++;
				m++;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			threads.remove(channel);
		}
		protected void PutMessage(String message) {
			cmd = message;
		}
		private boolean Compare(String message) {
			if(message == null) {
				return false;
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
}
