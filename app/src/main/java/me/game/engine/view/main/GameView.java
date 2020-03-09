package me.game.engine.view.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

import me.game.engine.control.GameControl;

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
	private GameControl mGameControl;

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
			mScreen = new Screen(this);
		}
	}

	public void startGame(){
		//TODO 需与计分面板绑定
		mScreen.onGameStarted();
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
		mScreen.onDraw(canvas);
	}

	public void drawSprite(Canvas canvas, Sprite sprite) {
		if(sprite == null) return;
		int elementSize = mScreen.getElementSizePx();
		List<Element> elements = sprite.getElements();
		if(elements == null) return;
		int offsetX = sprite.getX();
		int offsetY = sprite.getY();
		for (Element element : elements) {
			int left = (element.getX() + offsetX) * elementSize;
			int top = (element.getY() + offsetY) * elementSize;
			int right = left + elementSize;
			int bottom = top + elementSize;

			Drawable drawable = element.getElementDrawable(elementSize);
			drawable.setAlpha((int) (element.getAlpha() * 255));
			drawable.setBounds(left + gap, top + gap, right - gap, bottom - gap);
			drawable.draw(canvas);
		}
	}

	public void drawElement(Canvas canvas, Element element) {
		if(element == null) return;
		int elementSize = mScreen.getElementSizePx();
		int left = element.getX() * elementSize;
		int top = element.getY() * elementSize;
		int right = left + elementSize;
		int bottom = top + elementSize;

		Drawable drawable = element.getElementDrawable(elementSize);
		drawable.setAlpha((int) (element.getAlpha() * 255));
		drawable.setBounds(left + gap, top + gap, right - gap, bottom - gap);
		drawable.draw(canvas);
	}

	public void setGameControl(GameControl gameControl) {
		mGameControl = gameControl;
	}

	public GameControl getGameControl() {
		return mGameControl;
	}
}
