package com.mgr.controller.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abs.AbsDBExecutor;
import com.basic.util.Global;
import com.mgr.bean.HsArenaVo;
import com.mgr.bean.JsonResult;
import com.mgr.interf.GameConstants;
import com.mgr.interf.SysConstants;
import com.mgr.service.game.SummaryService;

@Controller
@RemoteProxy
public class SummaryCtrl extends AbsDBExecutor implements SysConstants,
		GameConstants {
	public static final String APP_SUMMARY = "summary";
	@Resource
	private SummaryService summaryService;

	@RequestMapping(APP_PATH + APP_SUMMARY)
	public String summary(HttpServletRequest request) {
		return Global.getAppModule(APP_SUMMARY, request);
	}

	/**
	 * 数据概要
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RemoteMethod
	public JsonResult loadSummary(HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		Map<String, Object> map = new HashMap<String, Object>();

		HsArenaVo total = summaryService.getTotal().getData();
		map.put("total", total);
		ArrayList<HsArenaVo> played = summaryService.getPlayedPerOccupation()
				.getData();
		map.put("played", played);
		// HsArenaVo average = (HsArenaVo) summaryServ
		// .getAverageWinsPerOccupation().getData();
		// map.put("average", average);
		//
		ArrayList<HsArenaVo> wins = summaryService.getCountPerWins().getData();
		map.put("wins", wins);

		jsonResult.setOk(true);
		jsonResult.setData(map);
		return jsonResult;
	}
}
