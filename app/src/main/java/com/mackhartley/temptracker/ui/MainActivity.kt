package com.mackhartley.temptracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mackhartley.temptracker.R
import com.mackhartley.temptracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)
    }
}