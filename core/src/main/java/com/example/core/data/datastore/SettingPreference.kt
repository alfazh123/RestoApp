package com.example.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "settings")


class SettingPreference (private val dataStore: DataStore<Preferences>){

    private val DAILY_REMINDER_KEY = booleanPreferencesKey("daily_reminder_setting")

    fun getDailyReminderSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[DAILY_REMINDER_KEY] ?: false
        }
    }

    suspend fun saveDailyReminderSetting(isDailyReminderActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[DAILY_REMINDER_KEY] = isDailyReminderActive
        }
    }

    companion object {
        private var instance: SettingPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreference =
            instance ?: synchronized(this) {
                instance ?: SettingPreference(dataStore)
            }
    }

}