package me.game.tetris;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;
import me.game.engine.control.GameControl;
import me.game.engine.view.main.GameView;
import me.game.engine.view.score.ScoreView;

public class MainActivity extends AppCompatActivity implements GameControl.Event {

	private GameControl mGameControl;
	private GameView mGameView;
	private ScoreView mScoreView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mGameControl = findViewById(R.id.game_control);
		mGameView = findViewById(R.id.game_view);
		mScoreView = findViewById(R.id.score_view);

		mGameControl.setTriggerInterval(100);
		mGameControl.setEvent(this);
		mGameView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				int width = mGameView.getWidth();
				ViewGroup.LayoutParams lp = mGameView.getLayoutParams();
				lp.height = width * 2;
				mGameView.setLayoutParams(lp);
				mGameView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				mScoreView.getLayoutParams().height = lp.height;
				mScoreView.setLayoutParams(mScoreView.getLayoutParams());
			}
		});
		mGameView.setGameControl(mGameControl);
		mGameView.startGame();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onLeft() {

	}

	@Override
	public void onRight() {

	}

	@Override
	public void onTop() {

	}

	@Override
	public void onBottom() {

	}

	@Override
	public void onAction() {
	}
}
