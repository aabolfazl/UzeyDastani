package me.akay.uzaydestan.stations

import androidx.lifecycle.MutableLiveData
import me.akay.uzaydestan.data.MissionStatus
import me.akay.uzaydestan.data.SpaceCraftStatus
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
    val spacecraftEntityLiveData: MutableLiveData<Resource<SpacecraftEntity>> = MutableLiveData()
    val timerLiveData: MutableLiveData<Long> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        addDisposable(repository.loadSpaceCraft(spacecraftEntityLiveData))
        addDisposable(repository.loadCurrentStation(currentSpaceStations))
        getStationsList()
    }

    fun didChangeFav(station: SpaceStationEntity) {
        addDisposable(repository.toggleFavorite(station))
    }

    fun travelToStation(destStation: SpaceStationEntity) {
        val spacecraft = repository.getCurrentSpacecraft()
        if (destStation.name.equals(spacecraft?.currentStation, true)) {
            errorLiveData.value = "travelToStation: current error"
            return
        }

        if (destStation.status == MissionStatus.COMPLETED.ordinal) {
            errorLiveData.value = "travelToStation: mission complete"
            return
        }

        if (destStation.status == MissionStatus.IN_PROGRESS.ordinal) {
            errorLiveData.value = "travelToStation: mission in progress"
            return
        }

        if (spacecraft?.status != SpaceCraftStatus.IDLE.ordinal) {
            errorLiveData.value = "travelToStation: spacecraft in mission"
            return
        }

        if (spacecraft.UGS < destStation.need) {
            errorLiveData.value = "travelToStation: cannot full station"
            return
        }

        addDisposable(repository.startTravelToDest(destStation))
    }

    fun getStationsList() {
        addDisposable(repository.loadStationList(spaceStations))
    }

}