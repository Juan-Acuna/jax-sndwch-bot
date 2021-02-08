package xyz.sandwichbot.models;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ModelCommand {
	private String name;
	private String desc;
	private String[] alias;
	private boolean enabled;
	private String parameter;
	private String parameterDesc;
	private ArrayList<ModelOption> options;
	private ModelCategory category;
	private Method source;
	public ModelCommand() {
		options = new ArrayList<ModelOption>();
		//optionsDesc = new ArrayList<String>();
	}
	public ModelCommand(String name, String desc, ModelCategory category, Method source) {
		this.name = name;
		this.desc = desc;
		this.category = category;
		this.source = source;
		options = new ArrayList<ModelOption>();
		category.addCommand(this);
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
	public String[] getAlias() {
		return alias;
	}
	public void setAlias(String[] alias) {
		this.alias = alias;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getParameterDesc() {
		return parameterDesc;
	}
	public void setParameterDesc(String parameterDesc) {
		this.parameterDesc = parameterDesc;
	}
	public ArrayList<ModelOption> getOptions() {
		return options;
	}
	public void addOption(ModelOption option) {
		this.options.add(option);
	}
	public ModelCategory getCategory() {
		return category;
	}
	public void setCategory(ModelCategory category) {
		this.category = category;
	}
	public Method getSource() {
		return source;
	}
	public void setSource(Method source) {
		this.source = source;
	}
}
