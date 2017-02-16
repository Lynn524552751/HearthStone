package com.mgr.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.abs.AbsDBExecutor;
import com.basic.util.Global;
import com.mgr.entity.SysNav;
import com.mgr.entity.SysUser;
import com.mgr.interf.SysConstants;
import com.mgr.service.LoginService;

@Controller
public class LoginCtrl extends AbsDBExecutor implements SysConstants {
	@Resource
	private LoginService loginService;

	/**
	 * 登录控制器
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(APP_PATH + APP_LOGIN)
	public String login(HttpServletRequest request) {
		return Global.getAppModule(APP_LOGIN, request);
	}

	/**
	 * 用户登陆请求
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = APP_PATH + APP_LOGIN, method = RequestMethod.POST)
	public String doLogin(@RequestParam("userName") String userName,
			@RequestParam("passWord") String passWord,
			HttpServletRequest request) {
		Global.getServiceLogger().info("登陆请求：" + userName);
		SysUser user = loginService.login(userName, passWord).getData();
		if (user == null) {
			request.setAttribute(LOGIN_R_FAILURE, LANG_LOGIN_FAILURE);
			return Global.getAppModule(APP_LOGIN, request);
		} else {
			List<SysNav> navs = loginService.loadUserNavList(user).getData();
			request.getSession().setAttribute(SESSION_NAVS, navs);
			request.getSession().setAttribute(SESSION_USER, user);
			return Global.retAppModule(APP_INDEX, request);
		}
	}

	/**
	 * 登出请求
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(APP_PATH + APP_LOGOUT)
	public String doLogout(HttpServletRequest request) {
		SysUser user = (SysUser) request.getSession()
				.getAttribute(SESSION_USER);
		if (user != null) {
			Global.getServiceLogger().debug("用户登出：" + user.getUsername());
			request.getSession().removeAttribute(SESSION_USER);
			request.getSession().removeAttribute(SESSION_NAVS);
		}
		request.getSession().invalidate();

		return Global.retAppModule(APP_LOGIN, request);
	}

}
