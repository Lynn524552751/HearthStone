package com.mgr.serviceImpl.game;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mgr.bean.HsArenaVo;
import com.mgr.bean.ServiceResult;
import com.mgr.dao.game.HsArenaReaderDao;
import com.mgr.interf.GameConstants;
import com.mgr.interf.SysConstants;
import com.mgr.service.game.SummaryService;

@Service("summaryService")
public class SummaryServiceImpl implements SummaryService, SysConstants,
		GameConstants {
	@Resource
	private HsArenaReaderDao hsArenaReaderDao;

	@Override
	public ServiceResult<HsArenaVo> getTotal() {
		ServiceResult<HsArenaVo> serviceResult = new ServiceResult<HsArenaVo>();

		HsArenaVo hsArenaVo = new HsArenaVo();
		//
		hsArenaVo.setCount(hsArenaReaderDao.queryHsArenaCount());
		//
		hsArenaVo.setWinsSum(hsArenaReaderDao.queryHsArenaSumByField("wins"));
		//
		hsArenaVo.setLossesSum(hsArenaReaderDao
				.queryHsArenaSumByField("losses"));
		//
		hsArenaVo.setGoldSum(hsArenaReaderDao.queryHsArenaSumByField("gold"));
		//
		hsArenaVo.setDustSum(hsArenaReaderDao.queryHsArenaSumByField("dust"));
		//
		hsArenaVo.setName(HS_OCCUPATION_ALL);
		//
		serviceResult.setData(hsArenaVo);

		return serviceResult;
	}

	@Override
	public ServiceResult<ArrayList<HsArenaVo>> getPlayedPerOccupation() {
		ServiceResult<ArrayList<HsArenaVo>> serviceResult = new ServiceResult<ArrayList<HsArenaVo>>();
		ArrayList<HsArenaVo> hsArenaVoList = new ArrayList<HsArenaVo>();

		for (String occupation : HS_OCCUPATION_LIST) {
			HsArenaVo hsArenaVo = new HsArenaVo();
			hsArenaVo.setName(occupation);
			hsArenaVo.setCount(hsArenaReaderDao
					.queryHsArenaCountByOccupation(occupation));

			hsArenaVo.setWinsSum(hsArenaReaderDao
					.queryHsArenaSumByFieldAndOccupation("wins", occupation));
			hsArenaVo.setLossesSum(hsArenaReaderDao
					.queryHsArenaSumByFieldAndOccupation("losses", occupation));
			hsArenaVoList.add(hsArenaVo);
		}
		serviceResult.setData(hsArenaVoList);
		return serviceResult;
	}

	@Override
	public ServiceResult<ArrayList<HsArenaVo>> getCountPerWins() {
		ServiceResult<ArrayList<HsArenaVo>> serviceResult = new ServiceResult<ArrayList<HsArenaVo>>();
		ArrayList<HsArenaVo> hsArenaVoList = new ArrayList<HsArenaVo>();

		for (int i = 0; i <= HS_MAX_WINS; i++) {
			HsArenaVo hsArenaVo = new HsArenaVo();
			hsArenaVo.setName(String.valueOf(i));
			hsArenaVo.setCount(hsArenaReaderDao.queryHsArenaCountByWins(i));

			hsArenaVoList.add(hsArenaVo);
		}
		serviceResult.setData(hsArenaVoList);
		return serviceResult;
	}

}
