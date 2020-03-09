package me.game.tetris.sprite;

import java.util.List;

import me.game.engine.view.main.Element;
import me.game.engine.view.main.Screen;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/3/9 14:20
 * <br>desc: L 形
 */
public class LSprite extends TetrisSprite{

	public LSprite(Screen screen) {
		super(screen);
	}

	@Override
	public void onSubCreate() {
		//■ ■
		//   ■
		//   ■
		addState(new TetrisState() {
			@Override
			void changeState(List<Element> elements) {
				width = 2;
				height = 3;
				elements.get(0).setX(0).setY(0);
				elements.get(1).setX(1).setY(0);
				elements.get(2).setX(1).setY(1);
				elements.get(3).setX(1).setY(2);
			}
		});
		//      ■
		//■ ■ ■
		addState(new TetrisState() {
			@Override
			void changeState(List<Element> elements) {
				width = 3;
				height = 2;
				elements.get(0).setX(1).setY(0);
				elements.get(1).setX(1).setY(1);
				elements.get(2).setX(1).setY(2);
				elements.get(3).setX(0).setY(2);
			}
		});
		//■
		//■
		//■ ■
		addState(new TetrisState() {
			@Override
			void changeState(List<Element> elements) {
				width = 2;
				height = 3;
				elements.get(0).setX(0).setY(0);
				elements.get(1).setX(0).setY(1);
				elements.get(2).setX(0).setY(2);
				elements.get(3).setX(1).setY(2);
			}
		});
		//■ ■ ■
		//■
		addState(new TetrisState() {
			@Override
			void changeState(List<Element> elements) {
				width = 3;
				height = 2;
				elements.get(0).setX(0).setY(0);
				elements.get(1).setX(1).setY(0);
				elements.get(2).setX(2).setY(0);
				elements.get(3).setX(0).setY(1);
			}
		});
	}

	@Override
	public int size() {
		return 4;
	}
}
