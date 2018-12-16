package com.roverphotos.mars.roverphotos.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.roverphotos.mars.roverphotos.constant.State
import com.roverphotos.mars.roverphotos.data.Photo

class MainViewModel(roverName: String, roverMaxDate: String) : ViewModel() {

    private var liveDataSource: MutableLiveData<MainDataSource>
    private val itemDataSourceFactory: MainDataSourceFactory = MainDataSourceFactory(roverName, roverMaxDate)
    var itemPagedList: LiveData<PagedList<Photo>>

    init {
        liveDataSource = itemDataSourceFactory.photoLiveDataSource
        //todo fix mete when next page size is equal to zero it breaks to pagination
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(MainDataSource.PAGE_SIZE * 2)
            .setPageSize(MainDataSource.PAGE_SIZE)
            .build()
        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<MainDataSource,
            State>(itemDataSourceFactory.photoLiveDataSource, MainDataSource::state)
}