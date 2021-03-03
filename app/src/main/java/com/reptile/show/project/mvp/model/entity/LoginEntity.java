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
    }
}
