package com.mgr.service.game;

import java.util.List;

import com.mgr.bean.ServiceResult;
import com.mgr.entity.HsArena;

public interface RecordService {
	/**
	 * 插入或更新数据
	 * 
	 * @param hsArena
	 * @return
	 */
	public ServiceResult<HsArena> saveOrUpdate(HsArena hsArena);

	/**
	 * 删除数据
	 * 
	 * @param hsArena
	 * @return
	 */
	public ServiceResult<HsArena> delete(int id);

	/**
	 * 获取数据
	 * 
	 * @param id
	 * @return
	 */
	public ServiceResult<HsArena> loadData(int id);

	/**
	 * 获取数据列表
	 * 
	 * @param id
	 * @return
	 */
	public ServiceResult<List<HsArena>> loadList(int size, int start,
			String order, String sort, String occupation, String wins);

	/**
	 * 查询记录总数
	 */
	public ServiceResult<Integer> loadListCount();
}
