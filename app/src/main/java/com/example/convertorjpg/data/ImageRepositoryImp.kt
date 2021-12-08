package com.example.convertorjpg.data

import io.reactivex.rxjava3.core.Single


class ImageRepositoryImp(imagePath: String) : ImageRepository {
    private val imageData = ImageData(imagePath)

    override fun getImagePath(): Single<ImageData> = Single.just(imageData)
}