package me.akay.uzaydestan.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [
    Spacecraft::class,
    SpaceStationEntity::class
], version = 1,
        exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun spacecraftDao(): SpacecraftDAO

    abstract fun spaceStationDAO(): SpaceStationDAO
}