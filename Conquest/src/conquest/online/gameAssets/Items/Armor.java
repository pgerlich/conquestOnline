package conquest.online.gameAssets.Items;

public class Armor extends AbstractEquipableItem {

	private int armor;
	@Override
	public void use() {
		// TODO Auto-generated method stub
		owned = true;
	}
	
	public void setArmor(int armor) {
		this.armor = armor;
	}
	
	public int getArmor() {
		return armor;
	}
}
