package conquest.online.gameAssets;

import map.Coordinate;

public interface Visible {
	
	//Draw self on screen at given coordinates
	void drawSelf(Coordinate c);
	
	Coordinate getCoordinate();
	
	void setCoordinate(Coordinate c);
}
