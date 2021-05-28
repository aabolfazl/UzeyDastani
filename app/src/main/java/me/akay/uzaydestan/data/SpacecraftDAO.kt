package me.akay.uzaydestan.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface SpacecraftDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(spacecraft: Spacecraft): Completable

    @Query("SELECT * FROM spacecraft")
    fun getSpacecraft(): Flowable<List<Spacecraft>>
}