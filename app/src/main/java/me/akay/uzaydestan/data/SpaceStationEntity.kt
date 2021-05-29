package me.akay.uzaydestan.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.akay.uzaydestan.datamodels.SpaceStation

@Entity(tableName = "space_station")
class SpaceStationEntity(
    @PrimaryKey
    var name: String,
    var coordinateX: Float,
    var coordinateY: Float,
    var capacity: Int,
    var stock: Int,
    var need: Int,
    var missionComplete: Boolean = false,
    var isFavorite: Boolean = false
) {
    constructor(model: SpaceStation) : this(
        model.name, model.coordinateX, model.coordinateY, model.capacity,
        model.stock, model.need
    )
}