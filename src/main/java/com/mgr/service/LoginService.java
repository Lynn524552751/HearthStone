package com.mgr.service;

import java.util.List;

import com.mgr.bean.ServiceResult;
import com.mgr.entity.SysNav;
import com.mgr.entity.SysUser;

public interface LoginService {
	/**
	 * 登录操作
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public ServiceResult<SysUser> login(String userName, String passWord);

	/**
	 * 读取某个账号可操作的应用导航
	 * 
	 * @param user
	 * @return
	 */
	public ServiceResult<List<SysNav>> loadUserNavList(SysUser user);
}
