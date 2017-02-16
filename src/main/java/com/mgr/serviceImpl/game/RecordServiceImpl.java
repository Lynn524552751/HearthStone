package com.mgr.serviceImpl.game;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mgr.bean.ServiceResult;
import com.mgr.dao.game.HsArenaReaderDao;
import com.mgr.dao.game.HsArenaWriterDao;
import com.mgr.entity.HsArena;
import com.mgr.interf.GameConstants;
import com.mgr.interf.SysConstants;
import com.mgr.service.game.RecordService;

@Service("recordService")
public class RecordServiceImpl implements RecordService, SysConstants,
		GameConstants {
	@Resource
	private HsArenaWriterDao hsArenaWriterDao;
	@Resource
	private HsArenaReaderDao hsArenaReaderDao;

	@Override
	public ServiceResult<HsArena> saveOrUpdate(HsArena hsArena) {
		ServiceResult<HsArena> serviceResult = new ServiceResult<HsArena>();
		hsArenaWriterDao.saveOrUpdate(hsArena);
		serviceResult.setData(hsArena);
		serviceResult.setOk(true);
		return serviceResult;
	}

	@Override
	public ServiceResult<HsArena> delete(int id) {
		ServiceResult<HsArena> serviceResult = new ServiceResult<HsArena>();
		hsArenaWriterDao.delete(id);
		serviceResult.setData(null);
		serviceResult.setOk(true);
		return serviceResult;
	}

	/**
	 * 加载数据
	 * 
	 * @param hsArena
	 * @return
	 */
	@Override
	public ServiceResult<HsArena> loadData(int id) {
		ServiceResult<HsArena> serviceResult = new ServiceResult<HsArena>();
		HsArena data = hsArenaReaderDao.findHsArenaById(id);
		serviceResult.setData(data);
		serviceResult.setOk(data != null);
		return serviceResult;
	}

	@Override
	public ServiceResult<List<HsArena>> loadList(int size, int start,
			String order, String sort, String occupation, String wins) {
		ServiceResult<List<HsArena>> serviceResult = new ServiceResult<List<HsArena>>();
		if (StringUtils.isBlank(sort)) {
			sort = "time";
		}
		if (occupation.equals("-1")) {
			occupation = null;
		}
		if (wins.equals("-1")) {
			wins = null;
		}
		List<HsArena> data = hsArenaReaderDao.findHsArenaList(size, start,
				order, sort, occupation, wins);
		serviceResult.setData(data);
		serviceResult.setOk(data != null);
		return serviceResult;
	}

	@Override
	public ServiceResult<Integer> loadListCount() {
		ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
		int data = hsArenaReaderDao.queryHsArenaCount();
		serviceResult.setData(data);
		serviceResult.setOk(data != -1);
		return serviceResult;
	}
}
