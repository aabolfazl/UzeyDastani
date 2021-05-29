package me.akay.uzaydestan.repository

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.akay.uzaydestan.BuildConfig
import me.akay.uzaydestan.data.SpaceStationEntity
import me.akay.uzaydestan.data.Spacecraft
import me.akay.uzaydestan.helper.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationRepository @Inject constructor(
        private val spacecraftDatabase: SpacecraftDatabaseStore,
        private val stationDatabase: SpaceStationDatabaseStore,
        private val stationNetworkStore: SpaceStationNetworkStore
) {
    private val TAG = "SpaceStationRepository"

    var currentSpaceCraft: Spacecraft? = getCurrentSpacecraft()

    private fun getCurrentSpacecraft(): Spacecraft? {
        return spacecraftDatabase.getCurrentSpacecraft()
                .subscribeOn(Schedulers.io())
                .doOnError { t -> Log.e(TAG, "current space craft", t.cause) }
                .blockingGet()
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
                }, { e ->
                    Resource.error(e.message!!, null)
                })
    }

    fun loadStationList(result: MutableLiveData<Resource<List<SpaceStationEntity>>>): Disposable {
        return stationDatabase.getCurrentSpacecraft()
                .mergeWith(getStationsFromNetwork())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    result.value = Resource.loading(null)
                }
                .subscribe({
                    result.value = Resource.success(it)
                }, { e ->
                    Resource.error(e.message!!, null)
                })
    }

    private fun getStationsFromNetwork(path: String = BuildConfig.STATION_PATH): Completable {
        return stationNetworkStore.getStationList(path)
                .map { entity ->
                    val databaseEntity: ArrayList<SpaceStationEntity> = ArrayList(entity.size)
                    for (item in entity) {
                        databaseEntity.add(SpaceStationEntity(item))
                    }
                    return@map databaseEntity
                }
                .flatMapCompletable { stations -> return@flatMapCompletable stationDatabase.insert(stations) }
    }
}