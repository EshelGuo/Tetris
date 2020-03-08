package me.game.engine.view.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by guoshiwen on 2020/3/8.
 */
public class GameView extends View {

	public static final String TAG = "GameView";

	protected Screen mScreen;

	private int offsetX;
	private int offsetY;

	//纠正过的宽高
	private int width;
	private int height;

	protected int gap = 2;//px

	public GameView(Context context) {
		this(context, null);
	}

	public GameView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setBackgroundColor(Setting.BASEIC_BACKGROUND);
		onCreate();
		if(mScreen == null){
			mScreen = new Screen();
		}
	}

	public void onCreate(){

	}

	@Override
	protected void onSizeChanged(int width, int height, int oldw, int oldh) {
		super.onSizeChanged(width, height, oldw, oldh);
		Log.d(TAG, "onSizeChanged() called with: w = [" + width + "], h = [" + height + "], oldw = [" + oldw + "], oldh = [" + oldh + "]");
		//借用变量
		oldw = width;
		oldh = height;

		float w_h_scale = mScreen.getWidth() * 1.0f / mScreen.getHeight();//宽高比
		//等比缩放，使绘制的元素都为方形
		height = (int) (oldw / w_h_scale);
		if(height <= oldh){
			offsetX = 0;
			offsetY = (oldh - height) / 2;
			this.width = oldw;
			this.height = height;
		}else {
			width = (int) (oldh * w_h_scale);

			offsetX = (oldw - width) / 2;
			offsetY = 0;

			this.width = width;
			this.height = oldh;
		}
		mScreen.onSizeChanged(this.width, this.height);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		Log.d(TAG, "onMeasure() called with: widthMeasure = [" + width + "], heightMeasure = [" + height + "]");
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		List<Element> elements = mScreen.getElements();
		if(elements == null || elements.isEmpty()){
			return;
		}


		for (Element element : elements) {
			drawElement(canvas, element);
		}
	}

	private void drawElement(Canvas canvas, Element element) {
		int elementSize = mScreen.getElementSizePx();
		int left = element.getX() * elementSize;
		int top = element.getY() * elementSize;
		int right = left + elementSize;
		int bottom = top + elementSize;

		Drawable drawable = element.getElementDrawable(elementSize);
		drawable.setBounds(left + gap, top + gap, right - gap, bottom - gap);
		drawable.draw(canvas);
	}
}
