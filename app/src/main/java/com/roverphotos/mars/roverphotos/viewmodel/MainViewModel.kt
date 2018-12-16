package com.roverphotos.mars.roverphotos.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.roverphotos.mars.roverphotos.data.Photo

class MainViewModel(roverName: String, roverMaxDate: String) : ViewModel() {

    private var liveDataSource: LiveData<PageKeyedDataSource<Int, Photo>>
    var itemPagedList: LiveData<PagedList<Photo>>

    init {
        val itemDataSourceFactory = MainDataSourceFactory(roverName, roverMaxDate)
        liveDataSource = itemDataSourceFactory.photoLiveDataSource
        //todo fix mete when next page size is equal to zero it breaks to pagination
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(MainDataSource.PAGE_SIZE * 3)
            .setPageSize(MainDataSource.PAGE_SIZE)
            .build()
        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, config).build()
    }

}