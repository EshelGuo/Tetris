package me.game.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import me.game.engine.tools.ViewUtils;
import me.game.engine.view.score.ScoreView;
import me.game.tetris.sprite.TetrisSprite;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/3/11 16:41
 * <br>desc: TODO
 */
public class TetrisScoreView extends ScoreView {

	private static int DP_5 = ViewUtils.dp2px(5);

	private TetrisSprite mNext;
	private int score;

	public TetrisScoreView(Context context) {
		super(context);
	}

	public TetrisScoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TetrisScoreView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TetrisSprite setNext(TetrisSprite next) {
		TetrisSprite temp = mNext;
		mNext = next;
		invalidate();
		return temp;
	}

	public void changeScore(Score score){
		this.score += score.score;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int startY = 0;
		drawText(canvas, "下一个: ", DP_5, DP_5 * 3, mTextPaint);
		startY += DP_5 * 10;
		if(mNext != null) {
			drawSprite(canvas, mNext, startY);
		}
		startY += 8 * elementSize;
		drawText(canvas, "得分: ", DP_5, startY, mTextPaint);
		startY += DP_5 * 6;
		drawLCDText(canvas, String.valueOf(score), DP_5 * 2, startY);
	}

	public enum Score{
		/**
		 * 下落一个块
		 */
		ONE(10),
		//消一行
		LINE(100),
		//消两行
		LINE_2(200),
		//消3行
		LINE_3(400),
		//消4行
		LINE_4(800);
		private int score;

		Score(int score) {
			this.score = score;
		}
	}
}
