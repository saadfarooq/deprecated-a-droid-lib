package com.github.adroid.location;

import android.location.Location;
import android.os.Bundle;

public class AsyncGetAccurateLocation extends AsyncGetLocation {

	@Override
	public void onLocationChanged(Location location) {}

	@Override
	public void onProviderDisabled(String provider) {}

	@Override
	public void onProviderEnabled(String provider) {}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}

	@Override
	protected Location doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onPostExecute(Location result) {
		// TODO Auto-generated method stub
		
	}

}
