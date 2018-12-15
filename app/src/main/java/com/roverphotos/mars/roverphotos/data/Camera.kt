package com.roverphotos.mars.roverphotos.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *  Created by metecanduyal on 15.12.2018.
 */

data class Camera(
    var name: String,
    @SerializedName("full_name")
    var fullName: String
) : Serializable