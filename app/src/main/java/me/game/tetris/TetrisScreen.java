package me.game.tetris;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;

import java.util.Random;

import me.game.engine.control.GameControl;
import me.game.engine.view.main.GameView;
import me.game.engine.view.main.Screen;
import me.game.tetris.sprite.ISprite;
import me.game.tetris.sprite.JSprite;
import me.game.tetris.sprite.LSprite;
import me.game.tetris.sprite.OSprite;
import me.game.tetris.sprite.SSprite;
import me.game.tetris.sprite.TSprite;
import me.game.tetris.sprite.TetrisSprite;
import me.game.tetris.sprite.ZSprite;

/**
 * Created by guoshiwen on 2020/3/8.
 */
public class TetrisScreen extends Screen implements GameControl.Event {

	private static char[] SEED = new char[]{'I', 'J', 'L', 'O', 'S', 'T', 'Z'};
	private Random mRandom = new Random();
	private Handler mHandler = new Handler(Looper.getMainLooper());

	private TetrisSprite currentTetris;
	private long sinkTime = 1000;
	private SinkTask mSinkTask = new SinkTask();

	public TetrisScreen(GameView gameView) {
		super(gameView);
	}

	@Override
	public void onCreate() {
		int size = width * height;
	}

	@Override
	public void onGameStarted() {
		getGameControl().setEvent(this);
		currentTetris = randomTetris();
		update();
		mHandler.postDelayed(mSinkTask, sinkTime);
	}

	private TetrisSprite randomTetris(){
		int index = mRandom.nextInt(SEED.length);
		TetrisSprite sprite = crateTetris(SEED[index]);
		if(sprite == null) return null;
		sprite.setX((width - sprite.getWidth()) / 2);
		sprite.setY(0);
		return sprite;
	}

	private TetrisSprite crateTetris(char seed){
		switch (seed){
			case 'I':
				return new ISprite(this);
			case 'J':
				return new JSprite(this);
			case 'L':
				return new LSprite(this);
			case 'O':
				return new OSprite(this);
			case 'S':
				return new SSprite(this);
			case 'T':
				return new TSprite(this);
			case 'Z':
				return new ZSprite(this);
		}
		return null;
	}

	@Override
	public void onDraw(Canvas canvas) {
		drawSprite(canvas, currentTetris);
	}

	@Override
	public void onLeft() {
		if(currentTetris != null){
			int x = currentTetris.getX();
			if(x == 0) return;
			currentTetris.setX(x - 1);
		}
		update();
	}

	@Override
	public void onRight() {
		if(currentTetris != null){
			int x = currentTetris.getX();
			if(x + currentTetris.getWidth() == width) return;
			currentTetris.setX(x + 1);
		}
		update();
	}

	@Override
	public void onTop() {

	}

	@Override
	public void onBottom() {
		if(sinkOver()){
			saveCurrentAndShowNextTeris();
		}
		if(currentTetris != null){
			currentTetris.setY(currentTetris.getY() + 1);
		}
		update();
	}

	@Override
	public void onAction() {
		if(currentTetris != null){
			currentTetris.spin();
		}
	}

	private void saveCurrentAndShowNextTeris(){
		currentTetris = randomTetris();
		update();
		mHandler.removeCallbacks(mSinkTask);
		mHandler.postDelayed(mSinkTask, sinkTime);
	}

	private class SinkTask implements Runnable {

		@Override
		public void run() {
			if(sinkOver()){
				saveCurrentAndShowNextTeris();
				return;
			}
			currentTetris.setY(currentTetris.getY() + 1);
			update();
			mHandler.postDelayed(this, sinkTime);
		}
	}

	private boolean sinkOver() {
		if(currentTetris == null) return false;
		if(currentTetris.getY() + currentTetris.getHeight() >= height - 1){
			return true;
		}
		return false;
	}
}
