package com.example.convertorjpg.data

import io.reactivex.rxjava3.core.Single

interface ImageRepository {
    fun getImagePath(): Single<ImageData>
}