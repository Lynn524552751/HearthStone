package com.mgr.daoImpl.game;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.abs.AbsDBExecutor;
import com.abs.AbsGlobal;
import com.mgr.dao.game.HsArenaWriterDao;
import com.mgr.entity.HsArena;
import com.mgr.interf.GameConstants;
import com.mgr.interf.SysConstants;

@Repository("hsArenaWriterDao")
public class HsArenaWriterDaoImpl extends AbsDBExecutor implements
		HsArenaWriterDao, SysConstants, GameConstants {

	@Override
	public void saveOrUpdate(HsArena hsArena) {
		Session dbSession = null;
		try {
			dbSession = this.getDBDao().getDBSession();
			dbSession.saveOrUpdate(hsArena);
			dbSession.flush();
		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error("saveOrUpdate ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
	}

	@Override
	public void delete(int id) {
		Session dbSession = null;
		try {
			HsArena hsArena = new HsArena();
			hsArena.setId(id);
			dbSession = this.getDBDao().getDBSession();
			dbSession.delete(hsArena);
			dbSession.flush();
		} catch (Exception e) {
			AbsGlobal.getServiceLogger().error("delete ：" + e.toString());
			e.printStackTrace();
		} finally {
			if (dbSession != null) {
				dbSession.close();
			}
		}
	}
}
