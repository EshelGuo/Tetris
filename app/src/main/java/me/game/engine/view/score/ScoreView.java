package me.game.engine.view.score;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import me.game.engine.tools.ViewUtils;
import me.game.engine.view.main.Element;
import me.game.engine.view.main.GameView;
import me.game.engine.view.main.Sprite;

/**
 * Created by guoshiwen on 2020/3/8.
 */
public class ScoreView extends View {

	public static final int PAINT_WIDTH = ViewUtils.dp2px(1);

	private Paint mPaint = new Paint();
	protected Paint LCDPaint = new Paint();
	protected Paint mTextPaint = new Paint();

	protected int elementSize;

	public ScoreView(Context context) {
		this(context, null);
	}

	public ScoreView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScoreView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(PAINT_WIDTH);
		mPaint.setColor(Color.BLACK);

		Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/LCDAT&TPhoneTimeDate.ttf");
		LCDPaint.setAntiAlias(true);
		LCDPaint.setColor(Color.BLACK);
		LCDPaint.setTypeface(typeface);
		LCDPaint.setTextSize(ViewUtils.dp2px(20));

		mTextPaint.setTextSize(ViewUtils.dp2px(13));
		mTextPaint.setColor(Color.BLACK);
		mTextPaint.setAntiAlias(true);

		setBackgroundColor(0xFF9AA987);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		elementSize = w / 6;
	}

	public void drawSprite(Canvas canvas, Sprite sprite, int y){

		int spriteWidth = sprite.getWidth() * elementSize;
		List<Element> elements = sprite.getElements();
		for (Element element : elements) {
			drawElement(canvas, element, elementSize, GameView.DEFAULT_GAP, (getWidth() - spriteWidth) / 2, y);
		}
	}

	public void drawElement(Canvas canvas, Element element, int elementSize, int gap, int offsetXPx, int offsetYPx) {
		if(element == null) return;
		int left = element.getX() * elementSize + offsetXPx;
		int top = element.getY() * elementSize + offsetYPx;
		int right = left + elementSize;
		int bottom = top + elementSize;

		Drawable drawable = element.getScoreElementDrawable(elementSize);
		drawable.setAlpha((int) (element.getAlpha() * 255));
		drawable.setBounds(left + gap, top + gap, right - gap, bottom - gap);
		drawable.draw(canvas);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawLine(0, 0, 0, getHeight(), mPaint);

//		drawSprite(canvas, new OSprite(null), 100);
	}

	public void drawLCDText(Canvas canvas, String score, float offsetX, float offsetY){
		int width = getWidth();
		float textWidth = LCDPaint.measureText(score);
		Paint.FontMetrics fontMetrics = LCDPaint.getFontMetrics();
		if(offsetX == -1){
			offsetX = (width - textWidth) / 2;
		}
		canvas.drawText(score, offsetX, offsetY - fontMetrics.top, LCDPaint);
	}

	public void drawText(Canvas canvas, String text, float offsetX, float offsetY, Paint paint){
		int width = getWidth();
		float textWidth = paint.measureText(text);
		Paint.FontMetrics fontMetrics = paint.getFontMetrics();
		if(offsetX == -1){
			offsetX = (width - textWidth) / 2;
		}
		canvas.drawText(text, offsetX, offsetY - fontMetrics.top, paint);
	}
}
