package conquest.online.gameAssets.Items;

public class Armor extends AbstractEquipableItem {

	private String armor;
	@Override
	public void use() {
		// TODO Auto-generated method stub
		owned = true;
	}
	
	public void setArmor(String armor) {
		this.armor = armor;
	}
	
	public String getArmor() {
		return armor;
	}
}
