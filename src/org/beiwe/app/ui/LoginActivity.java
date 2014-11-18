package org.beiwe.app.ui;

import org.beiwe.app.R;
import org.beiwe.app.RunningBackgroundProcessActivity;
import org.beiwe.app.session.LoginManager;
import org.beiwe.app.survey.TextFieldKeyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


//TODO: Eli.  update doc.
/**Ui presents an interface-less loading activity to the user.  Moves user to the correct activity based on application state.
 * Logs the User into the app, handles correct loading timing of various app components.
 * Helper class {@link LoginManager.java}
 * @authors Dor Samet, Eli Jones */

public class LoginActivity extends RunningBackgroundProcessActivity {
	//Note: LoginActivity cannot be a SessionActivity (without some stupid hacks)
	//because SessionActivities trigger a LoginActivity, which would cause an infinite loop.
	// In addition the LoginActivity should never the only activity on the activity stack,
	// so the existence of a BackgroundProcess is assured.
	
	private EditText password;
	private Context appContext;
	
	@Override
	/**The login activity Always prompts the user for the password. */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		appContext = getApplicationContext();

		setContentView(R.layout.activity_login);
		password = (EditText) findViewById(R.id.editText2);
		
		TextFieldKeyboard textFieldKeyboard = new TextFieldKeyboard(appContext);
		textFieldKeyboard.makeKeyboardBehave(password);
	}
	
	
	/**The Login Button
	 * IF session is logged in (value in shared prefs), keep the session logged in.
	 * IF session is not logged in, wait for user input.
	 * @param view*/
	public void loginButton(View view) {		
		if ( LoginManager.checkPassword( password.getText().toString() ) ) {
			LoginManager.loginOrRefreshLogin();
			finish();
			return;
		}
		AlertsManager.showAlert("Incorrect password", this);
	}
	
	
	/**Move user to the forgot password activity.
	 * @param view */
	public void forgotPassword(View view) {
		startActivity( new Intent(appContext, ForgotPasswordActivity.class) );
		finish();
	}
	
	@Override
	public void onBackPressed() { }
}