package com.mc;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Base64;
import com.google.gson.Gson;
import bean.Id;
import bean.Session;
import bean.Value;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import bean.HttpUtils;

public class McSkinCrawl {
	public static String sendGet(String url, Map<String, String> param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			if (param != null && param.size() > 0) {
				urlNameString = urlNameString + "?";
				for (String key : param.keySet()) {
					urlNameString = urlNameString + key + "=" + param.get(key);
				}
			}

			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static String B64(String t) {
		byte[] decoded = Base64.getDecoder().decode(t);
		String decodeStr = new String(decoded);
		return decodeStr;
	}

	public static Object id(String t) {
		String json = t;
		Gson gson = new Gson();
		Id user = gson.fromJson(json, Id.class);
		System.out.println(user);
		return user;

	}

	public static Object u_s(String t) {
		String json = t;
		Gson gson = new Gson();
		Session user = gson.fromJson(json, Session.class);
		System.out.println(user);
		return user;
	}

	public static Object textures(String t) {
		String json = t;
		Gson gson = new Gson();
		Value user = gson.fromJson(json, Value.class);
		System.out.println(user);
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
	public static void gen(String name) throws Exception {
		String u = "https://api.mojang.com/users/profiles/minecraft/";
		String s = McSkinCrawl.sendGet(u + name, null);
		// System.out.println(s);
		// System.out.println(args[i]);
		Id a = (Id) McSkinCrawl.id(s);
		if (a == null) {
			throw new Exception("The" + name + "skin not found!");
		}
		// System.out.println(a.getId());
		String b64u = "https://sessionserver.mojang.com/session/minecraft/profile/";
		String user_session = McSkinCrawl.sendGet(b64u + a.getId(), null);
		// System.out.println(user_session);
		Session b = (Session) McSkinCrawl.u_s(user_session);
		// System.out.println(b.getpro());
		// System.out.println(McSkinCrawl.B64(((Map<String,String>)
		// b.getpro().get(0)).get("value")));
		// System.out.println(b.getpro().get(0).getClass().toString());
		Map<String, String> map = (Map<String, String>) b.getpro().get(0);
		Value c = (Value) McSkinCrawl.textures(McSkinCrawl.B64(map.get("value")));
		// System.out.println(c.gettextures().toString());
		// System.out.println(McSkinCrawl.regex_url(c.gettextures().toString()).replace("}}",""));
		HttpUtils.saveImageToDisk(McSkinCrawl.regex_url(c.gettextures().toString()).replace("}}", ""), a.getName(),
				l());
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: mcskin <minecraft user name>");
		}
		for (int i = 0; i < args.length; i++) {
			try {
				gen(args[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
