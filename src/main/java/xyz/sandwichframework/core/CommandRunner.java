package xyz.sandwichframework.core;

import java.lang.reflect.Method;
import java.util.ArrayList;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import xyz.sandwichframework.models.CommandPacket;
import xyz.sandwichframework.models.InputParameter;
/**
 * Clase que ejecuta los comandos.
 * Class that runs the commands.
 * @author Juancho
 * @version 1.1
 */
class CommandRunner implements Runnable{

	private Method method;
	//private ArrayList<InputParameter> parameters;
	//private MessageReceivedEvent msg;
	private CommandPacket packet;
	
	protected CommandRunner(Method method, CommandPacket packet) {
		this.method=method;
		this.packet=packet;
	}/*
	protected CommandRunner(Method method, ArrayList<InputParameter> parameters,MessageReceivedEvent event) {
		super();
		this.method = method;
		this.parameters = parameters;
		this.msg=event;
	}
	
	protected CommandRunner(Method method, ArrayList<InputParameter> parameters,PrivateMessageReceivedEvent event) {
		super();
		this.method = method;
		this.parameters = parameters;
		this.msg = new MessageReceivedEvent(event.getJDA(), event.getResponseNumber(), event.getMessage());
	}*/

	@Override
	public void run() {
		try {
			method.invoke(null, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
