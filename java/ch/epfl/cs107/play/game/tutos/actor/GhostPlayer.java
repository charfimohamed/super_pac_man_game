package ch.epfl.cs107.play.game.tutos.actor;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class GhostPlayer extends MovableAreaEntity {

	private final static int ANIMATION_DURATION = 8;
	private float hp;
	private Sprite sprite;
	private TextGraphics hpText;

	public GhostPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String sprite) {
		super(owner, orientation, coordinates);
		hpText = new TextGraphics(Integer.toString((int) hp), 0.4f, Color.BLUE);
		this.sprite = new Sprite(sprite, 1, 1.f, (Positionable) this);
		hp = 10;
		this.hpText.setAnchor(new Vector(-0.3f, 0.1f));
		hpText.setParent((Positionable) this);
		resetMotion();
	}

	public boolean isWeak() {
		if (hp <= 0) {
			return true;
		}
		return false;
	}

	public void strengthen() {
		hp = 10;
	}

	public void draw(Canvas canvas) {
		sprite.draw(canvas);
		hpText.draw(canvas);
	}

	public void enterArea(Area area, DiscreteCoordinates position) {

		area.registerActor(this);
		area.setViewCandidate(this);
		setOwnerArea(area);
		setCurrentPosition(position.toVector());
		resetMotion();

	}

	public void leaveArea() {
		getOwnerArea().unregisterActor(this);
	}

	public void update(float deltaTime) {
		if (hp - deltaTime >= 0) {
			hp -= deltaTime;
		} else {
			hp = 0;
		}
		hpText.setText(Integer.toString((int) hp));
		Keyboard keyboard = getOwnerArea().getKeyboard();
		moveOrientate(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
		moveOrientate(Orientation.UP, keyboard.get(Keyboard.UP));
		moveOrientate(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
		moveOrientate(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

		super.update(deltaTime);

	}

	private void moveOrientate(Orientation orientation, Button b) {

		if (b.isDown()) {
			if (getOrientation() == orientation)
				move(ANIMATION_DURATION);
			else
				orientate(orientation);
		}
	}

	@Override
	public boolean takeCellSpace() {
		return true;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
	}

}
