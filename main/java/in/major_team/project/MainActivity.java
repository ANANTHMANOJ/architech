package in.major_team.project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.util.Log;

import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.medialablk.easygifview.EasyGifView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity{
    String JSON_STR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int height=displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.5),(int)(height*.2));

        //check and act
       // checkStatus();
         new BackgroundTask().execute();

        EasyGifView easyGifView = (EasyGifView) findViewById(R.id.easyGifView);
        easyGifView.setGifFromResource(R.drawable.loading);

    }



    //check whether nigga alive or dead af
    class CheckDeadAlive extends AsyncTask<Void, Void, String> {
        String json_url;
        @Override
        protected void onPreExecute() {
            json_url = "https://amnayak.000webhostapp.com/retrieve.php";
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(json_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                Log.d("lulz",String.valueOf(inputStream));
                if(bufferedReader.readLine().contains("1"))
                    return "1";
                bufferedReader.close();
                inputStream.close();
                httpsURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "0";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("1"))
            {
                Toast.makeText(MainActivity.this,"Raspbery ON",Toast.LENGTH_SHORT).show();
                new BackgroundTask().execute();
            }
            else
                Toast.makeText(MainActivity.this,"Raspbery not On",Toast.LENGTH_SHORT).show();
        }
    }


    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String json_url, json_string;

        @Override
        protected void onPreExecute() {
            json_url = "https://amnayak.000webhostapp.com/retrieve.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(json_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((json_string = bufferedReader.readLine()) != null) {
                    stringBuilder.append(json_string + "\n");

                }
                bufferedReader.close();
                inputStream.close();
                httpsURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = (TextView) findViewById(R.id.display);
            textView.setText(result);
            JSON_STR = result;
            if (JSON_STR == null) {
                Toast.makeText(getApplicationContext(), "Getting the Json File", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                intent.putExtra("json_data", JSON_STR);
                startActivity(intent);
            }
        }
    }

//    i.mi.com/9482426321    chakresh@1999

    private void checkStatus() {
        // Tag used to cancel the request
        String tag_string_req = "req_check";

        //http://192.168.160.1/check.php

        StringRequest strReq = new StringRequest(Request.Method.GET,
                "https://amnayak.000webhostapp.com/check.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "Response: " + response.toString());
                if(response.equals("1"))
                    new BackgroundTask().execute();
                else
                    Toast.makeText(MainActivity.this,"Not Alive",Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to reVquest queue

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
