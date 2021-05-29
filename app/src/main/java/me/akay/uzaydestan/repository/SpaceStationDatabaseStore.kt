package me.akay.uzaydestan.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import me.akay.uzaydestan.data.SpaceStationDAO
import me.akay.uzaydestan.data.SpaceStationEntity
import javax.inject.Inject

class SpaceStationDatabaseStore @Inject constructor(
    private val dao: SpaceStationDAO
) {

    fun insert(stations: List<SpaceStationEntity>): Completable = Completable.defer {
        dao.insert(stations)
        return@defer Completable.complete()
    }

    fun getCurrentSpacecraft(): Flowable<List<SpaceStationEntity>> {
        return dao.getSpaceStationList()
    }
}