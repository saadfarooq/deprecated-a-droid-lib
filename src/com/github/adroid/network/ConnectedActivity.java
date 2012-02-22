package com.github.adroid.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Superclass activity for Activities that require a network connection. The class registers and 
 * unregisters a connectivity broadcast receiver. An Alert is displayed if there is no 
 * connection and dismissed once connectivity is restored. 
 * 
 * @author Saad Farooq
 *
 */
public class ConnectedActivity extends Activity {
	
	private static final String TAG = "ConnectedActivity";
	private static final int TOAST_LENGTH = 100;
	ConnectivityManager connectivityManager;
	AlertDialog alert;
	
	 private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {	            
	            connectivityManager = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE );
	            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
	            
	            if ( activeNetInfo!= null && activeNetInfo.isConnectedOrConnecting() )
	            {
	              if (alert != null && alert.isShowing()) {
	            	  alert.dismiss();
	            	  Toast.makeText( context, "Active Network Type : " + connectivityManager.getActiveNetworkInfo().getTypeName() + ", "+ connectivityManager.getActiveNetworkInfo().getExtraInfo(), TOAST_LENGTH ).show();
	              }
	            } else {
//	            	Toast.makeText( context, "No active network", Toast.LENGTH_SHORT ).show();
	            	if (alert == null ) createAlert();
	            	
	            	if (!alert.isShowing()) alert.show();
	            }
	        }
	    };
	    
	@Override    
    protected void onResume() {
		super.onResume();
		Log.i(TAG, "Activity resumed. Registering connectivity receiver");
    	registerReceiver(mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	Log.i(TAG, "Activity paused. Unregistering connectivity receiver");
    	unregisterReceiver(mConnReceiver);
    }
    
    /**
     * Creates a non cancel-able alert asking user to turn connection on
     */
    private void createAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No network connection available. Please enable a network connection")
        .setCancelable(false);
        alert = builder.create();
    }
}
