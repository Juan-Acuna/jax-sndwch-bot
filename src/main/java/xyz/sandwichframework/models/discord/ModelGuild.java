package xyz.sandwichframework.models.discord;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.entities.Guild;
import xyz.sandwichframework.core.util.Language;

public class ModelGuild {
	private String id;
	protected String lastKnownName;
	protected Language language;
	protected boolean joined = true;
	protected Map<String, Boolean> allowedCommands = (Map<String, Boolean>) Collections.synchronizedMap(new HashMap<String, Boolean>());
	protected Map<String, Boolean> allowedCategories = (Map<String, Boolean>) Collections.synchronizedMap(new HashMap<String, Boolean>());
	protected Map<String, Boolean> allowedRoles = (Map<String, Boolean>) Collections.synchronizedMap(new HashMap<String, Boolean>());
	protected Map<String, Boolean> allowedMembers = (Map<String, Boolean>) Collections.synchronizedMap(new HashMap<String, Boolean>());
	protected Map<String, Boolean> allowedChannels = (Map<String, Boolean>) Collections.synchronizedMap(new HashMap<String, Boolean>());
	protected Map<String, String> specialRoles = (Map<String, String>) Collections.synchronizedMap(new HashMap<String, String>());
	protected boolean defaultDenyCommands = false;
	protected boolean defaultDenyCategories = false;
	protected boolean defaultDenyRoles = false;
	protected boolean defaultDenyMembers = false;
	protected boolean defaultDenyChannels = false;
	
	
	//protected String customPrefix = null;
	//protected String customOptionsPrefix = null;
	
	public ModelGuild(Guild guild) {
		id=guild.getId();
		lastKnownName=guild.getName();
		joined=true;
		specialRoles.put("admin", null);
	}
	public ModelGuild(String id) {
		this.id = id;
	}
	public ModelGuild(String id, String lastKnownName, Language language) {
		this.id = id;
		this.lastKnownName = lastKnownName;
		this.language = language;
		joined=true;
		specialRoles.put("admin", null);
	}
	public String getId() {
		return id;
	}
	public long getIdAsLong() {
		return Long.parseLong(id);
	}
	public String getLastKnownName() {
		return lastKnownName;
	}
	public void setLastKnownName(String lastKnownName) {
		this.lastKnownName = lastKnownName;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public boolean isJoined() {
		return joined;
	}
	public void setJoined(boolean joined) {
		this.joined = joined;
	}
	public Map<String, Boolean> getAllowedCommands() {
		return allowedCommands;
	}
	public void setAllowedCommands(Map<String, Boolean> allowedCommands) {
		this.allowedCommands = allowedCommands;
	}
	public Map<String, Boolean> getAllowedCategories() {
		return allowedCategories;
	}
	public void setAllowedCategories(Map<String, Boolean> allowedCategories) {
		this.allowedCategories = allowedCategories;
	}
	public Map<String, Boolean> getAllowedRoles() {
		return allowedRoles;
	}
	public void setAllowedRoles(Map<String, Boolean> allowedRoles) {
		this.allowedRoles = allowedRoles;
	}
	public Map<String, Boolean> getAllowedMembers() {
		return allowedMembers;
	}
	public void setAllowedMembers(Map<String, Boolean> allowedMembers) {
		this.allowedMembers = allowedMembers;
	}
	public Map<String, Boolean> getAllowedChannels() {
		return allowedChannels;
	}
	public void setAllowedChannels(Map<String, Boolean> allowedChannels) {
		this.allowedChannels = allowedChannels;
	}
	public Map<String, String> getSpecialRoles() {
		return specialRoles;
	}
	public void setSpecialRoles(Map<String, String> specialRoles) {
		this.specialRoles = specialRoles;
	}
	public void setSpecialRole(String roleFunction, String roleId) {
		specialRoles.put(roleFunction, roleId);
	}
	public String getSpecialRole(String roleFunction) {
		return specialRoles.get(roleFunction);
	}
	public boolean isDefaultDenyCommands() {
		return defaultDenyCommands;
	}
	public void setDefaultDenyCommands(boolean defaultDenyCommands) {
		this.defaultDenyCommands = defaultDenyCommands;
	}
	public boolean isDefaultDenyCategories() {
		return defaultDenyCategories;
	}
	public void setDefaultDenyCategories(boolean defaultDenyCategories) {
		this.defaultDenyCategories = defaultDenyCategories;
	}
	public boolean isDefaultDenyRoles() {
		return defaultDenyRoles;
	}
	public void setDefaultDenyRoles(boolean defaultDenyRoles) {
		this.defaultDenyRoles = defaultDenyRoles;
	}
	public boolean isDefaultDenyMembers() {
		return defaultDenyMembers;
	}
	public void setDefaultDenyMembers(boolean defaultDenyMembers) {
		this.defaultDenyMembers = defaultDenyMembers;
	}
	public boolean isDefaultDenyChannels() {
		return defaultDenyChannels;
	}
	public void setDefaultDenyChannels(boolean defaultDenyChannels) {
		this.defaultDenyChannels = defaultDenyChannels;
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
		ModelGuild other = (ModelGuild) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public boolean isCommandAllowed(String cmdId) {
		Boolean b = allowedCommands.get(cmdId);
		if(b!=null)
			return b.booleanValue();
		return !defaultDenyCommands;
	}
	public boolean isCategoryAllowed(String categoryId) {
		Boolean b = allowedCategories.get(categoryId);
		if(b!=null)
			return b.booleanValue();
		return !defaultDenyCategories;
	}
	public boolean isRoleAllowed(String roleId) {
		Boolean b = allowedRoles.get(roleId);
		if(b!=null)
			return b.booleanValue();
		return !defaultDenyRoles;
	}
	public boolean isMemberAllowed(String memberId) {
		Boolean b = allowedMembers.get(memberId);
		if(b!=null)
			return b.booleanValue();
		return !defaultDenyMembers;
	}
	public boolean isChannelAllowed(String channelId) {
		Boolean b = allowedChannels.get(channelId);
		if(b!=null)
			return b.booleanValue();
		return !defaultDenyChannels;
	}
	public void setAllowedCommand(String cmdId, boolean allowed) {
		allowedCommands.put(cmdId, allowed);
	}
	public void setAllowedCategory(String categoryId, boolean allowed) {
		allowedCategories.put(categoryId, allowed);
	}
	public void setAllowedRole(String roleId, boolean allowed) {
		allowedRoles.put(roleId, allowed);
	}
	public void setAllowedMember(String memberId, boolean allowed) {
		allowedMembers.put(memberId, allowed);
	}
	public void setAllowedChannel(String channelId, boolean allowed) {
		allowedChannels.put(channelId, allowed);
	}
}
