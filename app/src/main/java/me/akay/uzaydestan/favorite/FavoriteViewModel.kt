package me.akay.uzaydestan.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import me.akay.uzaydestan.data.SpaceStationEntity
import me.akay.uzaydestan.helper.Resource
import me.akay.uzaydestan.repository.ApplicationRepository
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val repository: ApplicationRepository) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val favoriteLiveData: MutableLiveData<Resource<List<SpaceStationEntity>>> by lazy {
        MutableLiveData<Resource<List<SpaceStationEntity>>>()
    }

    init {
        compositeDisposable.add(repository.loadFavoriteSpaceStationList(favoriteLiveData))
    }

    fun didChangeFav(station: SpaceStationEntity) {
        repository.toggleFavorite(station)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}