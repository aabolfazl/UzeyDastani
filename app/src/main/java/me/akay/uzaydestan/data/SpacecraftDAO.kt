package me.akay.uzaydestan.data

import androidx.room.*
import io.reactivex.Maybe

@Dao
interface SpacecraftDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(spacecraftEntity: SpacecraftEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(spacecraftEntity: SpacecraftEntity)

    @Query("SELECT * FROM spacecraft LIMIT 1")
    fun getSpacecraftMaybe(): Maybe<SpacecraftEntity>

    @Query("SELECT * FROM spacecraft LIMIT 1")
    fun getSpacecraft(): SpacecraftEntity

    @Query("DELETE FROM spacecraft")
    fun deleteAll()
}