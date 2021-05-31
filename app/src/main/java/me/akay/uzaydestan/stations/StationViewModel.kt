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
        addDisposable(repository.startDsTimer(timerLiveData))
        getStationsList()
    }

    fun didChangeFav(station: SpaceStationEntity) {
        addDisposable(repository.toggleFavorite(station))
    }

    fun travelToStation(destStation: SpaceStationEntity) {
        val spacecraft = repository.getCurrentSpacecraft() ?: return

        when {
            destStation.name.equals(spacecraft.currentStation, true) -> {
                errorLiveData.value = "current error"
                return
            }
            destStation.status == MissionStatus.COMPLETED.ordinal -> {
                errorLiveData.value = "mission complete"
                return
            }
            destStation.status == MissionStatus.IN_PROGRESS.ordinal -> {
                errorLiveData.value = "mission in progress"
                return
            }
            spacecraft.status != SpaceCraftStatus.IDLE.ordinal -> {
                errorLiveData.value = "spacecraft in mission"
                return
            }
            spacecraft.canTravel().not() -> {
                errorLiveData.value = "Ds error"
                return
            }
            spacecraft.UGS < destStation.need -> {
                errorLiveData.value = "UGS error"
                return
            }
            else -> addDisposable(repository.startTravelToDest(destStation))
        }
    }

    fun getStationsList() {
        addDisposable(repository.loadStationList(spaceStations))
    }

}