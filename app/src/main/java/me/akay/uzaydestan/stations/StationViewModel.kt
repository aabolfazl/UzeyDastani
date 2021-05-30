package me.akay.uzaydestan.stations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.akay.uzaydestan.data.SpaceStationEntity
import me.akay.uzaydestan.data.SpacecraftEntity
import me.akay.uzaydestan.helper.Resource
import me.akay.uzaydestan.repository.ApplicationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StationViewModel @Inject constructor(private val repository: ApplicationRepository) :
    ViewModel() {
    private val TAG = "MainViewModel"

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val spaceStations: MutableLiveData<Resource<List<SpaceStationEntity>>> by lazy {
        MutableLiveData<Resource<List<SpaceStationEntity>>>()
    }

    val currentSpaceStations: MutableLiveData<Resource<SpaceStationEntity>> by lazy {
        MutableLiveData<Resource<SpaceStationEntity>>()
    }

    val spacecraftEntityLiveData: MutableLiveData<SpacecraftEntity> by lazy {
        MutableLiveData<SpacecraftEntity>()
    }

    init {
        spacecraftEntityLiveData.value = repository.currentSpaceCraft
        compositeDisposable.add(repository.loadStationList(spaceStations))

        repository.loadCurrentStation(currentSpaceStations)

    }

    fun didChangeFav(station: SpaceStationEntity) {
        compositeDisposable.add(repository.toggleFavorite(station))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun travelToStation(station: SpaceStationEntity) {
        repository.updateCurrentStation(station.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

}