package com.roverphotos.mars.roverphotos.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *  Created by metecanduyal on 15.12.2018.
 */

data class Rover(
    var id: Int,
    var name: String,
    @SerializedName("landing_date")
    var landingDate: String,
    @SerializedName("launch_date")
    var launchDate: String,
    var status: String,
    @SerializedName("max_sol")
    var maxSol: Int,
    @SerializedName("max_date")
    var maxDate: String,
    @SerializedName("total_photos")
    var totalPhotos: Int,
    @SerializedName("cameras")
    var cameraList: List<Camera>
) : Serializable {
    companion object {
        const val STATUS_ACTIVE = "active"
        const val STATUS_COMPLETE = "complete"
    }
}