package xyz.sandwichbot.models;

import java.util.ArrayList;

public class ModelCategory {
	private String name;
	private String desc;
	private ArrayList<ModelCommand> commands;
	public ModelCategory(){}
	public ModelCategory(String name, String desc) {
		this.name = name;
		this.desc = desc;
		commands = new ArrayList<ModelCommand>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public ArrayList<ModelCommand> getCommands() {
		return commands;
	}
	public void setCommands(ArrayList<ModelCommand> commands) {
		this.commands = commands;
	}
	public void addCommand(ModelCommand command) {
		this.commands.add(command);
	}
}
