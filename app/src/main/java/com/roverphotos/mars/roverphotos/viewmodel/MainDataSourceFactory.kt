package com.roverphotos.mars.roverphotos.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import com.roverphotos.mars.roverphotos.data.Photo

/**
 *  Created by metecanduyal on 16.12.2018.
 */

class MainDataSourceFactory(private val roverName: String, private val roverMaxDate: String) : DataSource.Factory<Int, Photo>() {

    var photoLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Photo>>()

    override fun create(): DataSource<Int, Photo> {
        val photoDataSource = MainDataSource(roverName, roverMaxDate)
        photoLiveDataSource.postValue(photoDataSource)
        return photoDataSource
    }
}