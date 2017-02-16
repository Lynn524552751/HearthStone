package com.mgr.daoImpl.sys;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
		this.getDBDao().getDBTemplate().update(sysNav);
	}

	@Override
	public void insertSysNav(SysNav sysNav) {
		this.getDBDao().getDBTemplate().save(sysNav);
		// this.getDBDao().getDBTemplate().saveOrUpdate(sysNav);
	}

	@Override
	public void deleteSysNav(SysNav sysNav) {
		this.getDBDao().getDBTemplate().delete(sysNav);
	}

	@Override
	public SysNav findSysNavById(Integer id) {
		return (SysNav) this.getDBDao().findById(SysNav.class, id);
	}

	@Override
	public List<SysNav> findAllSysNavs() {
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

	@Override
	public List<SysNav> findSysNavsByTypeAndStatus(Integer navType,
			Integer navStatus) {
		Session dbSession = null;
		Criteria criteria = null;
		List<SysNav> sysNavList = new ArrayList<SysNav>();
		try {
			dbSession = this.getDBDao().getDBSession();
			sysNavList = dbSession.createCriteria(SysNav.class)
					.add(Restrictions.eq("navType", navType))
					.add(Restrictions.eq("navStatus", navStatus)).list();
		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error(
					"findSysNavsByTypeAndStatus ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
		return sysNavList;
	}

	@Override
	public SysNav findSysNavTypeByAppAndType(String navApp, Integer navType) {
		SysNav sysNav = new SysNav();
		Session dbSession = null;
		try {
			dbSession = this.getDBDao().getDBSession();
			sysNav = (SysNav) dbSession.createCriteria(SysNav.class)
					.add(Restrictions.eq("navApp", navApp))
					.add(Restrictions.eq("navType", navType)).uniqueResult();
		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error(
					"findSysNavTypeByAppAndType ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
		return sysNav;
	}

	@Override
	public List<SysNav> findSysNavByIds(List<Integer> ids) {
		List<SysNav> list = new ArrayList<SysNav>();
		Session dbSession = null;
		try {
			dbSession = this.getDBDao().getDBSession();
			list = dbSession.createCriteria(SysNav.class)
					.add(Restrictions.in("id", ids))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.addOrder(Order.asc("navIndex")).list();
		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error(
					"findSysNavByIds ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
		return list;
	}
}
