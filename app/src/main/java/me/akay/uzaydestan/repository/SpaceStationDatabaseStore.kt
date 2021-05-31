package me.akay.uzaydestan.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import me.akay.uzaydestan.data.MissionStatus
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

    fun findFirstStationMaybe(): Maybe<SpaceStationEntity?> {
        return dao.findFirstSpaceStationMaybe()
    }

    fun findSpaceStationByNameFlowable(name: String): Flowable<SpaceStationEntity> {
        return dao.findStationByNameFlowable(name)
    }

    fun findSpaceStationByName(name: String): SpaceStationEntity {
        return dao.findStationByName(name)
    }

    fun updateSpaceStation(spaceStation: SpaceStationEntity): Completable = Completable.defer {
        dao.updateStation(spaceStation)
        return@defer Completable.complete()
    }

    fun update(spaceStation: List<SpaceStationEntity>): Completable = Completable.defer {
        dao.update(spaceStation)
        return@defer Completable.complete()
    }

    fun missionComplete(destStation: SpaceStationEntity): Completable = Completable.defer {
        destStation.status = MissionStatus.COMPLETED.ordinal
        destStation.need = 0
        destStation.stock = destStation.capacity
        dao.updateStation(destStation)
        return@defer Completable.complete()
    }

    fun getFavoriteSpaceStationList(): Flowable<List<SpaceStationEntity>> {
        return dao.getFavoriteSpaceStationList()
    }

    fun deleteAll(): Completable = Completable.defer {
        dao.deleteAll()
        return@defer Completable.complete()
    }
}