package com.mgr.dao.sys;

import java.util.List;

import com.mgr.entity.SysNav;

public interface SysNavDao {
	/**
	 * update nav
	 * 
	 * @param nav
	 * @return
	 */
	public void updateSysNav(SysNav sysNav);

	/**
	 * insert nav
	 * 
	 * @param nav
	 * @return
	 */
	public void insertSysNav(SysNav sysNav);

	/**
	 * delete nav
	 * 
	 * @param nav
	 * @return
	 */
	public void deleteSysNav(SysNav sysNav);

	/**
	 * get all navs
	 * 
	 * @param nav
	 * @return
	 */
	public List<SysNav> getAllSysNavs();

	/**
	 * get all navs count
	 * 
	 * @param nav
	 * @return
	 */
	public Integer getAllSysNavsCount();
}
