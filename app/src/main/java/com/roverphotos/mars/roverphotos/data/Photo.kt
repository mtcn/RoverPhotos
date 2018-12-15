package com.roverphotos.mars.roverphotos.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *  Created by metecanduyal on 15.12.2018.
 */

class Photo(
    var id: Int,
    var sol: Int,
    var camera: Camera,
    @SerializedName("img_src")
    var image: String,
    @SerializedName("earth_date")
    var earthDate: String,
    var rover: Rover
) : Serializable