package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Keyboard;

public class Level0 extends SuperPacmanArea {

	private final static DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(10, 1);
	private Key key = new Key(this, Orientation.DOWN, new DiscreteCoordinates(3, 4));

	public static DiscreteCoordinates getPlayerSpawnPosition() {
		return PLAYER_SPAWN_POSITION;
	}

	@Override
	public DiscreteCoordinates getspawnCoordinate() {
		return PLAYER_SPAWN_POSITION;
	}

	@Override
	public String getTitle() {
		return "superpacman/Level0";
	}

	@Override
	public void update(float deltaTime) {
		
		Keyboard keyboard = getKeyboard();
		if (keyboard.get(Keyboard.TAB).isDown() ) {
			setPause(true);
		}
		super.update(deltaTime);
	}

	@Override
	protected void createArea() {
		super.createArea();
		registerActor(new Door("superpacman/Level1", Level1.getPlayerSpawnPosition(), Logic.TRUE, this, Orientation.UP,
				new DiscreteCoordinates(5, 9)));
		registerActor(new Door("superpacman/Level1", Level1.getPlayerSpawnPosition(), Logic.TRUE, this, Orientation.UP,
				new DiscreteCoordinates(6, 9)));
		registerActor(key);
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(5, 8), key));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(6, 8), key));

	}

	@Override
	public boolean isOn() {
		return false;
	}

	@Override
	public boolean isOff() {
		return false;
	}

	@Override
	public float getIntensity() {
		return 0;
	}

}
