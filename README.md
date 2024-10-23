WeatherMonitor App
Overview
WeatherMonitor is a Kotlin-based application designed to monitor real-time weather conditions across major Indian metro cities (Delhi, Mumbai, Chennai, Bangalore, Kolkata, Hyderabad). It retrieves data from the OpenWeatherMap API using Retrofit and stores the weather information locally in a Room Database. The app provides summarized insights through daily rollups and aggregates, allowing users to track weather patterns and receive alerts based on configurable thresholds.

Objective
The objective of WeatherMonitor is to develop a real-time data processing system to monitor and analyze weather data. It processes data continuously, generating daily summaries, alerting users when certain weather thresholds are breached, and providing visualizations of historical weather trends.

Key Features
Real-time Weather Monitoring:

Retrieves weather data from the OpenWeatherMap API every 5 minutes (configurable interval).
Focuses on key weather parameters like:
Main condition (e.g., Rain, Snow, Clear)
Temperature in Celsius (current and perceived)
Data update time
Data Processing and Analysis:

Converts temperature data from Kelvin to Celsius based on user preference.
Summarizes daily weather data including:
Average temperature
Maximum temperature
Minimum temperature
Dominant weather condition (based on frequency of weather conditions reported during the day)
Threshold-based Alerts:

User-configurable thresholds for temperature or weather conditions (e.g., alert when temperature exceeds 35°C).
Triggers alerts when thresholds are breached and displays the current weather conditions.
Daily Summaries and Visualizations:

Stores daily weather summaries in a Room Database.
Displays historical trends and daily weather data summaries.
Visualization of triggered alerts and weather patterns over time.
Data Source
API: OpenWeatherMap API (https://openweathermap.org/)
API Parameters:
main: Main weather condition
temp: Current temperature (in Celsius)
feels_like: Perceived temperature (in Celsius)
dt: Time of the data update (Unix timestamp)
API Setup
To use the OpenWeatherMap API:

Sign up for a free API key at OpenWeatherMap.
Add the API key to the application’s configuration file to enable API requests.
Tech Stack
Language: Kotlin
Database: Room Database for local storage of weather data
API Calls: Retrofit for HTTP requests to OpenWeatherMap API
Architecture: MVVM (Model-View-ViewModel) for better data management and separation of concerns
UI: XML-based UI for displaying weather data and summaries
How the App Works
Weather Data Retrieval: The app retrieves weather data from the OpenWeatherMap API at a configurable interval (e.g., every 5 minutes).

Temperature Conversion: The retrieved temperature data (in Kelvin) is converted to Celsius for user convenience.

Daily Weather Summary: At the end of each day, the app generates daily summaries, including average, minimum, and maximum temperatures, as well as the dominant weather condition.

Threshold Alerts: The app continuously monitors weather data and checks if any user-defined thresholds are breached (e.g., temperature exceeding 35°C for two consecutive updates). If a breach occurs, the app triggers an alert, notifying the user.

Data Storage: All weather data and daily summaries are stored locally in the Room Database for historical analysis and visualization.

Visualizations: The app provides visual insights, including daily summaries, historical weather trends, and triggered alerts.

Screenshots

![WhatsApp Image 2024-10-23 at 5 08 39 PM](https://github.com/user-attachments/assets/084db82f-bdcd-4864-ad69-00c64ce9e898)

![WhatsApp Image 2024-10-23 at 5 08 39 PM (1)](https://github.com/user-attachments/assets/5655140d-3ce4-41c6-9f00-7684f9f649a9)

![WhatsApp Image 2024-10-23 at 5 08 40 PM](https://github.com/user-attachments/assets/883f4159-6b98-4617-8799-3337e0f05c10)

![WhatsApp Image 2024-10-23 at 5 19 58 PM](https://github.com/user-attachments/assets/32e6d314-4774-4484-aed7-4bb928637230)



Installation
Clone the Repository:

bash
Copy code
git clone https://github.com/yourusername/weather-monitor-app.git
Open in Android Studio:

Open the project in Android Studio and allow Gradle to sync the dependencies.
Add API Key:

Sign up for an OpenWeatherMap API key.
Add the API key to the project by including it in the configuration file (res/values/api_keys.xml).
Run the App:

Install and run the app on an Android device or emulator.
Dependencies
Add these dependencies in your build.gradle file:

gradle
Copy code
// Retrofit for API calls
implementation "com.squareup.retrofit2:retrofit:2.9.0"
implementation "com.squareup.retrofit2:converter-gson:2.9.0"

// Room Database
implementation "androidx.room:room-runtime:2.5.0"
kapt "androidx.room:room-compiler:2.5.0"

// Kotlin Coroutines
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0"

// Lifecycle ViewModel
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0"
Test Cases
System Setup:

Ensure the system connects to the OpenWeatherMap API using a valid API key and fetches data correctly.
Data Retrieval:

Test data retrieval at configurable intervals and verify the system correctly parses and stores the weather data for each city.
Temperature Conversion:

Test the temperature conversion logic from Kelvin to Celsius (and user preference to Fahrenheit).
Daily Weather Summary:

Simulate several days of weather updates and verify that the daily summaries (average, maximum, minimum temperatures, and dominant weather condition) are calculated correctly.
Threshold Alerts:

Test user-configurable thresholds for temperature and weather conditions. Simulate weather data that breaches the thresholds and verify that alerts are triggered appropriately.
Future Enhancements
Push Notifications: Add support for push notifications when alerts are triggered.
Multi-city Support: Allow users to add and track weather for custom cities.
Additional Visualizations: Expand the range of visualizations to include hourly trends and more detailed historical data.
License
This project is licensed under the MIT License. See the LICENSE file for more details.

Enjoy using the WeatherMonitor app! Feel free to contribute by opening issues or submitting pull requests to improve the app's functionality and features.






