package com.shunyi.autoparts.ui.model;

import java.util.HashSet;
import java.util.Set;

/** 用户 */
public class User {
	/** ID */
	private long id;
	/** 用户名 */
	private String username;
	/** 加密的密码 */
	private String password;
	/** 是否激活 */
	private Boolean enabled;
	/** 用户店铺映射 */
	private Set<UserShopMapping> userShopMappingSet = new HashSet<>();
	/** 用户角色映射 */
	private Set<UserRoleMapping> userRoleMappingSet = new HashSet<>();

	public User() {}

	public User(String username, String password, Boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
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

	public Set<UserShopMapping> getUserShopMappingSet() {
		return userShopMappingSet;
	}

	public void setUserShopMappingSet(Set<UserShopMapping> userShopMappingSet) {
		this.userShopMappingSet = userShopMappingSet;
	}

	public Set<UserRoleMapping> getUserRoleMappingSet() {
		return userRoleMappingSet;
	}

	public void setUserRoleMappingSet(Set<UserRoleMapping> userRoleMappingSet) {
		this.userRoleMappingSet = userRoleMappingSet;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", enabled=" + enabled +
				", userShopMappingSet=" + userShopMappingSet +
				", userRoleMappingSet=" + userRoleMappingSet +
				'}';
	}
}