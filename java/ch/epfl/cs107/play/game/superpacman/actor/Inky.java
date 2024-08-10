package ch.epfl.cs107.play.game.superpacman.actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;


public class Inky extends FantomeChemin {

	private final int MAX_DISTANCE_WHEN_SCARED = 5;
	private final int MAX_DISTANCE_WHEN_NOT_SCARED = 10;
	

	public Inky(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, "superpacman/ghost.inky");
		setTargetPosition(randomCell(MAX_DISTANCE_WHEN_NOT_SCARED));
		setPath(((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(), getTargetPosition()));
		
	}

	

	@Override
	public Orientation getNextOrientation() {

		if (isTransition()) {
			setTransition(false);
			if (isAfraid()) {
				setTargetPosition(randomCell(MAX_DISTANCE_WHEN_SCARED));
				setPath(((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
						getTargetPosition()));
			} else {
				setTargetPosition(randomCell(MAX_DISTANCE_WHEN_NOT_SCARED));
				setPath(((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
						getTargetPosition()));
			}
		}
		
		if (isRespawn()) {
			setRespawn(false);
			if (isAfraid()) {
				setTargetPosition(randomCell(MAX_DISTANCE_WHEN_SCARED));
				setPath(((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
						getTargetPosition()));
			} else {
				setTargetPosition(randomCell(MAX_DISTANCE_WHEN_NOT_SCARED));
				setPath(((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
						getTargetPosition()));
			}
		}
		
		if (getCurrentMainCellCoordinates().equals(getTargetPosition())) {
			if (isAfraid()) {
				setTargetPosition(randomCell(MAX_DISTANCE_WHEN_SCARED));
				setPath(((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
						getTargetPosition()));
			} else {
				setTargetPosition(randomCell(MAX_DISTANCE_WHEN_NOT_SCARED));
				setPath(((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
						getTargetPosition()));
			}
		}
		
		if (getPlayer() != null && !isAfraid()) {
			setTargetPosition(getPlayer().getCurrentCells().get(0));
			setPath(((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
					getTargetPosition()));

		}
		
		if (getPath() == null) {
			setTargetPosition(randomCell());
			System.out.println("target" + getTargetPosition());
			setPath(((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(),
					getTargetPosition()));
		}
		
		return getPath().poll();
	}

	
}
