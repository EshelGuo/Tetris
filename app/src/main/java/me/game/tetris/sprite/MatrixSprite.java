package me.game.tetris.sprite;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.game.engine.view.main.Element;
import me.game.engine.view.main.Screen;
import me.game.engine.view.main.Sprite;
import me.game.engine.view.main.State;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/3/10 11:29
 * <br>desc: TODO
 */
public class MatrixSprite extends Sprite {

	private LineSprite[] mLines;

	public MatrixSprite(Screen screen) {
		super(screen);
	}

	@Override
	public void onCreate() {
		int screenHeight = mScreen.getHeight();
		mLines = new LineSprite[screenHeight];
	}

	@Override
	public void onDraw(Canvas canvas, int gap, int offsetX, int offsetY) {
		if (mLines == null) return;
		for (LineSprite line : mLines) {
			drawSprite(canvas, line, gap, offsetX, offsetY);
		}
	}

	//是否重叠
	public boolean hasOverlapping(Sprite sprite){
		List<Element> elements = sprite.getElements();
		for (Element element : elements) {
			LineSprite line = getLine(sprite.getElementY(element));
			if(line.hasElement(sprite.getElementX(element))) return true;
		}
		return false;
	}

	public LineSprite getLine(int y){
		LineSprite line = mLines[y];
		if(line == null){
			line = new LineSprite(mScreen, y);
			mLines[y] = line;
		}
		return line;
	}

	public void addSprite(Sprite sprite, final Callback callback){
		final List<LineSprite> fullLines = new ArrayList<>(3);
		List<Element> elements = sprite.getElements();
		for (Element element : elements) {
			LineSprite line = getLine(sprite.getElementY(element));
			line.addElement(sprite.getElementX(element), element);
			if(line.isFull()){
				fullLines.add(line);
			}
		}
		if(fullLines.isEmpty()){
			callback.run(0);
			return;
		}
		ClearAnimation animation = new ClearAnimation();
		animation.setListener(new ClearAnimation.Listener() {
			@Override
			public void onAnimationUpdate(int count) {
				for (LineSprite line : fullLines) {
					line.setState(count % 2 == 0 ? State.FULL : State.NONE);
				}
				update();
			}

			@Override
			public void onAnimationEnd() {
				removeFullLines(fullLines);
				callback.run(fullLines.size());
			}
		});
		animation.start();
	}

	private void removeFullLines(List<LineSprite> fullLines){
		List<Integer> fullLineNumbers = new ArrayList<>(fullLines.size());
		for (LineSprite fullLine : fullLines) {
			fullLineNumbers.add(fullLine.getY());
		}

		Collections.sort(fullLineNumbers, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		});

		for (int currentLineNumber = fullLineNumbers.get(0); currentLineNumber >= 0; currentLineNumber--) {
			LineSprite line = mLines[currentLineNumber];
			int dropNumber = 0;
			for (Integer fullLineNumber : fullLineNumbers) {
				if(currentLineNumber < fullLineNumber){
					dropNumber++;
				}
			}
			if(dropNumber > 0) {
				if(line != null) {
					line.setY(currentLineNumber + dropNumber);
				}
				mLines[currentLineNumber + dropNumber] = line;
				mLines[currentLineNumber] = null;
			}
		}

	}

	public interface Callback{
		void run(int lineNumber);
	}
}
