package com.mgr.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.GenericGenerator;

/**
 * MgrNav entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_nav")
@DataTransferObject
public class SysNav implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4441187603766276067L;

	private Integer id;
	private Integer parentId;
	private String navName;
	private String navApp;
	private String navIcon;
	private Integer navIndex;
	private Integer navType;
	private Integer navStatus;
	private List<SysNav> childNav = new ArrayList<SysNav>();

	// Constructors

	/** default constructor */
	public SysNav() {
	}

	/** full constructor */
	public SysNav(String navName, String navApp, Integer parentId,
			String navIcon, Integer navIndex, Integer navType,
			Integer navStatus, List<SysNav> childNav) {
		this.navName = navName;
		this.navApp = navApp;
		this.parentId = parentId;
		this.navIcon = navIcon;
		this.navIndex = navIndex;
		this.navType = navType;
		this.navStatus = navStatus;
		this.childNav = childNav;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	@Column(name = "nav_app", length = 45)
	public String getNavApp() {
		return this.navApp;
	}

	@Column(name = "nav_index")
	public Integer getNavIndex() {
		return this.navIndex;
	}

	@Column(name = "nav_name", length = 45)
	public String getNavName() {
		return this.navName;
	}

	@Column(name = "parent_id")
	public Integer getParentId() {
		return this.parentId;
	}

	@Column(name = "nav_icon", length = 45)
	public String getNavIcon() {
		return this.navIcon;
	}

	@Column(name = "nav_type", length = 45)
	public Integer getNavType() {
		return this.navType;
	}

	@Column(name = "nav_status", length = 45)
	public Integer getNavStatus() {
		return this.navStatus;
	}

	@OneToMany(targetEntity = SysNav.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parentId")
	@OrderBy("navIndex")
	public List<SysNav> getChildNav() {
		return childNav;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNavApp(String navApp) {
		this.navApp = navApp;
	}

	public void setNavIndex(Integer navIndex) {
		this.navIndex = navIndex;
	}

	public void setNavName(String navName) {
		this.navName = navName;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setNavIcon(String navIcon) {
		this.navIcon = navIcon;
	}

	public void setNavType(Integer navType) {
		this.navType = navType;
	}

	public void setNavStatus(Integer navStatus) {
		this.navStatus = navStatus;
	}

	public List<SysNav> setChildNav(List<SysNav> childNav) {
		return this.childNav = childNav;
	}

}
