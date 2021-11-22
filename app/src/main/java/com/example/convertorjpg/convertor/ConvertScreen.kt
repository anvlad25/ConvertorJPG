package com.example.convertorjpg.convertor

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.convertorjpg.convertor.ConvertFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object ConvertScreen : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment = ConvertFragment.newInstance()
}