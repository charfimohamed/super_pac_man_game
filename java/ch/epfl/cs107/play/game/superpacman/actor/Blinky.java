package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;

public class Blinky extends Fantome {
	private int randomInt=3;

	public Blinky(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position, "superpacman/ghost.blinky");

	}

	@Override
	public Orientation getNextOrientation() {
		randomInt = RandomGenerator.getInstance().nextInt(4);
		return  (Orientation.fromInt(randomInt));
	}
}
