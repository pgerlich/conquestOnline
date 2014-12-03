package conquest.online.gameAssets.Items;


public abstract class AbstractItem {

	String itemName;
    String cost;
    String picture;
    String description;
    String id;
    boolean owned = false;
    
    public void create(String name, String cost, String pic, String description, String id) {
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
    
    public String getCost() {
		return cost;
	}
    
    public void setId(String id) {
    	this.id = id;
    }
    
    public String getId() {
    	return id;
    }
    
    public void setCost(String cost) {
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
