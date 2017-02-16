package com.mgr.dao.sys;

import java.util.List;

import com.mgr.entity.SysRoleNav;

public interface SysRoleNavDao {
	public static final int PRIVILEGE_DISABLE = 0;
	public static final int PRIVILEGE_READONLY = 1;
	public static final int PRIVILEGE_WRITE = 2;

	/**
	 * 查询用户的某一栏目权限
	 * 
	 * @param navApp
	 * @return
	 */
	public SysRoleNav findSysRoleNavBySysRoleAndSysNav(Integer sysRoleId,
			Integer sysNavId);

	/**
	 * 查询用户某一权限的所有栏目id
	 * 
	 * @param navApp
	 * @return
	 */
	public List<SysRoleNav> findSysRoleNavBySysRoleAndPrivilege(
			Integer sysRoleId, Integer privilege);
}
