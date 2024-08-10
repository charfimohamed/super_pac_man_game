package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Clyde extends FantomeChemin {

	private final static int SPEED_NOT_AFRAID = 15;
	private final static int SPEED_AFRAID = 5;
	private final int MIN_AFRAID_DISTANCE = 10;

	public Clyde(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, "superpacman/ghost.clyde");
	}

	@Override
	public int score() {
		return 1000;
	}

	@Override
	public void scareGhost(boolean etat) {
		if (isAfraid() != etat) {
			setTransition(true);
		}
		setAfraid(etat);
		if (isAfraid()) {
			setSpeed(SPEED_AFRAID);
		} else {
			setSpeed(SPEED_NOT_AFRAID);
		}
	}

	@Override
	public Orientation getNextOrientation() {

		if (isTransition()) {
			setTransition(false);
			reevalutePath();
		}

		if (isRespawn()) {
			setRespawn(false);
			reevalutePath();
		}

		if (getCurrentMainCellCoordinates().equals(getTargetPosition())) {
			reevalutePath();
		}

		if (getPlayer() != null && !isAfraid()) {
			setTargetPosition(getPlayer().getCurrentCells().get(0));
			setPath(((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
					getTargetPosition()));
		}

		if (getPlayer() != null && isAfraid()) {
			setTargetPosition(randomCell(getPlayer().getCurrentCells().get(0), MIN_AFRAID_DISTANCE));
			setPath(((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
					getTargetPosition()));
		}

		if (getPath() == null) {
			reevalutePath();
		}

		return getPath().poll();
	}

}
