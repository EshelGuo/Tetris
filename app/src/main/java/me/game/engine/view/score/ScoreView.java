package me.game.engine.view.score;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import me.game.engine.tools.ViewUtils;

/**
 * Created by guoshiwen on 2020/3/8.
 */
public class ScoreView extends View {

	public static final int PAINT_WIDTH = ViewUtils.dp2px(1);

	private Paint mPaint = new Paint();

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
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawLine(0, 0, 0, getHeight(), mPaint);
	}
}
