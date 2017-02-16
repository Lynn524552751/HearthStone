package com.mgr.dao.sys;

import java.util.List;

import com.mgr.entity.SysNav;

public interface SysNavDao {
	public static final int FIRSTCLASS_PARENTID = 0;
	public static final int NAVTYPE_PRIVATE = 1;
	public static final int NAVTYPE_PUBLIC = 0;
	public static final int NAVSTATUS_DISPLAY = 1;
	public static final int NAVSTATUS_HIDE = 0;

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
	 * get navs
	 * 
	 * @param nav
	 * @return
	 */
	public SysNav findSysNavById(Integer id);

	/**
	 * get all navs
	 * 
	 * @param nav
	 * @return
	 */
	public List<SysNav> findAllSysNavs();

	/**
	 * get all navs count
	 * 
	 * @param nav
	 * @return
	 */
	public Integer getAllSysNavsCount();

	/**
	 * 获取栏目(是否公有，是否显示)
	 * 
	 * @return
	 */
	public List<SysNav> findSysNavsByTypeAndStatus(Integer navType,
			Integer navStatus);

	/**
	 * 获取栏目(路径，是否公有)
	 * 
	 * @param navApp
	 * @return 不需要 return null
	 */
	public SysNav findSysNavTypeByAppAndType(String navApp, Integer navType);

	/**
	 * 
	 * @param ids
	 * @return
	 */
	public List<SysNav> findSysNavByIds(List<Integer> ids);
}
