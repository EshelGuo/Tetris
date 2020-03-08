package me.game.engine.view.main;

import java.util.List;

/**
 * Created by guoshiwen on 2020/3/8.
 */
public class Screen {

	private int widthPx;
	private int heightPx;

	protected int elementSizePx;

	protected int width = 10;
	protected int height = 20;

	public Screen() {
		onCreate();
	}

	public void onCreate(){}

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

	public List<Element> getElements(){
		return null;
	}
}
