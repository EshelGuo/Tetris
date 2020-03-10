package me.game.tetris.sprite;

import android.os.Handler;
import android.os.Looper;

/**
 * <br>createBy guoshiwen
 * <br>createTime: 2020/3/10 16:12
 * <br>desc: TODO
 */
public class ClearAnimation {

	private Handler mHandler = new Handler(Looper.getMainLooper());
	private int count = 6;
	private Listener mListener;

	public ClearAnimation() {
	}

	public void setListener(Listener listener) {
		mListener = listener;
	}

	public void start(){
		new AnimationTask().run();
	}

	class AnimationTask implements Runnable{
		private int runnedCount = 0;
		@Override
		public void run() {
			runnedCount++;
			if(runnedCount > count){
				if(mListener != null) mListener.onAnimationEnd();
				return;
			}
			if(mListener != null) mListener.onAnimationUpdate(runnedCount);

			mHandler.postDelayed(this, 300);
		}
	}

	public interface Listener{
		void onAnimationUpdate(int count);
		void onAnimationEnd();
	}
}
