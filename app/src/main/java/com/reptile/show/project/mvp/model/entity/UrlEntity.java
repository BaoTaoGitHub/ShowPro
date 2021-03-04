package com.reptile.show.project.mvp.model.entity;

import java.util.List;

public class UrlEntity {


    /**
     * title : Title
     * url : http://kb.s/index.html
     * content :
     * init_time : 2021-03-03 00:11:05
     * status : 2
     * images : ["1"]
     */

    private String title;
    private String url;
    private String content;
    private String init_time;
    private Integer status;
    private List<String> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInit_time() {
        return init_time;
    }

    public void setInit_time(String init_time) {
        this.init_time = init_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
