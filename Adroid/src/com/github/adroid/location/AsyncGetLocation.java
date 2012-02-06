

package com.github.adroid.location;

import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.github.adroid.Adroid;
import com.github.adroid.AsyncCallBack;
import com.github.adroid.AsyncStarting;

public class AsyncGetLocation extends AsyncTask<Void, Void, Object> implements LocationListener {
	
	private final static String TAG = "AsyncGetLocation";
	
	Object caller;
	LocationManager mLocationManager;
	Location mLocation;
	
	public AsyncGetLocation(Activity caller) {
		this.caller = caller;
	}
	
	@Override
	protected void onPreExecute() {
		mLocationManager = (LocationManager) ((Context) caller).getSystemService(Service.LOCATION_SERVICE);
		
		// If the calling Activity implements the AsyncStarting interface
		if (AsyncStarting.class.isInstance(caller)) {
			// call its onBackgroundTaskStarted method
			((AsyncStarting) caller ).onBackgroundTaskStarted();
		}
		// Get the list of providers and check each one for a last known location; return the first one found
		List<String> providers = mLocationManager.getProviders(new Criteria(), true);
		for (String provider : providers) {
			mLocation = mLocationManager.getLastKnownLocation(provider);
			
			if (mLocation != null) {
				return;
			}
		}
		mLocationManager.requestLocationUpdates(mLocationManager.getBestProvider(new Criteria(), true), 0 , 0 , this);
	}
	
	@Override
	protected Object doInBackground(Void... arg0) {
		
		while (mLocation == null ) {
			try {
				Thread.sleep(Adroid.LOCATION_LISTENER_SLEEP_TIME);
			} catch (InterruptedException e) {
				Log.e(TAG, e.getMessage(), e);
			}
		}
		
		return mLocation;
	}
	
	@Override
	protected void onPostExecute(Object result) {
		((AsyncCallBack) caller).onBackgroundTaskCompleted(Adroid.GET_LOCATION, result);
	}

	@Override
	public void onLocationChanged(Location location) {
		mLocation = location;
		mLocationManager.removeUpdates(this);
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
