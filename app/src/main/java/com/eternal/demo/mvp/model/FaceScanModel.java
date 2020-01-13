package com.eternal.demo.mvp.model;

import android.app.Application;

import com.eternal.demo.app.Constant;
import com.eternal.demo.mvp.model.api.ApiService;
import com.eternal.demo.mvp.model.entity.FaceResultEntity;
import com.eternal.demo.utils.Base64Util;
import com.eternal.demo.utils.FileUtil;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.eternal.demo.mvp.contract.FaceScanContract;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


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
public class FaceScanModel extends BaseModel implements FaceScanContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FaceScanModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<FaceResultEntity> uploadFile(String path) {
        try {
            byte[] bytes = FileUtil.readFileByBytes(path);
            path = Base64Util.encode(bytes);
            Map<String, Object> map = new HashMap<>();
            map.put("image", path);
            map.put("image_type", "BASE64");
            map.put("face_field", "age,beauty,expression,face_shape,gender,glasses,race,quality,eye_status,emotion,face_type");
            //设置最多分析5张人脸，百度默认1,最大为10
            map.put("max_face_num", 5);
            path = mGson.toJson(map);
            return mRepositoryManager.obtainRetrofitService(ApiService.class)
                    .uploadFile(Constant.token, RequestBody.create(MediaType.parse("application/json"), path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}