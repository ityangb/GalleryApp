/**
 *
 * @author sleepygarden
 *
 */
package edu.coppola.galleryapp;

import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.TokenPair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {
	
	Button login;
	Button go;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
			Singleton.startSingleton();
			Singleton.getInstance().initApi(this);
			
			login = (Button) findViewById(R.id.login_btn);
			go = (Button) findViewById(R.id.to_menu);
			
			
			
			login.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                if (Singleton.getInstance().isLoggedIn()) {
	                    Singleton.getInstance().logOut(LoginActivity.this);
	                    login.setText("Sign in to Dropbox");
	                } else {
	                    Singleton.getInstance().getApi().getSession().startAuthentication(LoginActivity.this);
	                }
	            }
	        });
			go.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(LoginActivity.this, MenuActivity.class);
					startActivity(i);
				}
			});
			
			
	}
	 @Override
	    protected void onResume() {
	        super.onResume();
	        AndroidAuthSession session = Singleton.getInstance().getApi().getSession();

	        // The next part must be inserted in the onResume() method of the
	        // activity from which session.startAuthentication() was called, so
	        // that Dropbox authentication completes properly.
	        if (session.authenticationSuccessful()) {
	            try {
	                // Mandatory call to complete the auth
	                session.finishAuthentication();

	                // Store it locally in our app for later use
	                TokenPair tokens = session.getAccessTokenPair();
	                Singleton.getInstance().storeKeys(tokens.key, tokens.secret, LoginActivity.this);
	                Singleton.getInstance().setLoggedIn(true);
	                Log.d("AUTH","A-OK!");
                    login.setText("Sign out from Dropbox");
                    go.setVisibility(View.VISIBLE);
                    

	            } catch (IllegalStateException e) {
	            	Log.e("AUTH","something went wrong");
	            }
	        }
	    }
	
	

}
