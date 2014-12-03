package map;

public class Coordinate {
	public int x;
	public int y;
	
	public Coordinate(int i, int j) {
		// TODO Auto-generated constructor stub
		x = i;
		y = j;
	}
	public int getY(){
		return this.y;
	}
	public int getX(){
		return this.x;
	}
	
	/*returns the minumum distance between two points*/
	public double getDistanceTo(Coordinate a){
		double a2 = Math.pow((a.x-this.x), 2);
		double b2 = Math.pow((a.y-this.y), 2);
		return Math.pow((a2-b2), .5);
	}
	
	//adds two vectors
	public Coordinate add(Coordinate b){
		return new Coordinate(this.x + b.getX(), this.y + b.getY());
	}
	
	//creates a unit vector representing character's movement path movement/tick
	public Coordinate getUnitMoveVector(Coordinate dest){
		int rise = dest.getY() - this.getY();
		int run = dest.getX() - this.getX();
		int distance =(int) Math.pow( Math.pow(rise, 2) + Math.pow(run, 2), .5); // sqrt(a^2 + b^2) = c
		return new Coordinate(rise/distance, run/distance);
	}
}
