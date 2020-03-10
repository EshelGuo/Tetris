package me.game.tetris.sprite;

import android.graphics.Canvas;

import java.util.ArrayList;

import me.game.engine.view.main.Element;
import me.game.engine.view.main.Screen;
import me.game.engine.view.main.Sprite;
import me.game.engine.view.main.State;
import me.game.tetris.TetrisException;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/3/9 14:19
 * <br>desc: 方块落到底部后形成, 代表一行
 */
public class LineSprite extends Sprite {
	private Element[] mLine;

	public LineSprite(Screen screen, int y) {
		super(screen);
		setX(0);
		setY(y);
	}

	@Override
	public void onCreate() {
		int screenWidth = mScreen.getWidth();
		mLine = new Element[screenWidth];
	}

	public void addElement(int x, Element element) {
		if(mLine[x] != null){
			throw new TetrisException("ERROR: 在同一个位置添加Element");
		}
		element.setX(x);
		element.setY(0);
		mLine[x] = element;
	}

	@Override
	public void onDraw(Canvas canvas, int gap, int offsetX, int offsetY) {
		if(mLine == null) return;
		for (Element element : mLine) {
			drawElement(canvas, element, gap, offsetX, offsetY);
		}
	}

	@Override
	public void setState(State state) {
		super.setState(state);
		if(mLine == null) return;
		for (Element element : mLine) {
			if(element == null) continue;
			element.setState(state);
		}
	}

	public boolean isFull(){
		for (Element element : mLine) {
			if(element == null)
				return false;
		}
		return true;
	}

	public boolean hasElement(int x) {
		if (x > mLine.length) return false;
		return mLine[x] != null;
	}
}
