package ch.epfl.cs107.play.game.superpacman.area;

import java.util.Queue;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public abstract class SuperPacmanArea extends Area implements Logic {

	private SuperPacmanBehavior behavior;
	private int nbDiamondCollected = 0;
	private boolean restart = false;
	private boolean pause = false;
	private boolean resume = false;

	public boolean isResume() {
		return resume;
	}

	public void setResume(boolean resume) {
		this.resume = resume;
	}

	public boolean isRestart() {
		return restart;
	}

	public void setRestart(boolean restart) {
		this.restart = restart;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public int getNbDiamondCollected() {
		return nbDiamondCollected;
	}

	@Override
	public float getCameraScaleFactor() {
		return 15.f;
	}

	public boolean begin(Window window, FileSystem fileSystem) {

		if (super.begin(window, fileSystem)) {
			behavior = new SuperPacmanBehavior(window, getTitle());
			setBehavior(behavior);
			createArea();
			return true;
		}
		return false;
	}

	// a method that register the actors of the area
	protected void createArea() {
		behavior.registerActors(this);
		registerActor(new Background(this, new RegionOfInterest(0, 0, 1, 1), "superpacman/background"));
	}

	public int getNbDiamondTotal() {
		return behavior.getNbDiamond();
	}

	public void increaseNbDiamondCollected() {
		nbDiamondCollected++;
	}

	// a method that scares the ghosts registred to the area
	public void scare(boolean etat) {
		behavior.scare(etat);
	}

	public abstract DiscreteCoordinates getspawnCoordinate();

	// sets the signal of the node in the behavior's graph
	public void setSignalGate(DiscreteCoordinates coordinates, Logic signal) {
		behavior.setSignalGate(coordinates, signal);
	}

	// gives the shortest path between two coordinates
	public Queue<Orientation> shortestPath(DiscreteCoordinates a, DiscreteCoordinates b) {
		return behavior.shortestPath(a, b);
	}

}
