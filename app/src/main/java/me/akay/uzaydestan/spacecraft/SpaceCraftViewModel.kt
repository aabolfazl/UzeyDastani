package me.akay.uzaydestan.spacecraft

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import me.akay.uzaydestan.dependencyInjection.BaseViewModel
import me.akay.uzaydestan.helper.Resource
import me.akay.uzaydestan.repository.ApplicationRepository
import javax.inject.Inject

class SpaceCraftViewModel @Inject constructor(private val repository: ApplicationRepository) : BaseViewModel() {
    private val TAG = "SpaceCraftViewModel"

    val saveSpace = MediatorLiveData<Resource<Boolean?>>()

    fun currentScore(): Int = speed + capacity + durability

    var durability: Int = 5
        set(value) {
            field = value
            checkScoreNum()
        }

    var speed: Int = 5
        set(value) {
            field = value
            checkScoreNum()
        }

    var capacity: Int = 5
        set(value) {
            field = value
            checkScoreNum()
        }

    val scoreLiveData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(currentScore())
    }

    private fun checkScoreNum() {
        scoreLiveData.value = currentScore()
    }

    fun saveSpaceCraft(name: String) {
        addDisposable(repository.saveSpacecraft(name, durability, speed, capacity, saveSpace))
    }
}