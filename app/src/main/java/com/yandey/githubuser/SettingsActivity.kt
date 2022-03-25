package com.yandey.githubuser

import android.provider.Settings
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.yandey.githubuser.databinding.ActivitySettingsBinding
import com.yandey.githubuser.presentation.viewmodel.UserViewModel
import com.yandey.githubuser.presentation.viewmodel.UserViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: UserViewModelFactory
    lateinit var viewModel: UserViewModel
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        binding.apply {
            setTheme(viewModel, swTheme)
            changeLanguage(tvChangeLanguage)
            backToActivity(ivBack)
        }
    }

    private fun setTheme(viewModel: UserViewModel, switchMaterial: SwitchMaterial) {
        switchMaterial.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSetting(isChecked)
        }

        viewModel.getThemeSetting().observe(this@SettingsActivity) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.swTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.swTheme.isChecked = false
            }
        }
    }

    private fun changeLanguage(view: View) {
        view.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    private fun backToActivity(view: View) {
        view.setOnClickListener {
            onBackPressed()
        }
    }
}