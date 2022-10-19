package com.example.ecommerceapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityShoppingBinding

class ShoppingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.shopping_graph) as NavHostFragment?

        if (navHostFragment != null) {
            val navController = navHostFragment.navController

            NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        }
    }
}