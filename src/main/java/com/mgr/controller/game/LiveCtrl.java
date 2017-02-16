package com.mgr.controller.game;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abs.AbsDBExecutor;
import com.basic.util.Global;
import com.mgr.bean.JsonResult;
import com.mgr.bean.LiveHost;
import com.mgr.interf.GameConstants;
import com.mgr.interf.SysConstants;
import com.mgr.service.game.LiveService;

@Controller
@RemoteProxy
public class LiveCtrl extends AbsDBExecutor implements SysConstants,
		GameConstants {
	public static final String APP_LIVE = "live";
	@Resource
	private LiveService liveService;

	@RequestMapping(APP_PATH + APP_LIVE)
	public String addrun(HttpServletRequest request) {
		return Global.getAppModule(APP_LIVE, request);
	}

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RemoteMethod
	public JsonResult getLiveHosts(HttpServletRequest request) {
		int per = 32;
		String type = "all";
		JsonResult jsonResult = new JsonResult();
		ArrayList<LiveHost> list = liveService.loadLiveHosts(type, per)
				.getData();
		jsonResult.setOk(true);
		jsonResult.setData(list);
		return jsonResult;
	}
}
