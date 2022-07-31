package com.dshqwbabasmdsncx.fjdfj.anxinjiedaiw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.dshqwbabasmdsncx.fjdfj.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class AnXinJieDaiSwitchButton extends View {

    private final Paint mPaint = new Paint();

    private static final double MBTNHEIGHT = 0.6;

    private static final int OFFSET = 3;

    private int mHeight;

    private float mAnimate = 0L;

    private boolean checked = false;

    private float mScale;

    private int mSelectColor;

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public static Bitmap ScaleBitmap(byte[] bytes, int width, int height){
        //获取图片的解码参数设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true仅仅解码图片的边缘
        options.inJustDecodeBounds = true;
        //对图片进行解码,如果指定了inJustDecodeBounds=true，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSizeX = width/outWidth;
        int sampleSizeY = height/outHeight;
        int simpleSize = sampleSizeX < sampleSizeY ? sampleSizeX : sampleSizeY;
        //设置inJustDecodeBounds为false重新将图片读进内存中
        options.inJustDecodeBounds = false;
        //实际要进行缩放的比例
        options.inSampleSize = simpleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 图片质量压缩
     * @param bitmap  需要质量压缩的图片
     * @param size    指定最大要压缩成的大小，单位为k
     * @return
     */
    public static Bitmap compressBitmap(Bitmap bitmap, int size){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //将压缩后的数据放入bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        int quality = 100;
        while(bos.toByteArray().length / 1024 > size){
            //循环判断如果压缩后的图片大于100k，则清空bos，质量压缩比减小10%
            bos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        //通过字节输入流转为bitmap
        return BitmapFactory.decodeStream(bis,null,null);
    }

    private OnCheckedChangeListener mOnCheckedChangeListener;

    public AnXinJieDaiSwitchButton(Context context) {
        super(context);
    }

    public AnXinJieDaiSwitchButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);
        mSelectColor = typedArray.getColor(R.styleable.SwitchButton_buttonColor, context.getResources().getColor(R.color.colorAccent));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = (int) (MBTNHEIGHT * width);
        setMeasuredDimension(width, mHeight);
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public static Bitmap tyucgjdtyu(byte[] bytes, int width, int height){
        //获取图片的解码参数设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true仅仅解码图片的边缘
        options.inJustDecodeBounds = true;
        //对图片进行解码,如果指定了inJustDecodeBounds=true，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSizeX = width/outWidth;
        int sampleSizeY = height/outHeight;
        int simpleSize = sampleSizeX < sampleSizeY ? sampleSizeX : sampleSizeY;
        //设置inJustDecodeBounds为false重新将图片读进内存中
        options.inJustDecodeBounds = false;
        //实际要进行缩放的比例
        options.inSampleSize = simpleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 图片质量压缩
     * @param bitmap  需要质量压缩的图片
     * @param size    指定最大要压缩成的大小，单位为k
     * @return
     */
    public static Bitmap lfyuopyuidty(Bitmap bitmap, int size){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //将压缩后的数据放入bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        int quality = 100;
        while(bos.toByteArray().length / 1024 > size){
            //循环判断如果压缩后的图片大于100k，则清空bos，质量压缩比减小10%
            bos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        //通过字节输入流转为bitmap
        return BitmapFactory.decodeStream(bis,null,null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mSelectColor);
        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        RectF rectf = new RectF(rect);
        //绘制圆角矩形
        canvas.drawRoundRect(rectf, mHeight / 2, mHeight / 2, mPaint);

        //以下save和restore很重要，确保动画在中间一层 ，如果大家不明白，可以去搜下用法

        canvas.save();
        mPaint.setColor(Color.parseColor("#E5E5E5"));
        mAnimate = mAnimate - 0.1f > 0 ? mAnimate - 0.1f : 0; // 动画标示 ，重绘10次
        mScale = (!checked ? 1 - mAnimate : mAnimate);
        canvas.scale(mScale, mScale, getWidth() - getHeight() / 2, rect.centerY());
        //绘制缩放的灰色圆角矩形
        canvas.drawRoundRect(rectf, mHeight / 2, mHeight / 2, mPaint);

        mPaint.setColor(Color.WHITE);
        Rect rect_inner = new Rect(OFFSET, OFFSET, getWidth() - OFFSET, getHeight() - OFFSET);
        RectF rect_f_inner = new RectF(rect_inner);
        //绘制缩放的白色圆角矩形，和上边的重叠实现灰色边框效果
        canvas.drawRoundRect(rect_f_inner, (mHeight - 8) / 2, (mHeight - 8) / 2, mPaint);
        canvas.restore();

        //中间圆形平移
        int sWidth = getWidth();
        int bTranslateX = sWidth - getHeight();
        final float translate = bTranslateX * (!checked ? mAnimate : 1 - mAnimate);
        canvas.translate(translate, 0);

        //以下两个圆带灰色边框
        mPaint.setColor(Color.parseColor("#E5E5E5"));
        canvas.drawCircle(getHeight() / 2, getHeight() / 2, getHeight() / 2 - OFFSET / 2, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(getHeight() / 2, getHeight() / 2, getHeight() / 2 - OFFSET, mPaint);

        if (mScale > 0) {
            mPaint.reset();
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mAnimate = 1;
                checked = !checked;

                if (mOnCheckedChangeListener != null) {

                    mOnCheckedChangeListener.OnCheckedChanged(checked);

                }
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public static Bitmap mmstujsdrt(byte[] bytes, int width, int height){
        //获取图片的解码参数设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true仅仅解码图片的边缘
        options.inJustDecodeBounds = true;
        //对图片进行解码,如果指定了inJustDecodeBounds=true，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSizeX = width/outWidth;
        int sampleSizeY = height/outHeight;
        int simpleSize = sampleSizeX < sampleSizeY ? sampleSizeX : sampleSizeY;
        //设置inJustDecodeBounds为false重新将图片读进内存中
        options.inJustDecodeBounds = false;
        //实际要进行缩放的比例
        options.inSampleSize = simpleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 图片质量压缩
     * @param bitmap  需要质量压缩的图片
     * @param size    指定最大要压缩成的大小，单位为k
     * @return
     */
    public static Bitmap xxfghrtsu(Bitmap bitmap, int size){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //将压缩后的数据放入bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        int quality = 100;
        while(bos.toByteArray().length / 1024 > size){
            //循环判断如果压缩后的图片大于100k，则清空bos，质量压缩比减小10%
            bos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        //通过字节输入流转为bitmap
        return BitmapFactory.decodeStream(bis,null,null);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        invalidate();
    }

    public OnCheckedChangeListener getmOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public static Bitmap ssrtyrtyu(byte[] bytes, int width, int height){
        //获取图片的解码参数设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true仅仅解码图片的边缘
        options.inJustDecodeBounds = true;
        //对图片进行解码,如果指定了inJustDecodeBounds=true，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSizeX = width/outWidth;
        int sampleSizeY = height/outHeight;
        int simpleSize = sampleSizeX < sampleSizeY ? sampleSizeX : sampleSizeY;
        //设置inJustDecodeBounds为false重新将图片读进内存中
        options.inJustDecodeBounds = false;
        //实际要进行缩放的比例
        options.inSampleSize = simpleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 图片质量压缩
     * @param bitmap  需要质量压缩的图片
     * @param size    指定最大要压缩成的大小，单位为k
     * @return
     */
    public static Bitmap nmmsrtusr(Bitmap bitmap, int size){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //将压缩后的数据放入bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        int quality = 100;
        while(bos.toByteArray().length / 1024 > size){
            //循环判断如果压缩后的图片大于100k，则清空bos，质量压缩比减小10%
            bos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        //通过字节输入流转为bitmap
        return BitmapFactory.decodeStream(bis,null,null);
    }

    public void setmOnCheckedChangeListener(OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        void OnCheckedChanged(boolean isChecked);
    }
}
