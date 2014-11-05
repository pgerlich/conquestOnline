package conquest.online.gameAssets.Structures;
import conquest.online.gameAssets.Attackable;
import conquest.online.gameAssets.Visible;
import map.Coordinate;
public abstract class AbstractStructure implements Visible, Attackable {

	int level;
	
	
	public abstract  void levelUp();
	public abstract void use();
	public abstract Double getCost();

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
}
