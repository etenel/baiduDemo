package com.eternal.demo.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.RadioGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.eternal.demo.databinding.ActivityMapBinding;
import com.eternal.demo.utils.LocationUtils;
import com.eternal.demo.utils.MylocationListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.eternal.demo.di.component.DaggerMapComponent;
import com.eternal.demo.mvp.contract.MapContract;
import com.eternal.demo.mvp.presenter.MapPresenter;

import com.eternal.demo.R;


import org.jetbrains.annotations.NotNull;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class MapActivity extends BaseActivity<MapPresenter> implements MapContract.View {
    private ActivityMapBinding binding;
    private BaiduMap baiduMap;
    private LocationClient mLocClient;
    //传感器
    private SensorManager sensorManager;
    private MyLocationData myLocationData;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private int radius = 2000;//辐射半径，单位 米
    private MylocationListener myListener = new MylocationListener();
    //传感器
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            double x = event.values[SensorManager.DATA_X];
            if (Math.abs(x - lastX) > 1.0) {
                mCurrentDirection = (int) x;
                myLocationData = new MyLocationData.Builder()
                        .accuracy(mCurrentAccracy)// 设置定位数据的精度信息，单位：米
                        .direction(mCurrentDirection)// 此处设置开发者获取到的方向信息，顺时针0-360
                        .latitude(mCurrentLat)
                        .longitude(mCurrentLon)
                        .build();
                baiduMap.setMyLocationData(myLocationData);
            }
            lastX = x;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    private MapStatus mMapStatus;
    private CircleOptions mCircleOptions;
    private Overlay mCircle;
    private LatLng center;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMapComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_map; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        LocationUtils.INSTANCE.init().startLocation().setOnReceiveLocation(new LocationUtils.OnReceiveLocation() {
            @Override
            public void receiveLocation(@NotNull BDLocation location) {
                binding.tvAddress.setText(String.format("地址：%s 经度:%s纬度:%s", location.getAddrStr(), location.getLongitude(), location.getLatitude()));
            }
        });
        //百度地图初始化log提示error-code -10问题 https://developer.baidu.com/topic/show/290280
        baiduMap = binding.mapView.getMap();
        mLocClient = new LocationClient(this);
        mMapStatus = new MapStatus.Builder().zoom(14f).build();
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        baiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
            }
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
        }
        //圆心位置
        binding.rgSelect.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_one:
                    changeRadius(1000);
                    break;
                case R.id.rb_two:
                    changeRadius(2000);
                    break;
                case R.id.rb_three:
                    changeRadius(3000);
                    break;
                case R.id.rb_five:
                    changeRadius(5000);
                    break;
            }
        });
//构造CircleOptions对象
        mCircleOptions = new CircleOptions()
                .radius(radius)//单位米
                .fillColor(getResources().getColor(R.color.blue)) //填充颜色
                .stroke(new Stroke(5, getResources().getColor(R.color.light_blue)));
        initLocation();
    }

    //更改辐射半径
    private void changeRadius(int circleRadius) {
        if (center != null) {
            radius = circleRadius;
            mCircleOptions.radius(radius).center(center);
            if (mCircle == null) {
                mCircle = baiduMap.addOverlay(mCircleOptions);
            } else {
                mCircle.remove();
                mCircle = baiduMap.addOverlay(mCircleOptions);
            }
        } else {
            ToastUtils.showShort("请等待定位完成");
        }
    }

    private void initLocation() {
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        // 设置坐标类型
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
        //定位回调监听
        myListener.setOnReceiveLocation(new MylocationListener.OnReceiveLocation() {
            @Override
            public void receiveLocation(BDLocation location) {
                if (location == null || binding.mapView == null) {
                    return;
                }
                mCurrentLat = location.getLatitude();
                mCurrentLon = location.getLongitude();
                mCurrentAccracy = location.getRadius();
                myLocationData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())// 设置定位数据的精度信息，单位：米
                        .direction(mCurrentDirection)// 此处设置开发者获取到的方向信息，顺时针0-360
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .build();
                baiduMap.setMyLocationData(myLocationData);
                center = new LatLng(location.getLatitude(), location.getLongitude());
                mCircleOptions.radius(radius).center(center);
                if (mCircle == null) {
                    mCircle = baiduMap.addOverlay(mCircleOptions);
                } else {
                    mCircle.remove();
                    mCircle = baiduMap.addOverlay(mCircleOptions);
                }
                binding.tvAddress.setText("地址：" + location.getAddrStr() + " 经度:" + location.getLongitude() + "纬度:" + location.getLatitude());
            }
        });
    }

    @Override
    public void onResume() {
        binding.mapView.onResume();
        super.onResume();
        if (mLocClient != null) {
            mLocClient.start();
        }
    }

    @Override
    public void onPause() {
        binding.mapView.onPause();
        super.onPause();
        if (mLocClient != null) {
            mLocClient.stop();
        }
    }

    @Override
    public void onDestroy() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
        mLocClient.stop();
        if (baiduMap != null) {
            baiduMap.setMyLocationEnabled(false);
            baiduMap.clear();
        }
        if (binding.mapView != null) {
            binding.mapView.onDestroy();
        }
        super.onDestroy();

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
