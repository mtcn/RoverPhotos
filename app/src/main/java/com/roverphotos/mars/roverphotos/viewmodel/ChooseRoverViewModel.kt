package com.roverphotos.mars.roverphotos.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.roverphotos.mars.roverphotos.data.Rover

class ChooseRoverViewModel(roverList: ArrayList<Rover>?) : ViewModel() {

    var roverLiveData = MutableLiveData<ArrayList<Rover>>()

    init {
        roverLiveData.postValue(roverList)
    }

}