package gameAssets.Moves;
import map.Coordinate;

public class Attack {

	protected int coolDown;					//number of updates till useable again
	protected int speed;					//
	protected int power;
	protected int range;
	protected Coordinate attackCoordinates;
	
	
	/*returns true if useable false if not useable and does action*/
	public boolean use(){
		return false;
	}
	
	
	
}
