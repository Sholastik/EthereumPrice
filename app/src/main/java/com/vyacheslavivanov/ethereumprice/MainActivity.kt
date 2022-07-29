package com.vyacheslavivanov.ethereumprice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vyacheslavivanov.ethereumprice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}