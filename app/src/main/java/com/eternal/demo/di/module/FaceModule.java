package com.eternal.demo.di.module;

import com.eternal.demo.R;
import com.eternal.demo.adapter.FacePictureAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.eternal.demo.mvp.contract.FaceContract;
import com.eternal.demo.mvp.model.FaceModel;


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
@Module
public abstract class FaceModule {

    @Binds
    abstract FaceContract.Model bindFaceModel(FaceModel model);

    @ActivityScope
    @Provides
    static FacePictureAdapter provideFacePictureAdapter() {
        return new FacePictureAdapter(R.layout.item_face_picture);
    }
}