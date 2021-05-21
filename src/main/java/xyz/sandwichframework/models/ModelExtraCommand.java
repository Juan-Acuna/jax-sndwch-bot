package xyz.sandwichframework.models;

import java.lang.reflect.Method;

import net.dv8tion.jda.api.entities.MessageChannel;

public class ModelExtraCommand {
	String name;
	Method action;
	
	public ModelExtraCommand(String name, Method action) {
		this.name = name;
		this.action = action;
		System.out.println("MODEL EXTRA COMMAND CREADO: "+name);
	}

	public void Run(String command ,MessageChannel channel, String authorId, Object...args) {
		try {
			action.invoke(null, command ,channel, authorId, args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
