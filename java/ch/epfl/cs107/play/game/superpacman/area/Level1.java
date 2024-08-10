package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Teleport;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Keyboard;

public class Level1 extends SuperPacmanArea {
	
	private final static DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(15, 6);

	public static DiscreteCoordinates getPlayerSpawnPosition() {
		return PLAYER_SPAWN_POSITION;
	}

	@Override
	public String getTitle() {
		return "superpacman/Level1";
	}

	@Override
	public DiscreteCoordinates getspawnCoordinate() {
		return PLAYER_SPAWN_POSITION;
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
		registerActor(new Door("superpacman/Level2", Level2.getPlayerSpawnPosition(), Logic.TRUE, this,
				Orientation.DOWN, new DiscreteCoordinates(14, 0)));
		registerActor(new Door("superpacman/Level2", Level2.getPlayerSpawnPosition(), Logic.TRUE, this,
				Orientation.DOWN, new DiscreteCoordinates(15, 0)));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(14, 3), this));
		registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(15, 3), this));
		registerActor(new Teleport("superpacman/Level1", new DiscreteCoordinates(28, 12), Logic.TRUE, this,
				Orientation.LEFT, new DiscreteCoordinates(0, 12)));
		registerActor(new Teleport("superpacman/Level1", new DiscreteCoordinates(1, 12), Logic.TRUE, this,
				Orientation.RIGHT, new DiscreteCoordinates(29, 12)));

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
