package com.mgr.serviceImpl.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import com.mgr.bean.LiveHost;
import com.mgr.bean.ServiceResult;
import com.mgr.interf.GameConstants;
import com.mgr.interf.SysConstants;
import com.mgr.service.game.LiveService;
import com.mgr.serviceImpl.game.live.LiveCrawler;

@Service("liveService")
public class LiveServiceImpl implements LiveService, SysConstants,
		GameConstants {
	public static final String douyuUrl = "https://www.douyu.com/directory/game/How";
	public static final String douyuBoot = "https://www.douyu.com";
	public final static Map<String, String> douyuSelectMap = new HashMap<String, String>();
	static {
		douyuSelectMap.put("href", "a[href]");
		douyuSelectMap.put("title", "h3.ellipsis");
		douyuSelectMap.put("name", "span.dy-name");
		douyuSelectMap.put("num", "span.dy-num");
		douyuSelectMap.put("img", "data-original");
		douyuSelectMap.put("div", "a.play-list-link");
		douyuSelectMap.put("boot", douyuBoot);
	}
	public static final String pandaUrl = "http://www.panda.tv/cate/hearthstone";
	public static final String pandaBoot = "http://www.panda.tv";
	public final static Map<String, String> pandaSelectMap = new HashMap<String, String>();
	static {
		pandaSelectMap.put("href", "a[href]");
		pandaSelectMap.put("title", "div.video-title");
		pandaSelectMap.put("name", "span.video-nickname");
		pandaSelectMap.put("num", "span.video-number");
		pandaSelectMap.put("img", "data-original");
		pandaSelectMap.put("div", "li.video-list-item");
		pandaSelectMap.put("boot", pandaBoot);
	}
	public static final String quanminUrl = "http://www.quanmin.tv/game/hearthstone";
	public static final String quanminAjax = "http://www.quanmin.tv/json/categories/hearthstone/list.json?_t=";
	public static final String quanminBoot = "http://www.quanmin.tv";
	public final static Map<String, String> quanminSelectMap = new HashMap<String, String>();
	static {
		// quanminSelectMap.put("href", "a.w-video_a[href]");
		// quanminSelectMap.put("title", "h3.w-video_title");
		// quanminSelectMap.put("name", "span.w-video_nick");
		// quanminSelectMap.put("num", "span.w-video_view-num");
		// quanminSelectMap.put("img", "img");
		// quanminSelectMap.put("div", "li.fadeInUp");
		// quanminSelectMap.put("boot", quanminBoot);
		quanminSelectMap.put("href", "no");
		quanminSelectMap.put("title", "title");
		quanminSelectMap.put("name", "nick");
		quanminSelectMap.put("num", "view");
		quanminSelectMap.put("img", "thumb");
		quanminSelectMap.put("div", "");
		quanminSelectMap.put("boot", quanminBoot);
	}
	public static final String huyaUrl = "http://www.huya.com/g/393";
	public static final String huyaBoot = "http://www.huya.com";
	public final static Map<String, String> huyaSelectMap = new HashMap<String, String>();
	static {
		huyaSelectMap.put("href", "a[href]");
		huyaSelectMap.put("title", "a.new-clickstat");
		huyaSelectMap.put("name", "i.nick");
		huyaSelectMap.put("num", "i.js-num");
		huyaSelectMap.put("img", "data-original");
		huyaSelectMap.put("div", "li.game-live-item");
		huyaSelectMap.put("boot", huyaBoot);
	}

	@Override
	public ServiceResult<ArrayList<LiveHost>> loadLiveHosts(String type, int per) {
		ServiceResult<ArrayList<LiveHost>> serviceResult = new ServiceResult<ArrayList<LiveHost>>();
		ArrayList<LiveHost> list = new ArrayList<LiveHost>();

		String time = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");

		try {
			// douyu
			list.addAll(LiveCrawler.getHostsByUrl(douyuUrl, douyuSelectMap));
			// panda
			list.addAll(LiveCrawler.getHostsByUrl(pandaUrl, pandaSelectMap));
			// quanmin
			list.addAll(LiveCrawler.getHostsByAjax(quanminAjax + time,
					quanminSelectMap));
			// huya
			list.addAll(LiveCrawler.getHostsByUrl(huyaUrl, huyaSelectMap));
		} catch (Exception e) {
		}
		// desc
		Collections.sort(list, new Comparator() {
			@Override
			public int compare(Object a, Object b) {
				int one = ((LiveHost) a).getNumber();
				int two = ((LiveHost) b).getNumber();
				// desc
				return two - one;
			}
		});
		// System.out.println("size" + list.size());
		ArrayList<LiveHost> result = new ArrayList<LiveHost>();
		if (list.size() > per) {
			for (int i = 0; i < per; i++) {
				result.add(list.get(i));
			}
		} else {
			result = list;
		}
		serviceResult.setData(result);
		serviceResult.setOk(true);
		return serviceResult;
	}
}
