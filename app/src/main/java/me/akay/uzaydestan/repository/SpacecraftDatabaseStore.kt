package me.akay.uzaydestan.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import me.akay.uzaydestan.data.Spacecraft
import me.akay.uzaydestan.data.SpacecraftDAO
import javax.inject.Inject

class SpacecraftDatabaseStore @Inject constructor(
        private val dao: SpacecraftDAO
) {

    fun insert(spacecraft: Spacecraft): Completable = Completable.defer {
        dao.insert(spacecraft)
        return@defer Completable.complete()
    }

    fun getCurrentSpacecraft(): Maybe<Spacecraft> {
        return dao.getSpacecraft()
    }

    fun deleteAll(): Completable = Completable.defer {
        dao.deleteAll()
        return@defer Completable.complete()
    }
}