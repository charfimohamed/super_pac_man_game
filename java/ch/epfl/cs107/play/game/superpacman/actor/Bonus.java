package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

public class Bonus extends CollectableAreaEntity {
	private final static int ANIMATION_DURATION = 8;
	private Sprite[] sprites;
	private Animation animation;
	private final int TIMER = 10;
	private SoundAcoustics music = new SoundAcoustics(ResourcePath.getSounds("superpacman/transactionOk"), 1.0f, false, false,
			false, false);
	private boolean soundOn = false;

	public Bonus(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		sprites = RPGSprite.extractSprites("superpacman/coin", 4, 1, 1, (Positionable) this, 16, 16);
		animation = new Animation(ANIMATION_DURATION, sprites);
	}

	@Override
	public void update(float deltaTime) {
		animation.update(deltaTime);
		super.update(deltaTime);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
		super.acceptInteraction(v);
		soundOn = true;

	}

	@Override
	public void bip(Audio audio) {
		if (soundOn) {
			soundOn = false;
			music.shouldBeStarted();
			music.bip(audio);
		}
	}

	// intializes the timer 
	public int initTimer() {
		return TIMER;
	}

	@Override
	public void draw(Canvas canvas) {
		animation.draw(canvas);
	}

	@Override
	public int score() {
		return 0;
	}

}
