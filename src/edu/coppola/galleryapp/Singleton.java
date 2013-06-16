/**
 *
 * @author sleepygarden
 *
 */
package edu.coppola.galleryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.TokenPair;

public class Singleton {

	public static Singleton instance;

	DropboxAPI<AndroidAuthSession> mApi;
	private boolean mLoggedIn;

	public static void startSingleton() {
		instance = new Singleton();
		ImageCache.initCache();
	}

	public static Singleton getInstance() {
		return instance;
	}

	public void initApi(Context ctx) {
		AndroidAuthSession session = buildSession(ctx);
		mApi = new DropboxAPI<AndroidAuthSession>(session);

	}

	public boolean isLoggedIn() {
		return mLoggedIn;
	}

	public DropboxAPI<AndroidAuthSession> getApi() {
		return mApi;
	}

	public void setApi(DropboxAPI<AndroidAuthSession> api) {
		mApi = api;
	}

	public void logOut(Context ctx) {
		// Remove credentials from the session
		mApi.getSession().unlink();

		// Clear our stored keys
		clearKeys(ctx);
		// Change UI state to display logged out version
		mLoggedIn = false;
	}

	private String[] getKeys(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(Constants.ACCOUNT_PREFS_NAME, 0);
		String key = prefs.getString(Constants.ACCESS_KEY_NAME, null);
		String secret = prefs.getString(Constants.ACCESS_SECRET_NAME, null);
		if (key != null && secret != null) {
			String[] ret = new String[2];
			ret[0] = key;
			ret[1] = secret;
			return ret;
		} else {
			return null;
		}
	}

	 void storeKeys(String key, String secret, Context ctx) {
	        // Save the access key for later
	        SharedPreferences prefs = ctx.getSharedPreferences(Constants.ACCOUNT_PREFS_NAME, 0);
	        Editor edit = prefs.edit();
	        edit.putString(Constants.ACCESS_KEY_NAME, key);
	        edit.putString(Constants.ACCESS_SECRET_NAME, secret);
	        edit.commit();
	    }
	 
	 public void setLoggedIn(boolean loggedIn) {
		 mLoggedIn=loggedIn;
	 }
	
	private void clearKeys(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(Constants.ACCOUNT_PREFS_NAME, 0);
		Editor edit = prefs.edit();
		edit.clear();
		edit.commit();
	}

	private AndroidAuthSession buildSession(Context ctx) {
		AppKeyPair appKeyPair = new AppKeyPair(Constants.APP_KEY,
				Constants.APP_SECRET);
		AndroidAuthSession session;

		String[] stored = getKeys(ctx);
		if (stored != null) {
			AccessTokenPair accessToken = new AccessTokenPair(stored[0],
					stored[1]);
			session = new AndroidAuthSession(appKeyPair, Constants.ACCESS_TYPE,
					accessToken);
		} else {
			session = new AndroidAuthSession(appKeyPair, Constants.ACCESS_TYPE);
		}

		return session;
	}
}
