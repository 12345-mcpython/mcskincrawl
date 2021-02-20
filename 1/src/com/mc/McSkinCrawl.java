package com.mc;

public class McSkinCrawl {
	public static void gen(String name) throws Exception {
		try {
			OfficialDownload.official(name);
		} catch (Exception e) {
			LittleskinDownload.little(name);
		}

	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("Usage: java -jar mcskincrawl-0.0.1.jar <your minecraft user name>");
		}
		for (int i = 0; i < args.length; i++) {
			gen(args[i]);
		}
	}
}
