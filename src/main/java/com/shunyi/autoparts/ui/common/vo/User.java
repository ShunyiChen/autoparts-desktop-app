package com.shunyi.autoparts.ui.common.vo;

import java.util.HashSet;
import java.util.Set;

/**
 * @description 系统用户VO
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class User {
	/** ID */
	private Long id;
	/** 用户名 */
	private String username;
	/** 姓名 */
	private String chineseName;
	/** 英文名 */
	private String englishName;
	/** 性别 */
	private String sex;
	/** 电话 */
	private String phone;
	/** 电子邮件 */
	private String email;
	/** 加密的密码 */
	private String password;
	/** 是否激活 */
	private Boolean enabled;
	/** 用户和店铺映射关系 */
	protected Set<UserStoreMapping> userStoreMappingSet = new HashSet<>();
	/** 用户和角色映射关系 */
	protected Set<UserRoleMapping> userRoleMappingSet = new HashSet<>();

	public User() {}

	public User(Long id, String username, String chineseName, String englishName, String sex, String phone, String email, String password, Boolean enabled, Set<UserStoreMapping> userStoreMappingSet, Set<UserRoleMapping> userRoleMappingSet) {
		this.id = id;
		this.username = username;
		this.chineseName = chineseName;
		this.englishName = englishName;
		this.sex = sex;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.userStoreMappingSet = userStoreMappingSet;
		this.userRoleMappingSet = userRoleMappingSet;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserStoreMapping> getUserStoreMappingSet() {
		return userStoreMappingSet;
	}

	public void setUserStoreMappingSet(Set<UserStoreMapping> userStoreMappingSet) {
		this.userStoreMappingSet = userStoreMappingSet;
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
				", chineseName='" + chineseName + '\'' +
				", englishName='" + englishName + '\'' +
				", sex='" + sex + '\'' +
				", phone='" + phone + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", enabled=" + enabled +
				", userStoreMappingSet=" + userStoreMappingSet +
				", userRoleMappingSet=" + userRoleMappingSet +
				'}';
	}
}