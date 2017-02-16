package com.mgr.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.abs.AbsDBExecutor;
import com.abs.AbsGlobal;
import com.basic.util.Global;
import com.mgr.dao.sys.SysNavDao;
import com.mgr.entity.SysNav;
import com.mgr.entity.SysUser;
import com.mgr.interf.SysConstants;
import com.mgr.service.IndexService;

@Controller
public class IndexCtrl extends AbsDBExecutor implements HandlerInterceptor,
		SysConstants {
	@Resource
	private IndexService indexService;

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@RequestMapping(APP_PATH)
	public String defaultMonitor(HttpServletRequest request) {
		request.setAttribute("navApp", APP_INDEX);
		return index(request);
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
		request.setAttribute("navApp", APP_INDEX);
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
		if (request.getSession().getAttribute(SESSION_USER) == null) {
			response.sendRedirect("/"
					+ Global.getServiceInfo().getServiceName() + APP_PATH
					+ APP_LOGIN);
			return true;
		}

		if (requestUrl.indexOf("dwr") != -1) {
			return true;
		}
		// del "/"
		String navApp = requestUrl.substring(1);
		request.setAttribute("navApp", navApp);
		AbsGlobal.getServiceLogger().debug(
				"[IndexCtrl.preHandle]拦截请求：" + requestUrl);

		SysUser user = (SysUser) request.getSession()
				.getAttribute(SESSION_USER);
		List<SysNav> navs = (List<SysNav>) request.getSession().getAttribute(
				SESSION_NAVS);
		List<SysNav> activeNav = new ArrayList<SysNav>();
		int parentId = 0;
		for (SysNav nav : navs) {
			if (nav.getNavApp().equals(navApp)) {
				activeNav.add(nav);
				parentId = nav.getParentId();
			}
			while (parentId != SysNavDao.FIRSTCLASS_PARENTID) {
				for (SysNav nav2 : navs) {
					if (nav2.getId() == parentId) {
						// System.out.println(parentId);
						activeNav.add(nav2);
						parentId = nav2.getParentId();
					}
				}
			}
		}
		for (int i = activeNav.size(); i > 0; i--) {
			request.setAttribute("navApp" + i,
					activeNav.get(activeNav.size() - i));
		}
		return true;
	}
}
