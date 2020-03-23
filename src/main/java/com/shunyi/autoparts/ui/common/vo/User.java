package com.shunyi.autoparts.ui.common.vo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 系统用户
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class User {
	/** 自增ID */
	private Long id;
	/** 用户名 */
	private String username;
	/** 姓名 */
	private String chineseName;
	/** 英文名 */
	private String EnglishName;
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
	/** 创建时间 */
	private Date dateCreated;
	/** 创建者 */
	private String creator;
	/** 更新时间 */
	private Date dateUpdated;
	/** 更新者 */
	private String updater;
	/** 更新次数 */
	private Integer updatedCount;
	/** 删除时间 */
	private Date dateDeleted;
	/** 删除标记 */
	private Boolean deleteFlag;
	/** 删除者 */
	private String deleter;

	public User() {}

	public User(Long id, String username, String chineseName, String englishName, String sex, String phone, String email, String password, Boolean enabled, Set<UserStoreMapping> userStoreMappingSet, Set<UserRoleMapping> userRoleMappingSet, Date dateCreated, String creator, Date dateUpdated, String updater, Integer updatedCount, Date dateDeleted, Boolean deleteFlag, String deleter) {
		this.id = id;
		this.username = username;
		this.chineseName = chineseName;
		EnglishName = englishName;
		this.sex = sex;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.userStoreMappingSet = userStoreMappingSet;
		this.userRoleMappingSet = userRoleMappingSet;
		this.dateCreated = dateCreated;
		this.creator = creator;
		this.dateUpdated = dateUpdated;
		this.updater = updater;
		this.updatedCount = updatedCount;
		this.dateDeleted = dateDeleted;
		this.deleteFlag = deleteFlag;
		this.deleter = deleter;
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
		return EnglishName;
	}

	public void setEnglishName(String englishName) {
		EnglishName = englishName;
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Integer getUpdatedCount() {
		return updatedCount;
	}

	public void setUpdatedCount(Integer updatedCount) {
		this.updatedCount = updatedCount;
	}

	public Date getDateDeleted() {
		return dateDeleted;
	}

	public void setDateDeleted(Date dateDeleted) {
		this.dateDeleted = dateDeleted;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDeleter() {
		return deleter;
	}

	public void setDeleter(String deleter) {
		this.deleter = deleter;
	}

	@Override
	public String toString() {
		return username;
	}
}