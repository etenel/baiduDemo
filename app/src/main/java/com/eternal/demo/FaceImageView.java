package com.eternal.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


import androidx.appcompat.widget.AppCompatImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.eternal.demo.mvp.model.entity.FaceResultEntity;

public class FaceImageView extends AppCompatImageView {
    private FaceResultEntity.ResultBean.FaceListBean.LocationBean locationBean;
    private RectF rectF = new RectF();
    private Paint paint;
    private Bitmap image;
private Canvas mCanvas=new Canvas();


    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setLocationBean(FaceResultEntity.ResultBean.FaceListBean.LocationBean locationBean) {
        this.locationBean = locationBean;
      //  invalidate();
    }

    public FaceImageView(Context context) {
        this(context,null);
    }

    public FaceImageView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public FaceImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(locationBean!=null&&image!=null) {
            mCanvas.setBitmap(image);
            rectF.left = (float) locationBean.getLeft();
            rectF.top = (float) locationBean.getTop();
            rectF.right = (float) (rectF.left + locationBean.getWidth());
            rectF.bottom = (float) (rectF.top + locationBean.getHeight());
            mCanvas.rotate((float) locationBean.getRotation());
            mCanvas.drawRect(rectF, paint);
           setImageBitmap(image);

        }
    }
}
