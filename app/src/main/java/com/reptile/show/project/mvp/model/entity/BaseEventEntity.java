package com.reptile.show.project.mvp.model.entity;

public class BaseEventEntity<T> {
    private T obj;

    public BaseEventEntity(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }
}
