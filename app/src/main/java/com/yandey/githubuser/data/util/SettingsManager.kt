package com.yandey.githubuser.data.util

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsManager(private val context: Context) {

    private val Context.userPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(
        AppConstant.USER_PREF
    )

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        val wrappedKey = booleanPreferencesKey(AppConstant.THEME_PREF)
        context.userPreferenceDataStore.edit {
            it[wrappedKey] = isDarkModeActive
        }
    }

    fun getThemeSetting(): Flow<Boolean> =
        context.userPreferenceDataStore.data.map {
            it[booleanPreferencesKey(AppConstant.THEME_PREF)] ?: false
        }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var mInstance: SettingsManager? = null

        fun getInstance(context: Context): SettingsManager =
            mInstance ?: synchronized(this) {
                val newInstance = mInstance ?: SettingsManager(context).also { mInstance = it }
                newInstance
            }
    }
}
