package com.evil.liveplayer.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 
 * @author yuelaiye
 *  动态设置Button、ImageView等组件在不同状态下的背景/前景显示效果。
           扩展下的话可以前景/背景的显示效果可以使用网络图片。
 */
public class CreateSelector {
	
    /** 设置Selector。 */
    public static StateListDrawable newSelector(Context context, int idNormal, int idPressed, int idFocused,
                    int idUnable) {
            StateListDrawable bg = new StateListDrawable();
            Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
            Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
            Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
            Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);
            // View.PRESSED_ENABLED_STATE_SET
            bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
            // View.ENABLED_FOCUSED_STATE_SET
            bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
            // View.ENABLED_STATE_SET
            bg.addState(new int[] { android.R.attr.state_enabled }, normal);
            // View.FOCUSED_STATE_SET
            bg.addState(new int[] { android.R.attr.state_focused }, focused);
            // View.WINDOW_FOCUSED_STATE_SET
            bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
            // View.EMPTY_STATE_SET
            bg.addState(new int[] {}, normal);
            return bg;
    }

}
