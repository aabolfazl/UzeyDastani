package me.akay.uzaydestan.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface SpaceStationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stations: List<SpaceStationEntity>): Completable

    @Query("SELECT * FROM space_station")
    fun getSpaceStationList(): Flowable<List<SpaceStationEntity>>
}