package xyz.sandwichbot.models;

public class ModelOption {
	private String name;
	private String desc;
	private String[] alias;
	private boolean enabled = true;
	public ModelOption(String name, String desc, String[] alias, boolean enabled) {
		this.name = name;
		this.desc = desc;
		this.alias=alias;
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
