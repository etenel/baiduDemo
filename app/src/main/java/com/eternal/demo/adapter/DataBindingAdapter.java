package com.eternal.demo.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.eternal.demo.FaceImageView;
import com.eternal.demo.mvp.model.entity.FaceResultEntity;

public class DataBindingAdapter {
    @BindingAdapter("android:image")
    public static void loadImage(FaceImageView view, String url) {
     view.setImage(BitmapFactory.decodeFile(url).copy(Bitmap.Config.ARGB_8888,true));
    }
    @BindingAdapter("android:location")
    public static void location(FaceImageView view, FaceResultEntity.ResultBean.FaceListBean.LocationBean locationBean) {
    view.setLocationBean(locationBean);
    }
}
