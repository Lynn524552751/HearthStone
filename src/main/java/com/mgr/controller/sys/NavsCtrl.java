package com.mgr.controller.sys;

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
import com.mgr.interf.SysConstants;
import com.mgr.service.sys.NavsService;

@Controller
@RemoteProxy
public class NavsCtrl extends AbsDBExecutor implements SysConstants {
	public static final String APP_NAVS = "navs";
	@Resource
	private NavsService navsService;

	@RequestMapping(APP_PATH + APP_NAVS)
	public String addrun(HttpServletRequest request) {
		request.setAttribute("navApp", APP_NAVS);
		return Global.getAppModule(APP_NAVS, request);
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
		ArrayList<LiveHost> list = navsService.getLiveHosts1(type, per)
				.getData();
		jsonResult.setOk(true);
		jsonResult.setData(list);
		return jsonResult;
	}
}
