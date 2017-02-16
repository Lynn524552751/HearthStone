package com.mgr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.abs.AbsDBExecutor;
import com.abs.AbsGlobal;
import com.basic.util.Global;
import com.mgr.interf.SysConstants;

@Controller
public class IndexCtrl extends AbsDBExecutor implements HandlerInterceptor,
		SysConstants {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	/**
	 * 首页管理
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(APP_PATH + APP_INDEX)
	public String index(HttpServletRequest request) {
		request.setAttribute("navApp1", APP_INDEX);
		return Global.getAppModule(APP_INDEX, request);
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestUrl = request.getRequestURI().replace(
				request.getContextPath(), "");
		if (requestUrl.indexOf("dwr") != -1) {
			return true;
		}
		AbsGlobal.getServiceLogger().debug(
				"[IndexCtrl.preHandle]拦截请求：" + requestUrl);
		// check user privilege
		// if (request.getSession().getAttribute(SESSION_USER) == null) {
		// response.sendRedirect("/"
		// + Global.getServiceInfo().getServiceName() + APP_PATH
		// + APP_LOGIN);
		// } else {
		//
		// }
		// navList = ServMgr.loadMemberNav(dbSes, request);
		String[] app = requestUrl.split("/");
		for (int i = 1; i < app.length; i++) {
			for (String nav : app) {
				request.setAttribute("navApp" + i, nav);
			}
		}
		request.setAttribute("navApp", requestUrl.substring(1));
		return true;
	}
}
