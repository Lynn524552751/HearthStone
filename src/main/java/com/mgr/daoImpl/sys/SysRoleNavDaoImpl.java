package com.mgr.daoImpl.sys;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.abs.AbsDBExecutor;
import com.abs.AbsGlobal;
import com.mgr.dao.sys.SysRoleNavDao;
import com.mgr.entity.SysRoleNav;
import com.mgr.interf.SysConstants;

@Repository("SysRoleNavDao")
public class SysRoleNavDaoImpl extends AbsDBExecutor implements SysRoleNavDao,
		SysConstants {

	@Override
	public SysRoleNav findSysRoleNavBySysRoleAndSysNav(Integer sysRoleId,
			Integer sysNavId) {
		SysRoleNav sysRoleNav = new SysRoleNav();
		Session dbSession = null;
		try {
			dbSession = this.getDBDao().getDBSession();
			sysRoleNav = (SysRoleNav) dbSession
					.createCriteria(SysRoleNav.class)
					.add(Restrictions.eq("sysNav.id", sysNavId))
					.add(Restrictions.eq("sysRole.id", sysRoleId))
					.uniqueResult();
		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error(
					"findSysNavByNavApp ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
		return sysRoleNav;
	}

	@Override
	public List<SysRoleNav> findSysRoleNavBySysRoleAndPrivilege(
			Integer sysRoleId, Integer privilege) {
		List<SysRoleNav> list = new ArrayList<SysRoleNav>();
		Session dbSession = null;
		try {
			dbSession = this.getDBDao().getDBSession();
			list = dbSession.createCriteria(SysRoleNav.class)
					.add(Restrictions.eq("sysRole.id", sysRoleId))
					.add(Restrictions.ge("privilege", privilege)).list();
		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error(
					"findSysRoleNavBySysRoleAndPrivilege ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
		return list;
	}
}
