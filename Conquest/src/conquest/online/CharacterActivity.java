package conquest.online;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CharacterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character);
		String user = "";
		loadCharacter(user);
	}
	
	/**
	 * perhaps load the different character information here, stats and such
	 * pass an objec maybe and read it all from there
	 * @param user
	 */
	public void loadCharacter(String user) {
		//Name
		//Image
		//STATS
	}
}
