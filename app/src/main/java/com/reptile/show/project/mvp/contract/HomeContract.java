package com.reptile.show.project.mvp.contract;

import android.app.Activity;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.reptile.show.project.mvp.model.entity.BaseResponse;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.model.entity.UrlEntity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.Query;

public interface HomeContract {

    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView{
        Activity getActivity();

        void showWebUrl(String url);

        void showTitleBack(boolean canBack);

        SwipeRefreshLayout getRefresh();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IModel{
        //获取目录
        Observable<DirectoryEntity> getDirContent( String token,  int d_id,boolean isUpdate);
        //获取URL内容
        Observable<BaseResponse<UrlEntity>> getUrlContent(String token, int url_id,boolean isUpdate);
        //创建目录
        Observable<BaseResponse<Object>> createDir(String token,String name,int parent_id,boolean isUpdate);
        //删除目录
        Observable<BaseResponse<Object>> deleteDir(String token,int d_id,boolean isUpdate);
        //修改目录
        Observable<BaseResponse<Object>> editDir( String token,int d_id, String name,boolean isUpdate);
        //移动目录
        Observable<BaseResponse<Object>> moveDir( String token,int d_id, int parent_id,boolean isUpdate);
        //创建URL
        Observable<BaseResponse<Object>> createUrl(String token,int d_id,String url,boolean isUpdate);
        //移动URL
        Observable<BaseResponse<Object>> moveUrl( String token,int d_id, int url_id,boolean isUpdate);
        
    }
}
