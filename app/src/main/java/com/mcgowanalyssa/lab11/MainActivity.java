package com.mcgowanalyssa.lab11;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.weather.gov/gridpoints/LWX/96,71/forecast";
        final List<WeatherInfo> list=new ArrayList<>();

        TextView timedisplay = findViewById(R.id.time);
        TextView tempdisplay = findViewById(R.id.temperature);
        TextView descdisplay = findViewById(R.id.description);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONObject properties = object.getJSONObject("properties");
                            JSONArray periods = properties.getJSONArray("periods");
                            JSONObject today = periods.getJSONObject(0);

                            String description = today.getString("detailedForecast");
                            String temperature = today.getString("temperature");
                            String time = today.getString("name");
                            int windspeed = Integer.parseInt(today.getString("windSpeed").substring(0,2));

                            list.add(new WeatherInfo(windspeed));

                            //list.add(new WeatherInfo(quote,author));

                            //String quoteStr = '"'+list.get(0).quote+'"'+ " -" +list.get(0).author;
                            tempdisplay.setText(temperature);
                            timedisplay.setText(time);
                            descdisplay.setText(description);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tempdisplay.setText("That didn't work!");
                    }
                } ){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("User-Agent", "weather app");
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public class WeatherInfo{
        String description;
        int windspeed;
        public WeatherInfo(int speed) {
            //this.description = description;
            this.windspeed = speed;
        }
    }
}