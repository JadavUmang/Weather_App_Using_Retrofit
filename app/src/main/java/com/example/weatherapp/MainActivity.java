package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView cityNameText, temperatureText, descriptionText, humidityText, pressureText, windSpeedText, currentTimeText;
    private ImageView weatherIcon;
    private TextInputEditText cityInput;
    private MaterialButton searchButton;
    private CardView weatherCard;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View bindings
        cityNameText = findViewById(R.id.city_name);
        temperatureText = findViewById(R.id.temperature);
        descriptionText = findViewById(R.id.description);
        humidityText = findViewById(R.id.humidity);
        pressureText = findViewById(R.id.pressure);
        windSpeedText = findViewById(R.id.wind_speed);
        currentTimeText = findViewById(R.id.current_time);
        weatherIcon = findViewById(R.id.weather_icon);
        cityInput = findViewById(R.id.city_input);
        searchButton = findViewById(R.id.search_button);
        weatherCard = findViewById(R.id.weather_card);
        progressBar = findViewById(R.id.progress_bar);

        weatherCard.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        String apiKey = getString(R.string.api_key);

        searchButton.setOnClickListener(v -> {
            String cityName = cityInput.getText().toString().trim();
            if (!cityName.isEmpty()) {
                fetchWeather(cityName, apiKey);
            } else {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchWeather(String city, String apiKey) {
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherApi weatherApi = retrofit.create(OpenWeatherApi.class);
        Call<WeatherResponse> call = weatherApi.getWeather(city, apiKey);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weather = response.body();

                    String iconCode = weather.getWeather().get(0).getIcon();
                    String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";

                    cityNameText.setText(weather.getName());
                    temperatureText.setText(String.format(Locale.getDefault(), "Temperature: %.1f°C", weather.getMain().getTemp() - 273.15));
                    descriptionText.setText(weather.getWeather().get(0).getDescription());
                    humidityText.setText("Humidity: " + weather.getMain().getHumidity() + "%");
                    pressureText.setText("Pressure: " + weather.getMain().getPressure() + " hPa");
                    windSpeedText.setText("Wind Speed: " + weather.getWind().getSpeed() + " m/s");

                    String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                    currentTimeText.setText("Updated at: " + currentTime);

                    Glide.with(MainActivity.this).load(iconUrl).into(weatherIcon);

                    weatherCard.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MainActivity.this, "City not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Failed to fetch weather", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
