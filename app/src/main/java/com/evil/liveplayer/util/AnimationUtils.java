package com.evil.liveplayer.util;


import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * @项目名： FileChooser
 * @包名： com.evil.chooser.utils
 * @创建者: Noah.冯
 * @时间: 18:25
 * @描述： 动画
 */

public class AnimationUtils {


    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return
     */
    public static TranslateAnimation moveToViewBottom(long duration) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                1.0f
        );
        mHiddenAction.setDuration(duration);
        return mHiddenAction;
    }

    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocation(long duration) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f
        );
        mHiddenAction.setDuration(duration);
        return mHiddenAction;
    }

    /**
     * 隐藏
     */
    public static AlphaAnimation hideAlpha(long duration) {
        AlphaAnimation mHiddenAction = new AlphaAnimation(1.0f,0.0f);
        mHiddenAction.setDuration(duration);
        return mHiddenAction;
    }

    /**
     * 隐藏
     */
    public static AlphaAnimation showAlpha(long duration) {
        AlphaAnimation mHiddenAction = new AlphaAnimation(0.0f,1.0f);
        mHiddenAction.setDuration(duration);
        return mHiddenAction;
    }

    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return
     */
    public static void moveToViewBottom(View view,long duration) {
        view.setVisibility(View.GONE);
        view.setAnimation(moveToViewBottom(duration));
    }

    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    public static void moveToViewLocation(View view,long duration) {
        view.setVisibility(View.VISIBLE);
        view.setAnimation(moveToViewLocation(duration));
    }

    /**
     * 隐藏
     */
    public static void hideAlpha(View view,long duration) {
        view.setVisibility(View.GONE);
        view.setAnimation(hideAlpha(duration));
    }

    /**
     * 隐藏
     */
    public static void showAlpha(View view,long duration) {
        view.setVisibility(View.VISIBLE);
        view.setAnimation(showAlpha(duration));
    }

    /**
     * 给视图添加点击效果,让背景变深
     */
    public static void addTouchDrak(View view,View.OnClickListener listener) {
        view.setOnTouchListener(new ViewTouchDark());
        if (listener != null) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 给视图添加点击效果,让背景变暗
     */
    public static void addTouchLight(View view,View.OnClickListener listener) {
        view.setOnTouchListener(new ViewTouchLight());

        if (listener != null) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 让控件点击时，颜色变深
     */
    public static class ViewTouchDark implements View.OnTouchListener {

        public final float[] BT_SELECTED = new float[]{
                1,0,0,0,-50,0,1,0,0,-50,0,0,1,0,-50,0,0,0,1,0
        };
        public final float[] BT_NOT_SELECTED = new float[]{
                1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0
        };

        @SuppressWarnings("deprecation")
        @Override
        public boolean onTouch(View v,MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView)v;
                    iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView)v;
                    iv.setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            }
            return false;
        }
    }

    ;

    /**
     * 让控件点击时，颜色变暗
     */
    public static class ViewTouchLight implements View.OnTouchListener {
        public final float[] BT_SELECTED = new float[]{
                1,0,0,0,50,0,1,0,0,50,0,0,1,0,50,0,0,0,1,0
        };
        public final float[] BT_NOT_SELECTED = new float[]{
                1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0
        };

        @SuppressWarnings("deprecation")
        @Override
        public boolean onTouch(View v,MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView)v;
                    iv.setDrawingCacheEnabled(true);

                    iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView)v;
                    iv.setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            }
            return false;
        }
    }
}
