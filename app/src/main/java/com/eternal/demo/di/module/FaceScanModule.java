package com.eternal.demo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.eternal.demo.mvp.contract.FaceScanContract;
import com.eternal.demo.mvp.model.FaceScanModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2020 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class FaceScanModule {

    @Binds
    abstract FaceScanContract.Model bindFaceScanModel(FaceScanModel model);
}