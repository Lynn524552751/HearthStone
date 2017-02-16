package com.mgr.service.game;

import java.util.ArrayList;

import com.mgr.bean.HsArenaVo;
import com.mgr.bean.ServiceResult;

public interface SummaryService {

	public ServiceResult<HsArenaVo> getTotal();

	public ServiceResult<ArrayList<HsArenaVo>> getPlayedPerOccupation();

	public ServiceResult<ArrayList<HsArenaVo>> getCountPerWins();
}
