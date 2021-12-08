package com.example.convertorjpg.main

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import com.example.convertorjpg.App
import com.example.convertorjpg.convertor.ConvertScreen
import com.example.convertorjpg.R
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity(), MainView {
    private val navigator = AppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance = App()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            App.instance.router.newRootScreen(ConvertScreen)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
    }
}