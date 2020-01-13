package com.eternal.demo.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.eternal.demo.di.module.MapModule;
import com.eternal.demo.mvp.contract.MapContract;

import com.jess.arms.di.scope.ActivityScope;
import com.eternal.demo.mvp.ui.activity.MapActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2020 12:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MapModule.class, dependencies = AppComponent.class)
public interface MapComponent {
    void inject(MapActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MapComponent.Builder view(MapContract.View view);

        MapComponent.Builder appComponent(AppComponent appComponent);

        MapComponent build();
    }
}