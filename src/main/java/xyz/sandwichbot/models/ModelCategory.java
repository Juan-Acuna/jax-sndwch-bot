package xyz.sandwichbot.models;

import java.util.ArrayList;
import java.util.Collections;

public class ModelCategory implements Comparable<ModelCategory>{
	private String name;
	private String desc;
	private boolean nsfw;
	private boolean visible;
	private boolean isSpecial;
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
	public boolean isNsfw() {
		return nsfw;
	}
	public void setNsfw(boolean nsfw) {
		this.nsfw = nsfw;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
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
	public boolean isSpecial() {
		return isSpecial;
	}
	public void setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}
	@Override
	public int compareTo(ModelCategory arg0) {
		return name.compareTo(arg0.name);
	}
	public void sortCommands() {
		Collections.sort(commands);
	}
}
