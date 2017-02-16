package com.mgr.daoImpl.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.abs.AbsDBExecutor;
import com.abs.AbsGlobal;
import com.mgr.dao.game.HsArenaReaderDao;
import com.mgr.entity.HsArena;
import com.mgr.interf.GameConstants;
import com.mgr.interf.SysConstants;

@Repository("hsArenaReaderDao")
public class HsArenaReaderDaoImpl extends AbsDBExecutor implements
		HsArenaReaderDao, SysConstants, GameConstants {
	@Override
	public HsArena findHsArenaById(int id) {
		return (HsArena) this.getDBDao().findById(HsArena.class, id).get(0);
	}

	@Override
	public List<HsArena> findHsArenaList(int size, int start, String order,
			String sort, String occupation, String wins) {
		List<HsArena> list = new ArrayList<HsArena>();
		Session dbSession = null;
		try {
			dbSession = this.getDBDao().getDBSession();
			String whereSql = "";
			if (StringUtils.isNotBlank(occupation)
					|| StringUtils.isNotBlank(wins)) {
				whereSql = " WHERE";
				if (StringUtils.isNotBlank(occupation)) {
					whereSql += " occupation=\"" + occupation + "\"";
				}
				if (StringUtils.isNotBlank(wins)) {
					whereSql += " wins=" + wins;
				}
			}
			String limitSql = " LIMIT " + start + "," + size;
			String sortSql = " ORDER BY " + sort + " " + order;
			String sql = "SELECT * FROM hs_arena" + whereSql + sortSql
					+ limitSql;
			System.out.println(sql);
			list = dbSession.createSQLQuery(sql).addEntity(HsArena.class)
					.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AbsGlobal.getServiceLogger().error(
					"findHsArenaList ：" + e.toString());
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
		return list;
	}

	@Override
	public Integer queryHsArenaCount() {
		Integer count = 0;
		Session dbSession = null;
		Criteria criteria = null;

		try {
			dbSession = this.getDBDao().getDBSession();
			criteria = dbSession.createCriteria(HsArena.class);
			ProjectionList proList = Projections.projectionList();
			proList.add(Projections.rowCount());
			criteria.setProjection(proList);
			count = ((Number) criteria.uniqueResult()).intValue();

		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error(
					"queryHsArenaCount ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
		return count;
	}

	@Override
	public Integer queryHsArenaCountByOccupation(String occupation) {
		int count = 0;
		Session dbSession = null;
		Criteria criteria = null;

		try {
			dbSession = this.getDBDao().getDBSession();
			criteria = dbSession.createCriteria(HsArena.class);
			criteria.add(Restrictions.eq("occupation", occupation));
			ProjectionList proList = Projections.projectionList();
			proList.add(Projections.rowCount());
			criteria.setProjection(proList);
			count = ((Number) criteria.uniqueResult()).intValue();

		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error(
					"queryHsArenaCountByOccupation ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
		return count;
	}

	@Override
	public Integer queryHsArenaCountByWins(Integer wins) {
		int count = 0;
		Session dbSession = null;
		Criteria criteria = null;

		try {
			dbSession = this.getDBDao().getDBSession();
			criteria = dbSession.createCriteria(HsArena.class);
			criteria.add(Restrictions.eq("wins", wins));
			ProjectionList proList = Projections.projectionList();
			proList.add(Projections.rowCount());
			criteria.setProjection(proList);
			count = ((Number) criteria.uniqueResult()).intValue();

		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error(
					"queryHsArenaCountByWins ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
		return count;
	}

	@Override
	public int queryHsArenaSumByField(String field) {
		int sum = 0;
		Session dbSession = null;
		Criteria criteria = null;

		try {
			dbSession = this.getDBDao().getDBSession();
			criteria = dbSession.createCriteria(HsArena.class);
			ProjectionList proList = Projections.projectionList();
			proList.add(Projections.sum(field));
			criteria.setProjection(proList);
			if (criteria.uniqueResult() != null) {
				sum = ((Number) criteria.uniqueResult()).intValue();
			}

		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error(
					"queryHsArenaSumByField ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
		return sum;
	}

	@Override
	public int queryHsArenaSumByFieldAndOccupation(String field,
			String occupation) {
		int sum = 0;
		Session dbSession = null;
		Criteria criteria = null;
		try {
			dbSession = this.getDBDao().getDBSession();
			criteria = dbSession.createCriteria(HsArena.class);
			criteria.add(Restrictions.eq("occupation", occupation));
			ProjectionList proList = Projections.projectionList();
			proList.add(Projections.sum(field));
			criteria.setProjection(proList);
			if (criteria.uniqueResult() != null) {
				sum = ((Number) criteria.uniqueResult()).intValue();
			}

		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error(
					"queryHsArenaSumByFieldAndOccupation ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
		return sum;
	}
}
