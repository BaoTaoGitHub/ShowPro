package com.reptile.show.project.mvp.model.api.service;

import com.reptile.show.project.mvp.model.entity.LoginEntity;
import com.reptile.show.project.mvp.model.entity.StatusEntity;

import javax.annotation.PostConstruct;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginService {

    //注册
    @POST("/api/user/register")
    Observable<StatusEntity> register(@Query("phone") String phone,@Query("passwd") String pwd,@Query("code") String code);

    //登录
    @POST("/api/user/login")
    Observable<LoginEntity> login(@Query("phone") String phone,@Query("passwd") String pwd);
}
