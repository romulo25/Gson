package com.netmind.presentation;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupExample {

	public static void main(String[] args) throws IOException {
		// To print €
		// It doesn't work when I execute java -jar from commandline
		// or you can execute this command in cmd "chcp 1252"
		// ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "chcp",
		// "1252")
		// .inheritIO();
		// Process p = pb.start();
		/*
		 * try { p.waitFor(); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */

		// String rawString = "Imprimiendo euros €";
		/*
		 * byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);
		 * 
		 * String utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);
		 * System.out.println(utf8EncodedString);
		 */
		System.out.println("Imprimiendo euros €");

		/*
		 * String rawString2 = "Imprimiendo euros €"; ByteBuffer buffer =
		 * StandardCharsets.UTF_8.encode(rawString2);
		 * 
		 * String utf8EncodedString2 = StandardCharsets.UTF_8.decode(buffer)
		 * .toString(); System.out.println(utf8EncodedString2);
		 */

		String url = "http://en.wikipedia.org/";
		print("Fetching %s...", url);

		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");

		print("\nMedia: (%d)", media.size());
		for (Element src : media) {
			if (src.normalName().equals("img"))
				print(" * %s: <%s> %sx%s (%s)", src.tagName(),
						src.attr("abs:src"), src.attr("width"),
						src.attr("height"), trim(src.attr("alt"), 20));
			else
				print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
		}

		print("\nImports: (%d)", imports.size());
		for (Element link : imports) {
			print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"),
					link.attr("rel"));
		}

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			print(" * a: <%s>  (%s)", link.attr("abs:href"),
					trim(link.text(), 35));
		}
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

}
