package com.reptile.show.project.mvp.model.api.service;

import com.reptile.show.project.mvp.model.entity.BaseResponse;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.model.entity.UrlEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DirUrlService {

    /**
     * 创建目录
     * @param token
     * @param name 目录名称
     * @param parent_id 父级ID
     * @return
     */
    @FormUrlEncoded
    @POST("/api/directory/create")
    Observable<BaseResponse<Object>> createDir(@Field("token") String token,@Field("name")String name,@Field("parent_id")int parent_id);

    /**
     * 删除目录
     * @param token
     * @param d_id 目录id
     * @return
     */
    @FormUrlEncoded
    @POST("/api/directory/delete")
    Observable<BaseResponse<Object>> deleteDir(@Field("token") String token,@Field("d_id")int d_id);

    /**
     * 修改目录名称
     * @param token
     * @param d_id 目录id
     * @param name 修改后目录名
     * @return
     */
    @FormUrlEncoded
    @POST("/api/directory/edit")
    Observable<BaseResponse<Object>> editDir(@Field("token") String token,@Field("d_id")int d_id,@Field("name") String name);

    /**
     * 移动目录
     * @param token
     * @param d_id 被移动的目录id
     * @param parent_id 移动到的目录id
     * @return
     */
    @FormUrlEncoded
    @POST("/api/directory/move")
    Observable<BaseResponse<Object>> moveDir(@Field("token") String token,@Field("d_id")int d_id,@Field("parent_id") int parent_id);

    /**
     * 移动URL
     * @param token
     * @param d_id 目录id
     * @param url_id 被移动的url id
     * @return
     */
    @FormUrlEncoded
    @POST("/api/url/move")
    Observable<BaseResponse<Object>> moveUrl(@Field("token") String token,@Field("d_id")int d_id,@Field("url_id") int url_id);

    /**
     * 提交url 链接
     * @param token
     * @param d_id 目录id
     * @param url 提交的url
     * @return
     */
    @FormUrlEncoded
    @POST("/api/url/create")
    Observable<BaseResponse<Object>> createUrl(@Field("token") String token,@Field("d_id")int d_id,@Field("url") String url);

    /**
     * 获取目录内容
     * @param token
     * @param d_id
     * @return
     */
    @GET("/api/directory/ls")
    Observable<BaseResponse<DirectoryEntity>> getDirContent(@Query("token") String token, @Query("d_id") int d_id);

    /**
     * 获取URL内容
     * @param token
     * @param url_id
     * @return
     */
    @FormUrlEncoded
    @POST("/api/url/info")
    Observable<BaseResponse<UrlEntity>> getUrlContent(@Field("token") String token, @Field("url_id")int url_id);


}
