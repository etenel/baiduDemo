package com.eternal.demo.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.eternal.demo.mvp.model.entity.AccessTokenEntity;
import com.eternal.demo.mvp.model.entity.FaceResultEntity;
import com.eternal.demo.mvp.ui.activity.FaceActivity;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import top.zibin.luban.Luban;

import javax.inject.Inject;

import com.eternal.demo.mvp.contract.MainContract;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2019 15:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getAuth() {
        mModel.getAuth()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<AccessTokenEntity>(mErrorHandler) {
                    @Override
                    public void onNext(AccessTokenEntity faceDetectionEntity) {
                        LogUtils.json(faceDetectionEntity);
                        if (!TextUtils.isEmpty(faceDetectionEntity.getAccess_token())) {
                            SPUtils.getInstance().put("token", faceDetectionEntity.getAccess_token());
                            SPUtils.getInstance().put("expires", faceDetectionEntity.getExpires_in() + System.currentTimeMillis() / 1000);
                        } else {
                            ToastUtils.showShort(faceDetectionEntity.getError() + "-" + faceDetectionEntity.getError_description());
                        }
                    }
                });
    }

    public void sendFace(Bitmap bitmap) {
        String face_pic_url = "IMG_" + new Date() + ".jpg";
        File file = new File(mApplication.getExternalFilesDirs(Environment.DIRECTORY_PICTURES)[0].getAbsolutePath());
        if (!file.exists()) {
            file.mkdirs();
        }
        File image = new File(mApplication.getExternalFilesDirs(Environment.DIRECTORY_PICTURES)[0].getAbsolutePath(), face_pic_url);
        if (bitmap != null) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(image)) {
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fileOutputStream);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fileOutputStream);
                LogUtils.e(FileUtils.getFileSize(image));
                File uploadFile = Luban.with(mApplication).load(image).get().get(0);
                LogUtils.e(FileUtils.getFileSize(uploadFile));
                uploadFace(uploadFile.getAbsolutePath());
                bitmap.recycle();
            } catch (Exception e) {
                ToastUtils.showShort("保存图片失败");
                e.printStackTrace();
            } finally {
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    private void uploadFace(String path) {
        mModel.uploadFile(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<FaceResultEntity>(mErrorHandler) {
                    @Override
                    public void onNext(FaceResultEntity faceResult) {
                        LogUtils.json(faceResult);
                        if (faceResult.getError_code() == 0 &&faceResult.getResult()!=null) {
                            List<FaceResultEntity.ResultBean.FaceListBean> face_list = faceResult.getResult().getFace_list();
                            if(face_list==null||face_list.size()==0) {
                                return;
                            }
                            //人脸模糊程度大于0.2移除,卡通人物移除
                            LogUtils.e(face_list.size());
                           for(int i = 0; i < face_list.size(); i++) {
                             if(face_list.get(i).getQuality().getBlur()>0.2||face_list.get(i).getFace_type().getType().equals("cartoon")) {
                                 LogUtils.e(i+"-"+face_list.get(i).getQuality().getBlur()+"-"+face_list.get(i).getFace_type().getType());
                                 face_list.remove(i);
                                 i--;
                             }
                           }
                           if(face_list.size()>0) {
                               Intent intent = new Intent(mApplication, FaceActivity.class);
                               intent.putExtra("face_result", faceResult);
                               intent.putExtra("path", path);
                               ArmsUtils.startActivity(intent);
                           }
                        } else {
                            ToastUtils.showShort(faceResult.getError_msg());
                        }
                    }
                });
    }
}
