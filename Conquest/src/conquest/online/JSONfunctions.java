package conquest.online;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import android.util.Log;

public class JSONfunctions {
	  public static JSONObject getJSONfromURL(String url, List<NameValuePair> params ) {
	    InputStream is = null;
	    String result = "";
	    JSONObject jArray = null;

	    // Download JSON data from URL
	    try {
	        //Create and connect to url
	    	HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost(url);
	        
	        //Add POST Request params
	        httppost.setEntity(new UrlEncodedFormEntity(params));
	        
	        //get response from server
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();

	    } catch (Exception e) {
	        Log.e("log_tag", "Error in http connection " + e.toString());
	    }

	    // Convert response to string
	    try {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                is, "iso-8859-1"), 8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	        is.close();
	        result = sb.toString();
	    } catch (Exception e) {
	        Log.e("log_tag", "Error converting result " + e.toString());
	    }

	    try {
	        jArray = new JSONObject(result);
	    } catch (JSONException e) {
	        Log.e("log_tag", "Error parsing data " + e.toString());
	    }

	    return jArray;
	  }
	}