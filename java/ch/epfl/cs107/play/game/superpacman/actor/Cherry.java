package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

public class Cherry extends CollectableAreaEntity {
	private final int CHERRY_SCORE = 200 ;
	private Sprite sprite ;
	private SoundAcoustics music = new SoundAcoustics(ResourcePath.getSounds("superpacman/cherry"), 1.0f, false, false, false, false);
	private boolean soundOn = false;

	public Cherry(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		sprite = new Sprite("superpacman/cherry", 1, 1.f, (Positionable) this);
	}
	
	@Override
	public void bip(Audio audio) {
		if (soundOn) {
			soundOn = false;
			music.shouldBeStarted();
			music.bip(audio);
		}
	}
	
	@Override
	public int score() {
		return CHERRY_SCORE;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
		super.acceptInteraction(v);
		soundOn=true;
	}

	@Override
	public void draw(Canvas canvas) {
		if (sprite != null)
			sprite.draw(canvas);

	}

}
