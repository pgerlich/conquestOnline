package map;

public class Coordinate {
	int x;
	int y;
	
	public int getY(){
		return this.y;
	}
	public int getX(){
		return this.x;
	}
	
	/*returns the minumum distance between two points*/
	public double getDistance(Coordinate a){
		double a2 = Math.pow((a.x-this.x), 2);
		double b2 = Math.pow((a.y-this.y), 2);
		return Math.pow((a2-b2), .5);
	}
}
