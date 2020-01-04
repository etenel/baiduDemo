package com.eternal.demo.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.blankj.utilcode.util.ToastUtils;
import com.eternal.demo.app.Constant;
import com.eternal.demo.databinding.ActivityMainBinding;
import com.eternal.demo.utils.BitmapUtils;
import com.eternal.demo.utils.LocationUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eternal.demo.di.component.DaggerMainComponent;
import com.eternal.demo.mvp.contract.MainContract;
import com.eternal.demo.mvp.presenter.MainPresenter;

import com.eternal.demo.R;


import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.configuration.CameraConfiguration;
import io.fotoapparat.error.CameraErrorListener;
import io.fotoapparat.exception.camera.CameraException;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.result.BitmapPhoto;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.result.WhenDoneListener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static io.fotoapparat.log.LoggersKt.fileLogger;
import static io.fotoapparat.log.LoggersKt.logcat;
import static io.fotoapparat.log.LoggersKt.loggers;

import static io.fotoapparat.selector.AspectRatioSelectorsKt.standardRatio;
import static io.fotoapparat.selector.FlashSelectorsKt.autoFlash;
import static io.fotoapparat.selector.FlashSelectorsKt.autoRedEye;
import static io.fotoapparat.selector.FlashSelectorsKt.off;
import static io.fotoapparat.selector.FlashSelectorsKt.torch;
import static io.fotoapparat.selector.FocusModeSelectorsKt.autoFocus;
import static io.fotoapparat.selector.FocusModeSelectorsKt.continuousFocusPicture;
import static io.fotoapparat.selector.FocusModeSelectorsKt.fixed;
import static io.fotoapparat.selector.LensPositionSelectorsKt.back;
import static io.fotoapparat.selector.LensPositionSelectorsKt.front;

import static io.fotoapparat.selector.PreviewFpsRangeSelectorsKt.highestFps;
import static io.fotoapparat.selector.ResolutionSelectorsKt.highestResolution;
import static io.fotoapparat.selector.SelectorsKt.firstAvailable;
import static io.fotoapparat.selector.SensorSensitivitySelectorsKt.highestSensorSensitivity;

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
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private ActivityMainBinding binding;
    private Fotoapparat fotoapparat;
    private Disposable disposable;
    private long interval = 5;

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
                .lensPosition(back())
                .frameProcessor(new SampleFrameProcessor())
                .logger(loggers(
                        logcat(),
                        fileLogger(this)
                ))
                .cameraErrorCallback(new CameraErrorListener() {
                    @Override
                    public void onError(@NotNull CameraException e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                })
                .build();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        //提前一天刷新token
        if (TextUtils.isEmpty(Constant.token) || Constant.expires - currentTimeMillis < 86400) {
            mPresenter.getAuth();
        }
        PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.LOCATION)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        fotoapparat = createFotoapparat();
                        //百度地图初始化log提示error-code -10问题 https://developer.baidu.com/topic/show/290280
                        LocationUtils.INSTANCE.init().startLocation().setOnReceiveLocation(new LocationUtils.OnReceiveLocation() {
                            @Override
                            public void receiveLocation(@NotNull BDLocation location) {
                            //   ToastUtils.showShort("地址"+location.getAddress().address+"\n经度:"+location.getLongitude()+"纬度:"+location.getLatitude());
                            }
                        });
                    }

                    @Override
                    public void onDenied() {

                    }
                })
                .request();

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
                    if(disposable.isDisposed()) {
                        return;
                    }
                    binding.takePhoto.performClick();
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
        fotoapparat.stop();
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
}
