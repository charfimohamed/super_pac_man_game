package ch.epfl.cs107.play.game.superpacman.area;

import java.awt.Color;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Text;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.TextAlign;
import ch.epfl.cs107.play.window.Keyboard;

public class MenuPause extends SuperPacmanArea {

	@Override
	public void update(float deltaTime) {

		Keyboard keyboard = getKeyboard();
		if (keyboard.get(Keyboard.SPACE).isDown()) {
			setResume(true);
		}
		super.update(deltaTime);
	}

	@Override
	public String getTitle() {
		return "superpacman/menuPause";
	}

	@Override
	public float getCameraScaleFactor() {
		return 32.f;
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
		registerActor(new Text("Pause", new DiscreteCoordinates(16, 20), this, true, 3.f, Color.BLACK, true, true,
				TextAlign.Horizontal.CENTER, TextAlign.Vertical.MIDDLE, 1.f, 1.f));
		registerActor(new Text("To resume press : SPACE ", new DiscreteCoordinates(16, 15), this, true, 1.5f,
				Color.DARK_GRAY, false, false, TextAlign.Horizontal.CENTER, TextAlign.Vertical.MIDDLE, 1.f, 1.f));
		registerActor(new Background(this, new RegionOfInterest(0, 0, 240, 240), "superpacman/menuPause"));
	}

}
