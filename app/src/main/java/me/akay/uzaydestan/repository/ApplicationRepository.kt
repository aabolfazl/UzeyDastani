package me.akay.uzaydestan.repository

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.akay.uzaydestan.data.Spacecraft
import me.akay.uzaydestan.helper.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationRepository @Inject constructor(
        private val spacecraftDatabase: SpacecraftDatabaseStore
) {
    private val TAG = "SpaceStationRepository"

    var currentSpaceCraft: Spacecraft? = getCurrentSpacecraft()

    init {
        Log.i(TAG, ": loaded $currentSpaceCraft")
    }

    private fun getCurrentSpacecraft(): Spacecraft? {
        return spacecraftDatabase.getCurrentSpacecraft()
                .subscribeOn(Schedulers.io())
                .doOnError { t -> Log.e(TAG, "current space craft", t.cause) }
                .blockingGet()
    }

    fun loadData() {
//        val dis: Disposable = apiService.getSpaceStations("e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ data ->
//                Log.i(TAG, "Value is: ${data.size}") // TODO: 5/27/21
//            }, { e ->
//                Log.i(TAG, "Error: ${e.message}")
//            })
    }

    fun saveSpacecraft(name: String, durability: Int, speed: Int, capacity: Int, result: MediatorLiveData<Resource<Boolean?>>): Disposable {
        val spacecraft = Spacecraft(name, durability, speed, capacity, 100)
        return spacecraftDatabase.insert(spacecraft)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { currentSpaceCraft = getCurrentSpacecraft() }
                .doOnSubscribe {
                    result.value = Resource.loading(null)
                }
                .subscribe({
                    result.value = Resource.success(true)
                    Log.i(TAG, "saveSpacecraft successfully!")
                }, { e ->
                    Resource.error(e.message!!, null)
                    Log.e(TAG, "saveSpacecraft: error ", e.cause)
                })
    }
}