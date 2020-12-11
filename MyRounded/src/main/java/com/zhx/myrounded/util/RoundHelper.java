package com.zhx.myrounded.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zhx.myrounded.R;

public class RoundHelper {
    private Context mContext;
    private View mView;
    //当前view的背景图片或者src引用图片
    private Drawable mDrawable;

    private Paint mPaint;
    private Xfermode mXfermode;

    //控件圆角
    //边框同步控件圆角
    private float[] RadiusaRcs = new float[8];
    private float[] StrokeaRcs = new float[8];

    private int mWidth;
    private int mHeight;
    //边框颜色
    private int mStrokeColor;
    //边框宽度
    private float mStrokeWidth;
    //四周的角度
    private float mRadiusTopLeft;
    private float mRadiusTopRight;
    private float mRadiusBottomLeft;
    private float mRadiusBottomRight;
    //高斯模糊数值 0-25
    private int BlurNum;
    //得到一个目标路径源图
    private Bitmap TargetBitmap;
    //线性背景渐变颜色组
    private int[] colors;
    //线性渐变权重组
    private float[] weights;
    /**
     * 线性方向 默认从上至下
     * a 从上到下
     * b 从左到右
     * c 从左上角到右下角
     * d 从右上角到左下角
     */
    private int linearGradientDirectionType = 0;

    public void init(Context context, AttributeSet attrs, View view, Drawable drawable) {
        if (view instanceof ViewGroup && view.getBackground() == null) {
            view.setBackgroundColor(Color.parseColor("#00000000"));
        }
        mContext = context;
        mView = view;
        mDrawable = drawable;
        mPaint = new Paint();
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundCorner);
        if (array == null) {
            return;
        }
        float radius = array.getDimension(R.styleable.RoundCorner_rRadius, 0);
        float radiusLeft = array.getDimension(R.styleable.RoundCorner_rLeftRadius, radius);
        float radiusRight = array.getDimension(R.styleable.RoundCorner_rRightRadius, radius);
        float radiusTop = array.getDimension(R.styleable.RoundCorner_rTopRadius, radius);
        float radiusBottom = array.getDimension(R.styleable.RoundCorner_rBottomRadius, radius);

        mRadiusTopLeft = array.getDimension(R.styleable.RoundCorner_rTopLeftRadius, radiusTop > 0 ? radiusTop : radiusLeft);
        mRadiusTopRight = array.getDimension(R.styleable.RoundCorner_rTopRightRadius, radiusTop > 0 ? radiusTop : radiusRight);
        mRadiusBottomLeft = array.getDimension(R.styleable.RoundCorner_rBottomLeftRadius, radiusBottom > 0 ? radiusBottom : radiusLeft);
        mRadiusBottomRight = array.getDimension(R.styleable.RoundCorner_rBottomRightRadius, radiusBottom > 0 ? radiusBottom : radiusRight);

        mStrokeWidth = array.getDimension(R.styleable.RoundCorner_rStrokeWidth, 0);
        mStrokeColor = array.getColor(R.styleable.RoundCorner_rStrokeColor, 0xffffffff);

        BlurNum = array.getInteger(R.styleable.RoundCorner_blur, 0);

        String colorstr = array.getString(R.styleable.RoundedLinearLayout_lineargradientColor);
        if (!TextUtils.isEmpty(colorstr)) {
            String[] arr = colorstr.split("-");
            colors = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                colors[i] = Color.parseColor(arr[i]);
            }
        }
        String weightstr = array.getString(R.styleable.RoundedLinearLayout_lineargradientWeight);
        if (!TextUtils.isEmpty(weightstr)) {
            String[] arr = weightstr.split("-");
            weights = new float[arr.length];
            for (int i = 0; i < arr.length; i++) {
                weights[i] = Float.parseFloat(arr[i]);
            }
        }
        if (colors != null && weights != null && colors.length != weights.length) {
            throw new IndexOutOfBoundsException("颜色组和权重组长度不匹配");
        }

        linearGradientDirectionType = array.getInteger(R.styleable.RoundedLinearLayout_lineargradientDirectionType,0);

        setData();
        array.recycle();
    }

    private void setData() {
        RadiusaRcs[0] = mRadiusTopLeft - mStrokeWidth;
        RadiusaRcs[1] = mRadiusTopLeft - mStrokeWidth;
        RadiusaRcs[2] = mRadiusTopRight - mStrokeWidth;
        RadiusaRcs[3] = mRadiusTopRight - mStrokeWidth;
        RadiusaRcs[4] = mRadiusBottomLeft - mStrokeWidth;
        RadiusaRcs[5] = mRadiusBottomLeft - mStrokeWidth;
        RadiusaRcs[6] = mRadiusBottomRight - mStrokeWidth;
        RadiusaRcs[7] = mRadiusBottomRight - mStrokeWidth;

        StrokeaRcs[0] = mRadiusTopLeft;
        StrokeaRcs[1] = mRadiusTopLeft;
        StrokeaRcs[2] = mRadiusTopRight;
        StrokeaRcs[3] = mRadiusTopRight;
        StrokeaRcs[4] = mRadiusBottomLeft;
        StrokeaRcs[5] = mRadiusBottomLeft;
        StrokeaRcs[6] = mRadiusBottomRight;
        StrokeaRcs[7] = mRadiusBottomRight;
    }

    public void onSizeChanged(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public void preDraw(Canvas canvas) {
        // save canvas
        canvas.saveLayer(new RectF(0, 0, mWidth, mHeight), null, Canvas.ALL_SAVE_FLAG);
        if (mStrokeWidth > 0) {
            float multipeX = (1.0f - (mStrokeWidth / mWidth));
            float multipeY = (1.0f - (mStrokeWidth / mHeight));
            canvas.scale(multipeX, multipeY, mWidth / 2.0f, mHeight / 2.0f);
        }
    }

    public void drawPath(Canvas canvas) {
        // restart draw backgroup or image
        canvas.save();
        if (colors != null && colors.length > 0) {
            int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
            if (linearGradientDirectionType == 0) {
                y2 = mHeight;
            } else if (linearGradientDirectionType == 1) {
                x2 = mWidth;
            } else if (linearGradientDirectionType == 2) {
                x2 = mWidth;
                y2 = mHeight;
            } else if (linearGradientDirectionType == 3) {
                x1 = mWidth;
                y2 = mHeight;
            }
            mPaint.reset();
            LinearGradient linearGradient = new LinearGradient(x1, y1, x2, y2, colors, weights, Shader.TileMode.CLAMP);
            mPaint.setShader(linearGradient);
            canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
        }
        canvas.restore();

        canvas.save();
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setXfermode(mXfermode);
        Bitmap bitmap = null;
        if (TargetBitmap == null) {
            bitmap = getTargetRadiusBitmap();
        } else {
            bitmap = TargetBitmap;
        }
        canvas.drawBitmap(bitmap, 0f, 0f, mPaint);
        mPaint.setXfermode(null);
        canvas.restore();

        //如果开启到渐变则 边框失效 高斯模糊失效
        if (colors != null && colors.length > 0) {
            return;
        }
        // draw stroke
        canvas.save();
        if (mStrokeWidth > 0) {
            canvas.drawBitmap(getTargetStokeBitmap(), 0f, 0f, mPaint);
        }
        canvas.restore();
        // draw blur
        setBlur(BlurNum);
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public void setWeights(float[] weights) {
        this.weights = weights;
    }

    public void setBlur(int num) {
        if (num < 1 || num > 25) {
            return;
        }
        if (colors != null && colors.length > 0) {
            return;
        }
        if (mDrawable != null && mDrawable instanceof BitmapDrawable) {
            BlurNum = num;
            BitmapDrawable bitmapDrawable = (BitmapDrawable) mDrawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            blur(bitmap, BlurNum);
            if (mView != null) {
                mView.invalidate();
            }
        }
    }

    //得到一个目标圆角源图
    private Bitmap getTargetRadiusBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        Path path = new Path();
        RectF rectF = new RectF(0, 0, mWidth, mHeight);
        path.addRoundRect(rectF, RadiusaRcs, Path.Direction.CCW);
        canvas.drawPath(path, paint);
        return bitmap;
    }

    //得到一个目标边框源图
    private Bitmap getTargetStokeBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(mStrokeColor);
        paint.setStrokeWidth(mStrokeWidth);
        RectF rectF = new RectF(0, 0, mWidth, mHeight);
        Path path = new Path();
        path.addRoundRect(rectF, StrokeaRcs, Path.Direction.CCW);
        canvas.drawPath(path, paint);
        return bitmap;
    }

    //设置目标路径源图
    public void setTargetBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("目标路径源图不能为null");
        }
        TargetBitmap = bitmap;
        if (mView != null) {
            mView.invalidate();
        }
    }

    /**
     * 高斯模糊
     */
    private Bitmap blur(Bitmap bitmap, int radius) throws RSRuntimeException {
        RenderScript rs = null;
        try {
            rs = RenderScript.create(mContext);
            rs.setMessageHandler(new RenderScript.RSMessageHandler());
            Allocation input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(rs, input.getType());
            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

            blur.setInput(input);
            blur.setRadius(radius);
            blur.forEach(output);
            output.copyTo(bitmap);
        } finally {
            if (rs != null) {
                rs.destroy();
            }
        }
        return bitmap;
    }

    /**
     * 角度的操作
     */
    public void setRadius(float radiusDp) {
        if (mContext == null) {
            return;
        }
        setRadius(radiusDp, radiusDp, radiusDp, radiusDp);
    }

    public void setRadius(float radiusTopLeftDp, float radiusTopRightDp, float radiusBottomLeftDp, float radiusBottomRightDp) {
        if (mContext == null) {
            return;
        }
        mRadiusTopLeft = DensityUtil.dip2px(mContext, radiusTopLeftDp);
        mRadiusTopRight = DensityUtil.dip2px(mContext, radiusTopRightDp);
        mRadiusBottomLeft = DensityUtil.dip2px(mContext, radiusBottomLeftDp);
        mRadiusBottomRight = DensityUtil.dip2px(mContext, radiusBottomRightDp);
        if (mView != null) {
            setData();
            mView.invalidate();
        }
    }

    public void setRadiusLeft(float radiusDp) {
        if (mContext == null) {
            return;
        }
        setRadius(radiusDp, 0f, radiusDp, 0f);
    }

    public void setRadiusRight(float radiusDp) {
        if (mContext == null) {
            return;
        }
        setRadius(0f, radiusDp, 0f, radiusDp);
    }

    public void setRadiusTop(float radiusDp) {
        if (mContext == null) {
            return;
        }
        setRadius(radiusDp, radiusDp, 0f, 0f);
    }

    public void setRadiusBottom(float radiusDp) {
        if (mContext == null) {
            return;
        }
        setRadius(0f, 0f, radiusDp, radiusDp);
    }

    public void setRadiusTopLeft(float radiusDp) {
        if (mContext == null) {
            return;
        }
        setRadius(radiusDp, 0, 0, 0);
    }

    public void setRadiusTopRight(float radiusDp) {
        if (mContext == null) {
            return;
        }
        setRadius(0f, radiusDp, 0f, 0f);
    }

    public void setRadiusBottomLeft(float radiusDp) {
        if (mContext == null) {
            return;
        }
        setRadius(0f, 0f, radiusDp, 0f);
    }

    public void setRadiusBottomRight(float radiusDp) {
        if (mContext == null) {
            return;
        }
        setRadius(0f, 0f, 0f, radiusDp);
    }

    /**
     * 边框相关
     */

    public void setStrokeWidth(float widthDp) {
        if (mContext == null) {
            return;
        }
        mStrokeWidth = DensityUtil.dip2px(mContext, widthDp);
        setStrokeWidthColor(mStrokeWidth, mStrokeColor);
    }

    public void setStrokeColor(int color) {
        mStrokeColor = color;
        setStrokeWidthColor(mStrokeWidth, mStrokeColor);
    }

    public void setStrokeWidthColor(float widthDp, int color) {
        if (mContext == null) {
            return;
        }
        mStrokeWidth = DensityUtil.dip2px(mContext, widthDp);
        mStrokeColor = color;
        if (mView != null) {
            mView.invalidate();
        }
    }
}
