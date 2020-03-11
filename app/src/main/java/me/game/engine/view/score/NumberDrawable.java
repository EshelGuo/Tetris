package me.game.engine.view.score;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/3/11 10:02
 * <br>desc: TODO
 */
public class NumberDrawable extends Drawable {

	private int number;

	public NumberDrawable() {
	}

	public NumberDrawable(int number) {
		this.number = number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public void draw(@NonNull Canvas canvas) {

	}

	@Override
	public void setAlpha(@Alpha int alpha) {

	}

	@Override
	public void setColorFilter(@Nullable ColorFilter colorFilter) {

	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}
}
