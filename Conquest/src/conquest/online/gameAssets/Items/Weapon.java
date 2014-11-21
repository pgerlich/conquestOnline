package conquest.online.gameAssets.Items;
public class Weapon extends AbstractEquipableItem {

	
	private int currentAmmo;
	private int maxAmmo;
	private int reloadDelay;
	private int attack;
	
	public void setAttack(int attack){
		this.attack = attack;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public void reload(int delay){
		
	}



	@Override
	public void use() {
		// TODO Auto-generated method stub
		owned = true;
	}



}
