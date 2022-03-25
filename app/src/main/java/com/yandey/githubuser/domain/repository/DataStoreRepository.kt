package com.yandey.githubuser.domain.repository

import android.app.Application
import com.yandey.githubuser.data.util.SettingsManager

class DataStoreRepository(application: Application) {
    private val manager: SettingsManager = SettingsManager.getInstance(application)

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) =
        manager.saveThemeSetting(isDarkModeActive)

    fun getThemeSetting() = manager.getThemeSetting()
}