package me.game.tetris;

import android.content.Context;
import android.util.AttributeSet;

import me.game.engine.view.main.GameView;

/**
 * Created by guoshiwen on 2020/3/8.
 */
public class TetrisGameView extends GameView {

	public TetrisGameView(Context context) {
		super(context);
	}

	public TetrisGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TetrisGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onCreate() {
		mScreen = new TetrisScreen(this);
	}

	public void setScoreView(TetrisScoreView scoreView) {
		if(mScreen instanceof TetrisScreen){
			((TetrisScreen) mScreen).setScoreView(scoreView);
		}
	}
}
