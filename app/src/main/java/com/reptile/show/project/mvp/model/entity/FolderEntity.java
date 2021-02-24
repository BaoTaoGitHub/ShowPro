package com.reptile.show.project.mvp.model.entity;

/**
 * 首页HomeFragment的列表实体类
 */
public class FolderEntity {
    private final String title;
    private final String subtitle;

    public FolderEntity(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public String toString() {
        return "FolderEntity{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                '}';
    }
}
