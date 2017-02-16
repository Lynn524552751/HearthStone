package com.mgr.bean;

import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.annotations.DataTransferObject;

import com.mgr.entity.SysNav;

/**
 * HsArenaVo entity. @author MyEclipse Persistence Tools
 */
@DataTransferObject
public class SysNavVo extends SysNav {
	private static final long serialVersionUID = 2730486647350808501L;
	// Fields
	private SysNav sysNav;
	private List<SysNavVo> childSysNavs = new ArrayList<SysNavVo>();

	// Constructors
	public List<SysNavVo> getChildSysNavs() {
		return childSysNavs;
	}

	public void setChildSysNavs(List<SysNavVo> childSysNavs) {
		this.childSysNavs = childSysNavs;
	}

	public SysNav getSysNav() {
		return sysNav;
	}

	public void setSysNav(SysNav sysNav) {
		this.sysNav = sysNav;
	}

}
