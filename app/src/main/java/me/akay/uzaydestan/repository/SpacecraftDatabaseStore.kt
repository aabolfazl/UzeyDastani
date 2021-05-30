package me.akay.uzaydestan.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
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

    fun update(spacecraftEntity: SpacecraftEntity): Completable = Completable.defer {
        dao.update(spacecraftEntity)
        return@defer Completable.complete()
    }

    fun updateCurrentStation(name: String): Completable = Completable.defer {
        val spacecraft = dao.getSpacecraft()
        spacecraft.currentStation = name
        return@defer update(spacecraft)
    }

    fun getCurrentSpacecraft(): Maybe<SpacecraftEntity> {
        return dao.getSpacecraftMaybe()
    }

    fun getCurrentSpacecraftFlowable(): Flowable<SpacecraftEntity> {
        return dao.getSpacecraftFlowable()
    }

    fun deleteAll(): Completable = Completable.defer {
        dao.deleteAll()
        return@defer Completable.complete()
    }
}