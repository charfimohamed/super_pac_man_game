package ch.epfl.cs107.play.game.superpacman.area;

import java.awt.Color;

import ch.epfl.cs107.play.game.areagame.actor.Text;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.TextAlign;
import ch.epfl.cs107.play.window.Keyboard;

public class GameOver extends SuperPacmanArea {

	@Override
	public void update(float deltaTime) {

		Keyboard keyboard = getKeyboard();
		if (keyboard.get(Keyboard.R).isDown()) {
			setRestart(true);
		}
		super.update(deltaTime);
	}

	@Override
	public String getTitle() {
		return "superpacman/gameOver";
	}

	@Override
	public float getCameraScaleFactor() {
		return 25.f;
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

	@Override
	public DiscreteCoordinates getspawnCoordinate() {
		return null;
	}

	@Override
	protected void createArea() {
		registerActor(new Text("GAME OVER ", new DiscreteCoordinates(12, 20), this, true, 4f, Color.RED, true, false,
				TextAlign.Horizontal.CENTER, TextAlign.Vertical.MIDDLE, 1.f, 1.f));
		registerActor(new Text("To restart press : R  " ,
				new DiscreteCoordinates(12, 13), this, true, 1.5f, Color.WHITE, false, false,
				TextAlign.Horizontal.CENTER, TextAlign.Vertical.MIDDLE, 1.f, 1.f));
		registerActor(new Text("Highest Score : " + SuperPacman.getHighestScore(),
				new DiscreteCoordinates(12, 10), this, true, 1.5f, Color.WHITE, false, false,
				TextAlign.Horizontal.CENTER, TextAlign.Vertical.MIDDLE, 1.f, 1.f));
		super.createArea();
	}

}
