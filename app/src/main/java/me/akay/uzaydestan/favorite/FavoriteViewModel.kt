package me.akay.uzaydestan.favorite

import androidx.lifecycle.MutableLiveData
import me.akay.uzaydestan.data.SpaceStationEntity
import me.akay.uzaydestan.dependencyInjection.BaseViewModel
import me.akay.uzaydestan.helper.Resource
import me.akay.uzaydestan.repository.ApplicationRepository
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val repository: ApplicationRepository) : BaseViewModel() {

    val favoriteLiveData: MutableLiveData<Resource<List<SpaceStationEntity>>> by lazy {
        MutableLiveData<Resource<List<SpaceStationEntity>>>()
    }

    init {
        addDisposable(repository.loadFavoriteSpaceStationList(favoriteLiveData))
    }

    fun didChangeFav(station: SpaceStationEntity) {
        repository.toggleFavorite(station)
    }

}