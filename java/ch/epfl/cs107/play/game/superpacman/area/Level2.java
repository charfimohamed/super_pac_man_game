package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.game.superpacman.actor.Teleport;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.And;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Keyboard;

public class Level2 extends SuperPacmanArea {

	private final static DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(15, 29);
	private Key key1 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(3, 16));
	private Key key2 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(26, 16));
	private Key key3 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(2, 8));
	private Key key4 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(27, 8));

	public static DiscreteCoordinates getPlayerSpawnPosition() {
		return PLAYER_SPAWN_POSITION;
	}

	@Override
	public DiscreteCoordinates getspawnCoordinate() {
		return PLAYER_SPAWN_POSITION;
	}

	@Override
	public String getTitle() {
		return "superpacman/Level2";
	}

	@Override
	public void update(float deltaTime) {

		Keyboard keyboard = getKeyboard();
		if (keyboard.get(Keyboard.TAB).isDown()) {
			setPause(true);
		}
		super.update(deltaTime);
	}

	@Override
	protected void createArea() {
		super.createArea();
		registerActor(key1);
		registerActor(key2);
		registerActor(key3);
		registerActor(key4);
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 14), key1));
		registerActor(new Gate(this, Orientation.DOWN, new DiscreteCoordinates(5, 12), key1));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 10), key1));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 8), key1));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 14), key2));
		registerActor(new Gate(this, Orientation.DOWN, new DiscreteCoordinates(24, 12), key2));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 10), key2));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 8), key2));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(10, 2), new And(key3, key4)));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(19, 2), new And(key3, key4)));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(12, 8), new And(key3, key4)));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(17, 8), new And(key3, key4)));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(14, 3), this));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(15, 3), this)); 
		registerActor(new Door("superpacman/Congratulations", new DiscreteCoordinates(0, 0), Logic.TRUE,
				this, Orientation.DOWN, new DiscreteCoordinates(14, 0)));
		registerActor(new Door("superpacman/Congratulations", new DiscreteCoordinates(0, 0), Logic.TRUE,
				this, Orientation.DOWN, new DiscreteCoordinates(15, 0)));
		registerActor(new Teleport("superpacman/Level2", new DiscreteCoordinates(28, 22), Logic.TRUE, this,
				Orientation.LEFT, new DiscreteCoordinates(0, 12)));
		registerActor(new Teleport("superpacman/Level2", new DiscreteCoordinates(1, 12), Logic.TRUE, this,
				Orientation.RIGHT, new DiscreteCoordinates(29, 22)));
	}

	@Override
	public boolean isOn() {
		return getNbDiamondCollected() == getNbDiamondTotal();
	}

	@Override
	public boolean isOff() {
		return !(getNbDiamondCollected() == getNbDiamondTotal());
	}

	@Override
	public float getIntensity() {
		return 0;
	}

}
