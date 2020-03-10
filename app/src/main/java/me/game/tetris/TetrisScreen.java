package me.game.tetris;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.Random;

import me.game.engine.control.GameControl;
import me.game.engine.view.main.Element;
import me.game.engine.view.main.GameView;
import me.game.engine.view.main.Screen;
import me.game.engine.view.main.Sprite;
import me.game.tetris.sprite.ISprite;
import me.game.tetris.sprite.JSprite;
import me.game.tetris.sprite.LSprite;
import me.game.tetris.sprite.LineSprite;
import me.game.tetris.sprite.MatrixSprite;
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
	private MatrixSprite mMatrixSprite;

	public TetrisScreen(GameView gameView) {
		super(gameView);
	}

	@Override
	public void onCreate() {
		width = 10;
		height = 20;
		mMatrixSprite = new MatrixSprite(this);
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
		drawSprite(canvas, mMatrixSprite);
	}

	@Override
	public void onLeft() {
		if(currentTetris != null){
			int x = currentTetris.getX();
			if(x == 0) return;
			currentTetris.save();
			currentTetris.setX(x - 1);
			if(mMatrixSprite.hasOverlapping(currentTetris)){
				currentTetris.restore();
				return;
			}
		}
		update();
	}

	@Override
	public void onRight() {
		if(currentTetris != null){
			int x = currentTetris.getX();
			if(x + currentTetris.getWidth() == width) return;
			currentTetris.save();
			currentTetris.setX(x + 1);
			if(mMatrixSprite.hasOverlapping(currentTetris)){
				currentTetris.restore();
				return;
			}
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
			return;
		}
		if(currentTetris != null){
			currentTetris.setY(currentTetris.getY() + 1);
		}
		update();
	}

	@Override
	public void onAction() {
		if(currentTetris != null){
			currentTetris.save();
			currentTetris.spin();
			int w2 = currentTetris.getWidth();
			if(w2 + currentTetris.getX() > width){
				currentTetris.setX(width - w2);
			}
			if(mMatrixSprite.hasOverlapping(currentTetris)){
				currentTetris.restore();
			}else {
				update();
			}
		}
	}



	private void saveCurrentAndShowNextTeris(){
		mHandler.removeCallbacks(mSinkTask);
		Sprite sprite = currentTetris;
		currentTetris = null;
		mMatrixSprite.addSprite(sprite, new Runnable() {
			@Override
			public void run() {
				currentTetris = randomTetris();
				update();
				mHandler.postDelayed(mSinkTask, sinkTime);
			}
		});
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

		if(currentTetris.bottom() == height - 1){
			return true;
		}

		List<Element> elements = currentTetris.getElements();
		for (Element element : elements) {
			int elementY = currentTetris.getElementY(element);
			if(elementY >= height - 1){
				return true;
			}
			LineSprite line = mMatrixSprite.getLine(elementY + 1);
			if(line.hasElement(currentTetris.getElementX(element))) return true;
		}
		return false;
	}

	@Override
	public void onGameStoped() {
		mHandler.removeCallbacks(mSinkTask);
		currentTetris = null;
		mSinkTask = null;
		mHandler = null;
	}
}
