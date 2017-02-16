package com.mgr.daoImpl.sys;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import com.abs.AbsDBExecutor;
import com.abs.AbsGlobal;
import com.mgr.dao.sys.SysNavDao;
import com.mgr.entity.SysNav;
import com.mgr.interf.SysConstants;

@Repository("SysNavDao")
public class SysNavDaoImpl extends AbsDBExecutor implements SysNavDao,
		SysConstants {
	@Override
	public void updateSysNav(SysNav sysNav) {
		this.getDBDao().update(sysNav);
	}

	@Override
	public void insertSysNav(SysNav sysNav) {
		this.getDBDao().save(sysNav);
	}

	@Override
	public void deleteSysNav(SysNav sysNav) {
		this.getDBDao().delete(sysNav);
	}

	@Override
	public List<SysNav> getAllSysNavs() {
		Session dbSession = null;
		Criteria criteria = null;
		List<SysNav> sysNavList = new ArrayList<SysNav>();
		try {
			dbSession = this.getDBDao().getDBSession();
			criteria = dbSession.createCriteria(SysNav.class);
			sysNavList = criteria.list();
		} catch (Exception e) {
			AbsGlobal.getServiceLogger()
					.error("getAllSysNavs ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
		return sysNavList;
	}

	@Override
	public Integer getAllSysNavsCount() {
		Integer count = 0;
		Session dbSession = null;
		Criteria criteria = null;
		List<SysNav> sysNavList = new ArrayList<SysNav>();
		try {
			dbSession = this.getDBDao().getDBSession();
			ProjectionList proList = Projections.projectionList();
			proList.add(Projections.rowCount());
			criteria.setProjection(proList);
			count = ((Number) criteria.uniqueResult()).intValue();
		} catch (Exception e) {
			AbsGlobal.getServiceLogger()
					.error("getAllSysNavs ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
		return count;
	}
}
