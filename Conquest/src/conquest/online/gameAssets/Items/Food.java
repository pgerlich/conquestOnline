package conquest.online.gameAssets.Items;

public class Food extends AbstractItem {
	private String health;
	@Override
	public void use() {
		// TODO Auto-generated method stub
	}
	
	public void setHealth(String health) {
		this.health = health;
	}
	
	public String getHealth() {
		return health;
	}
}
