package me.akay.uzaydestan.stations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import me.akay.uzaydestan.data.SpaceStationEntity
import me.akay.uzaydestan.data.Spacecraft
import me.akay.uzaydestan.helper.Resource
import me.akay.uzaydestan.repository.ApplicationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StationViewModel @Inject constructor(repository: ApplicationRepository) :
    ViewModel() {
    private val TAG = "MainViewModel"

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val spaceStations: MutableLiveData<Resource<List<SpaceStationEntity>>> by lazy {
        MutableLiveData<Resource<List<SpaceStationEntity>>>()
    }

    val spacecraftLiveData: MutableLiveData<Spacecraft> by lazy {
        MutableLiveData<Spacecraft>()
    }

    init {
        spacecraftLiveData.value = repository.currentSpaceCraft
        compositeDisposable.add(repository.loadStationList(spaceStations))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}