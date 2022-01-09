package com.zhx.myrounded;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.zhx.myrounded.util.RoundHelper;
import com.zhx.myrounded.util.RoundMethodInterface;

/**
 * 圆角线性布局
 * 1.支持四个方向圆角 ✅
 * 2.支持线性背景渐变 ✅
 * 3.支持背景高斯模糊 ✅
 * 4.支持边框颜色 ✅
 * 5.支持自定义路径裁剪 ✅ （不支持xml属性定义）
 * <p>
 * ps:
 * 线性背景开启时不允许开启 高斯模糊失效 背景图片设置失效 边框失效
 * <p>
 * getBackground 失效 替换成 getDrawable
 */
public class RoundedImageView extends androidx.appcompat.widget.AppCompatImageView implements RoundMethodInterface {
    private static final String TAG = "RoundedImageView";
    private RoundHelper mHelper = new RoundHelper();
    private Context context;
    private AttributeSet attributeSet;

    public RoundedImageView(Context context) {
        this(context, null);
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (this.context == null) {
            this.context = context;
        }
        if (this.attributeSet == null) {
            this.attributeSet = attrs;
        }
        init();
    }

    private void init() {
        /**
         * 权重
         * src 大于 background
         * 如果src存在则background失效
         */
        if (getDrawable() != null) {
            mHelper.init(context, attributeSet, this, getDrawable());
        } else if (getBackground() != null) {
            mHelper.init(context, attributeSet, this, getBackground());
        } else {
            mHelper.init(context, attributeSet, this, null);
            Log.e(TAG, "src和background属性均为设置");
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHelper.onSizeChanged(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        mHelper.preDraw(canvas);
        super.draw(canvas);
        mHelper.drawPath(canvas);
    }

    @Override
    public void setRadius(float radiusDp) {
        mHelper.setRadius(radiusDp);
    }

    @Override
    public void setRadius(float radiusTopLeftDp, float radiusTopRightDp, float radiusBottomLeftDp, float radiusBottomRightDp) {
        mHelper.setRadius(radiusTopLeftDp, radiusTopRightDp, radiusBottomLeftDp, radiusBottomRightDp);
    }

    @Override
    public void setRadiusLeft(float radiusDp) {
        mHelper.setRadiusLeft(radiusDp);
    }

    @Override
    public void setRadiusRight(float radiusDp) {
        mHelper.setRadiusRight(radiusDp);
    }

    @Override
    public void setRadiusTop(float radiusDp) {
        mHelper.setRadiusTop(radiusDp);
    }

    @Override
    public void setRadiusBottom(float radiusDp) {
        mHelper.setRadiusBottom(radiusDp);
    }

    @Override
    public void setRadiusTopLeft(float radiusDp) {
        mHelper.setRadiusTopLeft(radiusDp);
    }

    @Override
    public void setRadiusTopRight(float radiusDp) {
        mHelper.setRadiusTopRight(radiusDp);
    }

    @Override
    public void setRadiusBottomLeft(float radiusDp) {
        mHelper.setRadiusBottomLeft(radiusDp);
    }

    @Override
    public void setRadiusBottomRight(float radiusDp) {
        mHelper.setRadiusBottomRight(radiusDp);
    }

    @Override
    public void setStrokeWidth(float widthDp) {
        mHelper.setStrokeWidth(widthDp);
    }

    @Override
    public void setStrokeColor(int color) {
        mHelper.setStrokeColor(color);
    }

    //提供的图片加载方法
    public void setImage(Object resid) {
        if (resid instanceof Integer) {
            setImageResource((Integer) resid);
            mHelper.setmDrawable(getDrawable());
        } else {
            setBackground((Drawable) resid);
            mHelper.setmDrawable((Drawable) resid);
        }
    }

    //gilde方法会调用此方法来加载图片
    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        if (drawable != null && mHelper != null) {
            mHelper.setmDrawable(drawable);
        }
    }

    @Override
    public void setBlur(int num) {
        mHelper.setBlur(num);
    }

    @Override
    public void setTargetBitmap(Bitmap bitmap) {
        mHelper.setTargetBitmap(bitmap);
    }

}
