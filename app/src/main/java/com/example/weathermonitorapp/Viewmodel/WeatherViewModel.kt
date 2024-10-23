package com.example.weathermonitorapp.Viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weathermonitorapp.API.ApiClient
import com.example.weathermonitorapp.API.WeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val _alertMessage = MutableLiveData<Pair<String, String>>()
    val alertMessage: LiveData<Pair<String, String>> = _alertMessage

    private val _weatherData = MutableLiveData<List<WeatherData>>()
    val weatherData: LiveData<List<WeatherData>> get() = _weatherData


    private val handler = Handler(Looper.getMainLooper())
    private var isRunning=  false

    private val cities = listOf("Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad")


fun checkTemperatureThreshold(currentTempCelsius: Double, maxThreshold: Double, minThreshold: Double) {
    if (currentTempCelsius > maxThreshold) {
        _alertMessage.value = Pair("Temperature Alert", "The temperature of $currentTempCelsius°C exceeds the maximum threshold!")
    } else if (currentTempCelsius < minThreshold) {
        _alertMessage.value = Pair("Temperature Alert", "The temperature of $currentTempCelsius°C is below the minimum threshold!")
    }
}


    fun startFetchingWeather(apiKey: String) {
        if (isRunning) return

        isRunning = true
        fetchWeatherForAllCities(apiKey)

        handler.postDelayed(object : Runnable {
            override fun run() {
                fetchWeatherForAllCities(apiKey)
                handler.postDelayed(this, TimeUnit.MINUTES.toMillis(5))
            }
        }, TimeUnit.MINUTES.toMillis(5))
    }


    private fun fetchWeatherForAllCities(apiKey: String)
    {
        val weatherResponse = mutableListOf<WeatherData>()
        val requests = cities.map{city->
            ApiClient.service.getWeatherData(city, apiKey).enqueue(object: Callback<WeatherData>{
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {

                    if(response.isSuccessful)
                    {
                        val weatherData = response.body()
                        weatherData?.let {
                            weatherResponse.add(it)
                            processWeatherData(weatherData)
                        }


                        if(weatherResponse.size == cities.size)
                        {
                            _weatherData.postValue(weatherResponse)
                        }
                    }
                    else{
                        Toast.makeText(getApplication(), "Error: ${response.message()}", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    Toast.makeText(getApplication(), "Failed to retrieve data: ${t.message}", Toast.LENGTH_SHORT).show()

                }

            })

        }
    }


    private fun processWeatherData(weatherData: WeatherData?) {
        weatherData?.let {
            val currentTemp = it.main.temp
        }
    }


}
