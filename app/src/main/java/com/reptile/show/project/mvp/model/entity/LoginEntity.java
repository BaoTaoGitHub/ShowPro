package com.reptile.show.project.mvp.model.entity;

public class LoginEntity {

    /**
     * ret : 1
     * desc : 操作成功
     * info : {"uid":181,"token":"k30s2wgvvoaknuqzdcldp8j8j0f8vz","nickname":"v9wtmb","avatar":"http://localhost:8000/avatar/default.png"}
     */

    private String ret;
    private String desc;
    private InfoBean info;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
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


}
