package com.eternal.demo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.baidu.location.BDLocation;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.eternal.demo.app.Constant;
import com.eternal.demo.databinding.ActivityFaceScanBinding;
import com.eternal.demo.utils.BitmapUtils;
import com.eternal.demo.utils.LocationUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eternal.demo.di.component.DaggerFaceScanComponent;
import com.eternal.demo.mvp.contract.FaceScanContract;
import com.eternal.demo.mvp.presenter.FaceScanPresenter;

import com.eternal.demo.R;


import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.error.CameraErrorListener;
import io.fotoapparat.exception.camera.CameraException;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.result.PhotoResult;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static io.fotoapparat.log.LoggersKt.fileLogger;
import static io.fotoapparat.log.LoggersKt.logcat;
import static io.fotoapparat.log.LoggersKt.loggers;
import static io.fotoapparat.selector.LensPositionSelectorsKt.back;
import static io.fotoapparat.selector.LensPositionSelectorsKt.front;


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
public class FaceScanActivity extends BaseActivity<FaceScanPresenter> implements FaceScanContract.View {
    private Fotoapparat fotoapparat;
    private Disposable disposable;
    private long interval = 5;
    private ActivityFaceScanBinding binding;

    private class SampleFrameProcessor implements FrameProcessor {
        @Override
        public void process(@NotNull Frame frame) {
            // Perform frame processing, if needed
        }
    }

    private Fotoapparat createFotoapparat() {
        return Fotoapparat
                .with(this)
                .into(binding.cameraView)
                .focusView(binding.focusView)
                .previewScaleType(ScaleType.CenterCrop)
                .lensPosition(front())
                .frameProcessor(new SampleFrameProcessor())
                .logger(loggers(
                        logcat(),
                        fileLogger(this)
                ))
                .cameraErrorCallback(new CameraErrorListener() {
                    @Override
                    public void onError(@NotNull CameraException e) {
                        Toast.makeText(FaceScanActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                })
                .build();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFaceScanComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_face_scan; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_face_scan);
        binding.setLifecycleOwner(this);
        fotoapparat = createFotoapparat();
        binding.ivClose.setOnClickListener(v->finish());
        // timeInterval(interval);
    }

    /**
     * 自动拍照
     *
     * @param time 拍照间隔时间
     */
    private void timeInterval(long time) {
        //五秒自动拍一次照
        disposable = Observable.interval(0, time, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (disposable.isDisposed()) {
                        return;
                    }
                    binding.takePhoto.performClick();
                });
    }

    public void takePhoto(View view) {
        if (TextUtils.isEmpty(Constant.token)) {
            return;
        }
        PhotoResult photoResult = fotoapparat.autoFocus().takePicture();
        photoResult.toBitmap().whenDone(bitmapPhoto -> {
            if (bitmapPhoto != null) {
                mPresenter.sendFace(BitmapUtils.rotateBitmap(bitmapPhoto.bitmap, -bitmapPhoto.rotationDegrees));
                bitmapPhoto.bitmap.recycle();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStart() {
        super.onStart();
        fotoapparat.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //timeInterval(interval);
    }

    @Override
    protected void onStop() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (PermissionUtils.isGranted(PermissionConstants.CAMERA)) {
            fotoapparat.stop();
        }
        super.onStop();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
