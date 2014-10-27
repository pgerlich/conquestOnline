package conquest.online.gameAssets.Actors;

import map.Coordinate;
import conquest.online.gameAssets.Attackable;
import conquest.online.gameAssets.Visible;
import conquest.online.gameAssets.Items.AbstractEquipableItem;
import conquest.online.gameAssets.Items.Armor;
import conquest.online.gameAssets.Items.Weapon;

public abstract class AbstractActor implements Visible, Attackable{
	
	protected Coordinate coordinates;
	
	protected Weapon[] equippedWeapons;
	protected Armor[] equippedArmor;
	
	protected int level;
	protected int baseDefense;
	protected int baseSpeed;
	protected int baseRange;
	protected int maxHealth;
	protected int currentHealth;
	
	protected String Name;
	
	
	
	
	public void adjustCurrentHealth(int change) {
		currentHealth += change;
	}
	
	public int getCurrentHealth() {
		return currentHealth;
	}
	
	public void adjustMaxHealth(int change) {
		maxHealth += change;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void equip(AbstractEquipableItem item){}
	
	
	
	
	///////////////////////////////////////////////////////////////////
	/// Displaying Character /////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	
	
	//Coming Soon!
	public void drawSelf(Coordinate c) {
		// TODO Auto-generated method stub

	}

	public Coordinate getCoordinate() {
		// TODO Auto-generated method stub
		return coordinates;
	}

	public void setCoordinate(Coordinate c) {
		coordinates = c;

	}
	
	//allows adjustment of coordinates without creating new coordinates (used for player movement)
	public void adjustCoordinate(int latChange, int lonChange){
		Coordinate c = new Coordinate(coordinates.getX() + latChange, coordinates.getY() + lonChange);
		setCoordinate(c);
	}
	
	
	//Moving character 
	public void moveCharacterTo(Coordinate c){
		setCoordinate(coordinates.add(coordinates.getUnitMoveVector(c))); // gets unit movement and adds to position
		
	}

	
	
}
