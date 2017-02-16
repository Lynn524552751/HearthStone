package com.mgr.serviceImpl.game.live;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.abs.AbsGlobal;
import com.mgr.bean.LiveHost;
import com.mgr.util.JsonUtil;
import com.mgr.util.JsoupUtil;

public class LiveCrawler {
	/**
	 * 从 URL 直接加载 HTML 文档
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<LiveHost> getHostsByUrl(String url,
			Map<String, String> map) throws IOException {
		ArrayList<LiveHost> list = new ArrayList<LiveHost>();
		try {
			// Jsoup 解析
			// Mozilla
			Document doc = JsoupUtil.getJsoupDocument(url);

			// System.out.println(doc.body().toString());
			Elements listDiv = doc.select(map.get("div"));
			for (Element element : listDiv) {
				LiveHost liveHost = new LiveHost();
				liveHost.setName(element.select(map.get("name")).first().text()
						.trim());
				liveHost.setTitle(element.select(map.get("title")).first()
						.text().trim());
				liveHost.setUrl(element.select(map.get("href"))
						.attr("abs:href").trim());
				liveHost.setNumber(parseNumber(element.select(map.get("num"))
						.first().text().trim()));
				liveHost.setImg(element.select("img").attr(map.get("img"))
						.trim());
				list.add(liveHost);
				// System.out.println(liveHost.getName());
			}
		} catch (Exception e) {
			AbsGlobal.getServiceLogger()
					.error("getHostsByUrl ：" + e.toString());
			e.printStackTrace();
		} finally {

		}
		return list;
	}

	/**
	 * 从 Ajax 直接加载 HTML 文档
	 * 
	 * @param <T>
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<LiveHost> getHostsByAjax(String url,
			Map<String, String> map) throws IOException {
		ArrayList<LiveHost> list = new ArrayList<LiveHost>();
		try {
			// Jsoup 解析
			Response res = JsoupUtil.getJsoupResponse(url);
			String body = res.body();
			// System.out.println(body.toString());
			// parseJsonToMap
			Map<String, Object> maps = JsonUtil.parseJsonToMap(body);
			List<Map<String, Object>> dataMap = (List<Map<String, Object>>) maps
					.get("data");
			for (Map<String, Object> item : dataMap) {
				LiveHost liveHost = new LiveHost();
				liveHost.setName(item.get(map.get("name")).toString());
				liveHost.setNumber(parseNumber(item.get(map.get("num"))
						.toString()));
				liveHost.setTitle(item.get(map.get("title")).toString());
				liveHost.setImg(item.get(map.get("img")).toString());
				liveHost.setUrl(map.get("boot") + "/"
						+ item.get(map.get("href")).toString());
				list.add(liveHost);
			}
		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error(
					"getHostsByAjax ：" + e.toString());
			e.printStackTrace();
		} finally {

		}
		return list;
	}

	/**
	 * 转换数字中文部分
	 * 
	 * @param num
	 * @return
	 */
	private static Integer parseNumber(String num) {
		int index = num.indexOf("万");
		int i = 0;
		if (index != -1) {
			num = num.substring(0, index);
			i = (int) Float.parseFloat(num) * 10000;
		} else {
			i = Integer.parseInt(num);
		}
		return i;
	}
}
