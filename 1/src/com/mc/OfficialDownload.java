package com.mc;

import java.util.Map;
import java.util.Base64;
import com.google.gson.Gson;
import bean.Id;
import bean.Session;
import bean.Value;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import bean.HttpUtils;

public class OfficialDownload {
	public static String B64(String t) {
		byte[] decoded = Base64.getDecoder().decode(t);
		String decodeStr = new String(decoded);
		return decodeStr;
	}

	public static Object id(String t) {
		String json = t;
		Gson gson = new Gson();
		Id user = gson.fromJson(json, Id.class);
		return user;

	}

	public static Object u_s(String t) {
		String json = t;
		Gson gson = new Gson();
		Session user = gson.fromJson(json, Session.class);
		return user;
	}

	public static Object textures(String t) {
		String json = t;
		Gson gson = new Gson();
		Value user = gson.fromJson(json, Value.class);
		return user;
	}

	public static String regex_url(String t) {
		Pattern p = Pattern.compile("[a-zA-z]+://[^\\s]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(t);
		m.find();
		return m.group();

	}

	public static String l() {
		return System.getProperty("user.dir");
	}

	@SuppressWarnings("unchecked")
	public static void official(String name) throws Exception {
		String u = "https://api.mojang.com/users/profiles/minecraft/";
		String s = Request.get(u + name);
		Id a = (Id) OfficialDownload.id(s);
		if (a == null) {
			throw new Exception("The " + name + " skin not found!");
		}
		String b64u = "https://sessionserver.mojang.com/session/minecraft/profile/";
		String user_session = Request.get(b64u + a.getId());
		Session b = (Session) OfficialDownload.u_s(user_session);
		Map<String, String> map = (Map<String, String>) b.getpro().get(0);
		Value c = (Value) OfficialDownload.textures(OfficialDownload.B64(map.get("value")));
		HttpUtils.saveImageToDisk(OfficialDownload.regex_url(c.gettextures().toString()).replace("}}", ""), a.getName(),
				l());
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: mcskin <minecraft user name>");
		}
		for (int i = 0; i < args.length; i++) {
			try {
				official(args[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
