package com.reptile.show.project.mvp.model.entity;

import android.graphics.drawable.Drawable;

import javax.annotation.Resource;

public class PopupAddEntity {
     private final String title;
     private final Drawable icon;

    public PopupAddEntity(String title, Drawable icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getIcon() {
        return icon;
    }

}
