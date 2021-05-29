package me.akay.uzaydestan.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Maybe

@Dao
interface SpacecraftDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(spacecraft: Spacecraft)

    @Query("SELECT * FROM spacecraft LIMIT 1")
    fun getSpacecraft(): Maybe<Spacecraft>

    @Query("DELETE FROM spacecraft")
    fun deleteAll()
}