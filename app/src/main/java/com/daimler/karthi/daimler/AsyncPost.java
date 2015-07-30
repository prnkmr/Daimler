package com.daimler.karthi.daimler;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by Praveen kumar on 29/07/2015.
 */
public class AsyncPost extends AsyncTask {
    String URL;
    List<NameValuePair> json;
    AsyncListener caller;
    HttpClient client;
    HttpPost post;
    HttpResponse httpResponse;
    String response;

    AsyncPost(String URL,List<NameValuePair> json,AsyncListener caller){
        this.URL=URL;
        this.json=json;
        this.caller=caller;

        execute();

    }
    @Override
    protected Object doInBackground(Object[] params) {
        client=new DefaultHttpClient();
        post= new HttpPost(URL);
        try {
            post.setEntity(new UrlEncodedFormEntity(json));
            httpResponse=client.execute(post);
            response= EntityUtils.toString(httpResponse.getEntity());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        caller.onResponse(response);
    }
}
