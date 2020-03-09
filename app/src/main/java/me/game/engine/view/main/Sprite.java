package me.game.engine.view.main;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by guoshiwen on 2020/3/8.
 */
public abstract class Sprite {

	protected int x;
	protected int y;

	protected int width;
	protected int height;

	//相对位置
	protected List<Element> mElements;
	private Screen mScreen;

	private State mState = State.FULL;

	public Sprite(Screen screen) {
		mElements = new LinkedList<>();
		mScreen = screen;
		onCreate();
	}

	public List<Element> getElements() {
		return mElements;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setElements(List<Element> elements) {
		mElements = elements;
	}

	public final void update(){
		if(mScreen != null) mScreen.update();
	}

	public abstract void onCreate();
}
