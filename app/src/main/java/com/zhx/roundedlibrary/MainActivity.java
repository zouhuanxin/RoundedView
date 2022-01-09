package com.zhx.roundedlibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.zhx.myrounded.RoundedImageView;
import com.zhx.myrounded.util.DensityUtil;

/**
 * a
 */
public class MainActivity extends AppCompatActivity {

    private RoundedImageView image1;
    private RoundedImageView image2;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
//        image1 = (RoundedImageView) findViewById(R.id.image1);
//        image1.setTargetBitmap(getBitmapHeart(DensityUtil.dip2px(this, 100), DensityUtil.dip2px(this, 100)));
        image2 = (RoundedImageView) findViewById(R.id.image2);
//        Glide.with(this).load(R.drawable.image2).into(new SimpleTarget<Drawable>() {
//            @Override
//            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
//                image2.setImage(resource);
//                image2.setBlur(25);
//            }
//        });
        Glide.with(this)
                .load("https://img1.baidu.com/it/u=554809536,2732110137&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=560")
                .into(image2);
//        image2.setImage(R.drawable.image2);
        image2.setBlur(25);
    }

    Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
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
