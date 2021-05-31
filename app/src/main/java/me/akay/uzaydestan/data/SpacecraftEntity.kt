package me.akay.uzaydestan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spacecraft")
data class SpacecraftEntity(
    val name: String,
    var durability: Int,
    var speed: Int,
    var capacity: Int,
    var damage: Int,
    var currentStation: String? = null, // must use server side stations Unique identifier
    var status: Int = SpaceCraftStatus.IDLE.ordinal,
    var EUS: Float = speed.toFloat() * 20,
    var UGS: Int = capacity * 10000,
    var DS: Int = durability * 10000
) {
    @PrimaryKey
    var id: Long = 0
}
