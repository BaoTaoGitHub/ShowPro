/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.reptile.show.project.app;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.http.log.RequestInterceptor;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.reptile.show.project.mvp.model.api.Api;
import com.reptile.show.project.mvp.model.api.service.LoginService;
import com.reptile.show.project.mvp.model.entity.BaseResponse;
import com.reptile.show.project.mvp.model.entity.Demo;
import com.reptile.show.project.mvp.model.entity.LoginEntity;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.progressmanager.body.ProgressRequestBody;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import retrofit2.Call;
import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link GlobalHttpHandler} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {
    private Context context;

    @Inject
    IRepositoryManager mRepositoryManager;//用于管理网络请求层, 以及数据缓存层

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
    }

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      {@link okhttp3.Interceptor.Chain}
     * @param response   {@link Response}
     * @return {@link Response}
     */
    @NonNull
    @Override
    public Response onHttpResultResponse(@Nullable String httpResult, @NonNull Interceptor.Chain chain, @NonNull Response response) {
//        if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response.body().contentType())) {
//            try {
//                List<Demo> list = ArmsUtils.obtainAppComponentFromContext(context).gson().fromJson(httpResult, new TypeToken<List<Demo>>() {
//                }.getType());
//                Demo demo = list.get(0);
//                Timber.w("Result ------> " + demo.getLogin() + "    ||   Avatar_url------> " + demo.getAvatarUrl());
//            } catch (Exception e) {
//                e.printStackTrace();
//                return response;
//            }
//        }
        if (!TextUtils.isEmpty(httpResult) && RequestInterceptor.isJson(response.body().contentType())) {
            try {
                BaseResponse<Object> baseResponse = ArmsUtils.obtainAppComponentFromContext(context).gson().fromJson(httpResult, new TypeToken<BaseResponse<Object>>() {
                }.getType());
                if ("9".equals(baseResponse.getRet())) {
//                    Request newRequest =  chain.request().newBuilder().header("token", token)
//                            .build();
//                    return chain.proceed(newRequest);
                    LoginEntity entity;
                    if ((entity = DataHelper.<LoginEntity>getDeviceData(context, AppConstants.LOGIN_SP)) != null) {
                        FormBody.Builder body = new FormBody.Builder();
                        body.add("phone", entity.getPhone());
                        body.add("passwd", entity.getPwd());
                        Request newRequest = chain.request().newBuilder().url(Api.APP_DOMAIN + "/api/user/login")
                                .post(body.build()).build();
                        return chain.proceed(newRequest);
                    }
                }
                Timber.w("接口:" + 1 + "返回Code ------> " + baseResponse.getRet() + "    ||   描述------> " + baseResponse.getDesc());
            } catch (Exception e) {
                e.printStackTrace();
                return response;
            }
        }
        //TODO Token 过期重新请求
        /* 这里如果发现 token 过期, 可以先请求最新的 token, 然后在拿新的 token 放入 Request 里去重新请求
        注意在这个回调之前已经调用过 proceed(), 所以这里必须自己去建立网络请求, 如使用 Okhttp 使用新的 Request 去请求
        create a new request and modify it accordingly using the new token
        Request newRequest = chain.request().newBuilder().header("token", newToken)
                             .build();

        retry the request

        response.body().close();
        如果使用 Okhttp 将新的请求, 请求成功后, 再将 Okhttp 返回的 Response return 出去即可
        如果不需要返回新的结果, 则直接把参数 response 返回出去即可*/
        return response;
    }

    /**
     * 这里可以在请求服务器之前拿到 {@link Request}, 做一些操作比如给 {@link Request} 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain   {@link okhttp3.Interceptor.Chain}
     * @param request {@link Request}
     * @return {@link Request}
     */
    @NonNull
    @Override
    public Request onHttpRequestBefore(@NonNull Interceptor.Chain chain, @NonNull Request request) {
        /* 如果需要在请求服务器之前做一些操作, 则重新构建一个做过操作的 Request 并 return, 如增加 Header、Params 等请求信息, 不做操作则直接返回参数 request
        return chain.request().newBuilder().header("token", tokenId)
                              .build(); */
        //TODO 注释暂时
        if (DataHelper.getDeviceData(context, AppConstants.LOGIN_SP) != null) {
            String token = DataHelper.<LoginEntity>getDeviceData(context, AppConstants.LOGIN_SP).getToken();
//            return chain.request().newBuilder().header("Authorization", token)
//                    .build();
            HttpUrl.Builder builder = chain.request().url().newBuilder();
            HttpUrl newUrl = builder.addQueryParameter("token", token).build();
            //利用新的Url 构建新的Request ，并发送给服务器
            Request newRequest = request.newBuilder()
                    .url(newUrl)
                    .build();
            return newRequest;
        }
        return request;
    }

    private synchronized String getNewToken(String phone, String pwd) throws IOException {
        String newToken = "";
//        BaseResponse<LoginEntity> response = mRepositoryManager.obtainRetrofitService(LoginService.class)
//                .login(phone,pwd);

        //同步获取token
        return "";
    }
}
