package com.eternal.demo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.eternal.demo.di.module.FaceScanModule;
import com.eternal.demo.mvp.contract.FaceScanContract;

import com.jess.arms.di.scope.ActivityScope;
import com.eternal.demo.mvp.ui.activity.FaceScanActivity;


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
@ActivityScope
@Component(modules = FaceScanModule.class, dependencies = AppComponent.class)
public interface FaceScanComponent {
    void inject(FaceScanActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FaceScanComponent.Builder view(FaceScanContract.View view);

        FaceScanComponent.Builder appComponent(AppComponent appComponent);

        FaceScanComponent build();
    }
}