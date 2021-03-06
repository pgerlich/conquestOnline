package conquest.online;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
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
import android.widget.Toast;
import conquest.online.client.MovementClient;
import conquest.online.gameAssets.Items.Armor;
import conquest.online.gameAssets.Items.Food;
import conquest.online.gameAssets.Items.Stock;
import conquest.online.gameAssets.Items.Weapon;
import conquest.online.gameAssets.Structures.AbstractStructure;

public class ShopActivity extends ActionBarActivity {

	// Find all the buttons for the listeners here
	public int i = 0;
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
	public ImageButton sOne;
	public ImageButton sTwo;
	public ImageButton sThree;
	public ImageButton sFour;
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
	public TextView sOneInfo;
	public TextView sTwoInfo;
	public TextView sThreeInfo;
	public TextView sFourInfo;
	public TextView message;
	public TextView stats;
	public getShop shop;
	// public getMoney mon;

	private UserSession user;
	public int gold;
	Food[] food = new Food[4];
	Weapon[] weapon = new Weapon[4];
	Armor[] armor = new Armor[4];
	AbstractStructure[] ds = new AbstractStructure[4];
	AbstractStructure[] os = new AbstractStructure[4];
	Stock[] stock = new Stock[4];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);

		user = new UserSession(getApplicationContext());
		user.updateAllStats();
		updateShop();

	}

	public void updateShop() {
		shop = new getShop(user.getUser(), user.getToken());
		shop.execute((Void) null);

		// mon = new getMoney(user.getUser(), user.getToken());
		// mon.execute((Void) null);
	}

	public void createStore() {

		food = shop.food;
		weapon = shop.weapon;
		armor = shop.armor;
		ds = shop.ds;
		os = shop.os;
		stock = shop.stock;

		if (i == 0) {
			populateStore();
		}
		i++;
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
	// when necessary.
	public void populateStore() {

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
		sOne = (ImageButton) findViewById(R.id.s_one);
		sTwo = (ImageButton) findViewById(R.id.s_two);
		sThree = (ImageButton) findViewById(R.id.s_three);
		sFour = (ImageButton) findViewById(R.id.s_four);
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
		sOneInfo = (TextView) findViewById(R.id.s_one_info);
		sTwoInfo = (TextView) findViewById(R.id.s_two_info);
		sThreeInfo = (TextView) findViewById(R.id.s_three_info);
		sFourInfo = (TextView) findViewById(R.id.s_four_info);
		message = (TextView) findViewById(R.id.error_message);
		stats = (TextView) findViewById(R.id.stats);
		@SuppressWarnings("unused")
		String message;

		stats.setText("Health:" + user.getCurHealth() + "/"
				+ user.getMaxHealth() + ", Gold:" + user.getMoney()
				+ ", Attack:" + user.getAttack() + ", Armor:" + user.getArmor());

		setImage(food[0].getPic(), foodOne);
		//foodOne.setBackgroundResource(R.drawable.food);
		foodOneInfo.setText(food[0].getName() + "-" + food[0].getDescription()
				+ ":" + food[0].getCost());
		if (!checkBalance(food[0].getCost())) {
			foodOneInfo.setTextColor(Color.RED);
		}
		// setImage(food[1].getPic(), foodTwo);
		foodTwo.setBackgroundResource(R.drawable.food);
		foodTwoInfo.setText(food[1].getName() + "-" + food[1].getDescription()
				+ ":" + food[1].getCost());
		if (!checkBalance(food[1].getCost())) {
			foodTwoInfo.setTextColor(Color.RED);
		}
		// setImage(food[2].getPic(), foodThree);
		foodThree.setBackgroundResource(R.drawable.food);
		foodThreeInfo.setText(food[2].getName() + "-"
				+ food[2].getDescription() + ":" + food[2].getCost());
		if (!checkBalance(food[2].getCost())) {
			foodThreeInfo.setTextColor(Color.RED);
		}
		// setImage(food[3].getPic(), foodFour);
		foodFour.setBackgroundResource(R.drawable.food);
		foodFourInfo.setText(food[3].getName() + "-" + food[3].getDescription()
				+ ":" + food[3].getCost());
		if (!checkBalance(food[3].getCost())) {
			foodFourInfo.setTextColor(Color.RED);
		}
		// setImage(weapon[0].getPic(), weaponOne);
		weaponOne.setBackgroundResource(R.drawable.weapon);
		weaponOneInfo.setText(weapon[0].getName() + "-"
				+ weapon[0].getDescription() + ":" + weapon[0].getCost());
		if (!checkBalance(weapon[0].getCost())) {
			weaponOneInfo.setTextColor(Color.RED);
		}
		// setImage(weapon[1].getPic(), weaponTwo);
		weaponTwo.setBackgroundResource(R.drawable.weapon);
		weaponTwoInfo.setText(weapon[1].getName() + "-"
				+ weapon[1].getDescription() + ":" + weapon[1].getCost());
		if (!checkBalance(weapon[1].getCost())) {
			weaponTwoInfo.setTextColor(Color.RED);
		}
		// setImage(weapon[2].getPic(), weaponThree);
		weaponThree.setBackgroundResource(R.drawable.weapon);
		weaponThreeInfo.setText(weapon[2].getName() + "-"
				+ weapon[2].getDescription() + ":" + weapon[2].getCost());
		if (!checkBalance(weapon[2].getCost())) {
			weaponThreeInfo.setTextColor(Color.RED);
		}
		// setImage(weapon[3].getPic(), weaponFour);
		weaponFour.setBackgroundResource(R.drawable.weapon);
		weaponFourInfo.setText(weapon[3].getName() + "-"
				+ weapon[3].getDescription() + ":" + weapon[3].getCost());
		if (!checkBalance(weapon[3].getCost())) {
			weaponFourInfo.setTextColor(Color.RED);
		}
		// setImage(armor[0].getPic(), armorOne);
		armorOne.setBackgroundResource(R.drawable.armor);
		armorOneInfo.setText(armor[0].getName() + "-"
				+ armor[0].getDescription() + ":" + armor[0].getCost());
		if (!checkBalance(armor[0].getCost())) {
			armorOneInfo.setTextColor(Color.RED);
		}
		// setImage(armor[1].getPic(), armorTwo);
		armorTwo.setBackgroundResource(R.drawable.armor);
		armorTwoInfo.setText(armor[1].getName() + "-"
				+ armor[1].getDescription() + ":" + armor[1].getCost());
		if (!checkBalance(armor[1].getCost())) {
			armorTwoInfo.setTextColor(Color.RED);
		}
		// setImage(armor[2].getPic(), armorThree);
		armorThree.setBackgroundResource(R.drawable.armor);
		armorThreeInfo.setText(armor[2].getName() + "-"
				+ armor[2].getDescription() + ":" + armor[2].getCost());
		if (!checkBalance(armor[2].getCost())) {
			armorThreeInfo.setTextColor(Color.RED);
		}
		// setImage(armor[3].getPic(), armorFour);
		armorFour.setBackgroundResource(R.drawable.armor);
		armorFourInfo.setText(armor[3].getName() + "-"
				+ armor[3].getDescription() + ":" + armor[3].getCost());
		if (!checkBalance(armor[3].getCost())) {
			armorFourInfo.setTextColor(Color.RED);
		}
		// setImage(ds[0].getPic(), dsOne);
		dsOne.setBackgroundResource(R.drawable.wall);
		dsOneInfo.setText(ds[0].getName() + "-" + ds[0].getDes() + ":"
				+ ds[0].getCost());
		if (!checkBalance(ds[0].getCost())) {
			dsOneInfo.setTextColor(Color.RED);
		}
		// setImage(ds[1].getPic(), dsTwo);
		dsTwo.setBackgroundResource(R.drawable.wall);
		dsTwoInfo.setText(ds[1].getName() + "-" + ds[1].getDes() + ":"
				+ ds[1].getCost());
		if (!checkBalance(ds[1].getCost())) {
			dsTwoInfo.setTextColor(Color.RED);
		}
		// setImage(ds[2].getPic(), dsThree);
		dsThree.setBackgroundResource(R.drawable.wall);
		dsThreeInfo.setText(ds[2].getName() + "-" + ds[2].getDes() + ":"
				+ ds[2].getCost());
		if (!checkBalance(ds[2].getCost())) {
			dsThreeInfo.setTextColor(Color.RED);
		}
		// setImage(ds[3].getPic(), dsFour);
		dsFour.setBackgroundResource(R.drawable.wall);
		dsFourInfo.setText(ds[3].getName() + "-" + ds[3].getDes() + ":"
				+ ds[3].getCost());
		if (!checkBalance(ds[3].getCost())) {
			dsFourInfo.setTextColor(Color.RED);
		}
		// setImage(os[0].getPic(), osOne);
		osOne.setBackgroundResource(R.drawable.turret);
		osOneInfo.setText(os[0].getName() + "-" + os[0].getDes() + ":"
				+ os[0].getCost());
		if (!checkBalance(os[0].getCost())) {
			osOneInfo.setTextColor(Color.RED);
		}
		// setImage(os[1].getPic(), osTwo);
		osTwo.setBackgroundResource(R.drawable.turret);
		osTwoInfo.setText(os[1].getName() + "-" + os[1].getDes() + ":"
				+ os[1].getCost());
		if (!checkBalance(os[1].getCost())) {
			osTwoInfo.setTextColor(Color.RED);
		}
		// setImage(os[2].getPic(), osThree);
		osThree.setBackgroundResource(R.drawable.turret);
		osThreeInfo.setText(os[2].getName() + "-" + os[2].getDes() + ":"
				+ os[2].getCost());
		if (!checkBalance(os[2].getCost())) {
			osThreeInfo.setTextColor(Color.RED);
		}
		// setImage(os[3].getPic(), osFour);
		osFour.setBackgroundResource(R.drawable.turret);
		osFourInfo.setText(os[3].getName() + "-" + os[3].getDes() + ":"
				+ os[3].getCost());
		if (!checkBalance(os[3].getCost())) {
			osFourInfo.setTextColor(Color.RED);
		}
		// setImage(stock[0].getPic(), sOne);
		sOne.setBackgroundResource(R.drawable.stock);
		sOneInfo.setText(stock[0].getName() + "-" + stock[0].getDescription()
				+ ":" + stock[0].getCost());
		if (!checkBalance(stock[0].getCost())) {
			sOneInfo.setTextColor(Color.RED);
		}
		// setImage(stock[1].getPic(), sTwo);
		sTwo.setBackgroundResource(R.drawable.stock);
		sTwoInfo.setText(stock[1].getName() + "-" + stock[1].getDescription()
				+ ":" + stock[1].getCost());
		if (!checkBalance(stock[1].getCost())) {
			sTwoInfo.setTextColor(Color.RED);
		}
		// setImage(stock[2].getPic(), sThree);
		sThree.setBackgroundResource(R.drawable.stock);
		sThreeInfo.setText(stock[2].getName() + "-" + stock[2].getDescription()
				+ ":" + stock[2].getCost());
		if (!checkBalance(stock[2].getCost())) {
			sThreeInfo.setTextColor(Color.RED);
		}
		//setImage(stock[3].getPic(), sFour);
		sFour.setBackgroundResource(R.drawable.stock);
		sFourInfo.setText(stock[3].getName() + "-" + stock[3].getDescription()
				+ ":" + stock[3].getCost());
		if (!checkBalance(stock[3].getCost())) {
			sFourInfo.setTextColor(Color.RED);
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
		
		URL url;
		String location = "http://proj-309-R12.cs.iastate.edu/images/shop/" + pic;
		try {
			url = new URL(location);
			Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			view.setImageBitmap(bmp);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		// TODO - THIS IS NEXT!! WOOHOOO
		// Drawable d = Drawable.createFromPath(pic);
		// view.setImageResource(R.drawable.placeholder);

		// try {
		// d = Drawable.createFromStream(getAssets().open(pic), null);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

//		AssetManager man = getAssets();
//		InputStream open = null;
//		try {
//			open = man.open(pic);
//			Bitmap bitmap = BitmapFactory.decodeStream(open);
//			// Assign the bitmap to an ImageView in this layout
//			view.setImageBitmap(bitmap);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (open != null) {
//				try {
//					open.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}

	public void fixStats() {
		stats.setText("Health:" + user.getCurHealth() + "/"
				+ user.getMaxHealth() + ", Gold:" + user.getMoney()
				+ ", Attack:" + user.getAttack() + ", Armor:" + user.getArmor());
	}

	/**
	 * This method is to create all the button listeners for the shop
	 */
	public void createListeners() {
		// every button is doing the right thing when clicked.
		@SuppressWarnings("unused")
		boolean space = true;

		foodOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(food[0].getCost())) {
					toast("working");
					spendMoney(food[0].getCost());
					int health = food[0].getHealth();
					user.adjStats(health, 0, 0);
					
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		foodTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(food[1].getCost())) {
					toast("working");
					spendMoney(food[1].getCost());
					int health = food[1].getHealth();
					user.adjStats(health, 0, 0);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		foodThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(food[2].getCost())) {
					toast("working");
					spendMoney(food[2].getCost());
					int health = food[2].getHealth();
					user.adjStats(health, 0, 0);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		foodFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(food[3].getCost())) {
					toast("working");
					spendMoney(food[3].getCost());
					int health = food[3].getHealth();
					user.adjStats(health, 0, 0);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		weaponOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"inventories");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(weapon[0].getCost()) && !weapon[0].own()
						&& slot.location != null) {
					toast("working");
					spendMoney(weapon[0].getCost());

					weapon[0].use();
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							weapon[0].getId(), 1, slot.location, slot.pId);
					use.execute((Void) null);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		weaponTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"inventories");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(weapon[1].getCost()) && !weapon[1].own()
						&& slot.location != null) {
					toast("working");
					spendMoney(weapon[1].getCost());

					weapon[1].use();
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							weapon[1].getId(), 1, slot.location, slot.pId);
					use.execute((Void) null);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		weaponThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"inventories");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(weapon[2].getCost()) && !weapon[2].own()
						&& slot.location != null) {
					toast("working");
					spendMoney(weapon[2].getCost());

					weapon[2].use();
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							weapon[2].getId(), 1, slot.location, slot.pId);
					use.execute((Void) null);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		weaponFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"inventories");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(weapon[3].getCost()) && !weapon[3].own()
						&& slot.location != null) {
					toast("working");
					spendMoney(weapon[3].getCost());

					weapon[3].use();
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							weapon[3].getId(), 1, slot.location, slot.pId);
					use.execute((Void) null);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		armorOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"inventories");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(armor[0].getCost()) && !armor[0].own()
						&& slot.location != null) {
					toast("working");
					spendMoney(armor[0].getCost());

					armor[0].use();
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							armor[0].getId(), 1, slot.location, slot.pId);
					use.execute((Void) null);

					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		armorTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"inventories");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(armor[1].getCost()) && !armor[1].own()
						&& slot.location != null) {
					toast("working");
					spendMoney(armor[1].getCost());

					armor[1].use();
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							armor[1].getId(), 1, slot.location, slot.pId);
					use.execute((Void) null);

					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		armorThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"inventories");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(armor[2].getCost()) && !armor[2].own()
						&& slot.location != null) {
					toast("working");
					spendMoney(armor[2].getCost());

					armor[2].use();
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							armor[2].getId(), 1, slot.location, slot.pId);
					use.execute((Void) null);

					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		armorFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"inventories");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(armor[3].getCost()) && !armor[3].own()
						&& slot.location != null) {
					toast("working");
					spendMoney(armor[3].getCost());

					armor[3].use();
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							armor[3].getId(), 1, slot.location, slot.pId);
					use.execute((Void) null);

					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		// need to add check to see if user owns a property or not (
		dsOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"chests");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(ds[0].getCost()) && slot.location != null) {
					toast("working");
					spendMoney(ds[0].getCost());
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							ds[0].getId(), 0, slot.location, slot.pId);
					use.execute((Void) null);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		dsTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"chests");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(ds[1].getCost()) && slot.location != null) {
					toast("working");
					spendMoney(ds[1].getCost());
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							ds[1].getId(), 0, slot.location, slot.pId);
					use.execute((Void) null);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		dsThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"chests");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(ds[2].getCost()) && slot.location != null) {
					toast("working");
					spendMoney(ds[2].getCost());
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							ds[2].getId(), 0, slot.location, slot.pId);
					use.execute((Void) null);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		dsFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"chests");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(ds[3].getCost()) && slot.location != null) {
					toast("working");
					spendMoney(ds[3].getCost());
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							ds[3].getId(), 0, slot.location, slot.pId);
					use.execute((Void) null);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		osOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"chests");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(os[0].getCost()) && slot.location != null) {
					toast("working");
					spendMoney(os[0].getCost());
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							os[0].getId(), 0, slot.location, slot.pId);
					use.execute((Void) null);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		osTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"chests");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(os[1].getCost()) && slot.location != null) {
					toast("working");
					spendMoney(os[1].getCost());
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							os[1].getId(), 0, slot.location, slot.pId);
					use.execute((Void) null);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		osThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"chests");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(os[2].getCost()) && slot.location != null) {
					toast("working");
					spendMoney(os[2].getCost());
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							os[2].getId(), 0, slot.location, slot.pId);
					use.execute((Void) null);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		osFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getInv slot = new getInv(user.getUser(), user.getToken(),
						"chests");
				slot.execute((Void) null);
				sleepThread();
				message.setVisibility(View.GONE);
				if (checkBalance(os[3].getCost()) && slot.location != null) {
					toast("working");
					spendMoney(os[3].getCost());
					buyItem use = new buyItem(user.getUser(), user.getToken(),
							os[3].getId(), 0, slot.location, slot.pId);
					use.execute((Void) null);
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		sOne.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(stock[0].getCost())) {
					toast("working");
					spendMoney(stock[0].getCost());
					addGPM(stock[0].getGpm());
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		sTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(stock[1].getCost())) {
					toast("working");
					spendMoney(stock[1].getCost());
					addGPM(stock[1].getGpm());
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		sThree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(stock[2].getCost())) {
					toast("working");
					spendMoney(stock[2].getCost());
					addGPM(stock[2].getGpm());
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});

		sFour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
				if (checkBalance(stock[3].getCost())) {
					toast("working");
					spendMoney(stock[3].getCost());
					addGPM(stock[3].getGpm());
					updateShop();
					toast("Successfully purchased!");
				} else {
					message.setVisibility(View.VISIBLE);
					toast("Sorry, cannot purchase item at this time");
				}

			}
		});
	}

	public boolean checkBalance(int i) {
		// if balance is enough return true, else return false

		if (i <= user.getMoney()) {
			// has enough gold to buy.
			return true;
		} else {
			return false;
		}
	}

	public void spendMoney(int i) {
		// take money away from user
		// make another AsyncTask? maybe idk
		adjStats spend = new adjStats(user.getUser(), user.getToken(), "money",
				i);
		spend.execute((Void) null);
	}

	public void sleepThread() {
		toast("sleeping");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Displays the message on the screen
	 * 
	 * @param message
	 */
	public void toast(String message) {
		// Toast an error message if it exists. Will close and leave page if it
		// doesn't
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	public void addGPM(int d) {
		adjStats gpm = new adjStats(user.getUser(), user.getToken(), "gpm", d);
		gpm.execute((Void) null);
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
		public Food[] food = new Food[4];
		public Weapon[] weapon = new Weapon[4];
		public Armor[] armor = new Armor[4];
		public AbstractStructure[] ds = new AbstractStructure[4];
		public AbstractStructure[] os = new AbstractStructure[4];
		public Stock[] stock = new Stock[4];

		// Instantiate task
		getShop(String username, String token) {
			this.username = username;
			this.token = token;

			message = "";
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			List<NameValuePair> postParams = new ArrayList<NameValuePair>(2);
			postParams.add(new BasicNameValuePair("user", username));
			postParams.add(new BasicNameValuePair("token", token));

			// change to shop, not get friends
			JSONObject requestShop = JSONfunctions
					.getJSONfromURL(
							"http://proj-309-R12.cs.iastate.edu/functions/shop/getItems.php",
							postParams);

			while (requestShop == null) {
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
				}
			}

			// Try and check if it succeeded
			try {
				String success = requestShop.getString("success");

				// Return true on success
				if (success.equals("1")) {

					for (int i = 0; i < 4; i++) {

						food[i] = new Food();
						String name = requestShop.getString("fn" + i);
						int c = Integer.parseInt(requestShop
								.getString("fc" + i));
						String pic = requestShop.getString("fp" + i);
						String des = requestShop.getString("fd" + i);
						int id = Integer.parseInt(requestShop.getString("fi"
								+ i));
						int health = requestShop.getInt("fh" + i);
						food[i].create(name, c, pic, des, id);
						food[i].setHealth(health);

						weapon[i] = new Weapon();
						name = requestShop.getString("wn" + i);
						c = Integer.parseInt(requestShop.getString("wc" + i));
						pic = requestShop.getString("wp" + i);
						des = requestShop.getString("wd" + i);
						id = Integer.parseInt(requestShop.getString("wi" + i));
						weapon[i].create(name, c, pic, des, id);

						armor[i] = new Armor();
						name = requestShop.getString("an" + i);
						c = Integer.parseInt(requestShop.getString("ac" + i));
						pic = requestShop.getString("ap" + i);
						des = requestShop.getString("ad" + i);
						id = Integer.parseInt(requestShop.getString("ai" + i));
						armor[i].create(name, c, pic, des, id);

						ds[i] = new AbstractStructure(
								Integer.parseInt(requestShop
										.getString("di" + i)),
								requestShop.getString("dp" + i));
						name = requestShop.getString("dn" + i);
						c = Integer.parseInt(requestShop.getString("dc" + i));
						pic = requestShop.getString("dp" + i);
						des = requestShop.getString("dd" + i);
						id = Integer.parseInt(requestShop.getString("di" + i));
						ds[i].create(name, c, pic, des, id);

						os[i] = new AbstractStructure(
								Integer.parseInt(requestShop
										.getString("oi" + i)),
								requestShop.getString("op" + i));
						name = requestShop.getString("on" + i);
						c = Integer.parseInt(requestShop.getString("oc" + i));
						pic = requestShop.getString("op" + i);
						des = requestShop.getString("od" + i);
						id = Integer.parseInt(requestShop.getString("oi" + i));
						os[i].create(name, c, pic, des, id);

						stock[i] = new Stock();
						name = requestShop.getString("sn" + i);
						c = requestShop.getInt("sc" + i);
						pic = requestShop.getString("sp" + i);
						int gpm = requestShop.getInt("sg" + i);
						des = requestShop.getString("sd" + i);
						id = Integer.parseInt(requestShop.getString("si" + i));
						stock[i].create(name, c, pic, des, id);
						stock[i].setGpm(gpm);
					}

					// Need to make sure that all arrays are made right
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
			toast("Successfully loaded store");
			createStore();
		}

		@Override
		protected void onCancelled() {
			// on cancel
		}
	}

	public class getInv extends AsyncTask<Void, Void, Boolean> {
		private final String username;
		private final String token;
		public int pId;
		public String ioc;
		public String location;
		public String message;
		public boolean sleep;

		getInv(String username, String token, String ioc) {
			this.username = username;
			this.token = token;
			this.ioc = ioc;
			sleep = true;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			List<NameValuePair> postParams = new ArrayList<NameValuePair>(3);
			postParams.add(new BasicNameValuePair("user", username));
			postParams.add(new BasicNameValuePair("token", token));
			postParams.add(new BasicNameValuePair("ioc", ioc));

			JSONObject getSlot = JSONfunctions
					.getJSONfromURL(
							"http://proj-309-R12.cs.iastate.edu/functions/shop/getSlot.php",
							postParams);

			while (getSlot == null) {
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
				}
			}

			// Try and check if it succeeded
			try {
				String success = getSlot.getString("success");

				// Return true on success
				if (success.equals("1")) {
					for (int i = 1; i <= 10; i++) {
						if (ioc.equals("chests"))
							if (getSlot.getInt("struc" + i) == -1) {
								location = "struc" + i;
								pId = getSlot.getInt("propertyID");
								message = "success";
								return true;
							}
						if (ioc.equals("inventories"))
							if (getSlot.getInt("item" + i) == -1) {
								location = "item" + i;
								message = "success";
							}
					}

					message = "no open slots";
					return true;

					// Set error message and return false.
				} else {
					message = getSlot.getString("message");
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

	// this item THIS IS NEEDED FOR DS, and OS
	public class buyItem extends AsyncTask<Void, Void, Boolean> {

		private final String username;
		private final String token;
		private final int id;
		private final int ioc;
		private final int pId;
		public String location;
		public String message;
		public boolean success;

		// Instantiate task
		buyItem(String username, String token, int id, int ioc,
				String location, int pId) {
			this.username = username;
			this.token = token;
			this.id = id;
			this.ioc = ioc;
			this.pId = pId;
			this.location = location;

			message = "";
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			try {
				// userStructures table.
				MovementClient mc = new MovementClient();

				// Have to start this on a new thread so it stays open and
				// listends for responses
				new Thread(mc).start();

				if (ioc == 0)
					mc.putChest(username, token, id, pId, location);
				if (ioc == 1)
					mc.putInv(username, token, id, pId, location);

				mc.close();
			} catch (Exception e) {
				// Idk why this would happen - only happens if it couldn't
				// connect to server.
			}

			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			// Nothing needs to go here
		}

		@Override
		protected void onCancelled() {
			// Nothing needs to go here
		}
	}

	// The idea is that it uses PHP to get the stat value, adjust it here, and
	// then put it back directly into the server?
	public class adjStats extends AsyncTask<Void, Void, Boolean> {

		private final String username;
		private final String token;
		private final String attribute;
		private final int amount;
		private int attack;
		private int maxHealth;
		private int curHealth;
		private int armor;
		private int money;
		private int gpm;
		/*
		 * Dont use these here yet, maybe can in the future, not sure private
		 * int wins; private int loses; private int kills; private int deaths;
		 * private String guild; private double lat; private double lon; private
		 * int speed; private int stealth; private int tech; private int level;
		 * private int exp;
		 */
		public String message;
		public boolean success;

		// Instantiate task
		adjStats(String username, String token, String attribute, int amount) {
			this.username = username;
			this.token = token;
			this.attribute = attribute;
			this.amount = amount;

			message = "";
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			List<NameValuePair> postParams = new ArrayList<NameValuePair>(2);
			postParams.add(new BasicNameValuePair("user", username));
			postParams.add(new BasicNameValuePair("token", token));

			// change to shop, not get friends
			JSONObject getStats = JSONfunctions
					.getJSONfromURL(
							"http://proj-309-R12.cs.iastate.edu/functions/character/requestStats.php",
							postParams);
			while (getStats == null) {
				sleepThread();
			}

			try {

				String success = getStats.getString("success");

				// Return true on success
				if (success.equals("1")) {
					curHealth = getStats.getInt("curHealth");
					money = getStats.getInt("money");
					gpm = getStats.getInt("gpm");

					if (attribute.equals("money")) {
						money -= amount;
					}
					if (attribute.equals("curHealth")) {
						curHealth += amount;
					}
					if (attribute.equals("gpm")) {
						gpm += amount;
					}

					MovementClient mc = new MovementClient();

					// Have to start this on a new thread so it stays open and
					// listens for responses
					new Thread(mc).start();

					// Attempt to update the stats of the user to the character
					// table.
					mc.updateStats(username, token, curHealth, money, gpm);

					mc.close();

					user.updateAllStats();

					message = "success";
					return true;
				} else {
					toast("FAILED TO UPDATE STATS");
					message = getStats.getString("message");
					return false;
				}
			} catch (Exception e) {
				// Idk why this would happen - only happens if it couldn't
				// connect to server.
			}

			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			// Nothing needs to go here
			fixStats();
			toast("Changed: " + attribute + " By: " + amount);
		}

		@Override
		protected void onCancelled() {
			// Nothing needs to go here
		}
	}

}
