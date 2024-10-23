package com.example.weathermonitorapp.API

data class WeatherData(
    val name:String,
    val main: Main,
    val weather: List<Weather>,
    val dt: Long,
    val wind:Wind
)
