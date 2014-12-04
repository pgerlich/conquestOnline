package conquest.online.gameAssets.Items;
public class Weapon extends AbstractEquipableItem {

	@SuppressWarnings("unused")
	private int currentAmmo;
	@SuppressWarnings("unused")
	private int maxAmmo;
	@SuppressWarnings("unused")
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
