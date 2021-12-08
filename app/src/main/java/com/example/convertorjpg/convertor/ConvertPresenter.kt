package com.example.convertorjpg.convertor

import android.net.Uri
import moxy.MvpPresenter

class ConvertPresenter(
    private val uriImage: Uri
) : MvpPresenter<ConvertView>() {

    override fun onFirstViewAttach() {
        println(uriImage)
    }
}
