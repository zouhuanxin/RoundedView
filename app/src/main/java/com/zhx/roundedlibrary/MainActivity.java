package com.zhx.roundedlibrary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.zhx.myrounded.RoundedImageView;
import com.zhx.myrounded.RoundedLinearLayout;
import com.zhx.myrounded.util.DensityUtil;

public class MainActivity extends AppCompatActivity {

    private RoundedImageView image1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        image1 = (RoundedImageView) findViewById(R.id.image1);
        image1.setTargetBitmap(getBitmapHeart(DensityUtil.dip2px(this,100),DensityUtil.dip2px(this,100)));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Bitmap getBitmapHeart(float width, float height) {
        Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        Path path = new Path();
        path.addArc(0, 0, width / 2.0f, height / 1.8f, -225, 225);
        path.arcTo(width / 2.0f, 0, width, height / 1.8f, -180, 225, false);
        path.lineTo(width / 2.0f, height);
        canvas.drawPath(path, paint);
        return bitmap;
    }
}
