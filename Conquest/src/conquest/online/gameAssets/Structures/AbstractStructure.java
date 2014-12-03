package conquest.online.gameAssets.Structures;
import conquest.online.gameAssets.Attackable;
import conquest.online.gameAssets.Visible;
import map.Coordinate;
import java.util.ArrayList;
public abstract class AbstractStructure implements Visible, Attackable {

	private int level;
	private String description;
	private String imageLocation;
	private String itemName;
	private int cost;
	private Coordinate startCoordinates;
	public ArrayList<Coordinate> floorPlan;
	private boolean owned = false;	
	
	public abstract void levelUp();
	public abstract void use();
	public abstract int getCost();
	
	public boolean own() {
		return owned;
	}
	
	public AbstractStructure(String name, int cost, String pic, String description, ArrayList<Coordinate> floor) {
    	setCost(cost);
    	setPic(pic);
    	setDes(description);
    	setName(name);
    	setFloorPlan(floor);
    	startCoordinates = new Coordinate(-1,-1);
    }
    
	//deep copy of floorplan provided
	//floor plan must include (0,0) to represent the starting grid block
	private void setFloorPlan(ArrayList<Coordinate> floor) {
		for(int i = 0; i < floor.size();  i++){
			this.floorPlan.add(new Coordinate(floor.get(i).getX(), floor.get(i).getY()));
		}
		
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public void setPic(String pic) {
		imageLocation = pic;
	}
	
	public String getPic() {
		return imageLocation;
	}
	
	public void setDes(String d) {
		description = d;
	}
	
	public String getDes() {
		return description;
	}
	
	public void setName(String name) {
		this.itemName = name;
	}
	
	public String getName() {
		return itemName;
	}

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
		return startCoordinates;
	}

	public void setCoordinate(Coordinate c) {
		startCoordinates = c;

	}
}
