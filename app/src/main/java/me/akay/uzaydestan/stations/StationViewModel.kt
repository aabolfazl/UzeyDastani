package me.akay.uzaydestan.stations

import android.os.CountDownTimer
import android.util.Log
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
    val timerLiveData: MutableLiveData<Long> = MutableLiveData()

    private val timer: CountDownTimer

    init {
        spacecraftEntityLiveData.value = repository.currentSpaceCraft
        addDisposable(repository.loadCurrentStation(currentSpaceStations))
        getStationsList()

        timer = object : CountDownTimer(repository.currentSpaceCraft?.getEUS()!!.toLong() * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i(TAG, "onTick: " + millisUntilFinished / 1000)
                timerLiveData.postValue(millisUntilFinished / 1000)
            }

            override fun onFinish() {
                Log.i(TAG, "onFinish: ")
            }
        }
        timer.start()
    }

    fun didChangeFav(station: SpaceStationEntity) {
        addDisposable(repository.toggleFavorite(station))
    }

    fun travelToStation(station: SpaceStationEntity) {
        if (station.name.equals(repository.currentSpaceCraft?.currentStation, true)) {
            Log.e(TAG, "travelToStation: current error")
            return
        }

        if (station.missionComplete) {
            Log.e(TAG, "travelToStation: mission complete")
            return
        }

        repository.updateCurrentStation(station.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getStationsList() {
        addDisposable(repository.loadStationList(spaceStations))
    }

}