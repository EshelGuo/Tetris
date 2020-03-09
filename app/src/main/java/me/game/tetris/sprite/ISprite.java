package me.game.tetris.sprite;

import java.util.List;

import me.game.engine.view.main.Element;
import me.game.engine.view.main.Screen;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/3/9 14:23
 * <br>desc: TODO
 */
public class ISprite extends TetrisSprite{

	public ISprite(Screen screen) {
		super(screen);
	}

	@Override
	public void onSubCreate() {
		//■
		//■
		//■
		//■
		addState(new TetrisState() {
			@Override
			void changeState(List<Element> elements) {
				width = 1;
				height = 4;
				for (int i = 0; i < elements.size(); i++) {
					Element element = elements.get(i);
					element.setX(0);
					element.setY(i);
				}
			}
		});

		//■ ■ ■ ■
		addState(new TetrisState() {
			@Override
			void changeState(List<Element> elements) {
				width = 4;
				height = 1;
				for (int i = 0; i < elements.size(); i++) {
					Element element = elements.get(i);
					element.setX(i);
					element.setY(0);
				}
			}
		});
	}

	@Override
	public int size() {
		return 4;
	}
}
