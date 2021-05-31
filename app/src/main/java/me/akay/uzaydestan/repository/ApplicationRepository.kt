package me.akay.uzaydestan.repository

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.akay.uzaydestan.BuildConfig
import me.akay.uzaydestan.data.SpaceStationEntity
import me.akay.uzaydestan.data.SpacecraftEntity
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

    var currentSpaceCraft: SpacecraftEntity? = getCurrentSpacecraft()

    private fun getCurrentSpacecraft(): SpacecraftEntity? {
        return spacecraftDatabase.getCurrentSpacecraftMaybe()
            .subscribeOn(Schedulers.io())
            .doOnError { t -> Log.e(TAG, "current space craft", t.cause) }
            .blockingGet()
    }

    fun saveSpacecraft(name: String, durability: Int, speed: Int, capacity: Int, result: MediatorLiveData<Resource<Boolean?>>): Disposable {
        val spacecraft = SpacecraftEntity(name, durability, speed, capacity, 100, null)
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
                Resource.error(e.toString(), null)
            })
    }

    fun loadStationList(result: MutableLiveData<Resource<List<SpaceStationEntity>>>): Disposable {
        return stationDatabase.getSpaceStationList()
            .mergeWith(getStationsFromNetwork())
            .mergeWith(setDefaultStation())
            .subscribeOn(Schedulers.io())
            .filter { it.isNotEmpty() }
            .map { entity ->
                val currentSpacecraft = spacecraftDatabase.getCurrentSpacecraft()
                if (currentSpacecraft.currentStation != null) {
                    val currentStation = stationDatabase.findSpaceStationByName(currentSpacecraft.currentStation!!)

                    for (item in entity) {
                        item.calculateStationDistance(currentStation)
                    }
                }
                return@map entity
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                result.value = Resource.loading(null)
            }
            .subscribe({
                result.value = Resource.success(it)
            }, { e ->
                result.value = Resource.error(e.toString(), null)
            })
    }

    fun loadCurrentStation(result: MutableLiveData<Resource<SpaceStationEntity>>): Disposable {
        return spacecraftDatabase.getCurrentSpacecraftFlowable()
            .subscribeOn(Schedulers.io())
            .flatMap { spacecraft -> getCurrentSpaceStation(spacecraft.currentStation) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                result.value = Resource.loading(null)
            }
            .subscribe({
                result.value = Resource.success(it)
            }, { e ->
                result.value = Resource.error(e.toString(), null)
            })
    }

    fun loadFavoriteSpaceStationList(result: MutableLiveData<Resource<List<SpaceStationEntity>>>): Disposable {
        return stationDatabase.getFavoriteSpaceStationList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                result.value = Resource.loading(null)
            }
            .subscribe({
                result.value = Resource.success(it)
            }, { e ->
                Resource.error(e.toString(), null)
            })
    }

    private fun getStationsFromNetwork(path: String = BuildConfig.STATION_PATH): Completable {
        return stationNetworkStore.getStationList(path)
            .subscribeOn(Schedulers.io())
            .map { entity ->
                val databaseEntity: ArrayList<SpaceStationEntity> = ArrayList(entity.size)
                for (item in entity) {
                    databaseEntity.add(SpaceStationEntity(item))
                }
                return@map databaseEntity
            }
            .flatMapCompletable { stations ->
                return@flatMapCompletable updateCurrentStation(stations[0].name)
                    .mergeWith(stationDatabase.insertOrUpdate(stations))
            }
    }

    private fun setDefaultStation(): Completable {
        return stationDatabase.findFirstStationMaybe()
            .subscribeOn(Schedulers.io())
            .flatMapCompletable { stations ->
                return@flatMapCompletable updateCurrentStation(stations.name)
            }
    }

    fun toggleFavorite(spaceStation: SpaceStationEntity): Disposable {
        spaceStation.isFavorite = spaceStation.isFavorite.not()
        return stationDatabase.updateSpaceStation(spaceStation)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun getCurrentSpaceStation(name: String?): Flowable<SpaceStationEntity> {
        return if (name != null) stationDatabase.findSpaceStationByNameFlowable(name) else Flowable.empty()
    }

    fun updateCurrentStation(name: String): Completable {
        return spacecraftDatabase.updateCurrentStation(name)
    }
}