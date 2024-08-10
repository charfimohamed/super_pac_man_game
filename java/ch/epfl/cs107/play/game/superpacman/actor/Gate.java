package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Gate extends AreaEntity {

	private Sprite sprite;
	private Logic logic;

	public Gate(Area area, Orientation orientation, DiscreteCoordinates position, Logic logic) {
		super(area, orientation, position);
		if (orientation == Orientation.DOWN || orientation == Orientation.UP) {
			sprite = new Sprite("superpacman/gate", 1.f, 1.f, (Positionable) this, new RegionOfInterest(0, 0, 64, 64));
		} else {
			sprite = new Sprite("superpacman/gate", 1.f, 1.f, (Positionable) this, new RegionOfInterest(0, 64, 64, 64));
		}
		this.logic = logic;
		((SuperPacmanArea) getOwnerArea()).setSignalGate(position, logic);
	}

	@Override
	public void update(float deltaTime) {
		((SuperPacmanArea) getOwnerArea()).setSignalGate(getCurrentMainCellCoordinates(), logic);
		super.update(deltaTime);
	}

	@Override
	public void draw(Canvas canvas) {
		if ((sprite != null) && logic.isOff())
			sprite.draw(canvas);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		return logic.isOff();
	}

	@Override
	public boolean isCellInteractable() {
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
	}

}
