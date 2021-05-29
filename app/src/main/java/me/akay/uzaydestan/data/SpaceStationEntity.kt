package me.akay.uzaydestan.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.akay.uzaydestan.datamodels.SpaceStation

@Entity(tableName = "space_station")
class SpaceStationEntity(
        @PrimaryKey
        val name: String,
        val coordinateX: Float,
        val coordinateY: Float,
        var capacity: Int,
        val stock: Int,
        val need: Int,
        val missionComplete: Boolean,
        val isFavorite: Boolean
) {
        constructor(model: SpaceStation) : this(
                model.name, model.coordinateX, model.coordinateY, model.capacity,
                model.stock, model.need, false, false
        )
}