package ch.epfl.cs107.play.game.tutos.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.tutos.Tuto2Behavior;
import ch.epfl.cs107.play.window.Window;

abstract public class Tuto2Area extends Area {
	private Tuto2Behavior behavior;

	protected abstract void createArea();

	public float getCameraScaleFactor() {
		return 10.f;
	}

	public boolean begin(Window window, FileSystem fileSystem) {

		if (super.begin(window, fileSystem)) {
			// Set the behavior map
			behavior = new Tuto2Behavior(window, getTitle());
			setBehavior(behavior);
			createArea();
			return true;
		}
		return false;
	}

}
