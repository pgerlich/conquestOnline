package conquest.online;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PersonalPropertyActivity extends Activity {
	
	//The double array of grid items
	public ImageView[][] grid;
	public int width;
	public int height;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_property);
		
		width = height = 7;
		
		populateGridsAndAddListeners();
		
	}
	
	/*
	 * Populates the grid array and adds listeners
	 */
	public void populateGridsAndAddListeners(){
		grid = new ImageView[width][height];
		
		grid[0][0] = (ImageView) findViewById(R.id.imageView00);
		grid[1][0] = (ImageView) findViewById(R.id.imageView10);
		grid[2][0] = (ImageView) findViewById(R.id.imageView20);
		grid[3][0] = (ImageView) findViewById(R.id.imageView30);
		grid[4][0] = (ImageView) findViewById(R.id.imageView40);
		grid[5][0] = (ImageView) findViewById(R.id.imageView50);
		grid[6][0] = (ImageView) findViewById(R.id.imageView60);
		grid[0][1] = (ImageView) findViewById(R.id.imageView01);
		grid[1][1] = (ImageView) findViewById(R.id.imageView11);
		grid[2][1] = (ImageView) findViewById(R.id.imageView21);
		grid[3][1] = (ImageView) findViewById(R.id.imageView31);
		grid[4][1] = (ImageView) findViewById(R.id.imageView41);
		grid[5][1] = (ImageView) findViewById(R.id.imageView51);
		grid[6][1] = (ImageView) findViewById(R.id.imageView61);
		grid[0][2] = (ImageView) findViewById(R.id.imageView02);
		grid[1][2] = (ImageView) findViewById(R.id.imageView12);
		grid[2][2] = (ImageView) findViewById(R.id.imageView22);
		grid[3][2] = (ImageView) findViewById(R.id.imageView32);
		grid[4][2] = (ImageView) findViewById(R.id.imageView42);
		grid[5][2] = (ImageView) findViewById(R.id.imageView52);
		grid[6][2] = (ImageView) findViewById(R.id.imageView62);
		grid[0][3] = (ImageView) findViewById(R.id.imageView03);
		grid[1][3] = (ImageView) findViewById(R.id.imageView13);
		grid[2][3] = (ImageView) findViewById(R.id.imageView23);
		grid[3][3] = (ImageView) findViewById(R.id.imageView33);
		grid[4][3] = (ImageView) findViewById(R.id.imageView43);
		grid[5][3] = (ImageView) findViewById(R.id.imageView53);
		grid[6][3] = (ImageView) findViewById(R.id.imageView63);
		grid[0][4] = (ImageView) findViewById(R.id.imageView04);
		grid[1][4] = (ImageView) findViewById(R.id.imageView14);
		grid[2][4] = (ImageView) findViewById(R.id.imageView24);
		grid[3][4] = (ImageView) findViewById(R.id.imageView34);
		grid[4][4] = (ImageView) findViewById(R.id.imageView44);
		grid[5][4] = (ImageView) findViewById(R.id.imageView54);
		grid[6][4] = (ImageView) findViewById(R.id.imageView64);
		grid[0][5] = (ImageView) findViewById(R.id.imageView05);
		grid[1][5] = (ImageView) findViewById(R.id.imageView15);
		grid[2][5] = (ImageView) findViewById(R.id.imageView25);
		grid[3][5] = (ImageView) findViewById(R.id.imageView35);
		grid[4][5] = (ImageView) findViewById(R.id.imageView45);
		grid[5][5] = (ImageView) findViewById(R.id.imageView55);
		grid[6][5] = (ImageView) findViewById(R.id.imageView65);
		grid[0][6] = (ImageView) findViewById(R.id.imageView05);
		grid[1][6] = (ImageView) findViewById(R.id.imageView16);
		grid[2][6] = (ImageView) findViewById(R.id.imageView26);
		grid[3][6] = (ImageView) findViewById(R.id.imageView36);
		grid[4][6] = (ImageView) findViewById(R.id.imageView46);
		grid[5][6] = (ImageView) findViewById(R.id.imageView56);
		grid[6][6] = (ImageView) findViewById(R.id.imageView66);

		for(int i = 0; i < height; i++ ) {
			for (int j = 0; j < width; j++) {
				addListenerToItem(grid[j][i], "(" + j + ", " + i + ")");
			}
		}
		
	}
	
	/**
	 * Adds a listener to the given item
	 * @param image
	 */
	public void addListenerToItem(ImageView image, final String location) {
		
		image.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				toast(location);
				return false;
			}
		});
		
	}
	
	/**
	 * Displays the message on the screen
	 * @param message
	 */
	public void toast(String message) {
		//Toast an error message if it exists. Will close and leave page if it doesn't
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
}
