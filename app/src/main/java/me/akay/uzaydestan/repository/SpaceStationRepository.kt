package me.akay.uzaydestan.repository

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.akay.uzaydestan.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpaceStationRepository @Inject constructor(private val apiService: ApiService) {
    private val TAG = "SpaceStationRepository"

    fun loadData() {
        val dis: Disposable = apiService.getSpaceStations("e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                Log.i(TAG, "Value is: ${data.size}") // TODO: 5/27/21
            }, { e ->
                Log.i(TAG, "Error: ${e.message}")
            })
    }
}