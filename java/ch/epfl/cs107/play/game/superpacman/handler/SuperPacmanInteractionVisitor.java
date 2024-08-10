package ch.epfl.cs107.play.game.superpacman.handler;

import ch.epfl.cs107.play.game.rpg.handler.RPGInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.Bonus;
import ch.epfl.cs107.play.game.superpacman.actor.Cherry;
import ch.epfl.cs107.play.game.superpacman.actor.Diamond;
import ch.epfl.cs107.play.game.superpacman.actor.Fantome;
import ch.epfl.cs107.play.game.superpacman.actor.Fire;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.HealthPoint;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.actor.Wall;
import ch.epfl.cs107.play.game.superpacman.actor.Water;

public interface SuperPacmanInteractionVisitor extends RPGInteractionVisitor {

	default void interactWith(SuperPacmanPlayer superPacmanPlayer) {
		// by default the interaction is empty
	}

	default void interactWith(Cherry cherry) {
		// by default the interaction is empty
	}

	default void interactWith(Diamond diamond) {
		// by default the interaction is empty
	}

	default void interactWith(Bonus bonus) {
		// by default the interaction is empty
	}

	default void interactWith(Wall wall) {
		// by default the interaction is empty
	}

	default void interactWith(Key key) {
		// by default the interaction is empty
	}

	default void interactWith(Gate gate) {
		// by default the interaction is empty
	}

	default void interactWith(Fantome fantome) {
		// by default the interaction is empty
	}
	default void interactWith(HealthPoint healthPoint) {
		// by default the interaction is empty
	}
	default void interactWith(Fire fire) {
		// by default the interaction is empty
	}
	
	default void interactWith(Water water) {
		// by default the interaction is empty
	}

}
