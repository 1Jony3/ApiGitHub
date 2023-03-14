package com.example.apigithub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.apigithub.R

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        d("lol", "MainActivity onCreate")

        val navHost = supportFragmentManager.findFragmentById(R.id.main) as NavHostFragment
        navController = navHost.navController
        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        d("lol", "NavigateUp")
        d("lol", "${navController.navigateUp()}")
        d("lol", "${navController}")
        d("lol", "${super.onSupportNavigateUp()}")
        d("lol", "////NavigateUp")
        return  navController.navigateUp() || super.onSupportNavigateUp()
    }
}