package conquest.online;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PropertyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property);
		
		loadProperty();
	}
	
	/**
	 * need to figure out a way to call this with different locations maybe? in  order
	 * to load different properties with the same methods.
	 */
	public void loadProperty() {
		//parameters coordinates maybe
		//load images and such
	}
}
