package com.mcgowanalyssa.lab11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
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
        LayoutInflater inflater = LayoutInflater.from(this);

        TextView timedisplay = findViewById(R.id.time);
        TextView tempdisplay = findViewById(R.id.temperature);
        TextView descdisplay = findViewById(R.id.description);
        HorizontalScrollView hourlyScrollView = findViewById(R.id.hourlyScrollView);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                    int windspeed = Integer.parseInt(today.getString("windSpeed").substring(0,2).trim());
                    boolean isday = today.getBoolean("isDaytime");

                    list.add(new WeatherInfo(windspeed));

                    //list.add(new WeatherInfo(quote,author));

                    tempdisplay.setText(temperature);
                    timedisplay.setText(time);
                    descdisplay.setText(description);


                    for (int i = 2; i < 20; i+=2){
                        JSONObject dayobj = periods.getJSONObject(i);

                        String day = dayobj.getString("name");
                        String daytemp = dayobj.getString("temperature");
                        String daydesc = dayobj.getString("shortForecast");

                        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dayweather, null, false);
                        //LinearLayout linear = (LinearLayout)findViewById(R.id.tenday);
                        //linear.addView(layout);

                        //TextView daydis = layout.findViewById(R.id.day);
                        //TextView daytempdis = layout.findViewById(R.id.daytemp);
                        //TextView daydescdis = layout.findViewById(R.id.daydesc);


                        //daydis.setText(day);
                        //daytempdis.setText(daytemp);
                        //daydescdis.setText(daydesc);
                    }

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

        String url2 = "https://api.weather.gov/gridpoints/LWX/96,72/forecast/hourly";
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject properties = object.getJSONObject("properties");
                    JSONArray periods = properties.getJSONArray("periods");
                    //JSONObject today = periods.getJSONObject(0);

                    for (int i = 0; i < 24; i++){
                        JSONObject hour = periods.getJSONObject(i);
                        System.out.println(i);
                        System.out.println(hour.getString("startTime"));

                        int time = Integer.parseInt(hour.getString("startTime").substring(11,13));
                        String temperature = hour.getString("temperature");
                        String description = hour.getString("shortForecast");

                        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.hourlyweather, null, false);
                        LinearLayout linear = (LinearLayout)findViewById(R.id.hourlyll);
                        linear.addView(layout);

                        TextView hourdis = layout.findViewById(R.id.hour);
                        TextView hourtempdis = layout.findViewById(R.id.hourtemp);
                        TextView hourdescdis = layout.findViewById(R.id.hourdesc);

                        int t2 = time%12; if(t2==0){t2+=12;}
                        String formattedTime = ""+t2;
                        if(time>=12){formattedTime+="PM";} else{formattedTime+="AM";}

                        hourdis.setText(formattedTime);
                        hourtempdis.setText(temperature);
                        hourdescdis.setText(description);
                    }
                    //list.add(new WeatherInfo(quote,author));

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
        queue.add(stringRequest2);
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