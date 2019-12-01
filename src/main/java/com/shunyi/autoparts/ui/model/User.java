package com.shunyi.autoparts.ui.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

/** 用户 */
public class User {
	private long id;
	/** 用户名 */
	private String username;
	/** 加密的密码 */
	@JsonIgnore
	private String password;
	/** 是否激活 */
	private Boolean enabled;
	/** 店铺 */
	private Shop shop;

	protected Set<UserRoleMapping> userUserRoleSet = new HashSet<>();

	public User() {}

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

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", enabled=" + enabled +
				", shop=" + shop +
				", userUserRoleSet=" + userUserRoleSet +
				'}';
	}
}