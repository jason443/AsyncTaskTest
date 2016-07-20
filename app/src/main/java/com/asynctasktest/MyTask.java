package com.asynctasktest;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ASUS on 2016/7/19.
 */
public class MyTask extends AsyncTask<String,Void,String>  {

    private Context mContext = null;
    private MyCallBack myCallBack;
    private ProgressBar progressBar;

    public MyTask(Context mContext, MyCallBack myCallBack, ProgressBar progressBar) {
        this.myCallBack = myCallBack;
        this.mContext = mContext;
        this.progressBar = progressBar;
    }

    protected String doInBackground(String...params) {
        String result = null;
        HttpURLConnection connection = null;
        InputStream in = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            in = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            if(stringBuilder != null) {
                publishProgress();
                result = stringBuilder.toString();
            }
        } catch(IOException e) {
            myCallBack.onCancle();
        }
        return result;
    }

    protected void onPostExecute(String result) {
        myCallBack.onFinish(result);
    }

    public void onCancelled() {
        myCallBack.onCancle();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        progressBar.setVisibility(View.GONE);
    }

}
