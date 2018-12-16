package com.roverphotos.mars.roverphotos.util

import android.content.Context
import com.roverphotos.mars.roverphotos.R
import com.roverphotos.mars.roverphotos.data.Photo
import com.roverphotos.mars.roverphotos.data.Rover
import java.text.SimpleDateFormat
import java.util.*

/**
 *  Created by metecanduyal on 15.12.2018.
 */

class AppUtility {

    companion object {
        const val TAG = "AppUtility"
        @Volatile
        private var instance: AppUtility? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: AppUtility().also { instance = it }
            }
    }

    fun getPreviousDate(currentDate: String): String {
        val calendar = Calendar.getInstance()
        calendar.time = getDateFormatter().parse(currentDate)
        calendar.add(Calendar.HOUR, -24)
        return convertDateToString(calendar.time)
    }

    fun isCurrentEarthDate(roverMaxDate: String): Boolean {
        return getCurrentDate() == roverMaxDate
    }

    private fun getCurrentDate(): String {
        val todayDate = Calendar.getInstance().time
        return getDateFormatter().format(todayDate)
    }

    private fun convertDateToString(date: Date): String {
        return getDateFormatter().format(date)
    }

    fun isRoverActive(status: String): Boolean {
        return status == Rover.STATUS_ACTIVE
    }

    fun getPhotoDescription(context: Context, photo: Photo?): String? {
        return "${context.getString(R.string.photo_id)} ${photo?.id}\n" +
                "${context.getString(R.string.photo_sol)} ${photo?.sol}\n" +
                "${context.getString(R.string.photo_earth_date)} ${photo?.earthDate}\n" +
                "${context.getString(R.string.rover_name)} ${photo?.rover?.name}\n" +
                "${context.getString(R.string.rover_total_photos)} ${photo?.rover?.totalPhotos}\n"
    }

    private fun getDateFormatter(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

}