package conquest.online.gameAssets.Items;


public abstract class AbstractItem {

	String itemName;
    int cost;
    String picture;
    String description;
    int id;
    boolean owned = false;
    
    public void create(String name, int cost, String pic, String description, int id) {
    	setCost(cost);
    	setPic(pic);
    	setDesc(description);
    	setName(name);
    	setId(id);
    }
    
    public boolean own() {
    	return owned;
    }
    
    public abstract void use();
    
    public int getCost() {
		return cost;
	}
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public int getId() {
    	return id;
    }
    
    public void setCost(int cost) {
    	this.cost = cost;
    }
    
    public String getPic() {
    	return picture;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public String getName() {
    	return itemName;
    }
    
    public void setPic(String picture) {
    	this.picture = picture; 
    }
    
    public void setName(String name) {
    	this.itemName = name;
    }
    
    public void setDesc(String d) {
    	this.description = d;
    }
    
}
