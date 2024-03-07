package com.bittelasia.theme.presenter.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bittelasia.network.domain.model.home.WeatherData
import com.bittelasia.network.domain.model.home.Zones
import com.bittelasia.network.domain.repository.MeshRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val dataAPI: MeshRepository,
) : ViewModel() {

    private val _weather: MutableStateFlow<WeatherData?> = MutableStateFlow(null)
    private val _weatherList: MutableStateFlow<List<WeatherData>?> = MutableStateFlow(null)

    private val _zoneWeather: MutableStateFlow<Zones?> = MutableStateFlow(null)
    private val _zoneListWeather: MutableStateFlow<Zones?> = MutableStateFlow(null)

    val uiState: StateFlow<WeatherUiState> = combine(
        _weatherList.filterNotNull(),
        _weather.filterNotNull(),
        _zoneWeather.filterNotNull(),
        _zoneListWeather.filterNotNull()
    ) { l, t, we, wl ->
        WeatherUiState.Ready(listWeather = l, weather = t, todayZone = we, listWeatherZone = wl)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WeatherUiState.Loading
    )

    fun initWeatherAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            val init = listOf(
                async(Dispatchers.IO) { listWeather() },
                async(Dispatchers.IO) { weatherUiThemes() },
            )
            init.awaitAll()
        }
    }

    private fun listWeather() = viewModelScope.launch(Dispatchers.IO) {
        dataAPI.getWeather().collectLatest { w ->
            val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            _weatherList.value = w
            _weather.value = w.find { date ->
                date.date == todayDate
            } ?: w.last()
        }
    }

    private fun weatherUiThemes() = viewModelScope.launch(Dispatchers.IO) {
        dataAPI.getThemes().collectLatest { zone ->
            _zoneWeather.value = zone.find { it.section == "WeatherTodayZone" }
            _zoneListWeather.value = zone.find { it.section == "WeatherForecastZone" }
        }
    }
}

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data object Error : WeatherUiState
    data class Ready(
        val listWeather: List<WeatherData>? = emptyList(),
        val weather: WeatherData? = null,
        val todayZone: Zones? = null,
        val listWeatherZone: Zones? = null,
    ) : WeatherUiState
}
