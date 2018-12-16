package com.roverphotos.mars.roverphotos.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.roverphotos.mars.roverphotos.data.Rover

/**
 *  Created by metecanduyal on 16.12.2018.
 */

class ChooseRoverViewModelFactory(private val roverList: ArrayList<Rover>?) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChooseRoverViewModel(roverList) as T
    }

}