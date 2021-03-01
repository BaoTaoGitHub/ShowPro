package com.reptile.show.project.mvp.model.entity;

import javax.annotation.Resource;

public class PopupAddEntity {
     private final String title;
     private final int icon;

    public PopupAddEntity(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

}
