package xyz.sandwichframework.models;

import java.lang.reflect.Method;

public class ModelExtraCommand {
	String name;
	Method action;
	
	public ModelExtraCommand(String name, Method action) {
		this.name = name;
		this.action = action;
	}

	public void Run(Object...args) {
		try {
			action.invoke(null, args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
