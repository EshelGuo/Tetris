package me.game.engine.view.main;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

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
	protected Screen mScreen;

	private State mState = State.FULL;

	public Sprite(Screen screen) {
		mElements = new LinkedList<>();
		mScreen = screen;
		onCreate();
	}

	public void setState(State state) {
		mState = state;
		List<Element> elements = getElements();
		if(elements == null || elements.isEmpty()) return;
		for (Element element : elements) {
			if(element != null) element.setState(mState);
		}
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

	public int bottom(){
		return y + height - 1;
	}

	public int right(){
		return x + width - 1;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getElementX(Element element){
		return getX() + element.getX();
	}

	public int getElementY(Element element){
		return getY() + element.getY();
	}

	public void setElements(List<Element> elements) {
		mElements = elements;
	}

	public void onDraw(Canvas canvas, int gap, int offsetX, int offsetY){
		List<Element> elements = getElements();
		if(elements == null) return;
		for (Element element : elements) {
			drawElement(canvas, element, gap, offsetX, offsetY);
		}
	}

	public void drawSprite(Canvas canvas, Sprite sprite, int gap, int offsetX, int offsetY){
		if(sprite == null) return;
		sprite.onDraw(canvas, gap, offsetX + getX(), offsetY + getY());
	}

	public void drawElement(Canvas canvas, Element element, int gap, int offsetX, int offsetY){
		if(element == null) return;
		int elementSize = mScreen.getElementSizePx();
		offsetX += getX();
		offsetY += getY();

		int left = (element.getX() + offsetX) * elementSize;
		int top = (element.getY() + offsetY) * elementSize;
		int right = left + elementSize;
		int bottom = top + elementSize;

		Drawable drawable = element.getElementDrawable(elementSize);
		drawable.setAlpha((int) (element.getAlpha() * 255));
		drawable.setBounds(left + gap, top + gap, right - gap, bottom - gap);
		drawable.draw(canvas);
	}

	public final void update(){
		if(mScreen != null) mScreen.update();
	}

	public abstract void onCreate();
}
