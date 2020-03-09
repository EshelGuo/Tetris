package me.game.tetris.sprite;

import java.util.List;

import me.game.engine.view.main.Element;
import me.game.engine.view.main.Screen;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/3/9 14:20
 * <br>desc: T 形
 */
public class TSprite extends TetrisSprite{

	public TSprite(Screen screen) {
		super(screen);
	}

	@Override
	public void onSubCreate() {
		//   ■
		//■ ■ ■
		addState(new TetrisState() {
			@Override
			void changeState(List<Element> elements) {
				width = 3;
				height = 2;
				elements.get(0).setXY(0, 1);
				elements.get(1).setXY(1, 1);
				elements.get(2).setXY(2, 1);
				elements.get(3).setXY(1, 0);
			}
		});

		//■
		//■ ■
		//■
		addState(new TetrisState() {
			@Override
			void changeState(List<Element> elements) {
				width = 2;
				height = 3;
				elements.get(0).setXY(0, 0);
				elements.get(1).setXY(0, 1);
				elements.get(2).setXY(0, 2);
				elements.get(3).setXY(1, 1);
			}
		});

		//■ ■ ■
		//   ■
		addState(new TetrisState() {
			@Override
			void changeState(List<Element> elements) {
				width = 3;
				height = 2;
				elements.get(0).setXY(0, 0);
				elements.get(1).setXY(1, 0);
				elements.get(2).setXY(2, 0);
				elements.get(3).setXY(1, 1);
			}
		});

		//   ■
		//■ ■
		//   ■
		addState(new TetrisState() {
			@Override
			void changeState(List<Element> elements) {
				width = 2;
				height = 3;
				elements.get(0).setXY(0, 1);
				elements.get(1).setXY(1, 0);
				elements.get(2).setXY(1, 1);
				elements.get(3).setXY(1, 2);
			}
		});
	}
}
