package bean;

import java.util.Map;

public class Profile {
	private String username;
	private Map<String, String> skins;
	private String cape;

	public Map<String, String> getSkins() {
		return skins;
	}

	public void setSkins(Map<String, String> skins) {
		this.skins = skins;
	}

	public String getCape() {
		return cape;
	}

	public void setCape(String cape) {
		this.cape = cape;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
