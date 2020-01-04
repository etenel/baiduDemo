package com.eternal.demo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.eternal.demo.di.module.FaceModule;
import com.eternal.demo.mvp.contract.FaceContract;

import com.jess.arms.di.scope.ActivityScope;
import com.eternal.demo.mvp.ui.activity.FaceActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/02/2020 14:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FaceModule.class, dependencies = AppComponent.class)
public interface FaceComponent {
    void inject(FaceActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FaceComponent.Builder view(FaceContract.View view);

        FaceComponent.Builder appComponent(AppComponent appComponent);

        FaceComponent build();
    }
}