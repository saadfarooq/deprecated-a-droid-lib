/*******************************************************************************
 * Copyright 2012 Saad Farooq
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.github.adroid.location;

import java.util.List;

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
import com.github.adroid.async.AsyncCaller;
import com.github.adroid.async.AsyncStarting;

public class AsyncGetLocation extends AsyncTask<Void, Void, Object> implements LocationListener {
	
	private final static String TAG = "AsyncGetLocation";
	
	private Object caller;
	private int requestCode = 0;
	LocationManager mLocationManager;
	Location mLocation;
	
	public AsyncGetLocation(AsyncCaller caller) {
		this.caller = caller;
	}
	
	public AsyncGetLocation(AsyncCaller caller, int requestCode) {
		this.caller = caller;
		this.requestCode = requestCode;
	}
	
	@Override
	protected void onPreExecute() {
		// If the calling Activity implements the AsyncStarting interface
		if (AsyncStarting.class.isInstance(caller)) {
			// call its onBackgroundTaskStarted method
			((AsyncStarting) caller ).onBackgroundTaskStarted();
		}
		
		mLocationManager = (LocationManager) ((Context) caller).getSystemService(Service.LOCATION_SERVICE);
		
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
		((AsyncCaller) caller).onBackgroundTaskCompleted(requestCode, result);
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
