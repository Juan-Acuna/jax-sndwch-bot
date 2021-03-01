package xyz.sandwichbot.core;

import java.lang.reflect.Method;
import java.util.ArrayList;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import xyz.sandwichbot.models.InputParameter;

class CommandRunner implements Runnable{

	private Method method;
	private ArrayList<InputParameter> parameters;
	private MessageReceivedEvent msg;
	private PrivateMessageReceivedEvent privmsg;
	
	public CommandRunner(Method method, ArrayList<InputParameter> parameters,MessageReceivedEvent event) {
		super();
		this.method = method;
		this.parameters = parameters;
		this.msg=event;
	}
	
	public CommandRunner(Method method, ArrayList<InputParameter> parameters,PrivateMessageReceivedEvent event) {
		super();
		this.method = method;
		this.parameters = parameters;
		this.privmsg=event;
	}

	@Override
	public void run() {
		if(msg==null) {
			try {
				method.invoke(null, privmsg, parameters);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			try {
				method.invoke(null, msg, parameters);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	

}
