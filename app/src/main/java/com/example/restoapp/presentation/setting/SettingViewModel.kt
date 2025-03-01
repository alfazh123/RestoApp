package com.example.restoapp.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.RestaurantUseCase
import kotlinx.coroutines.launch

class SettingViewModel(private val restaurantUseCase: RestaurantUseCase): ViewModel() {

    fun getThemeSetting() = restaurantUseCase.getThemeSetting().asLiveData()

    fun getDailyReminderSetting() = restaurantUseCase.getDailyReminderSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            restaurantUseCase.saveThemeSetting(isDarkModeActive)
        }
    }

    fun saveDailyReminderSetting(isDailyReminderActive: Boolean) {
        viewModelScope.launch {
            restaurantUseCase.saveDailyReminderSetting(isDailyReminderActive)
        }
    }

}