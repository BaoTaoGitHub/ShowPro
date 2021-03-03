package com.reptile.show.project.mvp.model.entity;

import java.util.List;

public class StatusEntity {

    /**
     * ret : 1
     * desc : 正常
     * info : []
     */

    private Integer ret;
    private String desc;
    private List<?> info;

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<?> getInfo() {
        return info;
    }

    public void setInfo(List<?> info) {
        this.info = info;
    }
}
