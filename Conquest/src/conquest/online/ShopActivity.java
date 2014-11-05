package conquest.online;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import conquest.client.classes.RegisterRequest;
import conquest.online.client.MovementClient;
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
	
	Food[] food = new Food[4];
	Weapon[] weapon = new Weapon[4];
	Armor[] armor = new Armor[4];
	DefensiveStructure[] ds = new DefensiveStructure[4];
	OffensiveStructure[] os = new OffensiveStructure[4];		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_shop);
		
		populateStore();
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
	 * This method is used to populate the store differently based on class type
	 * it needs to update the image button pictures as well as the info about them
	 */
	public void populateStore() {
		String message;
		
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
	}	
	
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
		private final String classChosen;
		public String message;
		public boolean success;

		//Instantiate task
		getShop(String username, String classChosen) {
			this.username = username;
			this.classChosen = classChosen;
			
			message = "";
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			
			try {
				MovementClient mc = new MovementClient();
				
				RegisterRequest register = new RegisterRequest();
				
				register.username = username;
				register.accountTypeCharacter = classChosen;		
						
				//FIXME: Must change this if we implement other login methods.
				register.accountType = 0;
				
				mc.client.sendUDP(register);
				
				//Wait for a response from the server
				while ( mc.regResponse == null ) {
					
				}
				
				message = mc.regResponse.message;
				success = mc.regResponse.success;
				mc.close();
				return success;

			}
			catch (Exception e){
				message = "Failed to connect to server";
				return false;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (success) {
				populateStore();
			}
		}

		@Override
		protected void onCancelled() {
			//on cancel
		}
	}
}
