package me.akay.uzaydestan.stations

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.akay.uzaydestan.data.Spacecraft
import me.akay.uzaydestan.datamodels.SpaceStation
import me.akay.uzaydestan.repository.ApplicationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StationViewModel @Inject constructor(private val repository: ApplicationRepository) : ViewModel() {
    private val TAG = "MainViewModel"

    val spaceStations: MutableLiveData<List<SpaceStation>> by lazy {
        MutableLiveData<List<SpaceStation>>()
    }

    val spacecraftLiveData: MutableLiveData<Spacecraft> by lazy {
        MutableLiveData<Spacecraft>()
    }

    init {
        spacecraftLiveData.value = repository.currentSpaceCraft
    }

    fun loadSpaceStationList() {
        Log.i(TAG, "load: ")
        repository.loadData()

        val listOf = listOf(
            SpaceStation("Abolfazl", 4f, 6f, 500, 5222, 52),
            SpaceStation("Abbasi", 4f, 6f, 605, 5222, 52),
            SpaceStation("Dar", 4f, 6f, 23423, 5222, 52),
            SpaceStation("Akay", 4f, 6f, 463, 5222, 52),
            SpaceStation("Bekliyorom", 4f, 6f, 1247, 5222, 52),
            SpaceStation("Soyle", 4f, 6f, 57446, 5222, 52),
        )

        spaceStations.value = listOf
    }

    override fun onCleared() {
        super.onCleared()
    }

}