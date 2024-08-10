package ch.epfl.cs107.play.game.superpacman.actor;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class SuperPacmanStatusGUI implements Graphics {
	private static final int JAUNE = 0;
	private static final int GRIS = 64;
	private int xp;
	private int hp;
	private int nbLivesMax;
	private float timer;

	public void setXp(int xp) {
		this.xp = xp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setTimer(float timer) {
		this.timer = timer;
	}

	protected void setNbLivesMax(int nbLivesMax) {
		this.nbLivesMax = nbLivesMax;
	}

	protected SuperPacmanStatusGUI(int hp, int xp, int nbLivesMax) {
		this.hp = hp;
		this.xp = xp;
		this.nbLivesMax = nbLivesMax;
	}

	@Override
	public void draw(Canvas canvas) {
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));
		ImageGraphics life;
		for (int i = 0; i < nbLivesMax; i++) {
			if (hp > i) {
				life = new ImageGraphics(ResourcePath.getSprite("superpacman/lifeDisplay"), 1.f, 1.f,
						new RegionOfInterest(JAUNE, 0, 64, 64), anchor.add(new Vector(i, height - 1.375f)), 1, 0.3f);
			} else {
				life = new ImageGraphics(ResourcePath.getSprite("superpacman/lifeDisplay"), 1.f, 1.f,
						new RegionOfInterest(GRIS, 0, 64, 64), anchor.add(new Vector(i, height - 1.375f)), 1, 0.3f);
			}
			life.draw(canvas);
		}
		TextGraphics score = new TextGraphics("score : " + Integer.toString(xp), 1.1f, Color.YELLOW, Color.BLACK, 0.04f,
				true, false, anchor.add(new Vector(7, (float) (height - 1.2))));
		score.draw(canvas);
		TextGraphics timerGraph = new TextGraphics("Power : " + Float.toString(timer), 1.1f, Color.RED, Color.BLACK,
				0.04f, true, false, anchor.add(new Vector(0, (float) (height -14))));
		if (timer > 0) {
			timerGraph.draw(canvas);
		}

	}

}
