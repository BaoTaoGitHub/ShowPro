package com.reptile.show.project.mvp.model;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Reply;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.reptile.show.project.app.AppConstants;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.model.api.cache.CommonCache;
import com.reptile.show.project.mvp.model.api.service.DirUrlService;
import com.reptile.show.project.mvp.model.entity.BaseResponse;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.model.entity.UrlEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

@FragmentScope
public class HomeModel extends BaseModel implements HomeContract.Model {

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {

    }

    @Override
    public Observable<DirectoryEntity> getDirContent(String token, int d_id,boolean isUpdate) {
//        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).getDirContent(d_id)
//        .map(dirEntity -> {
//            DirectoryEntity directoryEntity = dirEntity.getInfo();
//            List<DirectoryEntity.DirUrlBean> dirUrlBeans = new ArrayList<>();
//            for (DirectoryEntity.DirectoryBean dir : directoryEntity.getDirectory()) {
//                DirectoryEntity.DirUrlBean dirBean = new DirectoryEntity.DirUrlBean();
//                dirBean.setId(dir.getD_id());
//                dirBean.setTitle(dir.getName());
//                dirBean.setChildCount(dir.getCount());
//                dirBean.setParent_id(dir.getParent_id());
//                dirBean.setViewType(AppConstants.HomeAdapterViewType.TYPE_DIR);
//                dirUrlBeans.add(dirBean);
//            }
//            for (DirectoryEntity.UrlBean url : directoryEntity.getUrl()) {
//                DirectoryEntity.DirUrlBean urlBean = new DirectoryEntity.DirUrlBean();
//                urlBean.setId(url.getUrl_id());
//                urlBean.setContent(url.getUrl_content());
//                urlBean.setInit_time(url.getInit_time());
//                urlBean.setTitle(url.getUrl_title());
//                urlBean.setParent_id(url.getParent_id());
//                urlBean.setViewType(AppConstants.HomeAdapterViewType.TYPE_URL);
//                dirUrlBeans.add(urlBean);
//            }
//
//            directoryEntity.setDirUrl(dirUrlBeans);
//            dirEntity.setInfo(directoryEntity);
////                                    directoryEntityBaseResponse.setInfo(directoryEntity);
////                                    Reply<DirectoryEntity> entityReply = new Reply<DirectoryEntity>(directoryEntity,directoryEntityBaseResponse.getSource(),directoryEntityBaseResponse.isEncrypted());
//            return dirEntity;
//        });
        return Observable.just(mRepositoryManager.obtainRetrofitService(DirUrlService.class)
                .getDirContent(d_id))
                .flatMap(new Function<Observable<DirectoryEntity>, ObservableSource<DirectoryEntity>>() {
                    @Override
                    public ObservableSource<DirectoryEntity> apply(@NonNull Observable<DirectoryEntity> baseResponseObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(CommonCache.class)
                                .getDirContent(baseResponseObservable,new DynamicKey(""+ System.currentTimeMillis()),new EvictDynamicKey(isUpdate))
                                .map(directoryEntityBaseResponse -> {
                                    DirectoryEntity returnEntity = directoryEntityBaseResponse.getData();
                                    DirectoryEntity directoryEntity = directoryEntityBaseResponse.getData().getInfo();
                                    List<DirectoryEntity.DirUrlBean> dirUrlBeans = new ArrayList<>();
                                    for (DirectoryEntity.DirectoryBean dir : directoryEntity.getDirectory()) {
                                        DirectoryEntity.DirUrlBean dirBean = new DirectoryEntity.DirUrlBean();
                                        dirBean.setId(dir.getD_id());
                                        dirBean.setTitle(dir.getName());
                                        dirBean.setChildCount(dir.getCount());
                                        dirBean.setParent_id(dir.getParent_id());
                                        dirBean.setViewType(AppConstants.HomeAdapterViewType.TYPE_DIR);
                                        dirUrlBeans.add(dirBean);
                                    }
                                    for (DirectoryEntity.UrlBean url : directoryEntity.getUrl()) {
                                        DirectoryEntity.DirUrlBean urlBean = new DirectoryEntity.DirUrlBean();
                                        urlBean.setId(url.getUrl_id());
                                        urlBean.setContent(url.getUrl_content());
                                        urlBean.setInit_time(url.getInit_time());
                                        urlBean.setTitle(url.getUrl_title());
                                        urlBean.setParent_id(url.getParent_id());
                                        urlBean.setViewType(AppConstants.HomeAdapterViewType.TYPE_URL);
                                        dirUrlBeans.add(urlBean);
                                    }

                                    directoryEntity.setDirUrl(dirUrlBeans);
                                    returnEntity.setInfo(directoryEntity);
//                                    directoryEntityBaseResponse.setInfo(directoryEntity);
//                                    Reply<DirectoryEntity> entityReply = new Reply<DirectoryEntity>(directoryEntity,directoryEntityBaseResponse.getSource(),directoryEntityBaseResponse.isEncrypted());
                                    return returnEntity;
                                });
                    }
                });
    }

    @Override
    public Observable<BaseResponse<UrlEntity>> getUrlContent(String token, int url_id,boolean isUpdate) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).getUrlContent(token, url_id);
    }

    @Override
    public Observable<BaseResponse<Object>> createDir(String token, String name, int parent_id,boolean isUpdate) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).createDir(token, name, parent_id);
    }

    @Override
    public Observable<BaseResponse<Object>> deleteDir(String token, int d_id,boolean isUpdate) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).deleteDir(token, d_id);
    }

    @Override
    public Observable<BaseResponse<Object>> editDir(String token, int d_id, String name,boolean isUpdate) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).editDir(token, d_id, name);
    }

    @Override
    public Observable<BaseResponse<Object>> moveDir(String token, int d_id, int parent_id,boolean isUpdate) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).moveDir(token, d_id, parent_id);
    }

    @Override
    public Observable<BaseResponse<Object>> createUrl(String token, int d_id, String url,boolean isUpdate) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).createUrl(token, d_id, url);
    }

    @Override
    public Observable<BaseResponse<Object>> moveUrl(String token, int d_id, int url_id,boolean isUpdate) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).moveUrl(token, d_id, url_id);
    }
}
