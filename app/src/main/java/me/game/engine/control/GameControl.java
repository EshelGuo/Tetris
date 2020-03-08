package me.game.engine.control;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import me.game.tetris.R;
import me.game.engine.tools.ViewUtils;

/**
 * Created by guoshiwen on 2020/3/7.
 */
public class GameControl extends FrameLayout {

	private static final int[] STATE_NORMAL = {-android.R.attr.state_pressed};//-代表此属性为false
	private static final int[] STATE_PRESSED = {android.R.attr.state_pressed};

	private static final Event DEFAULT_EVENT = new DefaultEvent();

	private ImageButton mIbControlLeft;
	private ImageButton mIbControlTop;
	private ImageButton mIbControlRight;
	private ImageButton mIbControlBottom;
	private ImageButton mIbAction;

	private long triggerInterval = 100;
	private Event mEvent = DEFAULT_EVENT;

	public GameControl(Context context) {
		this(context, null);
	}

	public GameControl(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressLint("ClickableViewAccessibility")
	public GameControl(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.layout_control, this);
		mIbControlLeft = findViewById(R.id.ib_control_left);
		mIbControlTop = findViewById(R.id.ib_control_top);
		mIbControlRight = findViewById(R.id.ib_control_right);
		mIbControlBottom = findViewById(R.id.ib_control_bottom);
		mIbAction = findViewById(R.id.ib_action);
		initStyle();
		mIbControlLeft.setOnTouchListener(new OnTouchListenerImpl());
		mIbControlTop.setOnTouchListener(new OnTouchListenerImpl());
		mIbControlRight.setOnTouchListener(new OnTouchListenerImpl());
		mIbControlBottom.setOnTouchListener(new OnTouchListenerImpl());
		mIbAction.setOnTouchListener(new OnTouchListenerImpl());
	}

	private void initStyle() {
		Drawable green = createButtonBackground(0xFF329832);
		Drawable red = createButtonBackground(0xFFD20003);
		Drawable red2 = createButtonBackground(0xFFD20003);
		Drawable yellow = createButtonBackground(0xFFFBFB00);
		Drawable blue = createButtonBackground(0xFF3297FC);

		mIbControlLeft.setBackground(green);
		mIbControlRight.setBackground(yellow);
		mIbControlTop.setBackground(red);
		mIbControlBottom.setBackground(blue);
		mIbAction.setBackground(red2);
	}

	public void setTriggerInterval(long triggerInterval) {
		this.triggerInterval = triggerInterval;
	}

	public void setEvent(Event event) {
		mEvent = event;
	}

	private Drawable createButtonBackground(int color) {
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(STATE_NORMAL, createButtonDrawableByState(color, false));
		drawable.addState(STATE_PRESSED, createButtonDrawableByState(color, true));
		return drawable;
	}

	private Drawable createButtonDrawableByState(int color, boolean statePress) {
		ShapeDrawable shadow = new ShapeDrawable(new OvalShape());
		shadow.getPaint().setColor(getResources().getColor(R.color.color_shadow));

		ShapeDrawable main = new ShapeDrawable(new OvalShape());
		main.getPaint().setColor(color);
		LayerDrawable drawable = new LayerDrawable(new Drawable[]{shadow, main});
		int shadowSize = ViewUtils.dp2px(statePress ? 1 : 2);
		drawable.setLayerInset(1, 0, 0, shadowSize, shadowSize);
		return drawable;
	}

	private void onClick(View v) {
		switch (v.getId()) {
			case R.id.ib_control_left:
				mEvent.onLeft();
				break;
			case R.id.ib_control_top:
				mEvent.onTop();
				break;
			case R.id.ib_control_right:
				mEvent.onRight();
				break;
			case R.id.ib_control_bottom:
				mEvent.onBottom();
				break;
			case R.id.ib_action:
				mEvent.onAction();
				break;
		}
	}

	public class OnTouchListenerImpl implements OnTouchListener {

		private ViewConfiguration mViewConfiguration = ViewConfiguration.get(getContext());
		private Handler mHandler = new Handler(Looper.getMainLooper());

		private PointF downPoint = new PointF();
		private PointF movePoint = new PointF();
		//超过此时间将认为此事件为长按时间
		private int longPressTimeout = ViewConfiguration.getLongPressTimeout();
		private boolean isLongPressEvent;
		private boolean loseFocus;
		private View target;

		private LongPressTriggerTask mLongPressTriggerTask = new LongPressTriggerTask();
		private LongPressInvokeTask mLongPressInvokeTask = new LongPressInvokeTask();

		private void updateViewState(View view, int[] state){
			Drawable background = view.getBackground();
			if(background instanceof StateListDrawable){
				background.setState(state);
			}
		}

		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					target = v;
					isLongPressEvent = false;
					loseFocus = false;
					downPoint.x = event.getX();
					downPoint.y = event.getY();
					mHandler.postDelayed(mLongPressTriggerTask, longPressTimeout);
					updateViewState(v, STATE_PRESSED);
					return true;
				case MotionEvent.ACTION_MOVE:
					int touchSlop = mViewConfiguration.getScaledTouchSlop();
					movePoint.x = event.getX();
					movePoint.y = event.getY();
					if (Math.abs(movePoint.x - downPoint.x) > touchSlop ||
							Math.abs(movePoint.y - downPoint.y) > touchSlop) {
						mHandler.removeCallbacks(mLongPressTriggerTask);
						mHandler.removeCallbacks(mLongPressInvokeTask);
						loseFocus = true;
						updateViewState(v, STATE_NORMAL);
						return false;
					}
					return true;
				case MotionEvent.ACTION_UP:
					mHandler.removeCallbacks(mLongPressTriggerTask);
					mHandler.removeCallbacks(mLongPressInvokeTask);
					if(!loseFocus){
						if (!isLongPressEvent) {
							onClick(v);
						}
						updateViewState(v, STATE_NORMAL);
					}
					break;
			}
			return true;
		}

		private class LongPressTriggerTask implements Runnable {

			@Override
			public void run() {
				isLongPressEvent = true;
				onClick(target);
				mHandler.postDelayed(mLongPressInvokeTask, triggerInterval);
			}
		}

		private class LongPressInvokeTask implements Runnable {

			@Override
			public void run() {
				onClick(target);
				mHandler.postDelayed(this, triggerInterval);
			}
		}
	}

	public static class DefaultEvent implements Event {

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

	public interface Event {
		void onLeft();

		void onRight();

		void onTop();

		void onBottom();

		void onAction();
	}
}
