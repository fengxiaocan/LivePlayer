package com.evil.liveplayer.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.util
 * @创建者: Noah.冯
 * @时间: 16:16
 * @描述： 与view相关的工具类
 */
public class ViewUtils {

    /**
     * 设置listView的高度为所有子View的高度之和
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0;i < listAdapter.getCount();i++) {
            View listItem = listAdapter.getView(i,null,listView);
            listItem.measure(0,0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 获取Listview的高度，然后设置ViewPager的高度
     *
     * @param listView
     * @return
     */
    public static int setListViewHeightBasedOnChildrens(ListView listView) {
        if (listView == null) {
            return 0;
        }
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount();i < len;i++) { //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i,null,listView);
            listItem.measure(0,0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        return params.height;
    }


    /**
     * 修改整个界面所有控件的字体
     */
    public static void changeFonts(ViewGroup root,String path,Activity act) {
        //path是字体路径
        Typeface tf = Typeface.createFromAsset(act.getAssets(),path);
        for (int i = 0;i < root.getChildCount();i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView)v).setTypeface(tf);
            } else if (v instanceof Button) {
                ((Button)v).setTypeface(tf);
            } else if (v instanceof EditText) {
                ((EditText)v).setTypeface(tf);
            } else if (v instanceof ViewGroup) {
                changeFonts((ViewGroup)v,path,act);
            }
        }
    }

    /**
     * 修改整个界面所有控件的字体大小
     */
    public static void changeTextSize(ViewGroup root,int size,Activity act) {
        for (int i = 0;i < root.getChildCount();i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView)v).setTextSize(size);
            } else if (v instanceof Button) {
                ((Button)v).setTextSize(size);
            } else if (v instanceof EditText) {
                ((EditText)v).setTextSize(size);
            } else if (v instanceof ViewGroup) {
                changeTextSize((ViewGroup)v,size,act);
            }
        }
    }

    /**
     * 不改变控件位置，修改控件大小
     */
    public static void changeWH(View v,int W,int H) {
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)v.getLayoutParams();
        params.width = W;
        params.height = H;
        v.setLayoutParams(params);
    }

    /**
     * 修改控件的高
     */
    public static void changeH(View v,int H) {
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)v.getLayoutParams();
        params.height = H;
        v.setLayoutParams(params);
    }

    /**
     * 获取全局坐标系的一个视图区域， 返回一个填充的Rect对象；该Rect是基于总整个屏幕的
     *
     * @param view
     * @return
     */
    public static Rect getGlobalVisibleRect(View view) {
        Rect r = new Rect();
        view.getGlobalVisibleRect(r);
        return r;
    }

    /**
     * 算该视图在全局坐标系中的x，y值，（注意这个值是要从屏幕顶端算起，也就是索包括了通知栏的高度）
     * 获取在当前屏幕内的绝对坐标
     *
     * @param view
     * @return
     */
    public static int[] getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    /**
     * 计算该视图在它所在的widnow的坐标x，y值，//获取在整个窗口内的绝对坐标
     *
     * @param view
     * @return
     */
    public static int[] getLocationInWindow(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location;
    }

    /**
     * 测量View的宽高
     *
     * @param view View
     */
    public static void measureWidthAndHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(w,h);
    }


    /**
     * 把自身从父View中移除
     */
    public static void removeSelfFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup)parent;
                group.removeView(view);
            }
        }
    }

    /**
     * 判断触点是否落在该View上
     */
    public static boolean isTouchInView(MotionEvent ev,View v) {
        int[] vLoc = new int[2];
        v.getLocationOnScreen(vLoc);
        float motionX = ev.getRawX();
        float motionY = ev.getRawY();
        return motionX >= vLoc[0] &&
               motionX <= (vLoc[0] + v.getWidth()) &&
               motionY >= vLoc[1] &&
               motionY <= (vLoc[1] + v.getHeight());
    }

    /**
     * @param view
     * @param isAll
     */
    public static void requestLayoutParent(View view,boolean isAll) {
        ViewParent parent = view.getParent();
        while (parent != null && parent instanceof View) {
            if (!parent.isLayoutRequested()) {
                parent.requestLayout();
                if (!isAll) {
                    break;
                }
            }
            parent = parent.getParent();
        }
    }


    /**
     * 给TextView设置下划线
     *
     * @param textView
     */
    public static void setTVUnderLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView.getPaint().setAntiAlias(true);
    }


    /**
     * 获取view的宽度
     *
     * @param view
     * @return
     */
    public static int getViewWidth(View view) {
        measureWidthAndHeight(view);
        return view.getMeasuredWidth();
    }

    /**
     * 获取view的高度
     *
     * @param view
     * @return
     */
    public static int getViewHeight(View view) {
        measureWidthAndHeight(view);
        return view.getMeasuredHeight();
    }

    /**
     * 获取view的上下文
     *
     * @param view
     * @return
     */
    public static Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

    public static boolean isShow(View view) {
        return (view.getVisibility() != View.GONE && view.getVisibility() != View.INVISIBLE);
    }
}
