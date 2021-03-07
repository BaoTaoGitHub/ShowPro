package com.reptile.show.project.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.reptile.show.project.mvp.model.entity.BaseResponse;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.model.entity.UrlEntity;

import io.reactivex.Observable;

public interface MoveDirContract {
    interface View extends IView{
        Activity getActivity();
    }

    interface Model extends IModel {
        //获取目录
        Observable<BaseResponse<DirectoryEntity>> getDirContent(String token, int d_id, boolean isUpdate);

        //创建目录
        Observable<BaseResponse<Object>> createDir(String token,String name,int parent_id,boolean isUpdate);

        //移动目录
        Observable<BaseResponse<Object>> moveDir( String token,int d_id, int parent_id,boolean isUpdate);

        //移动URL
        Observable<BaseResponse<Object>> moveUrl( String token,int d_id, int url_id,boolean isUpdate);
    }
}
