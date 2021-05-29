package me.akay.uzaydestan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spacecraft")
data class Spacecraft(
        @PrimaryKey
        val name:
        String, val durability:
        Int, val speed: Int,
        val capacity: Int,
        val damage: Int)
