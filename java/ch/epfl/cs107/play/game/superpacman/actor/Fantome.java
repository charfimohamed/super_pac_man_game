package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
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

public abstract class Fantome extends MovableAreaEntity implements Interactor {

	private final static int RAYON = 5;
	private final static int GHOST_SCORE = 500;
	private final static int SPEED_NOT_AFRAID = 15;
	private final static int SPEED_AFRAID = 11;
	private int speed = 18;
	private final static int ANIMATION_DURATION = 8;
	private DiscreteCoordinates refuge;
	private Orientation desiredOrientation;
	private Sprite[][] spritesNotAfraid;
	private Sprite[] spritesAfraid;
	private Animation[] animationsNotAfraid;
	private Animation currentAnimation;
	private Animation animationAfraid;
	private FantomeHandler handler = new FantomeHandler();
	private SuperPacmanPlayer player;
	private boolean transition = false;
	private boolean respawn = false;
	private boolean afraid = false;
	private SoundAcoustics musicEat = new SoundAcoustics(ResourcePath.getSounds("superpacman/eat"), 1.0f, false, false,
			false, false);
	private SoundAcoustics musicEaten = new SoundAcoustics(ResourcePath.getSounds("superpacman/eaten"), 1.0f, false,
			false, false, false);
	private boolean soundOnEaten = false;
	private boolean soundOnEat = false;

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setSoundOnEaten(boolean soundOnEaten) {
		this.soundOnEaten = soundOnEaten;
	}

	public void setSoundOnEat(boolean soundOnEat) {
		this.soundOnEat = soundOnEat;
	}

	protected DiscreteCoordinates getRefuge() {
		return refuge;
	}

	protected boolean isRespawn() {
		return respawn;
	}

	protected void setRespawn(boolean respawn) {
		this.respawn = respawn;
	}

	protected boolean isTransition() {
		return transition;
	}

	public void setTransition(boolean arg) {
		transition = arg;
	}

	protected SuperPacmanPlayer getPlayer() {
		return player;
	}

	public void setAfraid(boolean afraid) {
		this.afraid = afraid;
	}

	public boolean isAfraid() {
		return afraid;
	}

	/**
	 * Default MovableAreaEntity constructor
	 * 
	 * @param area        (Area): Owner area. Not null
	 * @param position    (Coordinate): Initial position of the entity. Not null
	 * @param orientation (Orientation): Initial orientation of the entity. Not null
	 */
	public Fantome(Area area, Orientation orientation, DiscreteCoordinates position, String sprite) {
		super(area, orientation, position);
		desiredOrientation = orientation;
		refuge = position;
		spritesNotAfraid = RPGSprite.extractSprites(sprite, 2, 1, 1, this, 16, 16,
				new Orientation[] { Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT });
		animationsNotAfraid = Animation.createAnimations(ANIMATION_DURATION / 4, spritesNotAfraid);
		currentAnimation = animationsNotAfraid[desiredOrientation.ordinal()];
		spritesAfraid = RPGSprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1, (Positionable) this, 16, 16);
		animationAfraid = new Animation(ANIMATION_DURATION, spritesAfraid);

	}

	@Override
	public void update(float deltaTime) {
		if (isDisplacementOccurs()) {
			currentAnimation.update(ANIMATION_DURATION);
			animationAfraid.update(ANIMATION_DURATION);
		} else {
			desiredOrientation = getNextOrientation();
			orientate(desiredOrientation);
			move(speed);
			currentAnimation = animationsNotAfraid[desiredOrientation.ordinal()];
		}
		super.update(deltaTime);
	}

	@Override
	public void draw(Canvas canvas) {
		if (afraid) {
			animationAfraid.draw(canvas);
		} else {
			currentAnimation.draw(canvas);
		}
	}

	// abstract method that gives the next orientation of the ghost
	public abstract Orientation getNextOrientation();

	// when the ghost is dead this method respawns the ghost at its refuge position
	public void respawn() {
		getOwnerArea().leaveAreaCells(this, getEnteredCells());
		getOwnerArea().enterAreaCells(this, getCurrentCells());
		setCurrentPosition(refuge.toVector());
		getOwnerArea().enterAreaCells(this, getCurrentCells());
		resetMotion();
		respawn = true;
		player = null;
	}

	@Override
	public void bip(Audio audio) {
		if (soundOnEat) {
			soundOnEat = false;
			musicEat.shouldBeStarted();
			musicEat.bip(audio);
		}
		if (soundOnEaten) {
			soundOnEaten = false;
			musicEaten.shouldBeStarted();
			musicEaten.bip(audio);
		}
	}

	// method called by the class behavior to set the ghost boolean attributs and
	// its speed
	public void scareGhost(boolean etat) {
		if (afraid != etat) {
			setTransition(true);
		}
		afraid = etat;
		if (afraid) {
			speed = SPEED_AFRAID;
		} else {
			speed = SPEED_NOT_AFRAID;
		}
	}

	// gives the score that the player gets when he eats the ghost
	public int score() {
		return GHOST_SCORE;
	}

	@Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return false;
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		List<DiscreteCoordinates> resultat = new ArrayList<>();
		for (int i = -RAYON; i <= RAYON; i++) {
			for (int j = -RAYON; j <= RAYON; j++) {
				resultat.add(new DiscreteCoordinates(getCurrentMainCellCoordinates().x + i,
						getCurrentMainCellCoordinates().y + j));
			}
		}
		return resultat;
	}

	@Override
	public boolean wantsCellInteraction() {
		return false;
	}

	@Override
	public boolean wantsViewInteraction() {
		return true;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}

	private class FantomeHandler implements SuperPacmanInteractionVisitor {
		@Override
		public void interactWith(SuperPacmanPlayer superPacmanPlayer) {
			player = superPacmanPlayer;

		}

	}

}
