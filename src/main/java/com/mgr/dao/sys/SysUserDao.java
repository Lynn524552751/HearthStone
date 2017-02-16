package com.mgr.dao.sys;

import com.mgr.entity.SysUser;

public interface SysUserDao {

	/**
	 * update nav
	 * 
	 * @param nav
	 * @return
	 */
	public void updateSysUser(SysUser sysUser);

	/**
	 * insert nav
	 * 
	 * @param nav
	 * @return
	 */
	public void insertSysUser(SysUser sysUser);

	/**
	 * delete nav
	 * 
	 * @param nav
	 * @return
	 */
	public void deleteSysUser(SysUser sysUser);

	/**
	 * 查询用户 (账户 密码)
	 * 
	 * @param nav
	 * @return
	 */
	public SysUser findSysUserByAccountAndPassword(String account,
			String password);
}
