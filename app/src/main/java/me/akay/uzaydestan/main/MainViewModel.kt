package me.akay.uzaydestan.main

import android.util.Log
import androidx.lifecycle.ViewModel
import me.akay.uzaydestan.repository.SpaceStationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModel @Inject constructor(private val repository: SpaceStationRepository) : ViewModel() {
    private val TAG = "MainViewModel"

    fun load() {
        Log.i(TAG, "load: ")
        repository.loadData()
    }

    override fun onCleared() {
        super.onCleared()
    }

}