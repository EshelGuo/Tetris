package me.game.engine.view.main;

import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;

/**
 * Created by guoshiwen on 2020/3/8.
 */
public class Element {

	private int x;
	private int y;

	private int sizePx;
	private float alpha = 1.0f;

	private State state = State.FULL;

	private static Drawable mDrawable;

	public Element() {
	}

	public Element(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Element setX(int x) {
		this.x = x;
		return this;
	}

	public Element setXY(int x, int y){
		return setX(x).setY(y);
	}

	public Element setY(int y) {
		this.y = y;
		return this;
	}

	public Element setAlpha(float alpha) {
		this.alpha = alpha;
		return this;
	}

	public float getAlpha() {
		return alpha;
	}

	public Element setState(State state) {
		this.state = state;
		setAlpha(state.alpha);
		return this;
	}

	public State getState() {
		return state;
	}

	public Drawable getElementDrawable(int sizePx){
		if(this.sizePx != sizePx){
			mDrawable = createElementDrawable(sizePx);
			this.sizePx = sizePx;
		}
		return mDrawable;
	}

	private Drawable createElementDrawable(int sizePx){
		ShapeDrawable innerDrawable = new ShapeDrawable(new RectShape());
		innerDrawable.getPaint().setColor(Setting.BASEIC_COLOR);

		float corners = sizePx * 0.13f;
		@SuppressWarnings("UnnecessaryLocalVariable")
		float stroke_size = corners;
		RoundRectShape shape = new RoundRectShape(
				new float[]{corners, corners, corners, corners, corners, corners, corners, corners}
				, new RectF(stroke_size, stroke_size, stroke_size, stroke_size)
				, new float[]{corners, corners, corners, corners, corners, corners, corners, corners}
		);
		ShapeDrawable outsideDrawable = new ShapeDrawable(shape);
		Paint outsidePaint = outsideDrawable.getPaint();
		outsidePaint.setColor(Setting.BASEIC_COLOR);
		outsidePaint.setStyle(Paint.Style.FILL);

		int padding = (int) (sizePx * 0.33f);
		LayerDrawable drawable = new LayerDrawable(new Drawable[]{innerDrawable, outsideDrawable});
		drawable.setLayerInset(0, padding, padding, padding, padding);
		return drawable;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
