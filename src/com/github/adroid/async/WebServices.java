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
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.util.Base64;
import android.util.Log;

public class WebServices {
	static final String TAG = "WebServices";
	
	public static final String BASE_URL = "";
	public static final String WS_USERNAME = "";
	public static String WS_PASSWORD = "";
	
	static final int CONNECTION_TIMEOUT = 1000;
	static final int SOCKET_TIMOUT = 2000;
	static DefaultHttpClient client = getThreadSafeClient(); 

	public static String httpGet(final String methodName, List<NameValuePair> params) {
		
		String methodURL = BASE_URL + "/"+methodName;
		
		HttpGet httpGet;
		
		if (params != null) {
			httpGet = new HttpGet(methodURL+"?"+URLEncodedUtils.format(params, "UTF-8"));
		} else {
			httpGet = new HttpGet(methodURL);
		}
		
		
		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader(HTTP.CONTENT_TYPE, "application/json");
		
		Log.d(TAG,"Setting up GET request params");
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIMEOUT);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		HttpConnectionParams.setSoTimeout(httpParameters, SOCKET_TIMOUT);
		//client.setParams(httpParameters);
		
		// Apparently this solves the hanging problem
		ConnManagerParams.setTimeout( httpParameters, 2000 ); 
		
		Log.d(TAG,"Setting GET request credentials");
		// BasicResponseHandler returns the response body as string
		BasicResponseHandler handler = new BasicResponseHandler();
		client.getCredentialsProvider().setCredentials(new AuthScope(null,-1), new UsernamePasswordCredentials(WS_USERNAME, WS_PASSWORD));
		
		String response = null;
		try {
			Log.d(TAG,"Executing get");
			response = client.execute(httpGet,handler);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(),e);
			e.printStackTrace();
		} 
		return response;	
	}


	public static String httpPost(String methodName, String payLoad) {
		HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMOUT);
        
        Log.d(TAG, "Setting request credentials");
        // Apparently this doesn't work with POST Request
        //client.getCredentialsProvider().setCredentials(new AuthScope(null, -1), new UsernamePasswordCredentials(WS_USERNAME,WS_PASSWORD));
        
        String jsonResponse = null;
        
        Log.d(TAG, "HttpPost URL: "+ BASE_URL+"/"+methodName);
        HttpPost request = new HttpPost(BASE_URL+"/"+methodName);
        try {
        	request.setEntity(new StringEntity(payLoad, "UTF-8"));
        	request.setHeader("Authorization","Basic "+Base64.encodeToString((WS_USERNAME+":"+WS_PASSWORD).getBytes(),Base64.URL_SAFE|Base64.NO_WRAP));
        	request.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8");
        	BasicResponseHandler handler = new BasicResponseHandler();
        	Log.d(TAG, "Executing post request");
            jsonResponse = client.execute(request, handler);
        } catch (Exception e) {
        	Log.e(TAG, e.getMessage(),e);
        }
        return jsonResponse;
	}
	
	private static DefaultHttpClient getThreadSafeClient()  {

	    DefaultHttpClient client = new DefaultHttpClient();
	    ClientConnectionManager mgr = client.getConnectionManager();
	    HttpParams params = client.getParams();
	    client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, 

	            mgr.getSchemeRegistry()), params);
	    return client;
	}

}
