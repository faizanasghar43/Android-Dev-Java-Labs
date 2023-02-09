package com.example.my_weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    double _temperature;
    double _min_temperature;
    double _humidity;
    double _visibility;
    double _wind_speed;
    double _wind_direction;
    int _sun_rise_time;
    int _sun_set_time;
    double feels_like;
    double curr_temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button weather;
        TextView max_temp, min_temp, humidity, visibility, wind_speed, wind_direction, sun_rise_time, sun_set_time, city, current_temperature, feel;
        max_temp = findViewById(R.id.max_temp);
        weather = findViewById(R.id.fetch_btn);
        min_temp = findViewById(R.id.min_temp);
        humidity = findViewById(R.id.humidity2);
        visibility = findViewById(R.id.visibility_txt);
        wind_speed = findViewById(R.id.wind_txt);
        wind_direction =  findViewById(R.id.winddir_txt);
        sun_rise_time =  findViewById(R.id.sunrise2);
        sun_set_time = findViewById(R.id.sunset3);
        feel = findViewById(R.id.feel_temp);
        current_temperature = findViewById(R.id.current_temp);
        city = findViewById(R.id.city_txt);
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        char[] data = new char[5000];
                        try {

                            String input = city.getText().toString();
                            if (input.isEmpty()) {
                                throw new IllegalArgumentException("Add city");

                            }
                            URL u = new URL("https://api.openweathermap.org/data/2.5/weather?q="+input+",pk&appid=d6de83d2a1142dcf99b178276ea7ee75");
                            InputStream i = u.openStream();
                            BufferedReader b = new BufferedReader(new InputStreamReader(i));
                            int count = b.read(data);
                            String response = new String(data, 0, count);
                            JSONObject j = new JSONObject(response);
                            JSONObject main = j.getJSONObject("main");
                            JSONObject wind = j.getJSONObject("wind");
                            JSONObject s = j.getJSONObject("sys");
                            _temperature = main.getDouble("temp_max");
                            _temperature = Math.round(_temperature - 273.15);
                            _min_temperature = main.getDouble("temp_min");
                            _min_temperature = Math.round(_min_temperature - 273.15);
                            _humidity = main.getDouble("humidity");
                            _humidity = Math.round(_humidity);
                            _visibility = j.getDouble("visibility");
                            _visibility = Math.round(_visibility);
                            _wind_speed = wind.getDouble("speed");
                            _wind_speed = Math.round(_wind_speed);
                            _wind_direction = wind.getDouble("deg");
                            _sun_rise_time = s.getInt("sunrise");
                            _sun_set_time = s.getInt("sunset");
                            feels_like = main.getDouble("feels_like");
                            curr_temp = main.getDouble("temp");
                            Date date = new Date(_sun_rise_time * 1000L);
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String srt = formatter.format(date);
                            Date date1 = new Date(_sun_rise_time * 1000L);
                            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String sst = formatter1.format(date1);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    max_temp.setText(""+_temperature+"°C");
                                }
                            });


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    min_temp.setText(""+_min_temperature+"°C");
                                }
                            });
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    humidity.setText(""+_humidity);
                                }
                            });

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    visibility.setText(""+_visibility);
                                }
                            });
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    wind_speed.setText(""+_wind_speed);
                                }
                            });
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    wind_direction.setText(""+_wind_direction);

                                }
                            });
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    wind_direction.setText(""+_wind_direction);

                                }
                            });
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    sun_rise_time.setText(""+srt);

                                }
                            });
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    sun_set_time.setText(""+sst);

                                }
                            });
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    feel.setText(""+feels_like);

                                }
                            });
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    current_temperature.setText(""+curr_temp);

                                }
                            });



                        }
                        catch(Exception e){
                            e.printStackTrace();

                        }
                    }
                    };
                Thread t = new Thread(r);
                t.start();
                };








        });
    }
}