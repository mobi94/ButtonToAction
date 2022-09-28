package com.serhii.stasiuk.buttontoaction.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.serhii.stasiuk.buttontoaction.R
import com.serhii.stasiuk.buttontoaction.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initNavHostContainer()
    }

    private fun initNavHostContainer() {
        val container =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
    }
}