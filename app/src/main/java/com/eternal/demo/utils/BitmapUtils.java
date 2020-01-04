package com.eternal.demo.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapUtils {
    //镜像反转，旋转角度
    public static Bitmap rotateBitmap(Bitmap origin, float angle) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(angle);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }
}
