package com.eternal.demo.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eternal.demo.BR;
import com.eternal.demo.R;
import com.eternal.demo.databinding.ItemFacePictureBinding;
import com.eternal.demo.mvp.model.entity.FaceResultEntity;

import java.util.List;

public class FacePictureAdapter extends BaseQuickAdapter<FaceResultEntity.ResultBean.FaceListBean, BaseViewHolder> {
    private String path;
    private int allPosition;

    public void setAllPosition(int allPosition) {
        this.allPosition = allPosition;
    }

    public FacePictureAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FaceResultEntity.ResultBean.FaceListBean item) {
        ItemFacePictureBinding bind = DataBindingUtil.bind(helper.itemView);
        bind.setVariable(BR.result, item);
        bind.setVariable(BR.path, path);
        bind.setVariable(BR.position, (helper.getAdapterPosition()+1) + "/" + allPosition);
        bind.executePendingBindings();
    }
    public void setPath(String path) {
        this.path = path;
    }

}
