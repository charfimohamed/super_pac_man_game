package ch.epfl.cs107.play.game.superpacman.area;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.Cell;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.Blinky;
import ch.epfl.cs107.play.game.superpacman.actor.Bonus;
import ch.epfl.cs107.play.game.superpacman.actor.Cherry;
import ch.epfl.cs107.play.game.superpacman.actor.Clyde;
import ch.epfl.cs107.play.game.superpacman.actor.Diamond;
import ch.epfl.cs107.play.game.superpacman.actor.Fantome;
import ch.epfl.cs107.play.game.superpacman.actor.Fire;
import ch.epfl.cs107.play.game.superpacman.actor.HealthPoint;
import ch.epfl.cs107.play.game.superpacman.actor.Inky;
import ch.epfl.cs107.play.game.superpacman.actor.Pinky;
import ch.epfl.cs107.play.game.superpacman.actor.Wall;
import ch.epfl.cs107.play.game.superpacman.actor.Water;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public class SuperPacmanBehavior extends AreaBehavior {
	private List<Fantome> ghosts = new ArrayList<>();
	private AreaGraph graph = new AreaGraph();
	private int nbDiamond = 0;

	public int getNbDiamond() {
		return nbDiamond;
	}

	public SuperPacmanBehavior(Window window, String name) {
		super(window, name);
		int height = getHeight();
		int width = getWidth();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				SuperPacmanCellType type = SuperPacmanCellType.toType(getRGB(height - 1 - y, x));
				setCell(x, y, new SuperPacmanCell(x, y, type));
			}
		}
		setGraph();
	}

	// gives a two dimentional array of booleans depending on the cells type in the
	// neighborhood

	public boolean[][] neighborhood(int x, int y, int height, int width) {
		boolean[][] result = new boolean[3][3];
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (((x + i) < 0) || ((x + i) >= width) || ((y + j) >= height) || ((y + j) < 0)) {
					result[i + 1][2 - (j + 1)] = false;
				} else if (((SuperPacmanCell) getCell(x + i, y + j)).type == SuperPacmanCellType.WALL) {
					result[i + 1][2 - (j + 1)] = true;
				}

			}
		}
		return result;
	}

	// set the signal of the node in the graph
	public void setSignalGate(DiscreteCoordinates coordinates, Logic signal) {
		graph.setSignal(coordinates, signal);

	}

	// gives the shortest path between two coordinates
	public Queue<Orientation> shortestPath(DiscreteCoordinates a, DiscreteCoordinates b) {
		return graph.shortestPath(a, b);
	}

	// scares all the ghosts
	public void scare(boolean etat) {
		for (Fantome fantome : ghosts) {
			fantome.scareGhost(etat);

		}
	}

	protected void registerActors(Area area) {

		int height = getHeight();
		int width = getWidth();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.WALL) {
					area.registerActor(
							new Wall(area, new DiscreteCoordinates(x, y), neighborhood(x, y, height, width)));

				} else if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_CHERRY) {
					area.registerActor(new Cherry(area, Orientation.DOWN, new DiscreteCoordinates(x, y)));

				} else if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_DIAMOND) {
					area.registerActor(new Diamond(area, Orientation.DOWN, new DiscreteCoordinates(x, y)));
					nbDiamond++;

				} else if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_BONUS) {
					area.registerActor(new Bonus(area, Orientation.DOWN, new DiscreteCoordinates(x, y)));

				} else if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_HP) {
					area.registerActor(new HealthPoint(area, Orientation.DOWN, new DiscreteCoordinates(x, y)));

				} else if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_FIRE) {
					area.registerActor(new Fire(area, Orientation.DOWN, new DiscreteCoordinates(x, y)));

				} else if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_WATER) {
					area.registerActor(new Water(area, Orientation.DOWN, new DiscreteCoordinates(x, y)));

				} else if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_BLINKY) {
					Blinky blinky = new Blinky(area, Orientation.DOWN, new DiscreteCoordinates(x, y));
					area.registerActor(blinky);
					ghosts.add(blinky);

				} else if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_INKY) {
					Inky inky = new Inky(area, Orientation.DOWN, new DiscreteCoordinates(x, y));
					area.registerActor(inky);
					ghosts.add(inky);

				} else if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_PINKY) {
					Pinky pinky = new Pinky(area, Orientation.DOWN, new DiscreteCoordinates(x, y));
					area.registerActor(pinky);
					ghosts.add(pinky);
				} else if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_CLYDE) {
					Clyde clyde = new Clyde(area, Orientation.DOWN, new DiscreteCoordinates(x, y));
					area.registerActor(clyde);
					ghosts.add(clyde);
				}
			}
		}
	}

	private void setGraph() {
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				boolean hasLeftEdge = (x > 0
						&& (((SuperPacmanCell) getCell(x - 1, y)).type != SuperPacmanCellType.WALL));

				boolean hasUpEdge = (y < (getHeight() - 1)
						&& (((SuperPacmanCell) getCell(x, y + 1)).type != SuperPacmanCellType.WALL));

				boolean hasRightEdge = (x < (getWidth() - 1)
						&& (((SuperPacmanCell) getCell(x + 1, y)).type != SuperPacmanCellType.WALL));

				boolean hasDownEdge = (y > 0
						&& (((SuperPacmanCell) getCell(x, y - 1)).type != SuperPacmanCellType.WALL));

				graph.addNode(new DiscreteCoordinates(x, y), hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge);
			}
		}
	}

	public enum SuperPacmanCellType {

		NONE(0), // never used as real content
		WALL(-16777216), // black
		FREE_WITH_DIAMOND(-1), // white
		FREE_WITH_BLINKY(-65536), // red
		FREE_WITH_PINKY(-157237), // pink
		FREE_WITH_INKY(-16724737), // cyan
		FREE_WITH_CHERRY(-36752), // light red
		FREE_WITH_BONUS(-16478723), // light blue
		FREE_EMPTY(-6118750), // sort of gray
		FREE_WITH_HP(-14112955), // green
		FREE_WITH_FIRE(-256), // yellow
		FREE_WITH_WATER(-16776961), // dark blue
		FREE_WITH_CLYDE(-2403840); // orange

		final int type;

		SuperPacmanCellType(int type) {
			this.type = type;
		}

		public static SuperPacmanCellType toType(int type) {
			for (SuperPacmanCellType ict : SuperPacmanCellType.values()) {
				if (ict.type == type)
					return ict;
			}
			System.out.println(type);
			return NONE;
		}
	}

	public class SuperPacmanCell extends Cell {
		private final SuperPacmanCellType type;

		public SuperPacmanCell(int x, int y, SuperPacmanCellType type) {
			super(x, y);
			this.type = type;
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
		public void acceptInteraction(AreaInteractionVisitor v) {
		}

		@Override
		protected boolean canLeave(Interactable entity) {
			return true;
		}

		@Override
		protected boolean canEnter(Interactable entity) {
			return !this.hasNonTraversableContent();
		}

	}

}
