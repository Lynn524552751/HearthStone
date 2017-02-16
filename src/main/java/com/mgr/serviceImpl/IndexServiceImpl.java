package com.mgr.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mgr.bean.ServiceResult;
import com.mgr.dao.sys.SysNavDao;
import com.mgr.dao.sys.SysRoleNavDao;
import com.mgr.entity.SysNav;
import com.mgr.entity.SysRoleNav;
import com.mgr.entity.SysUser;
import com.mgr.interf.SysConstants;
import com.mgr.service.IndexService;

@Service("indexService")
public class IndexServiceImpl implements IndexService, SysConstants {
	@Resource
	private SysNavDao sysNavDao;
	@Resource
	private SysRoleNavDao sysRoleNavDao;

	@Override
	public ServiceResult<Boolean> checkUserPrivilege(SysUser user, String navApp) {
		ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
		// 判断是否需要权限
		SysNav sysNav = sysNavDao.findSysNavTypeByAppAndType(navApp,
				SysNavDao.NAVTYPE_PRIVATE);
		if (sysNav != null) {
			if (user == null) {
				serviceResult.setData(false);
				return serviceResult;
			}
			int sysRoleId = user.getSysUserRole().getSysRole().getId();
			SysRoleNav sysRoleNav = sysRoleNavDao
					.findSysRoleNavBySysRoleAndSysNav(sysRoleId, sysNav.getId());
			if (sysRoleNav == null
					|| sysRoleNav.getPrivilege() == SysRoleNavDao.PRIVILEGE_DISABLE) {
				serviceResult.setData(false);
				return serviceResult;
			}
		}
		serviceResult.setData(true);
		return serviceResult;
	}

}
