package ch.epfl.cs107.play.tutos;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.tutos.actor.SimpleGhost;
import ch.epfl.cs107.play.game.tutos.area.tuto1.Ferme;
import ch.epfl.cs107.play.game.tutos.area.tuto1.Village;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class Tuto1 extends AreaGame {

	private SimpleGhost player;

	private void createAreas() {
		addArea(new Ferme());
		addArea(new Village());
	}

	public void end() {

	}

	public void update(float deltaTime) {
		super.update(deltaTime);
		Keyboard keyboard = getWindow().getKeyboard();
		Button key = keyboard.get(Keyboard.UP);
		if (key.isDown()) {
			player.moveUp();
		}
		key = keyboard.get(Keyboard.DOWN);
		if (key.isDown()) {
			player.moveDown();
		}
		key = keyboard.get(Keyboard.LEFT);
		if (key.isDown()) {
			player.moveLeft();
		}
		key = keyboard.get(Keyboard.RIGHT);
		if (key.isDown()) {
			player.moveRight();
		}
		if (player.isWeak()) {
			switchArea();
		}

	}

	public boolean begin(Window window, FileSystem fileSystem) {

		if (super.begin(window, fileSystem)) {
			player = new SimpleGhost(new Vector(18, 7), "ghost.1");
			createAreas();
			Area currentArea = setCurrentArea("zelda/Ferme", true);
			currentArea.registerActor(player);
			currentArea.setViewCandidate(player);
			return true;
		} else
			return false;

	}

	public String getTitle() {
		return "Tuto1";

	}

	public void switchArea() {
		Area currentArea = getCurrentArea();
		currentArea.unregisterActor(player);
		player.strengthen();

		if ((currentArea.getTitle()).equals("zelda/Ferme")) {
			currentArea = setCurrentArea("zelda/Village", true);
		} else {
			currentArea = setCurrentArea("zelda/Ferme", true);
		}
		currentArea.registerActor(player);
		currentArea.setViewCandidate(player);

	}

}
