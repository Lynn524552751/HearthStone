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
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.GenericGenerator;

/**
 * MgrRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_role")
@DataTransferObject
public class SysRole implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 211932670909169999L;
	private Integer id;
	private String roleName;
	private String desc;
	// private Set<SysUserRole> sysUserRoles = new HashSet<SysUserRole>();
	private List<SysRoleNav> sysRoleNavs = new ArrayList<SysRoleNav>();

	// Constructors

	/** default constructor */
	public SysRole() {
	}

	/** full constructor */
	public SysRole(String roleName, String desc, // Set<SysUserRole>
													// sysUserRoles,
			List<SysRoleNav> sysRoleNavs) {
		this.roleName = roleName;
		this.desc = desc;
		// this.sysUserRoles = sysUserRoles;
		this.sysRoleNavs = sysRoleNavs;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "sysRole")
	// public Set<SysUserRole> getSysUserRoles() {
	// return this.sysUserRoles;
	// }

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysRole")
	public List<SysRoleNav> getSysRoleNavs() {
		return this.sysRoleNavs;
	}

	@Column(name = "desc")
	public String getDesc() {
		return this.desc;
	}

	@Column(name = "role_name", length = 45)
	public String getRoleName() {
		return this.roleName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// public void setMgrRoleMems(Set<SysUserRole> sysUserRoles) {
	// this.sysUserRoles = sysUserRoles;
	// }

	public void setSysRoleNavs(List<SysRoleNav> sysRoleNavs) {
		this.sysRoleNavs = sysRoleNavs;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
