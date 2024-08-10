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

public class HealthPoint extends CollectableAreaEntity {
	
	// an actor that will encrease the HP of the player during the interaction

	private final static int ANIMATION_DURATION = 8;
	private Sprite[] sprites;
	private Animation animation;
	private SoundAcoustics music = new SoundAcoustics(ResourcePath.getSounds("superpacman/hp"), 1.0f, false, false, false, false);
	private boolean soundOn = false;

	public HealthPoint(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		sprites = RPGSprite.extractSprites("zelda/heart", 4, 1, 1, (Positionable) this, 16, 16);
		animation = new Animation(ANIMATION_DURATION, sprites);
	}

	public void bip(Audio audio) {
		if (soundOn) {
			soundOn = false;
			music.shouldBeStarted();
			music.bip(audio);
		}
	}

	@Override
	public void update(float deltaTime) {
		animation.update(deltaTime);
		super.update(deltaTime);
	}

	@Override
	public void draw(Canvas canvas) {
		animation.draw(canvas);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
		super.acceptInteraction(v);
		soundOn = true;
	}

	@Override
	public int score() {
		return 0;
	}

}
