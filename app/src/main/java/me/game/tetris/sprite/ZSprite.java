package me.game.tetris.sprite;

import java.util.List;

import me.game.engine.view.main.Element;
import me.game.engine.view.main.Screen;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/3/9 15:56
 * <br>desc: TODO
 */
public class ZSprite extends TetrisSprite{

	public ZSprite(Screen screen) {
		super(screen);
	}

	@Override
	public void onSubCreate() {
		//■ ■
		//   ■ ■
		addState(new TetrisState() {
			@Override
			void changeState(List<Element> elements) {
				width = 3;
				height = 2;
				elements.get(0).setXY(0, 0);
				elements.get(1).setXY(1, 0);
				elements.get(2).setXY(1, 1);
				elements.get(3).setXY(2, 1);
			}
		});

		//   ■
		//■ ■
		//■
		addState(new TetrisState() {
			@Override
			void changeState(List<Element> elements) {
				width = 2;
				height = 3;
				elements.get(0).setXY(1, 0);
				elements.get(1).setXY(1, 1);
				elements.get(2).setXY(0, 1);
				elements.get(3).setXY(0, 2);
			}
		});
	}
}
