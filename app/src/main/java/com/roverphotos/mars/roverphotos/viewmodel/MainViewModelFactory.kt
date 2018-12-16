package com.roverphotos.mars.roverphotos.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 *  Created by metecanduyal on 16.12.2018.
 */

class MainViewModelFactory(private val roverName: String, private val roverMaxDate: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(roverName, roverMaxDate) as T
    }
}