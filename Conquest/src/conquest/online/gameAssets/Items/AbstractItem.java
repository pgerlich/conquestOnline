package conquest.online.gameAssets.Items;

public abstract class AbstractItem {

	String itemName;
    String cost;
    String picture;
    String description;
    boolean owned = false;
    
    public void create(String name, String cost, String pic, String description) {
    	setCost(cost);
    	setPic(pic);
    	setDesc(description);
    	setName(name);
    }
    
    public boolean own() {
    	return owned;
    }
    
    public abstract void use();
    
    public String getCost() {
		return cost;
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
