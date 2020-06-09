package com.shunyi.autoparts.ui.common;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author Shunyi
 *
 * @Description 用户身份验证响应
 * @CreateDate: 2020/03/09
 */
public class AuthenticationResponse {
    private String token;
    private String username;
    @JsonFormat(pattern=Constants.PATTERN_DATETIME,timezone="GMT+8")
    private Date lastLoginDate;
    private String error;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", lastLoginDate=" + lastLoginDate +
                ", error='" + error + '\'' +
                '}';
    }
}
