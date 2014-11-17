package conquest.online;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import conquest.online.R;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

	// Find all the buttons for the listeners here
	public ImageButton foodOne;
	public ImageButton foodTwo;
	public ImageButton foodThree;
	public ImageButton foodFour;
	public ImageButton weaponOne;
	public ImageButton weaponTwo;
	public ImageButton weaponThree;
	public ImageButton weaponFour;
	public ImageButton armorOne;
	public ImageButton armorTwo;
	public ImageButton armorThree;
	public ImageButton armorFour;
	public ImageButton dsOne;
	public ImageButton dsTwo;
	public ImageButton dsThree;
	public ImageButton dsFour;
	public ImageButton osOne;
	public ImageButton osTwo;
	public ImageButton osThree;
	public ImageButton osFour;
	public TextView foodOneInfo;
	public TextView foodTwoInfo;
	public TextView foodThreeInfo;
	public TextView foodFourInfo;
	public TextView weaponOneInfo;
	public TextView weaponTwoInfo;
	public TextView weaponThreeInfo;
	public TextView weaponFourInfo;
	public TextView armorOneInfo;
	public TextView armorTwoInfo;
	public TextView armorThreeInfo;
	public TextView armorFourInfo;
	public TextView dsOneInfo;
	public TextView dsTwoInfo;
	public TextView dsThreeInfo;
	public TextView dsFourInfo;
	public TextView osOneInfo;
	public TextView osTwoInfo;
	public TextView osThreeInfo;
	public TextView osFourInfo;
	public TextView message;

	private UserSession user;
	String gold;
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

		// makes the buttons
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

		// need to figure out how to set the background image to be read from
		// getPic String? maybe change it to be a drwable

		setImage(food[0].getPic(), foodOne);
		foodOneInfo.setText(food[0].getName() + "-" + food[0].getDescription()
				+ ":" + food[0].getCost());
		if (food[0].own()) {
			foodOneInfo.setTextColor(Color.RED);
		}
		setImage(food[1].getPic(), foodTwo);
		foodTwoInfo.setText(food[1].getName() + "-" + food[1].getDescription()
				+ ":" + food[1].getCost());
		if (food[1].own()) {
			foodTwoInfo.setTextColor(Color.RED);
		}
		setImage(food[2].getPic(), foodThree);
		foodThreeInfo.setText(food[2].getName() + "-"
				+ food[2].getDescription() + ":" + food[2].getCost());
		if (food[2].own()) {
			foodThreeInfo.setTextColor(Color.RED);
		}
		setImage(food[3].getPic(), foodFour);
		foodFourInfo.setText(food[3].getName() + "-" + food[3].getDescription()
				+ ":" + food[3].getCost());
		if (food[3].own()) {
			foodFourInfo.setTextColor(Color.RED);
		}
		setImage(weapon[0].getPic(), weaponOne);
		weaponOneInfo.setText(weapon[0].getName() + "-"
				+ weapon[0].getDescription() + ":" + weapon[0].getCost());
		if (weapon[0].own()) {
			weaponOneInfo.setTextColor(Color.RED);
		}
		setImage(weapon[1].getPic(), weaponTwo);
		weaponTwoInfo.setText(weapon[1].getName() + "-"
				+ weapon[1].getDescription() + ":" + weapon[1].getCost());
		if (weapon[1].own()) {
			weaponTwoInfo.setTextColor(Color.RED);
		}
		setImage(weapon[2].getPic(), weaponThree);
		weaponThreeInfo.setText(weapon[2].getName() + "-"
				+ weapon[2].getDescription() + ":" + weapon[2].getCost());
		if (weapon[2].own()) {
			weaponThreeInfo.setTextColor(Color.RED);
		}
		setImage(weapon[3].getPic(), weaponFour);
		weaponFourInfo.setText(weapon[3].getName() + "-"
				+ weapon[3].getDescription() + ":" + weapon[3].getCost());
		if (weapon[3].own()) {
			weaponFourInfo.setTextColor(Color.RED);
		}
		setImage(armor[0].getPic(), armorOne);
		armorOneInfo.setText(armor[0].getName() + "-"
				+ armor[0].getDescription() + ":" + armor[0].getCost());
		if (armor[0].own()) {
			armorOneInfo.setTextColor(Color.RED);
		}
		setImage(armor[1].getPic(), armorTwo);
		armorTwoInfo.setText(armor[1].getName() + "-"
				+ armor[1].getDescription() + ":" + armor[1].getCost());
		if (armor[1].own()) {
			armorTwoInfo.setTextColor(Color.RED);
		}
		setImage(armor[2].getPic(), armorThree);
		armorThreeInfo.setText(armor[2].getName() + "-"
				+ armor[2].getDescription() + ":" + armor[2].getCost());
		if (armor[2].own()) {
			armorThreeInfo.setTextColor(Color.RED);
		}
		setImage(armor[3].getPic(), armorFour);
		armorFourInfo.setText(armor[3].getName() + "-"
				+ armor[3].getDescription() + ":" + armor[3].getCost());
		if (armor[3].own()) {
			armorFourInfo.setTextColor(Color.RED);
		}
		setImage(ds[0].getPic(), dsOne);
		dsOneInfo.setText(ds[0].getName() + "-" + ds[0].getDes() + ":"
				+ ds[0].getCost());
		if (ds[0].own()) {
			dsOneInfo.setTextColor(Color.RED);
		}
		setImage(ds[1].getPic(), dsTwo);
		dsTwoInfo.setText(ds[1].getName() + "-" + ds[1].getDes() + ":"
				+ ds[1].getCost());
		if (ds[1].own()) {
			dsTwoInfo.setTextColor(Color.RED);
		}
		setImage(ds[2].getPic(), dsThree);
		dsThreeInfo.setText(ds[2].getName() + "-" + ds[2].getDes() + ":"
				+ ds[2].getCost());
		if (ds[2].own()) {
			dsThreeInfo.setTextColor(Color.RED);
		}
		setImage(ds[3].getPic(), dsFour);
		dsFourInfo.setText(ds[3].getName() + "-" + ds[3].getDes() + ":"
				+ ds[3].getCost());
		if (ds[3].own()) {
			dsFourInfo.setTextColor(Color.RED);
		}
		setImage(os[0].getPic(), osOne);
		osOneInfo.setText(os[0].getName() + "-" + os[0].getDes() + ":"
				+ os[0].getCost());
		if (os[0].own()) {
			osOneInfo.setTextColor(Color.RED);
		}
		setImage(os[1].getPic(), osTwo);
		osTwoInfo.setText(os[1].getName() + "-" + os[1].getDes() + ":"
				+ os[1].getCost());
		if (os[1].own()) {
			osTwoInfo.setTextColor(Color.RED);
		}
		setImage(os[2].getPic(), osThree);
		osThreeInfo.setText(os[2].getName() + "-" + os[2].getDes() + ":"
				+ os[2].getCost());
		if (os[2].own()) {
			osThreeInfo.setTextColor(Color.RED);
		}
		setImage(os[3].getPic(), osFour);
		osFourInfo.setText(os[3].getName() + "-" + os[3].getDes() + ":"
				+ os[3].getCost());
		if (os[3].own()) {
			osFourInfo.setTextColor(Color.RED);
		}
		createListeners();
	}

	/**
	 * This is used to take the picture loaded from the server and make it the
	 * buttons background image for the shop items
	 * 
	 * @param pic
	 * @param view
	 */
	public void setImage(String pic, ImageButton view) {
		AssetManager man = getAssets();
		InputStream open = null;
		try {
			open = man.open(pic);
			Bitmap bitmap = BitmapFactory.decodeStream(open);
			// Assign the bitmap to an ImageView in this layout
			view.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (open != null) {
				try {
					open.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This method is to create all the button listeners for the shop
	 */
	public void createListeners() {
		foodOne = (ImageButton) findViewById(R.id.food_one);
		foodTwo = (ImageButton) findViewById(R.id.food_two);
		foodThree = (ImageButton) findViewById(R.id.food_three);
		foodFour = (ImageButton) findViewById(R.id.food_four);
		weaponOne = (ImageButton) findViewById(R.id.weapon_one);
		weaponTwo = (ImageButton) findViewById(R.id.weapon_two);
		weaponThree = (ImageButton) findViewById(R.id.weapon_three);
		weaponFour = (ImageButton) findViewById(R.id.weapon_four);
		armorOne = (ImageButton) findViewById(R.id.armor_one);
		armorTwo = (ImageButton) findViewById(R.id.armor_two);
		armorThree = (ImageButton) findViewById(R.id.armor_three);
		armorFour = (ImageButton) findViewById(R.id.armor_four);
		dsOne = (ImageButton) findViewById(R.id.ds_one);
		dsTwo = (ImageButton) findViewById(R.id.ds_two);
		dsThree = (ImageButton) findViewById(R.id.ds_three);
		dsFour = (ImageButton) findViewById(R.id.ds_four);
		osOne = (ImageButton) findViewById(R.id.os_one);
		osTwo = (ImageButton) findViewById(R.id.os_two);
		osThree = (ImageButton) findViewById(R.id.os_three);
		osFour = (ImageButton) findViewById(R.id.os_four);
		foodOneInfo = (TextView) findViewById(R.id.food_one_info);
		foodTwoInfo = (TextView) findViewById(R.id.food_two_info);
		foodThreeInfo = (TextView) findViewById(R.id.food_three_info);
		foodFourInfo = (TextView) findViewById(R.id.food_four_info);
		weaponOneInfo = (TextView) findViewById(R.id.weapon_one_info);
		weaponTwoInfo = (TextView) findViewById(R.id.weapon_two_info);
		weaponThreeInfo = (TextView) findViewById(R.id.weapon_three_info);
		weaponFourInfo = (TextView) findViewById(R.id.weapon_four_info);
		armorOneInfo = (TextView) findViewById(R.id.armor_one_info);
		armorTwoInfo = (TextView) findViewById(R.id.armor_two_info);
		armorThreeInfo = (TextView) findViewById(R.id.armor_three_info);
		armorFourInfo = (TextView) findViewById(R.id.armor_four_info);
		dsOneInfo = (TextView) findViewById(R.id.ds_one_info);
		dsTwoInfo = (TextView) findViewById(R.id.ds_two_info);
		dsThreeInfo = (TextView) findViewById(R.id.ds_three_info);
		dsFourInfo = (TextView) findViewById(R.id.ds_four_info);
		osOneInfo = (TextView) findViewById(R.id.os_one_info);
		osTwoInfo = (TextView) findViewById(R.id.os_two_info);
		osThreeInfo = (TextView) findViewById(R.id.os_three_info);
		osFourInfo = (TextView) findViewById(R.id.os_four_info);
		message = (TextView) findViewById(R.id.error_message);

		foodTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(food[0].getCost())) {
					spendMoney(food[0].getCost());
					food[0].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							food[0].getId());
					use.execute((Void) null);
					
					addHealth add = new addHealth(user.getUser(), user.getToken(), food[0].getHealth());
					add.execute((Void) null);
				} else {
					message.setVisibility(View.VISIBLE);
				}
			}
		});

		foodOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(food[1].getCost())) {
					spendMoney(food[1].getCost());
					food[1].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							food[1].getId());
					use.execute((Void) null);
					
					addHealth add = new addHealth(user.getUser(), user.getToken(), food[1].getHealth());
					add.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		foodThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(food[2].getCost())) {
					spendMoney(food[2].getCost());
					food[2].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							food[2].getId());
					use.execute((Void) null);
					
					addHealth add = new addHealth(user.getUser(), user.getToken(), food[2].getHealth());
					add.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		foodFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(food[3].getCost())) {
					spendMoney(food[3].getCost());
					food[3].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							food[3].getId());
					use.execute((Void) null);
					
					addHealth add = new addHealth(user.getUser(), user.getToken(), food[3].getHealth());
					add.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		weaponOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(weapon[0].getCost())) {
					spendMoney(weapon[0].getCost());
					weapon[0].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							weapon[0].getId());
					use.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		weaponTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(weapon[1].getCost())) {
					spendMoney(weapon[1].getCost());
					weapon[1].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							weapon[1].getId());
					use.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		weaponThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(weapon[2].getCost())) {
					spendMoney(weapon[2].getCost());
					weapon[2].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							weapon[2].getId());
					use.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		weaponFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(weapon[3].getCost())) {
					spendMoney(weapon[3].getCost());
					weapon[3].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							weapon[3].getId());
					use.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		armorOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(armor[0].getCost())  && !armor[0].own()) {
					spendMoney(armor[0].getCost());
					armor[0].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							armor[0].getId());
					use.execute((Void) null);
					
					incrHealth incr = new incrHealth(user.getUser(), user.getToken(), armor[0].getArmor());
					incr.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		armorTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(armor[1].getCost()) && !armor[1].own()) {
					spendMoney(armor[1].getCost());
					armor[1].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							armor[1].getId());
					use.execute((Void) null);
					
					incrHealth incr = new incrHealth(user.getUser(), user.getToken(), armor[1].getArmor());
					incr.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		armorThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(armor[2].getCost()) && !armor[2].own()) {
					spendMoney(armor[2].getCost());
					armor[2].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							armor[2].getId());
					use.execute((Void) null);
					
					incrHealth incr = new incrHealth(user.getUser(), user.getToken(), armor[2].getArmor());
					incr.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		armorFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(armor[3].getCost()) && !armor[3].own()) {
					spendMoney(armor[3].getCost());
					armor[3].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							armor[3].getId());
					use.execute((Void) null);
					
					incrHealth incr = new incrHealth(user.getUser(), user.getToken(), armor[3].getArmor());
					incr.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		// TODO
		// need to add check to see if user owns a property or not
		dsOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(ds[0].getCost())) {
					spendMoney(ds[0].getCost());
					ds[0].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							ds[0].getId());
					use.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		dsTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(ds[1].getCost())) {
					spendMoney(ds[1].getCost());
					ds[1].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							ds[1].getId());
					use.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}
			}
		});

		dsThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(ds[2].getCost())) {
					spendMoney(ds[2].getCost());
					ds[2].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							ds[2].getId());
					use.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		dsFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(ds[3].getCost())) {
					spendMoney(ds[3].getCost());
					ds[3].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							ds[3].getId());
					use.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		osOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(os[0].getCost())) {
					spendMoney(os[0].getCost());
					os[0].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							os[0].getId());
					use.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		osTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(os[1].getCost())) {
					spendMoney(os[1].getCost());
					os[1].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							os[1].getId());
					use.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}

			}
		});

		osThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(os[2].getCost())) {
					spendMoney(os[2].getCost());
					os[2].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							os[2].getId());
					use.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}
			}
		});

		osFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(os[3].getCost())) {
					spendMoney(os[3].getCost());
					os[3].use();
					useItem use = new useItem(user.getUser(), user.getToken(),
							os[3].getId());
					use.execute((Void) null);

				} else {
					message.setVisibility(View.VISIBLE);
				}
			}
		});

	}

	public boolean checkBalance(String price) {
		// if balance is enough return true, else return false
		getMoney mon = new getMoney(user.getUser(), user.getToken());
		gold = mon.gold;

		if (price.equals(gold)) {
			// has enough gold to buy.
			return true;
		} else {
			return false;
		}
	}

	public void spendMoney(String cost) {
		// take money away from user
		// make another AsyncTask? maybe idk
		spendMoney spend = new spendMoney(user.getUser(), user.getToken(),
				cost.toString());
		gold = spend.gold;

	}

	// need to update this to get information for the shop, not register
	public class getShop extends AsyncTask<Void, Void, Boolean> {

		/**
		 * this is the info we need to fill from the database Food[] food = new
		 * Food[4]; Weapon[] weapon = new Weapon[4]; Armor[] armor = new
		 * Armor[4]; DefensiveStructure[] ds = new DefensiveStructure[4];
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

		// Instantiate task
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

			// change to shop, not get friends
			JSONObject requestShop = JSONfunctions
					.getJSONfromURL(
							"http://proj-309-R12.cs.iastate.edu/functions/shop/getItems.php",
							postParams);

			// Try and check if it succeeded
			try {
				String success = requestShop.getString("success");

				// Return true on success
				if (success.equals("1")) {

					for (int i = 0; i < 4; i++) {
						food[i].create(requestShop.getString("fn" + i),
								requestShop.getString("fc" + i),
								requestShop.getString("fp" + i),
								requestShop.getString("fd" + i));
						weapon[i].create(requestShop.getString("wn" + i),
								requestShop.getString("wc" + i),
								requestShop.getString("wp" + i),
								requestShop.getString("wd" + i));
						armor[i].create(requestShop.getString("an" + i),
								requestShop.getString("ac" + i),
								requestShop.getString("ap" + i),
								requestShop.getString("ad" + i));
						ds[i].create(requestShop.getString("dn" + i),
								requestShop.getString("dc" + i),
								requestShop.getString("dp" + i),
								requestShop.getString("dd" + i));
						os[i].create(requestShop.getString("on" + i),
								requestShop.getString("oc" + i),
								requestShop.getString("op" + i),
								requestShop.getString("od" + i));
					}

					message = "success";
					return true;

					// Set error message and return false.
				} else {
					message = requestShop.getString("message");
					return false;
				}

				// Off chance that some weird shit happens
			} catch (JSONException e) {
				// Something went wrong - typically JSON value doesn't exist
				// (success).
				message = "An error occured. Please try again later.";
				return false;
			}

		}

		@Override
		protected void onPostExecute(final Boolean success) {
			// dont know what to put here
		}

		@Override
		protected void onCancelled() {
			// on cancel
		}
	}

	public class getMoney extends AsyncTask<Void, Void, Boolean> {

		private final String username;
		private final String token;
		public String gold;
		public String message;

		getMoney(String username, String token) {
			this.username = username;
			this.token = token;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// Query the login script with their entered username/password
			List<NameValuePair> postParams = new ArrayList<NameValuePair>(2);
			postParams.add(new BasicNameValuePair("user", username));
			postParams.add(new BasicNameValuePair("token", token));

			JSONObject getMoney = JSONfunctions
					.getJSONfromURL(
							"http://proj-309-R12.cs.iastate.edu/functions/shop/getMoney.php",
							postParams);

			// Try and check if it succeeded
			try {
				String success = getMoney.getString("success");

				// Return true on success
				if (success.equals("1")) {
					gold = getMoney.getString("gold");

					message = "success";
					return true;

					// Set error message and return false.
				} else {
					message = getMoney.getString("message");
					return false;
				}

				// Off chance that some weird shit happens
			} catch (JSONException e) {
				// Something went wrong - typically JSON value doesn't exist
				// (success).
				message = "An error occured. Please try again later.";
				return false;
			}

		}

		@Override
		protected void onPostExecute(final Boolean success) {

		}

		@Override
		protected void onCancelled() {
			//
		}
	}

	// TODO - This needs to be from server, not with PHP
	public class spendMoney extends AsyncTask<Void, Void, Boolean> {

		private final String username;
		private final String token;
		public String gold;
		public String message;
		private final String cost;

		spendMoney(String username, String token, String cost) {
			this.username = username;
			this.token = token;
			this.cost = cost;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// Query the login script with their entered username/password
			List<NameValuePair> postParams = new ArrayList<NameValuePair>(3);
			postParams.add(new BasicNameValuePair("user", username));
			postParams.add(new BasicNameValuePair("token", token));
			postParams.add(new BasicNameValuePair("cost", cost));

			JSONObject spendMoney = JSONfunctions
					.getJSONfromURL(
							"http://proj-309-R12.cs.iastate.edu/functions/shop/spendMoney.php",
							postParams);

			// Try and check if it succeeded
			try {
				String success = spendMoney.getString("success");

				// Return true on success
				if (success.equals("1")) {
					gold = spendMoney.getString("gold");

					message = "success";
					return true;

					// Set error message and return false.
				} else {
					message = spendMoney.getString("message");
					return false;
				}

				// Off chance that some weird shit happens
			} catch (JSONException e) {
				// Something went wrong - typically JSON value doesn't exist
				// (success).
				message = "An error occured. Please try again later.";
				return false;
			}

		}

		@Override
		protected void onPostExecute(final Boolean success) {

		}

		@Override
		protected void onCancelled() {
			//
		}
	}

	// TODO - this adds the item to the player table so that we know they own
	// this item
	public class useItem extends AsyncTask<Void, Void, Boolean> {

		private final String username;
		private final String token;
		private final String id;
		public String message;
		public boolean success;

		// Instantiate task
		useItem(String username, String token, String id) {
			this.username = username;
			this.token = token;
			this.id = id;

			message = "";
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			List<NameValuePair> postParams = new ArrayList<NameValuePair>(3);
			postParams.add(new BasicNameValuePair("username", username));
			postParams.add(new BasicNameValuePair("token", token));
			postParams.add(new BasicNameValuePair("itemId", id));

			// change to shop, not get friends
			JSONObject useItem = JSONfunctions
					.getJSONfromURL(
							"http://proj-309-R12.cs.iastate.edu/functions/shop/useItem.php",
							postParams);

			// Try and check if it succeeded
			try {
				String success = useItem.getString("success");

				// Return true on success
				if (success.equals("1")) {

					message = "success";
					return true;

					// Set error message and return false.
				} else {
					message = useItem.getString("message");
					return false;
				}

				// Off chance that some weird shit happens
			} catch (JSONException e) {
				// Something went wrong - typically JSON value doesn't exist
				// (success).
				message = "An error occured. Please try again later.";
				return false;
			}

		}

		@Override
		protected void onPostExecute(final Boolean success) {
			// dont know what to put here
		}

		@Override
		protected void onCancelled() {
			// on cancel
		}
	}
	
	// TODO - this adds the health for food or armor to the player
		public class addHealth extends AsyncTask<Void, Void, Boolean> {

			private final String username;
			private final String token;
			private final String health;
			public String message;
			public boolean success;

			// Instantiate task
			addHealth(String username, String token, String health) {
				this.username = username;
				this.token = token;
				this.health = health;

				message = "";
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				List<NameValuePair> postParams = new ArrayList<NameValuePair>(3);
				postParams.add(new BasicNameValuePair("username", username));
				postParams.add(new BasicNameValuePair("token", token));
				postParams.add(new BasicNameValuePair("health", health));

				// change to shop, not get friends
				JSONObject useItem = JSONfunctions
						.getJSONfromURL(
								"http://proj-309-R12.cs.iastate.edu/functions/shop/addHealth.php",
								postParams);

				// Try and check if it succeeded
				try {
					String success = useItem.getString("success");

					// Return true on success
					if (success.equals("1")) {

						message = "success";
						return true;

						// Set error message and return false.
					} else {
						message = useItem.getString("message");
						return false;
					}

					// Off chance that some weird shit happens
				} catch (JSONException e) {
					// Something went wrong - typically JSON value doesn't exist
					// (success).
					message = "An error occured. Please try again later.";
					return false;
				}

			}

			@Override
			protected void onPostExecute(final Boolean success) {
				// dont know what to put here
			}

			@Override
			protected void onCancelled() {
				// on cancel
			}
		}
		
		// TODO - this adds the health for armor to the player
				public class incrHealth extends AsyncTask<Void, Void, Boolean> {

					private final String username;
					private final String token;
					private final String health;
					public String message;
					public boolean success;

					// Instantiate task
					incrHealth(String username, String token, String health) {
						this.username = username;
						this.token = token;
						this.health = health;

						message = "";
					}

					@Override
					protected Boolean doInBackground(Void... params) {
						List<NameValuePair> postParams = new ArrayList<NameValuePair>(3);
						postParams.add(new BasicNameValuePair("username", username));
						postParams.add(new BasicNameValuePair("token", token));
						postParams.add(new BasicNameValuePair("health", health));

						// change to shop, not get friends
						JSONObject useItem = JSONfunctions
								.getJSONfromURL(
										"http://proj-309-R12.cs.iastate.edu/functions/shop/incrHealth.php",
										postParams);

						// Try and check if it succeeded
						try {
							String success = useItem.getString("success");

							// Return true on success
							if (success.equals("1")) {

								message = "success";
								return true;

								// Set error message and return false.
							} else {
								message = useItem.getString("message");
								return false;
							}

							// Off chance that some weird shit happens
						} catch (JSONException e) {
							// Something went wrong - typically JSON value doesn't exist
							// (success).
							message = "An error occured. Please try again later.";
							return false;
						}

					}

					@Override
					protected void onPostExecute(final Boolean success) {
						// dont know what to put here
					}

					@Override
					protected void onCancelled() {
						// on cancel
					}
				}

}
