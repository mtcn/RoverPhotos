package com.roverphotos.mars.roverphotos.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.roverphotos.mars.roverphotos.api.NasaApi
import com.roverphotos.mars.roverphotos.constant.State
import com.roverphotos.mars.roverphotos.data.GetMarsPhotos
import com.roverphotos.mars.roverphotos.data.Photo
import com.roverphotos.mars.roverphotos.util.AppUtility
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *  Created by metecanduyal on 16.12.2018.
 */

class MainDataSource(
    private var roverName: String,
    private var roverMaxDate: String
) :
    PageKeyedDataSource<Int, Photo>() {

    private var paginationKey = FIRST_PAGE
    var state: MutableLiveData<State> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Photo>) {
        updateState(State.LOADING)
        GlobalScope.launch {
            val response = try {
                getPhotos(FIRST_PAGE).await()
            } catch (e: Exception) {
                Log.e("API", "loadInitial: " + e.message)
                null
            } ?: return@launch

            if (!response.photos.isEmpty()) {
                updateState(State.DONE)
                setPaginationValues(response, FIRST_PAGE)
                callback.onResult(response.photos, null, paginationKey)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        GlobalScope.launch {
            val response = try {
                getPhotos(params.key).await()
            } catch (e: Exception) {
                Log.e("API", "loadAfter: " + e.message)
                null
            } ?: return@launch

            setPaginationValues(response, params.key)
            callback.onResult(response.photos, paginationKey)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        //to do nothing because we don't have pull to refresh feature
    }

    private fun setPaginationValues(response: GetMarsPhotos, currentPage: Int) {
        if (response.photos.isEmpty() || response.photos.size < PAGE_SIZE) {
            paginationKey = FIRST_PAGE
            roverMaxDate = getPreviousDate(roverMaxDate)
        } else {
            paginationKey = currentPage + 1
        }
    }

    private fun updateState(state: State) = this.state.postValue(state)

    private fun getPhotos(page: Int) =
        NasaApi.create().getPhotos(roverName, roverMaxDate, page)

    private fun getPreviousDate(currentDate: String) = AppUtility.getInstance().getPreviousDate(currentDate)

    companion object {
        const val PAGE_SIZE = 25
        const val FIRST_PAGE = 1
    }
}