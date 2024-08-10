
package ch.epfl.cs107.play.game.areagame.actor;

import java.util.Collections;

import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/* class that defines actors in the game that can be collected 
 */
public abstract class CollectableAreaEntity extends AreaEntity {
	
	/**
     * Default CollectableAreaEntity constructor
     * @param area (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     */
	public CollectableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
	}

	public boolean takeCellSpace() {
		return false;
	}
	
    // abstract method that determines the score given to the player when collected
	public abstract int score();

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return false;
	}
	
	// unregister the actor from its area when  the interaction occurs 
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		getOwnerArea().unregisterActor(this);
	}
}
