package gameAssets.Structures;
import gameAssets.Attackable;
import gameAssets.Visible;
import map.Coordinate;
public abstract class AbstractStructure implements Visible, Attackable {

	int level;
	
	
	public abstract  void levelUp();
	

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
