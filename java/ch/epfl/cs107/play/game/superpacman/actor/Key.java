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
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

public class Key extends CollectableAreaEntity implements Logic {
	private Sprite sprite;
	private boolean signal = false;
	private float intensity =0.0f;
	private SoundAcoustics music = new SoundAcoustics(ResourcePath.getSounds("superpacman/key"), 1.0f, false, false,
			false, false);
	private boolean soundOn = false;

	public Key(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		sprite = new Sprite("superpacman/key", 1, 1.f, (Positionable) this);
	}

	@Override
	public void draw(Canvas canvas) {
		if (sprite != null)
			sprite.draw(canvas);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		super.acceptInteraction(v);
		((SuperPacmanInteractionVisitor) v).interactWith(this);
		soundOn = true;
	}
	
	public void bip(Audio audio) {
		if (soundOn) {
			soundOn = false;
			music.shouldBeStarted();
			music.bip(audio);
		}
	}

	//change the value of the signal and its intensity
	public void collected() {
		signal = true;
		intensity=1.0f;	
	}

	@Override
	public boolean isOn() {
		return signal;
	}

	@Override
	public boolean isOff() {
		return !signal;
	}

	@Override
	public float getIntensity() {
		return intensity;
	}

	@Override
	public int score() {
		return 0;
	}

}
