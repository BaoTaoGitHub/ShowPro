package com.reptile.show.project.mvp.model.entity;

import java.io.Serializable;

public class LoginEntity implements Serializable {


    /**
     * uid : 181
     * token : k30s2wgvvoaknuqzdcldp8j8j0f8vz
     * nickname : v9wtmb
     * avatar : http://localhost:8000/avatar/default.png
     */

    private Integer uid;
    private String token;
    private String nickname;
    private String avatar;
    private String phone;
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
