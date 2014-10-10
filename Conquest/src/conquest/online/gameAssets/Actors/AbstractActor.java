package gameAssets.Actors;

import map.Coordinate;
import gameAssets.Attackable;
import gameAssets.Visible;
import gameAssets.Items.AbstractEquipableItem;
import gameAssets.Items.Armor;
import gameAssets.Items.Weapon;

public abstract class AbstractActor implements Visible, Attackable{
	
	protected Weapon[] equippedWeapons;
	protected Armor[] equippedArmor;
	
	protected int level;
	protected int baseDefense;
	protected int baseSpeed;
	protected int baseRange;
	
	protected String Name;
	
	
	
	
	public void adjustCurrentHealth(int change) {
	}
	

	public int getCurrentHealth() {
		return 0;
	}
	
	public void adjustMaxHealth(int change) {
	}
	
	public int getMaxHealth() {
		return 0;
	}
	
	public void drawSelf(Coordinate c) {
		// TODO Auto-generated method stub

	}

	public Coordinate getCoordinate() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCoordinate(Coordinate c) {
		// TODO Auto-generated method stub

	}
	
	
	public void equip(AbstractEquipableItem item){}
	
	
}
