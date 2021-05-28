package me.akay.uzaydestan.repository

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.akay.uzaydestan.api.ApiService
import me.akay.uzaydestan.data.Spacecraft
import me.akay.uzaydestan.data.SpacecraftDAO
import me.akay.uzaydestan.helper.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationRepository @Inject constructor(private val apiService: ApiService, private val spacecraftDao: SpacecraftDAO) {
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

    fun saveSpacecraft(name: String, durability: Int, speed: Int, capacity: Int, result: MediatorLiveData<Resource<Boolean?>>): Disposable {
        val spacecraft = Spacecraft(name, durability, speed, capacity)
        return spacecraftDao.insert(spacecraft)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                Log.i("abbasiLog", "loading from rx: ")
                result.value = Resource.loading(null)
            }
            .subscribe({
                Log.i("abbasiLog", "success from rx: ")
                result.value = Resource.success(true)
            }, { e ->
                Log.i("abbasiLog", "error from rx: ")
                Resource.error(e.message!!, null)
            })
    }
}