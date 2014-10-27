package conquest.online;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class ShopActivity extends ActionBarActivity {
	
	//Find all the buttons for the listeners here
	ImageButton apple = (ImageButton) findViewById(R.id.apple);
	ImageButton cupcake = (ImageButton) findViewById(R.id.cupcake);
	ImageButton sandwich = (ImageButton) findViewById(R.id.sandwich);
	ImageButton chicken = (ImageButton) findViewById(R.id.chicken);
	ImageButton knife = (ImageButton) findViewById(R.id.knife);
	ImageButton sword = (ImageButton) findViewById(R.id.sword);
	ImageButton pistol = (ImageButton) findViewById(R.id.pistol);
	ImageButton rifle = (ImageButton) findViewById(R.id.rifle);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
		
		createListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * This method is to create all the button listeners for the shop
	 */
	public void createListeners() {
		apple.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int cost = readValue("apple");
				int apple_price = readValue("apple");
				if(checkBalance(cost)) {
					addHealth(apple_price);
				}				
			}
		});
		
		cupcake.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int cost = readValue("cupcake");
				int apple_price = readValue("cupcake");
				if(checkBalance(cost)) {
					addHealth(apple_price);
				}				
			}
		});
		
		sandwich.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int cost = readValue("sandwich");
				int apple_price = readValue("sandwich");
				if(checkBalance(cost)) {
					addHealth(apple_price);
				}				
			}
		});
		
		chicken.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int cost = readValue("chicken");
				int apple_price = readValue("chicken");
				if(checkBalance(cost)) {
					addHealth(apple_price);
				}				
			}
		});
		
		knife.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int cost = readValue("knife");
				int apple_price = readValue("knife");
				if(checkBalance(cost)) {
					addHealth(apple_price);
				}				
			}
		});
		
		sword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int cost = readValue("sword");
				int apple_price = readValue("sword");
				if(checkBalance(cost)) {
					addHealth(apple_price);
				}				
			}
		});
		
		pistol.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int cost = readValue("pistol");
				int apple_price = readValue("pistol");
				if(checkBalance(cost)) {
					addHealth(apple_price);
				}				
			}
		});
		
		rifle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int cost = readValue("rifle");
				int apple_price = readValue("rifle");
				if(checkBalance(cost)) {
					addHealth(apple_price);
				}				
			}
		});
	}
	
	public int readValue(String price) {
		return 0;
	}
	
	public boolean checkBalance(int x) {
		//if balance is enough return 1, else return 0
		return false;
	}
	
	public void addHealth(int x) {
		//adds x health to player
	}
}
