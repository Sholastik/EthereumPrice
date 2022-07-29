package com.vyacheslavivanov.ethereumprice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.vyacheslavivanov.ethereumprice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Should be run before super
        installSplashScreen() // SplashScreen won't show when run via Android Studio: https://issuetracker.google.com/issues/205021357

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
