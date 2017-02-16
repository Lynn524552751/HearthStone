package com.mgr.controller;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abs.AbsDBExecutor;
import com.basic.util.Global;
import com.mgr.interf.GameConstants;
import com.mgr.interf.SysConstants;

@Controller
@RemoteProxy
public class TestCtrl extends AbsDBExecutor implements SysConstants,
		GameConstants {
	public static final String APP_TEST = "test";

	@RequestMapping(APP_PATH + APP_TEST)
	public String addrun(HttpServletRequest request) {
		request.setAttribute("navApp1", APP_TEST);
		return Global.getAppModule(APP_TEST, request);
	}

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */

}
