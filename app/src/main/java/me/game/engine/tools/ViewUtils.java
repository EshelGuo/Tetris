package me.game.engine.tools;

import android.content.res.Resources;

/**
 * Created by guoshiwen on 2020/3/7.
 */
public class ViewUtils {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dp2px(float dpValue) {
		return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static float px2dp(float pxValue) {
		return (pxValue / Resources.getSystem().getDisplayMetrics().density);
	}
}
