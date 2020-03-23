package com.shunyi.autoparts.ui.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description 用户和店铺映射关系
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class UserStoreMapping {

    public static class Id implements Serializable {

        /** 用户ID */
        protected Long userId;
        /** 店铺ID */
        protected Long storeId;

        public Id() {}

        public Id(Long userId, Long storeId) {
            this.userId = userId;
            this.storeId = storeId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getStoreId() {
            return storeId;
        }

        public void setStoreId(Long storeId) {
            this.storeId = storeId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Id id = (Id) o;
            return Objects.equals(userId, id.userId) &&
                    Objects.equals(storeId, id.storeId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, storeId);
        }

        @Override
        public String toString() {
            return "Id{" +
                    "userId=" + userId +
                    ", storeId=" + storeId +
                    '}';
        }
    }

    protected UserStoreMapping.Id id = new Id();

    @JsonIgnore
    protected User user;

    @JsonIgnore
    protected Store store;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
