package com.mgr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.GenericGenerator;

/**
 * MgrRoleNav entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_role_nav")
@DataTransferObject
public class SysRoleNav implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7775885167842369295L;

	private Integer id;
	private SysRole sysRole;
	private SysNav sysNav;
	private Integer privilege;

	// Constructors

	/** default constructor */
	public SysRoleNav() {
	}

	/** full constructor */
	public SysRoleNav(SysRole sysRole, SysNav sysNav, Integer privilege) {
		this.sysRole = sysRole;
		this.sysNav = sysNav;
		this.privilege = privilege;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nav_id")
	public SysNav getSysNav() {
		return this.sysNav;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	public SysRole getSysRole() {
		return this.sysRole;
	}

	@Column(name = "privilege")
	public Integer getPrivilege() {
		return this.privilege;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSysNav(SysNav sysNav) {
		this.sysNav = sysNav;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

	public void setPrivilege(Integer privilege) {
		this.privilege = privilege;
	}

}
