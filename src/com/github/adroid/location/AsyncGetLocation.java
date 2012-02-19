package com.github.adroid.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;

import com.github.adroid.async.AsyncCaller;

public abstract class AsyncGetLocation extends AsyncTask<Void, Void, Location> implements LocationListener {
	AsyncCaller caller;
	int requestCode = 0;
	int timeInHours = 0;
	
	AsyncGetLocation() { };
	AsyncGetLocation(AsyncCaller caller) { 
		this.caller = caller;
	};
	
	AsyncGetLocation(AsyncCaller caller, int requestCode) { 
		this.caller = caller;
		this.requestCode = requestCode;
	};
	
	AsyncGetLocation(AsyncCaller caller, int requestCode, int timeInHours) { 
		this.caller = caller;
		this.requestCode = requestCode;
		this.timeInHours = timeInHours;
	}
	@Override
	public abstract void onLocationChanged(Location location);
	
	@Override
	public abstract void onProviderDisabled(String provider);
	
	@Override
	public abstract void onProviderEnabled(String provider);
	
	@Override
	public abstract void onStatusChanged(String provider, int status, Bundle extras);
	
	@Override
	protected abstract Location doInBackground(Void... params);
	
	@Override
	protected abstract void onPreExecute();
	
	@Override
	protected abstract void onPostExecute(Location result);
	
	
	
	
}
