package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Audio;


public class Teleport extends Door {
	
	//an actor that teleports he player from one coordinate to another

	private SoundAcoustics music = new SoundAcoustics(ResourcePath.getSounds("superpacman/teleport"), 1.0f, false, false, false, false);
	private boolean soundOn = false;

	public Teleport(String destination, DiscreteCoordinates otherSideCoordinates, Logic signal, Area area,
			Orientation orientation, DiscreteCoordinates position) {
		super(destination, otherSideCoordinates, signal, area, orientation, position);
		

	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		soundOn = true;
		super.acceptInteraction(v);
	}
	
	@Override
	public void bip(Audio audio) {
		if (soundOn) {
			soundOn = false;
			music.shouldBeStarted();
			music.bip(audio);
		}
	}

}
