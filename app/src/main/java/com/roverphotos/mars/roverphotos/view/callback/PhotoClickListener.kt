package com.roverphotos.mars.roverphotos.view.callback

import android.widget.ImageView
import com.roverphotos.mars.roverphotos.data.Photo

/**
 *  Created by metecanduyal on 16.12.2018.
 */

interface PhotoClickListener {
    fun onPhotoClicked(imageView: ImageView, photo: Photo)
}