package me.akay.uzaydestan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spacecraft")
data class SpacecraftEntity(
    @PrimaryKey
    val name: String,
    val durability: Int,
    val speed: Int,
    val capacity: Int,
    val damage: Int,
    var currentStation: String? = null // must use server side stations Unique identifier
)
