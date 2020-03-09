package me.game.engine.view.main;

public enum State {
	NONE(0.0f), HALF(0.5f), FULL(1.0f);

	public float alpha;

	State(float alpha) {
		this.alpha = alpha;
	}
}