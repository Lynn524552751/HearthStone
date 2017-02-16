package com.mgr.dao.game;

import java.util.List;

import com.mgr.entity.HsArena;

public interface HsArenaReaderDao {
	/**
	 * 查询id=?的记录
	 * 
	 * @param id
	 * @return
	 */
	public HsArena findHsArenaById(int id);

	/**
	 * 查询数据列表
	 * 
	 * @param id
	 * @return
	 */
	public List<HsArena> findHsArenaList(int size, int start, String order,
			String sort, String occupation, String wins);

	/**
	 * 查询记录总数
	 * 
	 * @param hsArena
	 * @return
	 */
	public Integer queryHsArenaCount();

	/**
	 * 查询某职业记录总数
	 * 
	 * @param hsArena
	 * @return
	 */
	public Integer queryHsArenaCountByOccupation(String occupation);

	/**
	 * 查询某胜场记录总数
	 * 
	 * @param hsArena
	 * @return
	 */
	public Integer queryHsArenaCountByWins(Integer wins);

	/**
	 * 查询某字段总和
	 * 
	 * @param hsArena
	 * @return
	 */
	public int queryHsArenaSumByField(String field);

	/**
	 * 查询某字段总和 职业分组
	 * 
	 * @param hsArena
	 * @return
	 */
	public int queryHsArenaSumByFieldAndOccupation(String field,
			String occupation);
}
