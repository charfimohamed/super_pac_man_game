package ch.epfl.cs107.play.game.superpacman;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.area.Congratulations;
import ch.epfl.cs107.play.game.superpacman.area.GameOver;
import ch.epfl.cs107.play.game.superpacman.area.Level0;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.game.superpacman.area.Level2;
import ch.epfl.cs107.play.game.superpacman.area.MenuPause;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

public class SuperPacman extends RPG {
	private static int highestScore = 0;
	private SuperPacmanPlayer player;
	private String leavingArea;

	// static method that gives the highest score used in game over menu and
	// congratulations menu
	public static int getHighestScore() {
		return highestScore;
	}

	// instantiates the areas and adds them to the game
	private void createAreas() {
		addArea(new Level0());
		addArea(new Level1());
		addArea(new Level2());
		addArea(new GameOver());
		addArea(new MenuPause());
		addArea(new Congratulations());
	}

	// override to the method begin in which we instantiate the player and we set
	// the area the the first level
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			createAreas();
			Area area = setCurrentArea("superpacman/Level0", true);
			player = new SuperPacmanPlayer(area, Level0.getPlayerSpawnPosition(), "superpacman/pacman");
			initPlayer(player);
			return true;
		} else
			return false;
	}

	@Override
	public String getTitle() {
		return "Super Pac-Man";
	}

	// restarts the game
	public void restart() {
		((SuperPacmanArea) getCurrentArea()).setRestart(false);
		begin(getWindow(), getFileSystem());
	}

	// pauses the game
	public void pause() {
		((SuperPacmanArea) getCurrentArea()).setPause(false);
		leavingArea = getCurrentArea().getTitle();
		setCurrentArea("superpacman/menuPause", true);
	}

	// resumes the game when paused
	public void resume() {
		((SuperPacmanArea) getCurrentArea()).setResume(false);
		setCurrentArea(leavingArea, false);
	}

	// override the method update so that the the games advances knowing the state
	// of the player (respawn,pause,resume,restart,....)
	@Override
	public void update(float deltaTime) {

		if (player.isEaten()) {
			player.setEaten(false);
			player.respawn();
		}

		if (((SuperPacmanArea) getCurrentArea()).isPause()) {
			pause();
		}

		if (((SuperPacmanArea) getCurrentArea()).isResume()) {
			resume();
		}

		if (((SuperPacmanArea) getCurrentArea()).isRestart()) {
			restart();
		}

		((SuperPacmanArea) getCurrentArea()).scare(!player.isVulnerable());
		super.update(deltaTime);

		if (player.getHp() == 0) {
			setCurrentArea("superpacman/gameOver", true);

		}
		if (player.getXp() > highestScore) {
			highestScore = player.getXp();
		}

	}

}
