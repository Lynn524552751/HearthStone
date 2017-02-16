package com.mgr.daoImpl.sys;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.abs.AbsDBExecutor;
import com.abs.AbsGlobal;
import com.mgr.dao.sys.SysUserDao;
import com.mgr.entity.SysUser;
import com.mgr.interf.SysConstants;

@Repository("SysUserDao")
public class SysUserDaoImpl extends AbsDBExecutor implements SysUserDao,
		SysConstants {

	@Override
	public void updateSysUser(SysUser sysUser) {
		this.getDBDao().getDBTemplate().update(sysUser);
	}

	@Override
	public void insertSysUser(SysUser sysUser) {
		this.getDBDao().getDBTemplate().save(sysUser);
		// this.getDBDao().getDBTemplate().saveOrUpdate(sysUser);
	}

	@Override
	public void deleteSysUser(SysUser sysUser) {
		this.getDBDao().getDBTemplate().delete(sysUser);
	}

	@Override
	public SysUser findSysUserByAccountAndPassword(String account,
			String password) {
		SysUser sysUser = null;
		Session dbSession = null;
		try {
			/*** 这一部分可以把业务逻辑独立出来，同访问控制类分开写 */
			dbSession = this.getDBDao().getDBSession();

			sysUser = (SysUser) dbSession.createCriteria(SysUser.class)
					.add(Restrictions.eq("account", account))
					.add(Restrictions.eq("password", password)).uniqueResult();
			sysUser.getSysUserRole().getSysRole();
		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error(
					"findSysUserByAccountAndPassword ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null)
				dbSession.close();
		}
		return sysUser;
	}
}
