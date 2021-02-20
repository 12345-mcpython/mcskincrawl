package com.mc;

import com.google.gson.Gson;

import bean.Profile;
import bean.HttpUtils;

public class LittleskinDownload {
	public static Object profile(String t) {
		String json = t;
		Gson gson = new Gson();
		Profile user = gson.fromJson(json, Profile.class);
		return user;
	}

	public static void little(String name) throws Exception {
		String profile_url = String.format("https://mcskin.littleservice.cn/csl/%s.json", name);
		String profile = Request.get(profile_url);
		Profile profile1 = (Profile) LittleskinDownload.profile(profile);
		if (profile1 == null) {
			throw new Exception("The " + name + " skin not found!");
		}
		String skin_id = profile1.getSkins().get("default");
		String skin_url = String.format("https://mcskin.littleservice.cn/csl/textures/%s", skin_id);
		HttpUtils.saveImageToDisk(skin_url, name, System.getProperty("user.dir"));
	}
}
