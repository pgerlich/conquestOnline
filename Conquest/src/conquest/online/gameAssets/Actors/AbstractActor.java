package conquest.online.gameAssets.Actors;

import map.Coordinate;
import conquest.online.gameAssets.Attackable;
import conquest.online.gameAssets.Visible;
import conquest.online.gameAssets.Items.AbstractEquipableItem;
import conquest.online.gameAssets.Items.Armor;
import conquest.online.gameAssets.Items.Weapon;

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
