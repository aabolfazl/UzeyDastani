package me.akay.uzaydestan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "space_station")
class SpaceStationEntity(
        @PrimaryKey
        val name: String,
        val coordinateX: Float,
        val coordinateY: Float,
        val capacity: Int,
        val stock: Int,
        val need: Int,
        val missionComplete: Boolean,
        val isFavorite: Boolean
)