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
 * MgrRoleMem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_user_role")
@DataTransferObject
public class SysUserRole implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8899425597235672484L;
	// Fields
	private Integer id;
	private SysUser sysUser;
	private SysRole sysRole;

	// Constructors

	/** default constructor */
	public SysUserRole() {
	}

	/** full constructor */
	public SysUserRole(SysUser sysUser, SysRole sysRole) {
		this.sysUser = sysUser;
		this.sysRole = sysRole;
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
	@JoinColumn(name = "user_id")
	public SysUser getSysUser() {
		return this.sysUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	public SysRole getSysRole() {
		return this.sysRole;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

}
