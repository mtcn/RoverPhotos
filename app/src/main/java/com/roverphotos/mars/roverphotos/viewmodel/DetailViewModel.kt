package com.roverphotos.mars.roverphotos.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.roverphotos.mars.roverphotos.data.Photo

class DetailViewModel(photo: Photo?) : ViewModel() {

    var photoLiveData = MutableLiveData<Photo>()

    init {
        photoLiveData.postValue(photo)
    }

}
