package me.akay.uzaydestan.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import me.akay.uzaydestan.datamodels.SpaceStation
import me.akay.uzaydestan.helper.AndroidUtils
import me.akay.uzaydestan.helper.Point

@Entity(tableName = "space_station")
class SpaceStationEntity(
    @PrimaryKey
    var name: String,
    var coordinateX: Float,
    var coordinateY: Float,
    var capacity: Int,
    var stock: Int,
    var need: Int,
    var status: Int = MissionStatus.UN_COMPLETE.ordinal,
    var isFavorite: Boolean = false
) {
    @Ignore
    var distanceToCurrent: Float = 0f

    @Ignore
    var isCurrent: Boolean = false

    constructor(model: SpaceStation) : this(
        model.name, model.coordinateX, model.coordinateY, model.capacity,
        model.stock, model.need
    )

    fun isEarth(): Boolean =
        capacity == 0 && coordinateX == 0.0f && coordinateY == 0.0f && stock == 0 && need == 0

    fun calculateStationDistance(current: SpaceStationEntity): Float {
        distanceToCurrent = AndroidUtils.calculateDistance(
            Point(current.coordinateX.toDouble(), current.coordinateY.toDouble()),
            Point(coordinateX.toDouble(), coordinateY.toDouble())
        ).toFloat()

        isCurrent = current.name.equals(name, true)

        return distanceToCurrent
    }
}
