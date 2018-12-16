package com.roverphotos.mars.roverphotos.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.roverphotos.mars.roverphotos.data.Photo

/**
 *  Created by metecanduyal on 16.12.2018.
 */

class DetailViewModelFactory (private val photo: Photo?) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(photo) as T
    }

}