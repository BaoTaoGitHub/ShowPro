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
package com.reptile.show.project.mvp.model.api.cache;

import com.reptile.show.project.mvp.model.entity.BaseResponse;
import com.reptile.show.project.mvp.model.entity.Demo;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.model.entity.LoginEntity;
import com.reptile.show.project.mvp.model.entity.UrlEntity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;

/**
 * ================================================
 * 展示 {@link RxCache#using(Class)} 中需要传入的 Providers 的使用方式
 * <p>
 * Created by JessYan on 08/30/2016 13:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface CommonCache {
    /**
     * LifeCache设置缓存过期时间. 如果没有设置@LifeCache , 数据将被永久缓存理除非你使用了 EvictProvider,EvictDynamicKey or EvictDynamicKeyGroup .
     * @param
     * @param idLastUserQueried 驱逐与一个特定的键使用EvictDynamicKey相关的数据。比如分页，排序或筛选要求
     * @param evictProvider  可以明确地清理指定的数据 DynamicKey.
     * @return
     */
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<List<Demo>>> getUsers(Observable<List<Demo>> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    //获取目录
    @LifeCache(duration = 1, timeUnit = TimeUnit.SECONDS)
    Observable<Reply<DirectoryEntity>> getDirContent(Observable<DirectoryEntity> dirEntity,DynamicKey dynamicKey,EvictProvider evictProvider);

    //获取URL内容
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<BaseResponse<UrlEntity>> getUrlContent(String token, int url_id,EvictProvider evictProvider);

    //创建目录
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<BaseResponse<Object>> createDir(String token, String name, int parent_id,EvictProvider evictProvider);

    //删除目录
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<BaseResponse<Object>> deleteDir(String token, int d_id,EvictProvider evictProvider);

    //修改目录
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<BaseResponse<Object>> editDir(String token, int d_id, String name,EvictProvider evictProvider);

    //移动目录
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<BaseResponse<Object>> moveDir(String token, int d_id, int parent_id,EvictProvider evictProvider);

    //移动URL
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<BaseResponse<Object>> moveUrl(String token, int d_id, int url_id,EvictProvider evictProvider);
}
