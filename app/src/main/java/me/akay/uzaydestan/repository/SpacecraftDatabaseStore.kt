package me.akay.uzaydestan.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import me.akay.uzaydestan.data.SpaceCraftStatus
import me.akay.uzaydestan.data.SpacecraftDAO
import me.akay.uzaydestan.data.SpacecraftEntity
import javax.inject.Inject

class SpacecraftDatabaseStore @Inject constructor(
    private val dao: SpacecraftDAO
) {

    fun insert(spacecraftEntity: SpacecraftEntity): Completable = Completable.defer {
        dao.insert(spacecraftEntity)
        return@defer Completable.complete()
    }

    private fun update(spacecraftEntity: SpacecraftEntity): Completable = Completable.defer {
        dao.update(spacecraftEntity)
        return@defer Completable.complete()
    }

    fun updateCurrentStation(name: String): Completable = Completable.defer {
        val spacecraft = getCurrentSpacecraft()
        spacecraft.currentStation = name
        return@defer update(spacecraft)
    }

    fun getCurrentSpacecraftMaybe(): Maybe<SpacecraftEntity> {
        return dao.getSpacecraftMaybe()
    }


    private fun getCurrentSpacecraft(): SpacecraftEntity {
        return dao.getSpacecraft()
    }

    fun getCurrentSpacecraftFlowable(): Flowable<SpacecraftEntity> {
        return dao.getSpacecraftFlowable()
    }

    fun deleteAll(): Completable = Completable.defer {
        dao.deleteAll()
        return@defer Completable.complete()
    }

    fun updateEUS(eusInInterval: Float): Single<SpacecraftEntity> =
        Single.defer {
            val spacecraft = getCurrentSpacecraft()
            spacecraft.EUS = spacecraft.EUS - eusInInterval
            dao.update(spacecraft)
            return@defer Single.just(spacecraft)
        }

    fun setMissionStatus(inMission: SpaceCraftStatus): Single<SpacecraftEntity> =
        Single.defer {
            val spacecraft = getCurrentSpacecraft()
            spacecraft.status = inMission.ordinal
            dao.update(spacecraft)
            return@defer Single.just(spacecraft)
        }

    fun setMissionStatus2(inMission: SpaceCraftStatus): Completable = Completable.defer {
        val spacecraft = getCurrentSpacecraft()
        spacecraft.status = inMission.ordinal
        dao.update(spacecraft)
        return@defer Completable.complete()
    }

    fun updateUGS(UGS: Int, toIdle: Boolean = false): Completable = Completable.defer {
        val spacecraft = getCurrentSpacecraft()
        spacecraft.UGS -= UGS
        spacecraft.status = if (toIdle) SpaceCraftStatus.IDLE.ordinal else spacecraft.status
        dao.update(spacecraft)
        return@defer Completable.complete()
    }
}