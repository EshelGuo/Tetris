package me.game.tetris;

import java.util.ArrayList;
import java.util.List;

import me.game.engine.view.main.Element;
import me.game.engine.view.main.Screen;

/**
 * Created by guoshiwen on 2020/3/8.
 */
public class TetrisScreen extends Screen {

	private List<Element> mElements;

	@Override
	public void onCreate() {
		int size = width * height;
		mElements = new ArrayList<>(size);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				mElements.add(new Element(x, y));
			}
		}
	}

	@Override
	public List<Element> getElements() {
		return mElements;
	}
}
