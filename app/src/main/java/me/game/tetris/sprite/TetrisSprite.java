package me.game.tetris.sprite;

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

	private List<TetrisState> mStates;

	private int index = 0;

	private TetrisState startState;
	private Backup mBackup = new Backup();

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
			startState = mStates.get(0);
		}

		for (int i = 0; i < mStates.size(); i++) {
			TetrisState next = mStates.get(i);
			if(next == startState){
				next.changeState(mElements);
				index = i;
				break;
			}
		}
	}

	public void onSubCreate(){}

	public int size(){
		return 4;
	}

	private int nextIndex(){
		index++;
		if(index >= mStates.size()){
			index = 0;
		}
		return index;
	}

	//向右旋转
	public void spin(){
		if(mStates.size() <= 1){
			return;
		}
		int index = nextIndex();
		mStates.get(index).changeState(mElements);
	}

	public void save(){
		mBackup.index = index;
		mBackup.x = x;
		mBackup.y = y;
	}

	public void restore(){
		index = mBackup.index;
		x = mBackup.x;
		y = mBackup.y;
		mStates.get(index).changeState(mElements);
	}

	public abstract class TetrisState {
//		private int state;
		abstract void changeState(List<Element> elements);
	}

	private class Backup {
		int index;
		int x;
		int y;
	}
}
