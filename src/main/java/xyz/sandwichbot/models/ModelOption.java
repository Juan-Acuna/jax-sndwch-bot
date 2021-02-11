package xyz.sandwichbot.models;

public class ModelOption implements Comparable<ModelOption>{
	private String name;
	private String desc;
	private String[] alias;
	private boolean enabled = true;
	private boolean visible;
	public ModelOption(String name, String desc, String[] alias, boolean enabled, boolean visible) {
		this.name = name;
		this.desc = desc;
		this.alias=alias;
		this.enabled = enabled;
		this.visible=visible;
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
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	@Override
	public int compareTo(ModelOption o) {
		return name.compareTo(o.name);
	}
}
