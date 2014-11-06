package conquest.online.gameAssets.Structures;
import conquest.online.gameAssets.Attackable;
import conquest.online.gameAssets.Visible;
import map.Coordinate;
public abstract class AbstractStructure implements Visible, Attackable {

	int level;
	String description;
	String picture;
	String itemName;
	Double cost;
	
	
	
	public abstract  void levelUp();
	public abstract void use();
	public abstract Double getCost();
	
	public void create(String name, Double cost, String pic, String description) {
    	setCost(cost);
    	setPic(pic);
    	setDes(description);
    	setName(name);
    }
    
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public void setPic(String pic) {
		picture = pic;
	}
	
	public String getPic() {
		return picture;
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
		return null;
	}

	public void setCoordinate(Coordinate c) {
		// TODO Auto-generated method stub

	}
}
