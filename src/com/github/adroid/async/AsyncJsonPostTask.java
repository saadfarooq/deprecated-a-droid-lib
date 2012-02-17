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

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Manages getting the Json response for a webservice call.
 * Takes up to three parameters; a String defining the method call to be made on the webservices,
 * a Json string as the payload for the POST and a class variable defining
 * the class of Java object that the result is to be converted to (optional).
 * The result is parsed from Json and returned to the calling activity through the onBackgroundTaskCompleted()
 * method defined in the AsyncCaller interface.
 * @author Saad Farooq
 *
 */
public class AsyncJsonPostTask extends AsyncTask<Object, Void, Object> {
	
	final static String TAG = "AsyncJsonPostTask";
	private AsyncCaller activity;
	private int requestCode = 0;
	private boolean startingCallback;
		
	/**
	 * Perform a POST to a server and optionally convert response from Json string into a Java object
	 * <b>Note:</b> Retrieving Java objects works only with ASP.net services currently
	 * @param activity
	 */
	public AsyncJsonPostTask (AsyncCaller activity) {
		this.activity = activity;
		startingCallback = AsyncStarting.class.isInstance((Object) activity);
	}
	
	/**
	 * Perform a POST to a server and optionally convert response from Json string into a Java object
	 * <b>Note:</b> Retrieving Java objects works only with ASP.net services currently
	 * @param activity
	 */
	public AsyncJsonPostTask (AsyncCaller activity, int requestCode) {
		this.activity = activity;
		this.requestCode = requestCode;
	}
	
	@Override
	protected void onPreExecute() {
		if (startingCallback) {
			((AsyncStarting) activity).onBackgroundTaskStarted();
		}
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		
		String response = WebServices.httpPost((String)params[0], (String)params[1]);
	
		if (params.length == 3) {
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse(response);
			jsonObject = jsonObject.get("d").getAsJsonObject();
			
			Object result = new Gson().fromJson(jsonObject, (Class<?>) params[2]);
			
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
