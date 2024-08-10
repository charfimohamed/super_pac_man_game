package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.actor.SoundAcoustics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class SuperPacmanPlayer extends Player {

	private final static int SPEED_NOT_VULNERABLE = 4;
	private final static int SPEED_VULNERABLE = 6;
	private final static int SPEED_ON_WATER = 10;
	private final static int ANIMATION_DURATION = 8;
	private static int speed = 6;
	private Orientation desiredOrientation;
	private SuperPacmanPlayerHandler handler = new SuperPacmanPlayerHandler();
	private Animation currentAnimation;
	private Sprite[][] sprites;
	private Animation[] animations;
	private SuperPacmanStatusGUI superPacmanPlayerStatusGUI;
	private int hp = 3;
	private int xp = 0;
	private int nbLivesMax = 5;
	private float timerBonus = 0;
	private boolean vulnerable = true;
	private boolean eaten = false;
	private SoundAcoustics musicGameOver = new SoundAcoustics(ResourcePath.getSounds("superpacman/gameOver"), 1.0f,
			false, false, false, false);

	private boolean soundOnGameOver = false;

	public boolean isEaten() {
		return eaten;
	}

	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}

	public boolean isVulnerable() {
		return vulnerable;
	}

	public int getHp() {
		return hp;

	}

	public int getXp() {
		return xp;

	}

	public SuperPacmanPlayer(Area owner, DiscreteCoordinates coordinates, String sprite) {

		super(owner, Orientation.RIGHT, coordinates);
		desiredOrientation = Orientation.RIGHT;
		sprites = RPGSprite.extractSprites(sprite, 4, 1, 1, this, 64, 64,
				new Orientation[] { Orientation.DOWN, Orientation.LEFT, Orientation.UP, Orientation.RIGHT });
		animations = Animation.createAnimations(ANIMATION_DURATION / 4, sprites);
		currentAnimation = animations[desiredOrientation.ordinal()];
		superPacmanPlayerStatusGUI = new SuperPacmanStatusGUI(hp, xp, nbLivesMax);
		resetMotion();
	}

	@Override
	public void update(float deltaTime) {

		Keyboard keyboard = getOwnerArea().getKeyboard();
		if (keyboard.get(Keyboard.LEFT).isDown() || keyboard.get(Keyboard.A).isDown()) {
			desiredOrientation = Orientation.LEFT;
			
		} else if (keyboard.get(Keyboard.RIGHT).isDown() || keyboard.get(Keyboard.D).isDown()) {
			desiredOrientation = Orientation.RIGHT;
			
		} else if (keyboard.get(Keyboard.DOWN).isDown() || keyboard.get(Keyboard.S).isDown()) {
			desiredOrientation = Orientation.DOWN;
			
		} else if (keyboard.get(Keyboard.UP).isDown() || keyboard.get(Keyboard.W).isDown()) {
			desiredOrientation = Orientation.UP;
		}

		if (!isDisplacementOccurs()) {
			if ((getOwnerArea().canEnterAreaCells(this,
					Collections.singletonList(getCurrentMainCellCoordinates().jump(desiredOrientation.toVector()))))) {
				orientate(desiredOrientation);
				currentAnimation = animations[desiredOrientation.ordinal()];
			}
			currentAnimation.reset();
			move(speed);
		} else if (isDisplacementOccurs()) {

			currentAnimation.update(ANIMATION_DURATION);
		}
		
		if (timerBonus > 0) {
			timerBonus -= deltaTime;
		} else {
			speed = SPEED_VULNERABLE;
			vulnerable = true;
		}
		
		superPacmanPlayerStatusGUI.setHp(hp);
		superPacmanPlayerStatusGUI.setXp(xp);
		superPacmanPlayerStatusGUI.setTimer(timerBonus);

		super.update(deltaTime);
	}

	// when the player is dead this method respawns the player
	public void respawn() {
		getOwnerArea().leaveAreaCells(this, getEnteredCells());
		getOwnerArea().enterAreaCells(this, getCurrentCells());
		setCurrentPosition(((SuperPacmanArea) getOwnerArea()).getspawnCoordinate().toVector());
		getOwnerArea().enterAreaCells(this, getCurrentCells());
		resetMotion();

	}

	@Override
	public void bip(Audio audio) {
		if (soundOnGameOver) {
			soundOnGameOver = false;
			musicGameOver.shouldBeStarted();
			musicGameOver.bip(audio);
		}
	}

	public void decreaseHp() {
		if (hp - 1 >= 0) {
			hp--;
		}
	}

	public void encreaseHp() {
		if (hp + 1 <= nbLivesMax) {
			hp++;
		}
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return null;
	}

	@Override
	public boolean wantsCellInteraction() {
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		return false;
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}

	@Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}

	@Override
	public void draw(Canvas canvas) {
		currentAnimation.draw(canvas);
		superPacmanPlayerStatusGUI.draw(canvas);
	}

	private class SuperPacmanPlayerHandler implements SuperPacmanInteractionVisitor {

		@Override
		public void interactWith(Door door) {
			setIsPassingADoor(door);
		}

		@Override
		public void interactWith(Cherry cherry) {
			xp += cherry.score();
		}

		@Override
		public void interactWith(Diamond diamond) {
			xp += diamond.score();
			((SuperPacmanArea) getOwnerArea()).increaseNbDiamondCollected();
		}

		@Override
		public void interactWith(Key key) {
			key.collected();
		}

		@Override
		public void interactWith(Bonus bonus) {
			speed = SPEED_NOT_VULNERABLE;
			timerBonus = bonus.initTimer();
			vulnerable = false;

		}

		@Override
		public void interactWith(HealthPoint healthPoint) {
			encreaseHp();
		}

		@Override
		public void interactWith(Fire fire) {
			eaten = true;
			decreaseHp();
			if (hp == 0) {
				soundOnGameOver = true;
			}
		}

		@Override
		public void interactWith(Water water) {
			speed = SPEED_ON_WATER;
		}

		@Override
		public void interactWith(Fantome fantome) {
			if (vulnerable) {
				eaten = true;
				fantome.respawn();
				decreaseHp();
				if (hp == 0) {
					soundOnGameOver = true;
				}
				if (hp != 0) {
					fantome.setSoundOnEaten(true);
				}

			} else {
				fantome.setSoundOnEat(true);
				xp += fantome.score();
				fantome.respawn();

			}

		}

	}

}
