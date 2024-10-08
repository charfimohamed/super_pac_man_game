package ch.epfl.cs107.play.tutos;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.tutos.actor.GhostPlayer;
import ch.epfl.cs107.play.game.tutos.area.tuto2.Ferme;
import ch.epfl.cs107.play.game.tutos.area.tuto2.Village;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class Tuto2 extends AreaGame {
	private GhostPlayer player;
	public final static float CAMERA_SCALE_FACTOR = 13.f;
	public final static float STEP = 0.05f;
	private final String[] areas = { "zelda/Ferme", "zelda/Village" };
	private final DiscreteCoordinates[] startingPositions = { new DiscreteCoordinates(2, 10),
			new DiscreteCoordinates(5, 15) };

	private int areaIndex;

	private void createAreas() {
		addArea(new Ferme());
		addArea(new Village());
	}

	public void end() {

	}

	public void update(float deltaTime) {
		if (player.isWeak()) {
			switchArea();
		}
		super.update(deltaTime);
	}

	public boolean begin(Window window, FileSystem fileSystem) {

		if (super.begin(window, fileSystem)) {

			createAreas();
			areaIndex = 0;
			Area area = setCurrentArea(areas[areaIndex], true);
			player = new GhostPlayer(area, Orientation.DOWN, startingPositions[areaIndex], "ghost.1");
			area.registerActor(player);
			area.setViewCandidate(player);
			return true;
		} else
			return false;

	}

	public String getTitle() {
		return "Tuto2";

	}

	protected void switchArea() {

		player.leaveArea();

		areaIndex = (areaIndex == 0) ? 1 : 0;

		Area currentArea = setCurrentArea(areas[areaIndex], false);
		player.enterArea(currentArea, startingPositions[areaIndex]);

		player.strengthen();
	}

}
