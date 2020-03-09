package me.game.tetris.sprite;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import me.game.engine.view.main.Element;
import me.game.engine.view.main.Screen;
import me.game.engine.view.main.Sprite;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/3/9 13:34
 * <br>desc: 俄罗斯方块的基本元素
 *
 *
 */
public abstract class TetrisSprite extends Sprite {

	private LinkedList<TetrisState> mStates;
	private Iterator<TetrisState> mIterator;

	private TetrisState startState;

	public TetrisSprite(Screen screen) {
		super(screen);
	}

	public void addState(TetrisState state){
		mStates.add(state);
	}

	public void setStartState(TetrisState startState) {
		this.startState = startState;
	}

	@Override
	public final void onCreate() {
		mStates = new LinkedList<>();
		for (int i = 0; i < size(); i++) {
			mElements.add(new Element());
		}
		onSubCreate();
		if(startState == null){
			startState = mStates.getFirst();
		}
		if(mIterator == null){
			mIterator = mStates.iterator();
		}
		while (mIterator.hasNext()){
			TetrisState next = mIterator.next();
			if(next == startState){
				next.changeState(mElements);
			}
		}
	}

	public void onSubCreate(){}

	public int size(){
		return 4;
	}

	//向右旋转
	public void spin(){
		if(mStates.size() <= 1){
			return;
		}
		if(mIterator == null) mIterator = mStates.iterator();
		if(mIterator.hasNext()){
			mIterator.next().changeState(mElements);
		}else {
			mIterator = mStates.iterator();
			spin();
		}
		update();
	}

	public abstract class TetrisState {
//		private int state;
		abstract void changeState(List<Element> elements);
	}
}
