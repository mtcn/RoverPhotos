package com.roverphotos.mars.roverphotos.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.roverphotos.mars.roverphotos.api.NasaApi
import com.roverphotos.mars.roverphotos.data.Rover
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val responseSubject: BehaviorSubject<Boolean> by lazy { BehaviorSubject.create<Boolean>() }
    val animationSubject: BehaviorSubject<Boolean> by lazy { BehaviorSubject.create<Boolean>() }
    var isComplete = MutableLiveData<Map<Boolean, ArrayList<Rover>?>>()
    var roverData = arrayListOf<Rover>()

    init {
        getRoversData()
        observeEvents()
    }

    fun getRoversData() {
        val service = NasaApi.create()
        GlobalScope.launch {
            val response = try {
                service.getRovers().await()
            } catch (e: Exception) {
                null
            }

            if (response == null)
                responseSubject.onNext(false)
            else if (!response.rovers.isEmpty()) {
                roverData.addAll(response.rovers)
                responseSubject.onNext(true)
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun observeEvents() {
        val completionObservable: Observable<Boolean> = Observable.combineLatest(
            animationSubject,
            responseSubject,
            BiFunction { a, r -> a && r })

        completionObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ isDone ->
                val map = HashMap<Boolean, ArrayList<Rover>?>()
                map[isDone] = roverData
                isComplete.postValue(map)
            }, { err -> Log.e("ERROR", err.message) })
    }
}
