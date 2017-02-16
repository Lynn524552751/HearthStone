package com.mgr.controller.sys;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.abs.AbsDBExecutor;
import com.basic.util.Global;
import com.mgr.interf.SysConstants;
import com.mgr.service.sys.UserService;

@Controller
@RemoteProxy
public class UserCtrl extends AbsDBExecutor implements SysConstants {
	public static final String APP_USER = "user";
	@Resource
	private UserService userService;

	@RequestMapping(APP_PATH + APP_USER)
	public String root(HttpServletRequest request) {
		return Global.getAppModule(APP_USER, request);
	}

	@RequestMapping(APP_PATH + APP_USER + APP_INDEX)
	public String index(HttpServletRequest request) {
		return Global.getAppModule(APP_USER + "/" + APP_LIST, request);
	}

	@RequestMapping(APP_PATH + APP_USER + APP_VIEW)
	public String view(HttpServletRequest request) {
		return Global.getAppModule(APP_USER + "/" + APP_VIEW, request);
	}

	@RequestMapping(APP_PATH + APP_USER + APP_ADD)
	public String add(HttpServletRequest request) {
		return Global.getAppModule(APP_USER + "/" + APP_VIEW, request);
	}

	@RequestMapping(value = APP_PATH + APP_USER, method = RequestMethod.POST)
	public String edit(@RequestParam("action") String action,
			@RequestParam("id") Integer id, HttpServletRequest request) {
		return Global.getAppModule(APP_USER + "/" + APP_VIEW, request);
	}
}
