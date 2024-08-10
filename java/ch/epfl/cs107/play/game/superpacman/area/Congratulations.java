package ch.epfl.cs107.play.game.superpacman.area;

import java.awt.Color;

import ch.epfl.cs107.play.game.areagame.actor.Text;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.TextAlign;
import ch.epfl.cs107.play.window.Keyboard;

public class Congratulations extends SuperPacmanArea {

	@Override
	public void update(float deltaTime) {

		Keyboard keyboard = getKeyboard();
		if (keyboard.get(Keyboard.R).isDown()) {
			setRestart(true);
		}
		super.update(deltaTime);
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
	public String getTitle() {
		return "superpacman/Congratulations";
	}

	@Override
	public DiscreteCoordinates getspawnCoordinate() {
		return null;
	}

	@Override
	protected void createArea() {
		registerActor(new Text("Congratulations", new DiscreteCoordinates(7, 5), this, true, 1.5f, Color.RED, true,
				false, TextAlign.Horizontal.CENTER, TextAlign.Vertical.MIDDLE, 1.f, 1.f));
		registerActor(new Text("Highest Score : " + SuperPacman.getHighestScore(), new DiscreteCoordinates(7, 3), this,
				true, 1.5f, Color.WHITE, false, false, TextAlign.Horizontal.CENTER, TextAlign.Vertical.MIDDLE, 1.f,
				1.f));
		registerActor(new Text("To restart press : R ", new DiscreteCoordinates(7, 1), this, true, 0.5f, Color.WHITE,
				true, false, TextAlign.Horizontal.CENTER, TextAlign.Vertical.MIDDLE, 1.f, 1.f));
	}

}
