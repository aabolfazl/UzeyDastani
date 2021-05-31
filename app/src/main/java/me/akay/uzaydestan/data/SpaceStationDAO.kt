package me.akay.uzaydestan.data

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface SpaceStationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(stations: List<SpaceStationEntity>)

    @Update
    fun update(stations: List<SpaceStationEntity>)

    @Query("SELECT name FROM space_station WHERE isFavorite")
    fun getFavoriteStationName(): List<String>

    @Query("SELECT * FROM space_station")
    fun getSpaceStationListFlowable(): Flowable<List<SpaceStationEntity>>

    @Query("SELECT * FROM space_station LIMIT 1")
    fun findFirstSpaceStationMaybe(): Maybe<SpaceStationEntity?>

    @Update
    fun updateStation(spaceStationEntity: SpaceStationEntity)

    @Query("SELECT * FROM space_station WHERE isFavorite")
    fun getFavoriteSpaceStationListFlowable(): Flowable<List<SpaceStationEntity>>

    @Query("SELECT * FROM space_station WHERE name == :stationName")
    fun findStationByNameFlowable(stationName: String): Flowable<SpaceStationEntity>

    @Query("SELECT * FROM space_station WHERE name == :stationName")
    fun findStationByName(stationName: String): SpaceStationEntity

    @Query("DELETE FROM space_station")
    fun deleteAll()

}