package me.game.engine.view.main;

import android.graphics.Canvas;

import java.util.List;

import me.game.engine.control.GameControl;

/**
 * Created by guoshiwen on 2020/3/8.
 */
public class Screen {

	private int widthPx;
	private int heightPx;

	protected int elementSizePx;

	protected int width = 10;
	protected int height = 20;

	private GameView mGameView;

	public Screen(GameView gameView) {
		mGameView = gameView;
		onCreate();
	}

	public void onCreate(){}

	public void onGameStarted(){

	}

	protected GameControl getGameControl(){
		return mGameView.getGameControl();
	}

	public void onSizeChanged(int width, int height){
		this.widthPx = width;
		this.heightPx = height;
		elementSizePx = widthPx / this.width;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getElementSizePx() {
		return elementSizePx;
	}

	public void onDraw(Canvas canvas){

	}

	public void drawSprite(Canvas canvas, Sprite sprite){
		mGameView.drawSprite(canvas, sprite);
	}

	public void drawSprites(Canvas canvas, List<Sprite> sprites){
		if(sprites == null) return;

		for (Sprite sprite : sprites) {
			drawSprite(canvas, sprite);
		}
	}

	public void drawElement(Canvas canvas, Element element) {
		mGameView.drawElement(canvas, element);
	}

	public void drawElements(Canvas canvas, List<Element> elements){
		if(elements == null) return;

		for (Element element : elements) {
			drawElement(canvas, element);
		}
	}

	public final void update(){
		if(mGameView == null) return;
		mGameView.invalidate();
	}
}
