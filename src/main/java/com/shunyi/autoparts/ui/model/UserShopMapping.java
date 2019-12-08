package com.shunyi.autoparts.ui.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/** 用户和店铺映射关系 */
public class UserShopMapping {

    public static class Id implements Serializable {

        protected Long userId;

        protected Long shopId;

        public Id() {}

        public Id(Long userId, Long shopId) {
            this.userId = userId;
            this.shopId = shopId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getShopId() {
            return shopId;
        }

        public void setShopId(Long shopId) {
            this.shopId = shopId;
        }
    }

    protected UserShopMapping.Id id = new Id();

    @JsonIgnore
    protected User user;

    @JsonIgnore
    protected Shop shop;

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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
