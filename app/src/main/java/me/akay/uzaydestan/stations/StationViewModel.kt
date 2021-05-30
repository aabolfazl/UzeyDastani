package me.akay.uzaydestan.stations

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.akay.uzaydestan.data.SpaceStationEntity
import me.akay.uzaydestan.data.SpacecraftEntity
import me.akay.uzaydestan.dependencyInjection.BaseViewModel
import me.akay.uzaydestan.helper.Resource
import me.akay.uzaydestan.repository.ApplicationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StationViewModel @Inject constructor(private val repository: ApplicationRepository) : BaseViewModel() {
    private val TAG = "MainViewModel"

    val spaceStations: MutableLiveData<Resource<List<SpaceStationEntity>>> = MutableLiveData()
    val currentSpaceStations: MutableLiveData<Resource<SpaceStationEntity>> = MutableLiveData()
    val spacecraftEntityLiveData: MutableLiveData<SpacecraftEntity> = MutableLiveData()

    init {
        spacecraftEntityLiveData.value = repository.currentSpaceCraft
        addDisposable(repository.loadCurrentStation(currentSpaceStations))
        getStationsList()
    }

    fun didChangeFav(station: SpaceStationEntity) {
        addDisposable(repository.toggleFavorite(station))
    }

    fun travelToStation(station: SpaceStationEntity) {
        repository.updateCurrentStation(station.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getStationsList() {
        addDisposable(repository.loadStationList(spaceStations))
    }

}