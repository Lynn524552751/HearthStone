package com.mgr.service;

import com.mgr.bean.ServiceResult;
import com.mgr.entity.SysUser;

public interface IndexService {
	/**
	 * 判断用户是否能访问此栏目
	 * 
	 * @param user
	 * @param navApp
	 * @return
	 */
	public ServiceResult<Boolean> checkUserPrivilege(SysUser user, String navApp);

}
