package me.akay.uzaydestan.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import me.akay.uzaydestan.data.SpaceStationDAO
import me.akay.uzaydestan.data.SpaceStationEntity
import javax.inject.Inject

class SpaceStationDatabaseStore @Inject constructor(
    private val dao: SpaceStationDAO
) {

    fun insertOrUpdate(stations: List<SpaceStationEntity>): Completable = Completable.defer {
        val spaceStationList = dao.getFavoriteStationName()

        for (station in stations) {
            station.isFavorite = spaceStationList.contains(station.name)
        }

        dao.insert(stations)
        return@defer Completable.complete()
    }

    fun getSpaceStationList(): Flowable<List<SpaceStationEntity>> {
        return dao.getSpaceStationListFlowable()
    }

    fun updateSpaceStation(spaceStation: SpaceStationEntity): Completable = Completable.defer {
        dao.updateStation(spaceStation)
        return@defer Completable.complete()
    }

    fun update(spaceStation: List<SpaceStationEntity>): Completable = Completable.defer {
        dao.update(spaceStation)
        return@defer Completable.complete()
    }

    fun getFavoriteSpaceStationList(): Flowable<List<SpaceStationEntity>> {
        return dao.getFavoriteSpaceStationList()
    }
}