package com.reptile.show.project.mvp.model.api.service;

import com.reptile.show.project.mvp.model.entity.BaseResponse;
import com.reptile.show.project.mvp.model.entity.LoginEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {

    //注册
    @FormUrlEncoded
    @POST("/api/user/register")
    Observable<BaseResponse<Object>> register(@Field("phone") String phone, @Field("passwd") String pwd, @Field("code") String code);

    //登录
    @FormUrlEncoded
    @POST("/api/user/login")
    Observable<BaseResponse<LoginEntity>> login(@Field("phone") String phone,@Field("passwd") String pwd);
}
