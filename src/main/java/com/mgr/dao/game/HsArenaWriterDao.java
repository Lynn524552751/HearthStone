package com.mgr.dao.game;

import com.mgr.entity.HsArena;

public interface HsArenaWriterDao {
	/**
	 * 插入或保存数据
	 * 
	 * @param hsArena
	 * @return
	 */
	public void saveOrUpdate(HsArena hsArena);

	/**
	 * 删除数据
	 * 
	 * @param hsArena
	 * @return
	 */
	public void delete(int id);
}
