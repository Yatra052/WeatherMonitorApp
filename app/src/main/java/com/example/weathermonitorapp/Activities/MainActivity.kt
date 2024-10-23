package com.example.weathermonitorapp.Activities

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.weathermonitorapp.API.WeatherData
import com.example.weathermonitorapp.R
import com.example.weathermonitorapp.Viewmodel.WeatherViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var tvCityName: TextView
    private lateinit var tvCurrentTemp: TextView
    private lateinit var tvFeelsLike: TextView
    private lateinit var maxTemp: TextView
    private lateinit var minTemp: TextView
    private lateinit var avgTemp:TextView
    private lateinit var dominantCondition:TextView
    private  lateinit var datee:TextView
    private lateinit var time:TextView
    private lateinit var humidity:TextView
    private lateinit var speed:TextView
    private lateinit var sealevel:TextView
    private lateinit var grandlevel:TextView
    private lateinit var weatherChart: PieChart
    private lateinit var icon:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCityName = findViewById(R.id.tv_city_name)
        tvCurrentTemp = findViewById(R.id.tv_current_temp)
        tvFeelsLike = findViewById(R.id.tv_feels_like)
        datee = findViewById(R.id.current_date)
        maxTemp = findViewById(R.id.max_temp)
        minTemp = findViewById(R.id.min_temp)
        avgTemp = findViewById(R.id.avg_temp)
        dominantCondition = findViewById(R.id.dominant_condition)
        time = findViewById(R.id.last_updated_time)
        humidity = findViewById(R.id.humidity)
        speed = findViewById(R.id.windspeed)
        sealevel = findViewById(R.id.seaLevel)
        grandlevel = findViewById(R.id.grandlevel)
        weatherChart = findViewById(R.id.weather_chart)
        icon = findViewById(R.id.icon)


        val currentTempCelsius = 36.85
        val maxThreshold = 35.0
        val minThreshold = 0.0


        weatherViewModel.checkTemperatureThreshold(currentTempCelsius, maxThreshold, minThreshold)


        weatherViewModel.alertMessage.observe(this){alertData->
            val (title, message) = alertData
            showAlert(title, message)


        }
        val apiKey = "9dede6ef2cb09858115b1af69e893d43"
        weatherViewModel.startFetchingWeather(apiKey)


        weatherViewModel.weatherData.observe(this) { weatherDataList ->
            updateUI(weatherDataList)
            setupPieChart(weatherDataList)
        }


    }

    private fun setupPieChart(weatherDataList: List<WeatherData>?) {
        val pieEntries = mutableListOf<PieEntry>()

        weatherDataList?.forEach { weatherData ->

            val condition = weatherData.weather[0].main

            val value = weatherData.main.temp_max - 273.15

            pieEntries.add(PieEntry(value.toFloat(), condition))
        }


        val pieDataSet = PieDataSet(pieEntries, "Weather Conditions")

        pieDataSet.colors = listOf(
            resources.getColor(R.color.red),
            resources.getColor(R.color.blue),
            resources.getColor(R.color.gg)
        )

        val pieData = PieData(pieDataSet)
        weatherChart.data = pieData
        weatherChart.setBackgroundColor(resources.getColor(R.color.gg))
        weatherChart.description.text = "Temperature Distribution by Weather Condition"
        weatherChart.isDrawHoleEnabled = true
        weatherChart.setDrawEntryLabels(true)
        weatherChart.setUsePercentValues(true)
        weatherChart.invalidate()
    }


    private fun updateUI(weatherDataList:List<WeatherData>) {

        weatherDataList.forEach {weatherData->
            val tempCelsius = weatherData.main.temp - 273.15
            val feelsLikeCelsius = weatherData.main.feels_like - 273.15
            val iconCode = weatherData.weather[0].icon // Get the icon code
            val iconUrl = "https://openweathermap.org/img/wn/$iconCode@2x.png"
            tvCityName.text = weatherData.name
            tvCurrentTemp.text = "${tempCelsius.toInt()}°C"
            tvFeelsLike.text = "Feels Like ${feelsLikeCelsius.toInt()}°C"
            maxTemp.text = "${(weatherData.main.temp_max - 273.15).toInt()} °C"
            minTemp.text = "${(weatherData.main.temp_min - 273.15).toInt()} °C"
            avgTemp.text = "${((weatherData.main.temp_max + weatherData.main.temp_min) / 2 - 273.15).toInt()} °C"
            dominantCondition.text = "${weatherData.weather.first().main}"


            val date = Date(weatherData.dt * 1000)
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            datee.text = dateFormat.format(date)
            time.text = "Last updated: ${timeFormat.format(date)}"

            sealevel.text = "${weatherData.main.sea_level.toInt() ?: "N/A"}"
            grandlevel.text = "${weatherData.main.grnd_level?.toInt() ?: "N/A"}"
            humidity.text = "${weatherData.main.humidity}%"
            speed.text = "${weatherData.wind.speed} m"

            Glide.with(this)
                .load(iconUrl)
                .into(icon)

        }


    }


    private fun showAlert(title:String, message:String)
    {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK" , null)
            .create()
            .show()
    }

}
