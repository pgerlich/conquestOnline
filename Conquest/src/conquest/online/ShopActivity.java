package conquest.online;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import conquest.online.gameAssets.Items.Armor;
import conquest.online.gameAssets.Items.Food;
import conquest.online.gameAssets.Items.Weapon;
import conquest.online.gameAssets.Structures.DefensiveStructure;
import conquest.online.gameAssets.Structures.OffensiveStructure;

public class ShopActivity extends ActionBarActivity {
	
	//Find all the buttons for the listeners here
	ImageButton foodOne = (ImageButton) findViewById(R.id.food_one);
	ImageButton foodTwo = (ImageButton) findViewById(R.id.food_two);
	ImageButton foodThree = (ImageButton) findViewById(R.id.food_three);
	ImageButton foodFour = (ImageButton) findViewById(R.id.food_four);
	ImageButton weaponOne = (ImageButton) findViewById(R.id.weapon_one);
	ImageButton weaponTwo = (ImageButton) findViewById(R.id.weapon_two);
	ImageButton weaponThree = (ImageButton) findViewById(R.id.weapon_three);
	ImageButton weaponFour = (ImageButton) findViewById(R.id.weapon_four);
	ImageButton armorOne = (ImageButton) findViewById(R.id.armor_one);
	ImageButton armorTwo = (ImageButton) findViewById(R.id.armor_two);
	ImageButton armorThree = (ImageButton) findViewById(R.id.armor_three);
	ImageButton armorFour = (ImageButton) findViewById(R.id.armor_four);
	ImageButton dsOne = (ImageButton) findViewById(R.id.ds_one);
	ImageButton dsTwo = (ImageButton) findViewById(R.id.ds_two);
	ImageButton dsThree = (ImageButton) findViewById(R.id.ds_three);
	ImageButton dsFour = (ImageButton) findViewById(R.id.ds_four);
	ImageButton osOne = (ImageButton) findViewById(R.id.os_one);
	ImageButton osTwo = (ImageButton) findViewById(R.id.os_two);
	ImageButton osThree = (ImageButton) findViewById(R.id.os_three);
	ImageButton osFour = (ImageButton) findViewById(R.id.os_four);
	TextView foodOneInfo = (TextView) findViewById(R.id.food_one_info);
	TextView foodTwoInfo = (TextView) findViewById(R.id.food_two_info);
	TextView foodThreeInfo = (TextView) findViewById(R.id.food_three_info);
	TextView foodFourInfo = (TextView) findViewById(R.id.food_four_info);
	TextView weaponOneInfo = (TextView) findViewById(R.id.weapon_one_info);
	TextView weaponTwoInfo = (TextView) findViewById(R.id.weapon_two_info);
	TextView weaponThreeInfo = (TextView) findViewById(R.id.weapon_three_info);
	TextView weaponFourInfo = (TextView) findViewById(R.id.weapon_four_info);
	TextView armorOneInfo = (TextView) findViewById(R.id.armor_one_info);
	TextView armorTwoInfo = (TextView) findViewById(R.id.armor_two_info);
	TextView armorThreeInfo = (TextView) findViewById(R.id.armor_three_info);
	TextView armorFourInfo = (TextView) findViewById(R.id.armor_four_info);
	TextView dsOneInfo = (TextView) findViewById(R.id.ds_one_info);
	TextView dsTwoInfo = (TextView) findViewById(R.id.ds_two_info);
	TextView dsThreeInfo = (TextView) findViewById(R.id.ds_three_info);
	TextView dsFourInfo = (TextView) findViewById(R.id.ds_four_info);
	TextView osOneInfo = (TextView) findViewById(R.id.os_one_info);
	TextView osTwoInfo = (TextView) findViewById(R.id.os_two_info);
	TextView osThreeInfo = (TextView) findViewById(R.id.os_three_info);
	TextView osFourInfo = (TextView) findViewById(R.id.os_four_info); 
	TextView message = (TextView) findViewById(R.id.error_message);
	
	private UserSession user;
	Food[] food = new Food[4];
	Weapon[] weapon = new Weapon[4];
	Armor[] armor = new Armor[4];
	DefensiveStructure[] ds = new DefensiveStructure[4];
	OffensiveStructure[] os = new OffensiveStructure[4];		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_shop);
		
		user = new UserSession(getApplicationContext());
		
		getShop shop = new getShop(user.getUser(), user.getToken());
		shop.execute((Void) null);
		food = shop.food;
		weapon = shop.weapon;
		armor = shop.armor;
		ds = shop.ds;
		os = shop.os;
		
		//makes the buttons
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
	 * this is to set all the images, names, descriptions, and costs.
	 */
	public void populateStore() {
		String message;
		//need to figure out how to set the background image to be read from getPic String? maybe change it to be a drwable
		foodOne.setBackgroundResource(1);
		foodOneInfo.setText(food[0].getName() + "-" + food[0].getDescription() + ":" + food[0].getCost());
		if (food[0].own()) {
			foodOneInfo.setTextColor(Color.RED);
		}
		foodTwo.setBackgroundResource(1);
		foodTwoInfo.setText(food[1].getName() + "-" + food[1].getDescription() + ":" + food[1].getCost());
		if (food[1].own()) {
			foodTwoInfo.setTextColor(Color.RED);
		}
		foodThree.setBackgroundResource(1);
		foodThreeInfo.setText(food[2].getName() + "-" + food[2].getDescription() + ":" + food[2].getCost());
		if (food[2].own()) {
			foodThreeInfo.setTextColor(Color.RED);
		}
		foodFour.setBackgroundResource(1);
		foodFourInfo.setText(food[3].getName() + "-" + food[3].getDescription() + ":" + food[3].getCost());
		if (food[3].own()) {
			foodFourInfo.setTextColor(Color.RED);
		}
		weaponOne.setBackgroundResource(1);
		weaponOneInfo.setText(weapon[0].getName() + "-" + weapon[0].getDescription() + ":" + weapon[0].getCost());
		if (weapon[0].own()) {
			weaponOneInfo.setTextColor(Color.RED);
		}
		weaponTwo.setBackgroundResource(1);
		weaponTwoInfo.setText(weapon[1].getName() + "-" + weapon[1].getDescription() + ":" + weapon[1].getCost());
		if (weapon[1].own()) {
			weaponTwoInfo.setTextColor(Color.RED);
		}
		weaponThree.setBackgroundResource(1);
		weaponThreeInfo.setText(weapon[2].getName() + "-" + weapon[2].getDescription() + ":" + weapon[2].getCost());
		if (weapon[2].own()) {
			weaponThreeInfo.setTextColor(Color.RED);
		}
		weaponFour.setBackgroundResource(1);
		weaponFourInfo.setText(weapon[3].getName() + "-" + weapon[3].getDescription() + ":" + weapon[3].getCost());
		if (weapon[3].own()) {
			weaponFourInfo.setTextColor(Color.RED);
		}
		armorOne.setBackgroundResource(1);
		armorOneInfo.setText(armor[0].getName() + "-" + armor[0].getDescription() + ":" + armor[0].getCost());
		if (armor[0].own()) {
			armorOneInfo.setTextColor(Color.RED);
		}
		armorTwo.setBackgroundResource(1);
		armorTwoInfo.setText(armor[1].getName() + "-" + armor[1].getDescription() + ":" + armor[1].getCost());
		if (armor[1].own()) {
			armorTwoInfo.setTextColor(Color.RED);
		}
		armorThree.setBackgroundResource(1);
		armorThreeInfo.setText(armor[2].getName() + "-" + armor[2].getDescription() + ":" + armor[2].getCost());
		if (armor[2].own()) {
			armorThreeInfo.setTextColor(Color.RED);
		}
		armorFour.setBackgroundResource(1);
		armorFourInfo.setText(armor[3].getName() + "-" + armor[3].getDescription() + ":" + armor[3].getCost());
		if (armor[3].own()) {
			armorFourInfo.setTextColor(Color.RED);
		}
		dsOne.setBackgroundResource(1);
		dsOneInfo.setText(ds[0].getName() + "-" + ds[0].getDes() + ":" + ds[0].getCost());
		if (ds[0].own()) {
			dsOneInfo.setTextColor(Color.RED);
		}
		dsTwo.setBackgroundResource(1);
		dsTwoInfo.setText(ds[1].getName() + "-" + ds[1].getDes() + ":" + ds[1].getCost());
		if (ds[1].own()) {
			dsTwoInfo.setTextColor(Color.RED);
		}
		dsThree.setBackgroundResource(1);
		dsThreeInfo.setText(ds[2].getName() + "-" + ds[2].getDes() + ":" + ds[2].getCost());
		if (ds[2].own()) {
			dsThreeInfo.setTextColor(Color.RED);
		}
		dsFour.setBackgroundResource(1);
		dsFourInfo.setText(ds[3].getName() + "-" + ds[3].getDes() + ":" + ds[3].getCost());
		if (ds[3].own()) {
			dsFourInfo.setTextColor(Color.RED);
		}
		osOne.setBackgroundResource(1);
		osOneInfo.setText(os[0].getName() + "-" + os[0].getDes() + ":" + os[0].getCost());
		if (os[0].own()) {
			osOneInfo.setTextColor(Color.RED);
		}
		osTwo.setBackgroundResource(1);
		osTwoInfo.setText(os[1].getName() + "-" + os[1].getDes() + ":" + os[1].getCost());
		if (os[1].own()) {
			osTwoInfo.setTextColor(Color.RED);
		}
		osThree.setBackgroundResource(1);
		osThreeInfo.setText(os[2].getName() + "-" + os[2].getDes() + ":" + os[2].getCost());
		if (os[2].own()) {
			osThreeInfo.setTextColor(Color.RED);
		}
		osFour.setBackgroundResource(1);
		osFourInfo.setText(os[3].getName() + "-" + os[3].getDes() + ":" + os[3].getCost());	
		if (os[3].own()) {
			osFourInfo.setTextColor(Color.RED);
		}
		createListeners();
	}
	
	/**
	 * This method is to create all the button listeners for the shop
	 */
	public void createListeners() {
		foodTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(food[0].getCost())) {
					spendMoney(food[0].getCost());
					food[0].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
			}
		});
		
		foodOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(food[1].getCost())) {
					spendMoney(food[1].getCost());
					food[1].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		foodThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(food[2].getCost())) {
					spendMoney(food[2].getCost());
					food[2].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		foodFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(food[3].getCost())) {
					spendMoney(food[3].getCost());
					food[3].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
				
								
			}
		});
		
		weaponOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(weapon[0].getCost())) {
					spendMoney(weapon[0].getCost());
					weapon[0].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		weaponTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(weapon[1].getCost())) {
					spendMoney(weapon[1].getCost());
					weapon[1].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		weaponThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(weapon[2].getCost())) {
					spendMoney(weapon[2].getCost());
					weapon[2].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		weaponFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(weapon[3].getCost())) {
					spendMoney(weapon[3].getCost());
					weapon[3].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		armorOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(armor[0].getCost())) {
					spendMoney(armor[0].getCost());
					armor[0].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		armorTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(armor[1].getCost())) {
					spendMoney(armor[1].getCost());
					armor[1].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		armorThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(armor[2].getCost())) {
					spendMoney(armor[2].getCost());
					armor[2].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		armorFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(armor[3].getCost())) {
					spendMoney(armor[3].getCost());
					armor[3].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		dsOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(ds[0].getCost())) {
					spendMoney(ds[0].getCost());
					ds[0].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		dsTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(ds[1].getCost())) {
					spendMoney(ds[1].getCost());
					ds[1].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
			}
		});
		
		dsThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(ds[2].getCost())) {
					spendMoney(ds[2].getCost());
					ds[2].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		dsFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(ds[3].getCost())) {
					spendMoney(ds[3].getCost());
					ds[3].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		osOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(os[0].getCost())) {
					spendMoney(os[0].getCost());
					os[0].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		osTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(os[1].getCost())) {
					spendMoney(os[1].getCost());
					os[1].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
								
			}
		});
		
		osThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(os[2].getCost())) {
					spendMoney(os[2].getCost());
					os[2].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
			}
		});
		
		osFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if(checkBalance(os[3].getCost())) {
					spendMoney(os[3].getCost());
					os[3].use();
				}
				else {
					message.setVisibility(View.VISIBLE);
				}
			}
		});
		
	}
	
	public boolean checkBalance(Double x) {
		//if balance is enough return 1, else return 0
		return false;
	}
	
	public void spendMoney(double cost) {
		//take money away from user
		//make another AsyncTask? maybe idk
	}	
	
	
	//need to update this to get information for the shop, not register
	public class getShop extends AsyncTask<Void, Void, Boolean> {

		/** 
		 * this is the info we need to fill from the database
		 * Food[] food = new Food[4];
		 * Weapon[] weapon = new Weapon[4];
		 * Armor[] armor = new Armor[4];
		 * DefensiveStructure[] ds = new DefensiveStructure[4];
		 * OffensiveStructure[] os = new OffensiveStructure[4];
		 */
		
		private final String username;
		private final String token;
		public String message;
		public boolean success;
		public Food[] food = new Food[4];
		public Weapon[] weapon = new Weapon[4];
		public Armor[] armor = new Armor[4];
		public DefensiveStructure[] ds = new DefensiveStructure[4];
		public OffensiveStructure[] os = new OffensiveStructure[4];

		//Instantiate task
		getShop(String username, String token) {
			this.username = username;
			this.token = token;
			
			message = "";
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			List<NameValuePair> postParams = new ArrayList<NameValuePair>(3);
	        postParams.add(new BasicNameValuePair("username", username));
	        postParams.add(new BasicNameValuePair("token", token));
			
	        //change to shop, not get friends
			JSONObject requestShop = JSONfunctions.getJSONfromURL("http://proj-309-R12.cs.iastate.edu/functions/shop/getItems.php", postParams);					
			
			//Try and check if it succeeded
			try {
				String success = requestShop.getString("success");
				
				//Return true on success
				if ( success.equals("1") ) {
					
					for (int i = 0; i < 4; i++ ) {
						food[i].create(requestShop.getString("fn"+i),requestShop.getDouble("fc"+i), requestShop.getString("fp"+i),requestShop.getString("fd"+i));
						weapon[i].create(requestShop.getString("wn"+i),requestShop.getDouble("wc"+i), requestShop.getString("wp"+i),requestShop.getString("wd"+i));
						armor[i].create(requestShop.getString("an"+i),requestShop.getDouble("ac"+i), requestShop.getString("ap"+i),requestShop.getString("ad"+i));
						ds[i].create(requestShop.getString("dn"+i),requestShop.getDouble("dc"+i), requestShop.getString("dp"+i),requestShop.getString("dd"+i));
						os[i].create(requestShop.getString("on"+i),requestShop.getDouble("oc"+i), requestShop.getString("op"+i),requestShop.getString("od"+i));
					}
					
					message = "success";
					return true;
					
				//Set error message and return false.
				} else {
					message = requestShop.getString("message");
					return false;
				}
			
			//Off chance that some weird shit happens
			} catch (JSONException e) {
				//Something went wrong - typically JSON value doesn't exist (success).
				message = "An error occured. Please try again later.";
				return false;
			}
			
		}
		@Override
		protected void onPostExecute(final Boolean success) {
			//dont know what to put here
		}

		@Override
		protected void onCancelled() {
			//on cancel
		}
	}
}
