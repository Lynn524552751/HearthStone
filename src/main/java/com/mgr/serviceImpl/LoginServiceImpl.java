package com.mgr.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.basic.util.Global;
import com.mgr.bean.ServiceResult;
import com.mgr.dao.sys.SysNavDao;
import com.mgr.dao.sys.SysRoleNavDao;
import com.mgr.dao.sys.SysUserDao;
import com.mgr.entity.SysNav;
import com.mgr.entity.SysRoleNav;
import com.mgr.entity.SysUser;
import com.mgr.interf.SysConstants;
import com.mgr.service.LoginService;

@Service("loginService")
public class LoginServiceImpl implements LoginService, SysConstants {
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysRoleNavDao sysRoleNavDao;
	@Resource
	private SysNavDao sysNavDao;

	public ServiceResult<SysUser> login(String userName, String passWord) {
		ServiceResult<SysUser> serviceResult = new ServiceResult<SysUser>();
		// MD5加密，对密码先加密
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		passWord = md5.encodePassword(userName, passWord);
		userName = md5.encodePassword(userName, userName);
		Global.getServiceLogger().info("登陆请求：" + userName + ", " + passWord);
		SysUser sysUser = sysUserDao.findSysUserByAccountAndPassword(userName,
				passWord);
		serviceResult.setData(sysUser);
		return serviceResult;
	}

	@Override
	public ServiceResult<List<SysNav>> loadUserNavList(SysUser user) {
		ServiceResult<List<SysNav>> serviceResult = new ServiceResult<List<SysNav>>();
		//

		//
		List<SysNav> sysNavList = new ArrayList<SysNav>();
		if (user != null) {
			int sysRoleId = user.getSysUserRole().getSysRole().getId();
			List<SysRoleNav> sysRoleNavList = sysRoleNavDao
					.findSysRoleNavBySysRoleAndPrivilege(sysRoleId,
							SysRoleNavDao.PRIVILEGE_READONLY);
			ArrayList<Integer> sysNavIds = new ArrayList<Integer>();
			for (SysRoleNav sysRoleNav : sysRoleNavList) {
				sysNavIds.add(sysRoleNav.getSysNav().getId());
			}
			sysNavList = sysNavDao.findSysNavByIds(sysNavIds);
		}
		serviceResult.setData(sysNavList);
		return serviceResult;
	}
}
