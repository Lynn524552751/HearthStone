package com.mgr.util;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.abs.AbsGlobal;

public class JsoupUtil {
	private static Document doc;
	private static Response res;

	public static Document getJsoupDocument(String url) {

		try {
			doc = Jsoup.connect(url).data("query", "Java").userAgent("Mozilla")
					.cookie("auth", "token").timeout(3000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			AbsGlobal.getServiceLogger().error(
					"getJsoupDocument ：" + e.toString());
			e.printStackTrace();
		}
		return doc;
	}

	public static Response getJsoupResponse(String url) {
		try {
			res = Jsoup
					.connect(url)
					.header("Accept", "*/*")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language",
							"zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
					.timeout(3000).ignoreContentType(true).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			AbsGlobal.getServiceLogger().error(
					"getJsoupResponse ：" + e.toString());
			e.printStackTrace();
		}
		return res;
	}
}
