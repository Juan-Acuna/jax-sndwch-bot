package xyz.snadwichbot.core;

import java.lang.reflect.Method;
import java.util.ArrayList;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import xyz.sandwichbot.models.InputParameter;

class CommandRunner implements Runnable{

	private Method method;
	private ArrayList<InputParameter> parameters;
	private MessageReceivedEvent event;
	
	public CommandRunner(Method method, ArrayList<InputParameter> parameters,MessageReceivedEvent event) {
		super();
		this.method = method;
		this.parameters = parameters;
		this.event=event;
	}

	@Override
	public void run() {
		try {
			method.invoke(null, event, parameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
