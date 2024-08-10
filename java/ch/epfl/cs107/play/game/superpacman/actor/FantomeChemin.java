package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Queue;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;

public abstract class FantomeChemin extends Fantome {
	
	private final int MAX_RANDOM_ATTEMPT = 200;
	private DiscreteCoordinates targetPosition;
	private Queue<Orientation> path;

	protected Queue<Orientation> getPath() {
		return path;
	}

	protected void setPath(Queue<Orientation> path) {
		this.path = path;
	}

	protected void setTargetPosition(DiscreteCoordinates targetPosition) {

		this.targetPosition = targetPosition;
	}

	protected DiscreteCoordinates getTargetPosition() {
		return targetPosition;
	}

	public FantomeChemin(Area area, Orientation orientation, DiscreteCoordinates position, String sprite) {
		super(area, orientation, position, sprite);
	}

	//gives a random attainable cell 
	public DiscreteCoordinates randomCell() {
		DiscreteCoordinates resultat;
		Queue<Orientation> path;
		do {
			int x = RandomGenerator.getInstance().nextInt(getOwnerArea().getWidth());
			int y = RandomGenerator.getInstance().nextInt(getOwnerArea().getHeight());
			resultat = new DiscreteCoordinates(x, y);
			path = ((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(), resultat);
		} while (path == null);
		return resultat;
	}

	//overload of the method randomCell depending on a distance 
	public DiscreteCoordinates randomCell(int rayon) {
		DiscreteCoordinates resultat;
		Queue<Orientation> path;
		do {
			int x = RandomGenerator.getInstance().nextInt(getOwnerArea().getWidth());
			int y = RandomGenerator.getInstance().nextInt(getOwnerArea().getHeight());
			resultat = new DiscreteCoordinates(x, y);
			path = ((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(), resultat);
		} while ((DiscreteCoordinates.distanceBetween(resultat, getRefuge()) > rayon) || (path == null));
		return resultat;
	}
	
	// overload of the method random cell defined in the ascendant class depending
		// on a minimum distance between two coodinates
		public DiscreteCoordinates randomCell(DiscreteCoordinates coordinates, int distance) {
			DiscreteCoordinates resultat;
			Queue<Orientation> path;
			int attempt = 0;
			do {
				int x = RandomGenerator.getInstance().nextInt(getOwnerArea().getWidth());
				int y = RandomGenerator.getInstance().nextInt(getOwnerArea().getHeight());
				resultat = new DiscreteCoordinates(x, y);
				path = ((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(), resultat);
				if (path != null) {
					attempt++;
				}
			} while ((attempt < MAX_RANDOM_ATTEMPT) && ((path == null)
					|| (DiscreteCoordinates.distanceBetween(resultat, coordinates) < distance)));
			return resultat;
		}
	
	//change the target to a random cell and the path
		public void reevalutePath() {
			setTargetPosition(randomCell());
			setPath(((SuperPacmanArea) getOwnerArea()).shortestPath(getCurrentMainCellCoordinates(), getTargetPosition()));
		}

}
