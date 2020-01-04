package com.eternal.demo.app;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.Nullable;

public class Constant {
    public static String token = SPUtils.getInstance().getString("token");
    public static long expires = SPUtils.getInstance().getLong("expires");


    @Nullable
    public static String CITY;
    @Nullable
    public static String PROVINCE;
    @Nullable
    public static String DISTRICT;
}
