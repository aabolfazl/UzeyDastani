package me.akay.uzaydestan.data

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface SpaceStationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stations: List<SpaceStationEntity>)

    @Update
    fun update(stations: List<SpaceStationEntity>)

    @Query("SELECT name FROM space_station WHERE isFavorite")
    fun getFavoriteStationName(): List<String>

    @Query("SELECT * FROM space_station")
    fun getSpaceStationListFlowable(): Flowable<List<SpaceStationEntity>>

    @Update
    fun updateStation(spaceStationEntity: SpaceStationEntity)

    @Query("SELECT * FROM space_station WHERE isFavorite")
    fun getFavoriteSpaceStationList(): Flowable<List<SpaceStationEntity>>

}