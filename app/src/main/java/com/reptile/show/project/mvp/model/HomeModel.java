package com.reptile.show.project.mvp.model;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.reptile.show.project.app.AppConstants;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.model.api.service.DirUrlService;
import com.reptile.show.project.mvp.model.entity.BaseResponse;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.model.entity.UrlEntity;

import java.util.ArrayList;
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
    public Observable<BaseResponse<DirectoryEntity>> getDirContent(String token, int d_id) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).getDirContent(token, d_id)
                .map(new Function<BaseResponse<DirectoryEntity>, BaseResponse<DirectoryEntity>>() {
                    @Override
                    public BaseResponse<DirectoryEntity> apply(BaseResponse<DirectoryEntity> directoryEntityBaseResponse) throws Exception {
                        DirectoryEntity directoryEntity = directoryEntityBaseResponse.getInfo();
                        List<DirectoryEntity.DirUrlBean> dirUrlBeans = new ArrayList<>();
                        for (DirectoryEntity.DirectoryBean dir : directoryEntity.getDirectory()) {
                            DirectoryEntity.DirUrlBean dirBean = new DirectoryEntity.DirUrlBean();
                            dirBean.setId(dir.getD_id());
                            dirBean.setTitle(dir.getName());
                            dirBean.setChildCount(dir.getCount());
                            dirBean.setViewType(AppConstants.HomeAdapterViewType.TYPE_DIR);
                            dirUrlBeans.add(dirBean);
                        }
                        for (DirectoryEntity.UrlBean url : directoryEntity.getUrl()) {
                            DirectoryEntity.DirUrlBean urlBean = new DirectoryEntity.DirUrlBean();
                            urlBean.setId(url.getUrl_id());
                            urlBean.setContent(url.getUrl_content());
                            urlBean.setInit_time(url.getInit_time());
                            urlBean.setTitle(url.getUrl_title());
                            urlBean.setViewType(AppConstants.HomeAdapterViewType.TYPE_URL);
                            dirUrlBeans.add(urlBean);
                        }

                        directoryEntity.setDirUrl(dirUrlBeans);
                        directoryEntityBaseResponse.setInfo(directoryEntity);
                        return directoryEntityBaseResponse;
                    }
                });
    }

    @Override
    public Observable<BaseResponse<UrlEntity>> getUrlContent(String token, int url_id) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).getUrlContent(token, url_id);
    }

    @Override
    public Observable<BaseResponse<Object>> createDir(String token, String name, int parent_id) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).createDir(token, name, parent_id);
    }

    @Override
    public Observable<BaseResponse<Object>> deleteDir(String token, int d_id) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).deleteDir(token, d_id);
    }

    @Override
    public Observable<BaseResponse<Object>> editDir(String token, int d_id, String name) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).editDir(token, d_id, name);
    }

    @Override
    public Observable<BaseResponse<Object>> moveDir(String token, int d_id, int parent_id) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).moveDir(token, d_id, parent_id);
    }

    @Override
    public Observable<BaseResponse<Object>> moveUrl(String token, int d_id, int url_id) {
        return mRepositoryManager.obtainRetrofitService(DirUrlService.class).moveUrl(token, d_id, url_id);
    }
}
