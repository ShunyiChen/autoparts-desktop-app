package com.shunyi.autoparts.ui.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description 角色和权限关系VO
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class RolePermissionMapping {
    /** ID */
    public static class Id implements Serializable {

        /** 角色ID */
        protected Long roleId;

        /** 权限ID */
        protected Long permissionId;

        public Id() {}

        /**
         * Constructor
         *
         * @param roleId 角色ID
         * @param permissionId 权限ID
         */
        public Id(Long roleId, Long permissionId) {
            this.roleId = roleId;
            this.permissionId = permissionId;
        }

        public Long getRoleId() {
            return roleId;
        }

        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }

        public Long getPermissionId() {
            return permissionId;
        }

        public void setPermissionId(Long permissionId) {
            this.permissionId = permissionId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {return true;}
            if (o == null || getClass() != o.getClass()) {return false;}
            Id id = (Id) o;
            return Objects.equals(roleId, id.roleId) &&
                    Objects.equals(permissionId, id.permissionId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(roleId, permissionId);
        }

        @Override
        public String toString() {
            return "Id{" +
                    "roleId=" + roleId +
                    ", permissionId=" + permissionId +
                    '}';
        }
    }

    protected Id id = new Id();

    @JsonIgnore
    protected Role role;

    @JsonIgnore
    protected Permission permission;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "RolePermissionMapping{" +
                "id=" + id +
                ", role=" + role +
                ", permission=" + permission +
                '}';
    }
}
