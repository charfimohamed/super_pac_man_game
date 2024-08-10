package ch.epfl.cs107.play.game.tutos.actor;

import java.awt.Color;
import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class SimpleGhost extends Entity {
	private final float delta = 0.05f;
	private float hp;
	private Sprite sprite;
	private TextGraphics hpText ;
	public boolean isWeak() {
		if (hp <= 0) {
			return true;
		}
		return false;
	}

	public void strengthen() {
		hp = 10;
	}

	public SimpleGhost(Vector position, String spriteName) {
		super(position);
		hpText = new TextGraphics(Integer.toString((int) hp), 0.4f, Color.BLUE);
		sprite = new Sprite(spriteName, 1, 1.f, (Positionable) this);
		hp = 10;
		this.hpText.setAnchor(new Vector(-0.3f, 0.1f));
		hpText.setParent((Positionable) this);
	}

	public void draw(Canvas canvas) {
		sprite.draw(canvas);
		hpText.draw(canvas);

	}

	public void moveUp() {
		setCurrentPosition(getPosition().add(0.f, delta));
	}

	public void moveDown() {
		setCurrentPosition(getPosition().add(0.f, -delta));

	}

	public void moveLeft() {
		setCurrentPosition(getPosition().add(-delta, 0.f));

	}

	public void moveRight() {
		setCurrentPosition(getPosition().add(delta, 0.f));

	}

	public void update(float deltaTime) {
		if (hp - deltaTime >= 0) {
			hp -= deltaTime;
		} else {
			hp = 0;
		}
		hpText.setText(Integer.toString((int) hp));
	}

}
