package com.reptile.show.project.mvp.model.entity;

import android.os.Build;

import com.reptile.show.project.app.AppConstants;

import java.util.List;

public class DirectoryEntity {

    private List<DirUrlBean> dirUrl;
    private List<DirectoryBean> directory;
    private List<UrlBean> url;

    public void setDirUrl(List<DirUrlBean> dirUrl) {
        this.dirUrl = dirUrl;
    }

    public List<DirUrlBean> getDirUrl() {
        return dirUrl;
    }

    public List<DirectoryBean> getDirectory() {
        return directory;
    }

    public void setDirectory(List<DirectoryBean> directory) {
        this.directory = directory;
    }

    public List<UrlBean> getUrl() {
        return url;
    }

    public void setUrl(List<UrlBean> url) {
        this.url = url;
    }

    public static class DirectoryBean {
        /**
         * d_id : 2
         * name : 123456
         * count : 1
         */

        private Integer d_id;
        private String name;
        private Integer count;

        public Integer getD_id() {
            return d_id;
        }

        public void setD_id(Integer d_id) {
            this.d_id = d_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    public static class UrlBean {
        /**
         * url_id : 5
         * url_title : Title
         * url_content :
         * status : 2
         * init_time : 2021-03-03 00:11:05
         */

        private Integer url_id;
        private String url_title;
        private String url_content;
        private Integer status;
        private String init_time;

        public Integer getUrl_id() {
            return url_id;
        }

        public void setUrl_id(Integer url_id) {
            this.url_id = url_id;
        }

        public String getUrl_title() {
            return url_title;
        }

        public void setUrl_title(String url_title) {
            this.url_title = url_title;
        }

        public String getUrl_content() {
            return url_content;
        }

        public void setUrl_content(String url_content) {
            this.url_content = url_content;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getInit_time() {
            return init_time;
        }

        public void setInit_time(String init_time) {
            this.init_time = init_time;
        }
    }

    public static class DirUrlBean{
        private int viewType;
        private Integer id;
        private String title;
        private boolean isCheck;

        private int childCount;

        private String content;
        private Integer status;
        private String init_time;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getChildCount() {
            return childCount;
        }

        public void setChildCount(int childCount) {
            this.childCount = childCount;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getInit_time() {
            return init_time;
        }

        public void setInit_time(String init_time) {
            this.init_time = init_time;
        }
    }
}
