package me.akay.uzaydestan.repository

import io.reactivex.Single
import me.akay.uzaydestan.api.ApiService
import me.akay.uzaydestan.datamodels.SpaceStation
import javax.inject.Inject

class SpaceStationNetworkStore @Inject constructor(
    private val api: ApiService
) {
    fun getStationList(path: String): Single<List<SpaceStation>> {
        return api.getSpaceStations(path)
    }
}