package com.roverphotos.mars.roverphotos.util

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

    fun convertStringToDate(date: String): Date {
        return getDateFormatter().parse(date)
    }

    private fun convertDateToString(date: Date): String {
        return getDateFormatter().format(date)
    }

    fun isRoverActive(status: String): Boolean {
        return status == Rover.STATUS_ACTIVE
    }

    fun getPhotoDescription(photo: Photo): String {
        return "getPhotoDescription"
    }

    private fun getDateFormatter(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

}