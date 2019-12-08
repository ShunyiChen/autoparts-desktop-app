package com.shunyi.autoparts.ui.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

/** 用户 */
public class User {
	/** ID */
	private long id;
	/** 用户名 */
	private String username;
	/** 加密的密码 */
	@JsonIgnore
	private String password;
	/** 是否激活 */
	private Boolean enabled;
	/** 用户店铺映射 */
	protected Set<UserShopMapping> userShopRoleSet = new HashSet<>();
	/** 用户角色映射 */
	protected Set<UserRoleMapping> userUserRoleSet = new HashSet<>();

	public User() {}

	public User(String username, String password, Boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public Set<UserRoleMapping> getUserUserRoleSet() {
		return userUserRoleSet;
	}

	public void setUserUserRoleSet(Set<UserRoleMapping> userUserRoleSet) {
		this.userUserRoleSet = userUserRoleSet;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserShopMapping> getUserShopRoleSet() {
		return userShopRoleSet;
	}

	public void setUserShopRoleSet(Set<UserShopMapping> userShopRoleSet) {
		this.userShopRoleSet = userShopRoleSet;
	}
}