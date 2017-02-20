package com.mgr.controller.game;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abs.AbsDBExecutor;
import com.basic.util.Global;
import com.mgr.bean.JsonResult;
import com.mgr.bean.ServiceResult;
import com.mgr.entity.HsArena;
import com.mgr.interf.GameConstants;
import com.mgr.interf.SysConstants;
import com.mgr.service.game.RecordService;
import com.mgr.util.JsonUtil;

@Controller
@RemoteProxy
public class RecordCtrl extends AbsDBExecutor implements SysConstants,
		GameConstants {
	public static final String APP_RECORD = "record";
	@Resource
	private RecordService recordService;

	@RequestMapping(APP_PATH + APP_RECORD)
	public String record(HttpServletRequest request) {
		return list(request);
	}

	/**
	 * 竞技场数据 列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = APP_PATH + APP_RECORD, params = APP_LIST, method = RequestMethod.GET)
	public String list(HttpServletRequest request) {
		request.setAttribute(APP_ACTION, APP_LIST);
		return Global.getAppModule(APP_RECORD, request);
	}

	/**
	 * 竞技场数据 添加页面
	 * 
	 * @return
	 */
	@RequestMapping(value = APP_PATH + APP_RECORD, params = APP_ADD, method = RequestMethod.GET)
	public String add(HttpServletRequest request) {
		request.setAttribute(APP_ACTION, APP_ADD);
		return Global.getAppModule(APP_RECORD, request);
	}

	/**
	 * 竞技场数据 查看页面
	 * 
	 * @return
	 */
	@RequestMapping(value = APP_PATH + APP_RECORD, params = APP_VIEW, method = RequestMethod.GET)
	public String view(@RequestParam("id") Integer id,
			HttpServletRequest request) {
		request.setAttribute(APP_DATAID, id);
		request.setAttribute(APP_ACTION, APP_VIEW);
		return Global.getAppModule(APP_RECORD, request);
	}

	/**
	 * 竞技场数据 编辑页面
	 * 
	 * @return
	 */
	@RequestMapping(value = APP_PATH + APP_RECORD, params = APP_EDIT, method = RequestMethod.GET)
	public String edit(@RequestParam("id") Integer id,
			HttpServletRequest request) {
		request.setAttribute(APP_DATAID, id);
		request.setAttribute(APP_ACTION, APP_EDIT);
		return Global.getAppModule(APP_RECORD, request);
	}

	/**
	 * 添加或修改数据
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RemoteMethod
	public JsonResult save(HsArena hsArena, HttpServletRequest request) {
		ServiceResult<HsArena> serviceResult = recordService
				.saveOrUpdate(hsArena);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(serviceResult.getData());
		jsonResult.setOk(serviceResult.isOk());
		return jsonResult;
	}

	/**
	 * 删除数据
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RemoteMethod
	public JsonResult del(Integer id, HttpServletRequest request) {
		ServiceResult<HsArena> serviceResult = recordService.delete(id);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(serviceResult.getData());
		jsonResult.setOk(serviceResult.isOk());
		return jsonResult;
	}

	/**
	 * 加载数据
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RemoteMethod
	public JsonResult loadData(Integer id, HttpServletRequest request) {
		ServiceResult<HsArena> serviceResult = recordService.loadData(id);
		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(serviceResult.getData());
		jsonResult.setOk(serviceResult.isOk());
		return jsonResult;
	}

	/**
	 * 加载数据列表
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(APP_PATH + APP_RECORD + "/loadList")
	public void loadList(HttpServletRequest request,
			HttpServletResponse response) {
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset"));
		String order = request.getParameter("order");
		String sort = request.getParameter("sort");
		String occupation = request.getParameter("occupation");
		String wins = request.getParameter("wins");

		ServiceResult<List<HsArena>> serviceResult = recordService.loadList(
				limit, offset, order, sort, occupation, wins);
		ServiceResult<Integer> count = recordService.loadListCount();
		JsonResult jsonResult = new JsonResult();
		// jsonResult.setData(serviceResult.getData());
		// jsonResult.setOk(serviceResult.isOk());
		String list = JsonUtil.parseListToJson(serviceResult.getData());
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("rows", list);
		jsonObj.put("total", count.getData());
		try {
			response.getWriter().write(jsonObj.toString());
		} catch (IOException e) {
			Global.getServiceLogger().error("[ServAnalysis.adDeposit]：");
			e.printStackTrace();
		}
	}
}
