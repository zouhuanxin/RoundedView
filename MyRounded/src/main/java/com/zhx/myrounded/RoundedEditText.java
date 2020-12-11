package com.zhx.myrounded;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.zhx.myrounded.util.RoundHelper;
import com.zhx.myrounded.util.RoundMethodInterface;

/**
 * 圆角线性布局
 * 1.支持四个方向圆角 ✅
 * 2.支持边框颜色 ✅
 * 3.支持自定义路径裁剪 ✅ （不支持xml属性定义）
 *
 */
public class RoundedEditText extends androidx.appcompat.widget.AppCompatEditText implements RoundMethodInterface {
    private RoundHelper mHelper = new RoundHelper();
    private Context context;
    private AttributeSet attributeSet;

    public RoundedEditText(Context context) {
        this(context, null);
    }

    public RoundedEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public RoundedEditText(Context context, @Nullable AttributeSet attrs,int defStyleAttr) {
        super(context, attrs,defStyleAttr);
        if (this.context == null) {
            this.context = context;
        }
        if (this.attributeSet == null) {
            this.attributeSet = attrs;
        }
        init();
    }


    private void init() {
        mHelper.init(context, attributeSet, this, getBackground());
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

    }

    @Override
    public void setStrokeColor(int color) {

    }

    @Override
    public void setBlur(int num) {

    }

    @Override
    public void setTargetBitmap(Bitmap bitmap) {

    }

}
