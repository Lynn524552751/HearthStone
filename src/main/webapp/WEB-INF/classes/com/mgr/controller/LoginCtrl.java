package com.mgr.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.abs.AbsDBExecutor;
import com.basic.util.Global;
import com.mgr.entity.SysUser;
import com.mgr.interf.SysConstants;

@Controller
public class LoginCtrl extends AbsDBExecutor implements SysConstants {

	/**
	 * 用户登陆请求
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = APP_PATH + APP_LOGIN, method = RequestMethod.POST)
	public String doLogin(@RequestParam("userName") String userName,
			@RequestParam("passWord") String passWord,
			HttpServletRequest request) {
		// MD5加密，对密码先加密
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		passWord = md5.encodePassword(userName, passWord);
		Global.getServiceLogger().info("登陆请求：" + userName + ", " + passWord);

		Session dbSession = null;
		try {
			/*** 这一部分可以把业务逻辑独立出来，同访问控制类分开写 */
			dbSession = this.getDBDao().getDBSession();
			List<SysUser> list = null;
			// 实际的登陆查询操作，查询时对账号加密
			System.out.println(md5.encodePassword(userName, userName));
			list = dbSession
					.createCriteria(SysUser.class)
					.add(Restrictions.eq("account",
							md5.encodePassword(userName, userName)))
					.add(Restrictions.eq("password", passWord)).list();
			if (list.size() == 1) {
				SysUser mem = list.get(0);
				request.getSession().setAttribute(SESSION_USER, mem);
				return Global.retAppModule(APP_INDEX, request);
			}

			Global.getServiceLogger().debug("登陆失败：" + userName);
			request.setAttribute(LOGIN_R_FAILURE, LANG_LOGIN_FAILURE);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(LOGIN_R_FAILURE, LANG_COM_ERR_DEF);
		} finally {
			if (dbSession != null)
				dbSession.close();
		}
		return Global.getAppModule(APP_LOGIN, request);
	}

	/**
	 * 登出请求
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(APP_PATH + APP_LOGOUT)
	public String doLogout(HttpServletRequest request) {
		SysUser mem = (SysUser) request.getSession().getAttribute(SESSION_USER);
		if (mem != null) {
			Global.getServiceLogger().debug("用户登出：" + mem.getUsername());
			request.getSession().removeAttribute(SESSION_USER);
		}
		request.getSession().invalidate();

		return Global.retAppModule(APP_LOGIN, request);
	}

	/**
	 * 读取某个账号可操作的应用导航
	 * 
	 * @param memId
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void loadUserNav(int memId, HttpServletRequest request) {
		// List<MgrNav> mnList = new ArrayList<MgrNav>();
		// Session dbSession = null;
		// try {
		// dbSession = this.getDBDao().getDBSession();
		// List<MgrRoleMem> mrmList = dbSession
		// .createCriteria(MgrRoleMem.class)
		// .add(Restrictions.like("id.memId", memId)).list();
		// for (MgrRoleMem mrm : mrmList) {
		// List<MgrRole> mrList = dbSession.createCriteria(MgrRole.class)
		// // .add( Restrictions
		// // .like( "id" , mrm.getId().getRoleId() ) )
		// .list();
		//
		// for (MgrRole mr : mrList) {
		// for (MgrRoleNav mrn : mr.getMgrRoleNavs()) {
		// if (!mnList.contains(mrn.getMgrNav())
		// && mrn.getNavPrivilege() > PRIVILEGE_NONE) {
		// mnList.add(mrn.getMgrNav());
		// }
		// }
		// }
		// }
		//
		// for (int i = 0; i < mnList.size(); i++) {
		// MgrNav iNav = mnList.get(i);
		// for (int j = mnList.size() - 1; j > i; j--) {
		// MgrNav jNav = mnList.get(j);
		// if (iNav.getNavIndex().intValue() > jNav.getNavIndex()
		// .intValue()) {
		// Collections.swap(mnList, i, j);
		// }
		// }
		// }
		// request.getSession().setAttribute(SESSION_NAVS, mnList);
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// if (dbSession != null)
		// dbSession.close();
		// }
	}

	@RequestMapping(APP_PATH + APP_LOGIN)
	public String login(HttpServletRequest request) {
		return Global.getAppModule(APP_LOGIN, request);
	}
}
