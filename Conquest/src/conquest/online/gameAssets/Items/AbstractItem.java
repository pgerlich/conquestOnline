package conquest.online.gameAssets.Items;

public abstract class AbstractItem {

	String itemName;
    Double cost;
    
    public abstract void use();
    
    public Double getCost() {
		return cost;
	}
}
