package com.makao.makaofit.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.makao.makaofit.R

class TutorialActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var needShowContentOnStop = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setTheme(R.style.SplashScreenTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        setupController()

        showContent()
    }

    private fun setupController() {
        navController = Navigation.findNavController(this, R.id.section_tutorial)
    }

    override fun onStop() {
        super.onStop()

        if (needShowContentOnStop) {
            needShowContentOnStop = false
            showContent()
        }
    }

    /**
     * Shows screen content.
     */
    private fun showContent() {
        window?.setBackgroundDrawableResource(R.color.white)
        findViewById<View>(android.R.id.content).visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        finish()
    }
}