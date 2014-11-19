package conquest.online.gameAssets.Structures;

public class DefensiveStructure extends AbstractStructure {

	

	public void levelUp() {
		// TODO Auto-generated method stub
		level++;
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub
		owned = true;
	}

	@Override
	public String getCost() {
		// TODO Auto-generated method stub
		return cost;
	}


}
