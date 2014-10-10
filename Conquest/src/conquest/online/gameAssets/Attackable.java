package conquest.online.gameAssets;

public interface Attackable {

	void adjustCurrentHealth(int change);
	int getCurrentHealth();
	
	void adjustMaxHealth(int change);
	int getMaxHealth();
	
	
	
}
