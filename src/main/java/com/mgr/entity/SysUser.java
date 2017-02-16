package com.mgr.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.GenericGenerator;

/**
 * ComMember entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_user")
@DataTransferObject
public class SysUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7655940504381281088L;
	private Integer id;
	private String account;
	private String password;
	private String username;
	private String nickname;
	private SysUserRole sysUserRole;

	// Constructors

	/** default constructor */
	public SysUser() {
	}

	/** full constructor */
	public SysUser(String account, String password, String username,
			String nickname, SysUserRole sysUserRole) {
		this.account = account;
		this.password = password;
		this.username = username;
		this.nickname = nickname;
		this.sysUserRole = sysUserRole;

	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	@Column(name = "account", length = 45, nullable = false)
	public String getAccount() {
		return this.account;
	}

	@Column(name = "password", length = 45, nullable = false)
	public String getPassword() {
		return this.password;
	}

	@Column(name = "username", length = 45, nullable = false)
	public String getUsername() {
		return this.username;
	}

	@Column(name = "nickname", length = 45, nullable = false)
	public String getNickname() {
		return this.nickname;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	public SysUserRole getSysUserRole() {
		return this.sysUserRole;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setSysUserRole(SysUserRole sysUserRole) {
		this.sysUserRole = sysUserRole;
	}

}
