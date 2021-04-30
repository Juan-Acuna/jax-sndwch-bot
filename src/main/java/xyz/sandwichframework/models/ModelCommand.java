package xyz.sandwichframework.models;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import xyz.sandwichframework.core.LanguageHandler;

public class ModelCommand implements Comparable<ModelCommand>{
	private String id;
	private HashMap<Language, String> name;
	private HashMap<Language, String> desc;
	private HashMap<Language, String[]> alias;
	private boolean enabled;
	private String parameter;
	private String parameterDesc;
	private boolean visible;
	private ArrayList<ModelOption> options;
	private ModelCategory category;
	private Method source;
	public ModelCommand() {
		this.name=new HashMap<Language, String>();
		this.desc=new HashMap<Language, String>();
		this.alias=new HashMap<Language, String[]>();
		this.options = new ArrayList<ModelOption>();
		//optionsDesc = new ArrayList<String>();
	}
	public ModelCommand(Language lang, String id, ModelCategory category, Method source) {
		this.name=new HashMap<Language, String>();
		this.desc=new HashMap<Language, String>();
		this.alias=new HashMap<Language, String[]>();
		this.id=id;
		this.name.put(lang, id);
		this.category = category;
		this.source = source;
		options = new ArrayList<ModelOption>();
		category.addCommand(this);
	}
	public ModelCommand(Language lang,String id, String desc, ModelCategory category, Method source) {
		this.name=new HashMap<Language, String>();
		this.desc=new HashMap<Language, String>();
		this.alias=new HashMap<Language, String[]>();
		this.id=id;
		this.name.put(lang, id);
		this.desc.put(lang, desc);
		this.category = category;
		this.source = source;
		options = new ArrayList<ModelOption>();
		category.addCommand(this);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getName(Language lang) {
		if(name.containsKey(lang)) {
			return name.get(lang);
		}
		if(name.containsKey(LanguageHandler.getLenguageParent(lang))) {
			return name.get(LanguageHandler.getLenguageParent(lang));
		}
		return name.get(name.keySet().toArray()[0]);
	}
	public void setName(Language lang, String name) {
		this.name.put(lang, name);
	}
	public String getDesc(Language lang) {
		if(desc.containsKey(lang)) {
			return desc.get(lang);
		}
		if(desc.containsKey(LanguageHandler.getLenguageParent(lang))) {
			return desc.get(LanguageHandler.getLenguageParent(lang));
		}
		return desc.get(desc.keySet().toArray()[0]);
	}
	public void setDesc(Language lang, String desc) {
		this.desc.put(lang, desc);
	}
	public String[] getAlias(Language lang) {
		if(alias.containsKey(lang)) {
			return alias.get(lang);
		}
		if(alias.containsKey(LanguageHandler.getLenguageParent(lang))) {
			return alias.get(LanguageHandler.getLenguageParent(lang));
		}
		return alias.get(alias.keySet().toArray()[0]);
	}
	public void setAlias(Language lang, String[] alias) {
		this.alias.put(lang, alias);
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
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
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
	@Override
	public int compareTo(ModelCommand arg0) {
		return id.compareTo(arg0.id);
	}
	public void sortOptions() {
		Collections.sort(options);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelCommand other = (ModelCommand) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
