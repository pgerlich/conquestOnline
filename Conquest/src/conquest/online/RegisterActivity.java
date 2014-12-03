package conquest.online;

import conquest.client.classes.RegisterRequest;
import conquest.online.client.MovementClient;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	
	/**
	 * When user is registering and they select the Spy, this sets their class type to Spy
	 */
	public void makeSpy(View view) {
		TextView t = (TextView) findViewById(R.id.classChosen);
		t.setText("Spy");
		
	}
	
	/**
	 * When the user is registering and they select the Engineer, this method is called and sets their class to Engineer
	 */
	public void makeEngineer(View view) {
		TextView t = (TextView) findViewById(R.id.classChosen);
		t.setText("Engineer");
	}
	
	/**
	 * When the user is registering and they select the Soldier, this method is called and sets their class to soldier
	 */
	public void makeSoldier(View view) {
		TextView t = (TextView) findViewById(R.id.classChosen);
		t.setText("Soldier");
	}
	
	/**
	 * When you click register, it takes all of the info you typed - compares passwords,
	 * and kicks the information off to an asynchronous background task to register you.
	 * @param view
	 */
	public void registerUser(View view){
		EditText userEdit = (EditText) findViewById(R.id.username);
		EditText passEdit = (EditText) findViewById(R.id.password);
		EditText confirmEdit = (EditText) findViewById(R.id.confirm);
		EditText emailEdit = (EditText) findViewById(R.id.email);
		TextView cChosen = (TextView) findViewById(R.id.classChosen);
		
		String classChosen = cChosen.getText().toString();
		String username = userEdit.getText().toString();
		String password = passEdit.getText().toString();
		String confirmPass = confirmEdit.getText().toString();
		String email = emailEdit.getText().toString();
		
		
		//Check passwords match
		if (password.equals(confirmPass) && !classChosen.equals("Choose Class")) {
			//Execute background process - try and register account
			RegistrationProcess register = new RegistrationProcess(username, password, email, classChosen);
			register.execute((Void) null);

			toast(register.message);

		} else if (!password.equals(confirmPass)) {
			toast("Passwords did not match!");
		} else {
			toast("Please chooose a class");
		}
		
	}
	
	/**
	 * Displays message on screen
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
	
    /**
     * Call to go back home after successful registration.. 
     * FIXME: Change to go to map later and log then in?
     */
    public void goToMain() {
    	Intent main = new Intent(this, MainActivity.class);
    	startActivity(main);	
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
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_register,
					container, false);
			return rootView;
		}
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class RegistrationProcess extends AsyncTask<Void, Void, Boolean> {

		private final String email;
		private final String username;
		private final String password;
		private final String classChosen;
		public String message;
		public boolean success;

		//Instantiate task
		RegistrationProcess(String username, String password, String email, String classChosen) {
			this.username = username;
			this.password= password;
			this.email = email;
			this.classChosen= classChosen;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			
			try {
				MovementClient mc = new MovementClient();
			
				RegisterRequest register = new RegisterRequest();
				
				register.username = username;
				register.password = password;
				register.email = email;
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
				finish();
				goToMain();
			}
		}

		@Override
		protected void onCancelled() {
			//on cancel
		}
	}
}
