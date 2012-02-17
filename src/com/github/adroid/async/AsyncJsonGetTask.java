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
package com.github.adroid.async;

import java.util.List;

import org.apache.http.NameValuePair;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Manages getting the Json response for a webservice call.
 * Takes three parameters; a String defining the method call to be made on the webservices, 
 * a HashMap of parameters to pass to the webservice and a class variable defining
 * the class of Java object that the result is to be converted to which is use by Gson.
 * The result is parsed as Json and returned to the calling activity through the onBackgroundTaskCompleted()
 * method defined in the AsyncCaller interface.
 * @author Saad Farooq (sfarooq@andrew.cmu.edu)
 *
 */
public class AsyncJsonGetTask extends AsyncTask<Object, Void, Object> {
	
	final static String TAG = "AsyncJsonGetTask";
	private AsyncCaller activity;
	private int requestCode = 0;
	private boolean startingCallback;
	
	
	/**
	 * Calls a webservice in the background and gets Json response
	 * <b>Note:</b> Retrieving Java objects works only with ASP.net services currently 
	 * 
	 * <h2>For the execute command</h2>
	 * <b>First parameter:</b> webservice call name, such as : <b>GetOrders</b> </br>
	 * <b>Second parameter:</b> List of name value pairs, sample code: </br></br>
	 * List{@literal <NameValuePair>} params = new ArrayList{@literal <NameValuePair>}(); </br>
	 * 		params.add(new BasicNameValuePair("name", value));</br>
	 * 
	 * @param activity activity which extends AsyncCaller abstract class
	 */
	public AsyncJsonGetTask (AsyncCaller activity) {
		this.activity = activity;
		startingCallback = AsyncStarting.class.isInstance((Object) activity);
	}
	
	/**
	 * Calls a webservice in the background and gets Json response
	 * <b>Note:</b> Retrieving Java objects works only with ASP.net services currently 
	 * 
	 * <h2>For the execute command</h2>
	 * <b>First parameter:</b> webservice call name, such as : <b>GetOrders</b> </br>
	 * <b>Second parameter:</b> List of name value pairs, sample code: </br></br>
	 * List{@literal <NameValuePair>} params = new ArrayList{@literal <NameValuePair>}(); </br>
	 * 		params.add(new BasicNameValuePair("name", value));</br>
	 * 
	 * @param activity
	 * @param requestCode
	 */
	public AsyncJsonGetTask(AsyncCaller activity, int requestCode) {
		this.activity = activity;
		this.requestCode = requestCode;
		startingCallback = AsyncStarting.class.isInstance((Object) activity);
	}

	@Override
	protected void onPreExecute() {
		if (startingCallback) {
			((AsyncStarting) activity).onBackgroundTaskStarted();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Object doInBackground(Object... params) {
		
		String response;
		
		if (params.length > 1) {
			response = WebServices.httpGet((String) params[0], (List<NameValuePair>) params[1]);
		} else {
			response = WebServices.httpGet((String) params[0], null ); 
		}
		
		if (params.length == 3) {
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse(response);

			Object result = new Gson().fromJson(jsonObject.get("d"), (Class<?>) params[2]);
			
			return result; 
		} else {
			return response;
		}
	}
	
	@Override
	protected void onPostExecute(Object result) {
		activity.onBackgroundTaskCompleted(requestCode, result);
	}
}
